package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Santes;
import com.ARSTM.service.Iservice;

@Component
public class santeBean {
	@Autowired
	Iservice service;
	private Santes sante = new Santes();
	private Santes selectedSante = new Santes();
	private List listSante = new ArrayList<>();
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(sante);
		actualiserList();
		vider(sante);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		vider(sante);
		actualiserList();
	}
	
	public void vider(Santes objSante) {
		objSante.setLibsante(null);
	}
	
	public void actualiserList(){
			listSante.clear();
			listSante = getService().getObjects("Santes");
		}
	
	public void selectionner(){
		setSante(selectedSante);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Santes santeTemp = new Santes();
		santeTemp.setCodesante(selectedSante.getCodesante());
		santeTemp.setLibsante(selectedSante.getLibsante());
		
		
		getService().deleteObject(santeTemp);
		vider(sante);
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

	public Santes getSante() {
		return sante;
	}

	public void setSante(Santes sante) {
		this.sante = sante;
	}

	public List getListSante() {
		if(listSante.isEmpty()){
			listSante = getService().getObjects("Santes");
		}
		return listSante;
	}

	public void setListSante(List listSante) {
		this.listSante = listSante;
	}

	public Santes getSelectedSante() {
		return selectedSante;
	}

	public void setSelectedSante(Santes selectedSante) {
		this.selectedSante = selectedSante;
	}
}
