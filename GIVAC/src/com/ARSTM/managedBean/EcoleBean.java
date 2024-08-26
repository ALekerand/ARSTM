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
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class EcoleBean {
	@Autowired
	Iservice service;
	private Ecole ecole = new Ecole();
	private Ecole selectedEcole = new Ecole();
	private List listEcole = new ArrayList<>();
	private Filieres choosedFiliere = new Filieres();
	private List listFiliere = new ArrayList<>();
	
	// Contrôle de coposant
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
		ecole.setAbrevEcole(getEcole().getAbrevEcole().toUpperCase());
		getService().addObject(ecole);
		actualiserList();
		vider(ecole);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}
	
	public void modifier(){
		getService().updateObject(ecole);
		vider(ecole);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(ecole);
		actualiserList();
	}
	
	public void vider(Ecole objEcole) {
		objEcole.setNomEcole(null);
		objEcole.setAbrevEcole(null);
	}
	
	public void actualiserList(){
			listEcole.clear();
			listEcole = getService().getObjects("Ecole");
		}
	
	public void selectionner(){
		setEcole(selectedEcole);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
		btnModifier.setDisabled(false);
	}
	
	
	public void supprimer() {
		Ecole ecoleTemp = new Ecole();
		
		ecoleTemp.setCodeEcole(selectedEcole.getCodeEcole());
		ecoleTemp.setAbrevEcole(selectedEcole.getAbrevEcole());
		ecoleTemp.setNomEcole(selectedEcole.getNomEcole());
		getService().deleteObject(ecoleTemp);
		vider(ecoleTemp);
		vider(ecole);
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

	public Ecole getSelectedEcole() {
		return selectedEcole;
	}

	public void setSelectedEcole(Ecole selectedEcole) {
		this.selectedEcole = selectedEcole;
	}

	public List getListEcole() {
		if (listEcole.isEmpty()) {
			listEcole = getService().getObjects("Ecole");
		}
		return listEcole;
	}

	public void setListEcole(List listEcole) {
		this.listEcole = listEcole;
	}

	public Ecole getEcole() {
		return ecole;
	}

	public void setEcole(Ecole ecole) {
		this.ecole = ecole;
	}

	public Filieres getChoosedFiliere() {
		return choosedFiliere;
	}

	public void setChoosedFiliere(Filieres choosedFiliere) {
		this.choosedFiliere = choosedFiliere;
	}

	public List getListFiliere() {
		if (listFiliere.isEmpty()) {
			listFiliere = getService().getObjects("Filieres");
		}
		return listFiliere;
	}

	public void setListFiliere(List listFiliere) {
		this.listFiliere = listFiliere;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}
}
