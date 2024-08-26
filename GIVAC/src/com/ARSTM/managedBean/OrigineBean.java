package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Mode;
import com.ARSTM.model.Origine;
import com.ARSTM.model.Sexe;
import com.ARSTM.model.Statut;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class OrigineBean {
	@Autowired
	Iservice service;
	private Origine origine = new Origine();
	private Origine selectedorigine = new Origine();
	private List listorigine= new ArrayList<>();
	
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(origine);
		actualiserList();
		vider(origine);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(origine);
		actualiserList();
	}
	
	public void vider(Origine objOrig) {
		objOrig.setLibOrigine(null);
	}
	
	public void actualiserList(){
			listorigine.clear();
			listorigine = getService().getObjects("Origine");
		}
	
	public void selectionner(){
		setOrigine(selectedorigine);
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void modifier(){
		getService().updateObject(origine);
		vider(origine);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}
	
	
	public void supprimer() {
		Origine origTemp = new Origine();
		origTemp.setCodeOriginr(selectedorigine.getCodeOriginr());
		origTemp.setLibOrigine(selectedorigine.getLibOrigine());
		getService().deleteObject(origTemp);
		vider(origTemp);
		vider(origine);
		actualiserList();
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
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
	
	public Origine getOrigine() {
		return origine;
	}

	public void setOrigine(Origine origine) {
		this.origine = origine;
	}

	public Origine getSelectedorigine() {
		return selectedorigine;
	}

	public void setSelectedorigine(Origine selectedorigine) {
		this.selectedorigine = selectedorigine;
	}

	public List getListorigine() {
		if (listorigine.isEmpty()) {
			listorigine = getService().getObjects("Origine");
		}
		return listorigine;
	}

	public void setListorigine(List listorigine) {
		this.listorigine = listorigine;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

}
