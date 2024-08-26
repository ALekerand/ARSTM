package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Typeue;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class TypeUeBean {
	@Autowired
	Iservice service;
	private Typeue typeue = new Typeue();
	private Typeue selectedTypeue = new Typeue();
	private List listTypeue = new ArrayList<>();
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	@PostConstruct
public void initialiser(){
	btnSuprimer.setDisabled(true);
	btnModifier.setDisabled(true);
	
}
		
	public void enregistrer(){
		typeue.setLibTypeUe(getTypeue().getLibTypeUe().toUpperCase());
		getService().addObject(typeue);
		actualiserList();
		vider(typeue);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}
	
	public void modifier(){
		getService().updateObject(typeue);
		vider(typeue);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(typeue);
		actualiserList();
	}
	
	public void vider(Typeue objTypeue) {
		objTypeue.setLibTypeUe(null);
	}
	
	public void actualiserList(){
			listTypeue.clear();
			listTypeue = getService().getObjects("Typeue");
		}
	
	public void selectionner(){
		setTypeue(selectedTypeue);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
		btnModifier.setDisabled(false);
	}
	
	
	public void supprimer() {
		Typeue typeueTemp = new Typeue();
		typeueTemp.setCodeTypeUe(selectedTypeue.getCodeTypeUe());
		typeueTemp.setLibTypeUe(selectedTypeue.getLibTypeUe());
		vider(typeueTemp);
		vider(typeue);
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

	public Typeue getSelectedTypeue() {
		return selectedTypeue;
	}

	public void setSelectedTypeue(Typeue selectedTypeue) {
		this.selectedTypeue = selectedTypeue;
	}

	public List getListTypeue() {
		if (listTypeue.isEmpty()) {
			listTypeue = getService().getObjects("Typeue");
		}
		return listTypeue;
	}

	public void setListTypeue(List listTypeue) {
		this.listTypeue = listTypeue;
	}

	public Typeue getTypeue() {
		return typeue;
	}

	public void setTypeue(Typeue typeue) {
		this.typeue = typeue;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}
}
