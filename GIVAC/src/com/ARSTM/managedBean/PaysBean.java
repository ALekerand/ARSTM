package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Pays;
import com.ARSTM.service.Iservice;

@Component
public class PaysBean {
	@Autowired
	Iservice service;
	private Pays pays = new Pays();
	private Pays selectedPays = new Pays();
	private List listPays = new ArrayList<>();
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(pays);
		actualiserList();
		vider(pays);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		vider(pays);
		actualiserList();
	}
	
	public void vider(Pays objPays) {
		objPays.setLibpays(null);
		objPays.setAbrevpays(null);
		objPays.setRepublic(null);
	}
	
	public void actualiserList(){
			listPays.clear();
			listPays = getService().getObjects("Pays");
		}
	
	public void selectionner(){
		setPays(selectedPays);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Pays paystemp = new Pays();
		paystemp = selectedPays;
		getService().deleteObject(paystemp);
		vider(paystemp);
		actualiserList();
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression effcetuée!", null));
	}
	
	
	/**************************ACCESSEURS*************************/
	
	public Iservice getService() {
		return service;
	}

	public void setService(Iservice service) {
		this.service = service;
	}

	public CommandButton getBtnValider() {
		return btnValider;
	}

	public void setBtnValider(CommandButton btnValider) {
		this.btnValider = btnValider;
	}

	public CommandButton getBtnSuprimer() {
		return btnSuprimer;
	}

	public void setBtnSuprimer(CommandButton btnSuprimer) {
		this.btnSuprimer = btnSuprimer;
	}

	public List getListPays() {
		if(listPays.isEmpty()){
			listPays = getService().getObjects("Pays");
		}
		return listPays;
	}

	public void setListPays(List listPays) {
		this.listPays = listPays;
	}

	public Pays getSelectedPays() {
		return selectedPays;
	}

	public void setSelectedPays(Pays selectedPays) {
		this.selectedPays = selectedPays;
	}

	public Pays getPays() {
		return pays;
	}

	public void setPays(Pays pays) {
		this.pays = pays;
	}
}
