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
import com.ARSTM.model.Tformation;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class TypeFormationBean {
	@Autowired
	Iservice service;
	private Tformation tformation = new Tformation();
	
	
	//private Ecole selectedEcole = new Ecole();
	private Tformation selectedTformation = new Tformation();
	//private List listEcole = new ArrayList<>();
	private List listTformation = new ArrayList<>();
	//private Filieres choosedFiliere = new Filieres();
//	private List listFiliere = new ArrayList<>();
	
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
		System.out.println("-----DEBUT Enregistrement OK---");
		tformation.setAbrevTformation(getTformation().getAbrevTformation().toUpperCase());
		getService().addObject(tformation);
		System.out.println("-----Enregistrement OK---");
		actualiserList();
		vider(tformation);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}
	
	public void modifier(){
		getService().updateObject(tformation);
		vider(tformation);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(tformation);
		actualiserList();
	}
	
	public void vider(Tformation objTformation) {
		objTformation.setLibTformation(null);
		objTformation.setAbrevTformation(null);
	}
	
	public void actualiserList(){
			listTformation.clear();
			listTformation = getService().getObjects("Tformation");
		}
	
	public void selectionner(){
		setTformation(selectedTformation);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
		btnModifier.setDisabled(false);
	}
	
	
	public void supprimer() {
		Tformation tformationTemp = new Tformation();
		tformationTemp.setCodeTformation(selectedTformation.getCodeTformation());
		tformationTemp.setLibTformation(selectedTformation.getLibTformation());
		tformationTemp.setAbrevTformation(selectedTformation.getAbrevTformation());
		getService().deleteObject(tformationTemp);
		vider(tformationTemp);
		vider(tformation);
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

	public Tformation getTformation() {
		return tformation;
	}

	public void setTformation(Tformation tformation) {
		this.tformation = tformation;
	}

	public List getListTformation() {
		if (listTformation.isEmpty()) {
			listTformation = getService().getObjects("Tformation");
		}
		return listTformation;
	}

	public void setListTformation(List listTformation) {
		this.listTformation = listTformation;
	}

	public Tformation getSelectedTformation() {
		return selectedTformation;
	}

	public void setSelectedTformation(Tformation selectedTformation) {
		this.selectedTformation = selectedTformation;
	}
}
