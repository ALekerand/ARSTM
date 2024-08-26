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
import com.ARSTM.model.Fonction;
import com.ARSTM.model.Specialite;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class FonctionBean {
	@Autowired
	Iservice service;
	
	private Fonction fonction = new Fonction();
	private Fonction selectedFonction = new Fonction();
	private List listFonction = new ArrayList<>();
	
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(fonction);
		actualiserList();
		vider(fonction);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(fonction);
		actualiserList();
	}
	
public void modifier(){
		
		getService().updateObject(fonction);
		vider(fonction);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}
	
	public void vider(Fonction object) {
		object.setLibelleFonction(null);
	}
	
	public void actualiserList(){
		listFonction.clear();
		listFonction = getService().getObjects("Fonction");
		}
	
	public void selectionner(){
		setFonction(selectedFonction);
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Fonction objTemp = new Fonction();
		objTemp.setCodeFonction(selectedFonction.getCodeFonction());
		objTemp.setLibelleFonction(selectedFonction.getLibelleFonction());
		getService().deleteObject(objTemp);
		vider(fonction);
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

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

	public Fonction getFonction() {
		return fonction;
	}

	public void setFonction(Fonction fonction) {
		this.fonction = fonction;
	}

	public Fonction getSelectedFonction() {
		return selectedFonction;
	}

	public void setSelectedFonction(Fonction selectedFonction) {
		this.selectedFonction = selectedFonction;
	}

	public List getListFonction() {
		if (listFonction.isEmpty()) {
			listFonction = getService().getObjects("Fonction");
		}
		return listFonction;
	}

	public void setListFonction(List listFonction) {
		this.listFonction = listFonction;
	}
}
