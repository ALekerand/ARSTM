package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Cycle;
import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Matiere;
import com.ARSTM.model.TypeLogement;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class typeLogementBean {
	@Autowired
	Iservice service;
	
	private TypeLogement typelogement = new TypeLogement();
	private TypeLogement selectedTypeLogement = new TypeLogement();
	private List listeTypeLogement = new ArrayList<>();
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(typelogement);
		actualiserList();
		vider(typelogement);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(typelogement);
		actualiserList();
	}
	
	public void vider(TypeLogement objTypeLogement) {
		  objTypeLogement.setLibtypeLogement(null);
		 
	}
	
	public void actualiserList(){
		listeTypeLogement.clear();
		listeTypeLogement = getService().getObjects("TypeLogement");
		}
	
	
	public void selectionner(){
		setTypelogement(selectedTypeLogement);
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Cycle cycleTemp = new Cycle();
		TypeLogement typeLogementTemp = new TypeLogement();
		typeLogementTemp.setCodetypeLogement(selectedTypeLogement.getCodetypeLogement());
		typeLogementTemp.setLibtypeLogement(selectedTypeLogement.getLibtypeLogement());
		
		getService().deleteObject(selectedTypeLogement);
		vider(selectedTypeLogement);
		actualiserList();
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression effcetuée!", null));
	}
	
	public void modifier(){
		getService().updateObject(typelogement);
		vider(typelogement);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
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

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

	public TypeLogement getTypelogement() {
		return typelogement;
	}

	public void setTypelogement(TypeLogement typelogement) {
		this.typelogement = typelogement;
	}

	public TypeLogement getSelectedTypeLogement() {
		return selectedTypeLogement;
	}

	public void setSelectedTypeLogement(TypeLogement selectedTypeLogement) {
		this.selectedTypeLogement = selectedTypeLogement;
	}

	public List getListeTypeLogement() {
		if (listeTypeLogement.isEmpty()) {
			listeTypeLogement = getService().getObjects("TypeLogement");
		}
		return listeTypeLogement;
	}

	public void setListeTypeLogement(List listeTypeLogement) {
		this.listeTypeLogement = listeTypeLogement;
	}

}
