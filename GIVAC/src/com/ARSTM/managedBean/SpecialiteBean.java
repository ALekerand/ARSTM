package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Specialite;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class SpecialiteBean {
	@Autowired
	Iservice service;
	private Specialite specialite = new Specialite();
	private Specialite selectedSpecialite = new Specialite();
	private List listeSpecialite = new ArrayList<>();
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(specialite);
		actualiserList();
		vider(specialite);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(specialite);
		actualiserList();
	}
	
public void modifier(){
		
		getService().updateObject(specialite);
		vider(specialite);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}
	
	public void vider(Specialite objSpecialite) {
		objSpecialite.setLibelleSpecial(null);
	}
	
	public void actualiserList(){
		listeSpecialite.clear();
		listeSpecialite = getService().getObjects("Specialite");
		}
	
	public void selectionner(){
		setSpecialite(selectedSpecialite);
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Specialite specialiteTemp = new Specialite();
		specialiteTemp.setCodeSpecial(selectedSpecialite.getCodeSpecial());
		specialiteTemp.setLibelleSpecial(selectedSpecialite.getLibelleSpecial());
		getService().deleteObject(specialiteTemp);
		vider(specialiteTemp);
		vider(specialite);
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

	public Specialite getSpecialite() {
		return specialite;
	}

	public void setSpecialite(Specialite specialite) {
		this.specialite = specialite;
	}

	public Specialite getSelectedSpecialite() {
		return selectedSpecialite;
	}

	public void setSelectedSpecialite(Specialite selectedSpecialite) {
		this.selectedSpecialite = selectedSpecialite;
	}

	public List getListeSpecialite() {
		if (listeSpecialite.isEmpty()) {
			listeSpecialite = getService().getObjects("Specialite");
		}
		return listeSpecialite;
	}

	public void setListeSpecialite(List listeSpecialite) {
		this.listeSpecialite = listeSpecialite;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}
}
