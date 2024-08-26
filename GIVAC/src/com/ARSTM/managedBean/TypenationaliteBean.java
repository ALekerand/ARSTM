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

import com.ARSTM.model.Domaine;
import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Typenationalite;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class TypenationaliteBean {
	@Autowired
	Iservice service;
	private Typenationalite typenationalite = new Typenationalite();
	private Typenationalite selectedTypenationalite = new Typenationalite();
	private List listTypenationalite =new ArrayList<>();
				
	// Contrôle de composant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	@PostConstruct
public void initialiser(){
	//btnValider.setDisabled(false);
	btnSuprimer.setDisabled(true);
	btnModifier.setDisabled(true);
	
}
		
	public void enregistrer(){
		typenationalite.setLibTypenationalite(getTypenationalite().getLibTypenationalite().toUpperCase());
		getService().addObject(typenationalite);
		actualiserList();
		vider(typenationalite);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}
	
	public void modifier(){
		getService().updateObject(typenationalite);
		vider(typenationalite);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(typenationalite);
		actualiserList();
	}
	
	public void vider(Typenationalite objTypenationalite) {
		objTypenationalite.setLibTypenationalite(null);
	}
	
	public void actualiserList(){
			listTypenationalite.clear();
			listTypenationalite = getService().getObjects("Typenationalite");
		}
	
	public void selectionner(){
		setTypenationalite(selectedTypenationalite);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
		btnModifier.setDisabled(false);
	}
	
	
	public void supprimer() {
		//Domaine domaineTemp = new Domaine();
		Typenationalite typenationaliteTemp = new Typenationalite();
		typenationaliteTemp.setCodeTypenationalite(selectedTypenationalite.getCodeTypenationalite());
		typenationaliteTemp.setLibTypenationalite(selectedTypenationalite.getLibTypenationalite());
		getService().deleteObject(typenationaliteTemp);
		vider(typenationaliteTemp);
		vider(typenationalite);
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
	
	public Typenationalite getTypenationalite() {
		return typenationalite;
	}

	public void setTypenationalite(Typenationalite typenationalite) {
		this.typenationalite = typenationalite;
	}

	public Typenationalite getSelectedTypenationalite() {
		return selectedTypenationalite;
	}

	public void setSelectedTypenationalite(Typenationalite selectedTypenationalite) {
		this.selectedTypenationalite = selectedTypenationalite;
	}

	public List getListTypenationalite() {
		if (listTypenationalite.isEmpty()) {
			listTypenationalite = getService().getObjects("Typenationalite");
		}
		return listTypenationalite;
	}

	public void setListTypenationalite(List listTypenationalite) {
		this.listTypenationalite = listTypenationalite;
	}
}
