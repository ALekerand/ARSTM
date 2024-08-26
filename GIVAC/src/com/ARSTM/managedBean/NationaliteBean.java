package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Nationalites;
import com.ARSTM.service.Iservice;

@Component
public class NationaliteBean {
	@Autowired
	Iservice service;
	private Nationalites nationalites = new Nationalites();
	private Nationalites selectedNatlite = new Nationalites();
	private List<Nationalites> listNationalite = new ArrayList<>();	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(nationalites);
		actualiserList();
		vider(nationalites);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		vider(nationalites);
		actualiserList();
	}
	
	public void vider(Nationalites obNation) {
		obNation.setLibnationalite(null);
	}
	
	public void actualiserList(){
		listNationalite.clear();
		listNationalite = getService().getObjects("Nationalites");
		}
	
	public void selectionnerNationalite(){
		setNationalites(selectedNatlite);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Nationalites nationTemp = new Nationalites();
		nationTemp.setCodenationalite(selectedNatlite.getCodenationalite());
		nationTemp.setLibnationalite(selectedNatlite.getLibnationalite());
	//	nationTemp = selectedNatlite;
		getService().deleteObject(nationTemp);
		vider(nationalites);
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

	public Nationalites getNationalites() {
		return nationalites;
	}

	public void setNationalites(Nationalites nationalites) {
		this.nationalites = nationalites;
	}

	public Nationalites getSelectedNatlite() {
		return selectedNatlite;
	}

	public void setSelectedNatlite(Nationalites selectedNatlite) {
		this.selectedNatlite = selectedNatlite;
	}

	@SuppressWarnings("unchecked")
	public List getListNationalite() {
		if(listNationalite.isEmpty()){
			listNationalite = getService().getObjects("Nationalites");
		}
		return listNationalite;
	}

	public void setListNationalite(List listNationalite) {
		this.listNationalite = listNationalite;
	}

}
