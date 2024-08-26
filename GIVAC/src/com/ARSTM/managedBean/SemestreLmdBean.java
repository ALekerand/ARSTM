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
import com.ARSTM.model.SemestreLmd;
import com.ARSTM.model.Typeue;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class SemestreLmdBean {
	@Autowired
	Iservice service;
	private SemestreLmd semestrelmd = new SemestreLmd();
	private SemestreLmd selectedSemestrelmd = new SemestreLmd();
	private List listSemestrelmd = new ArrayList<>();
	
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
		semestrelmd.setLibSemestreLmd(getSemestrelmd().getLibSemestreLmd().toUpperCase());
		getService().addObject(semestrelmd);
		actualiserList();
		vider(semestrelmd);
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}
	
	public void modifier(){
		getService().updateObject(semestrelmd);
		vider(semestrelmd);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(semestrelmd);
		actualiserList();
	}
	
	public void vider(SemestreLmd objSemestrelmd) {
		objSemestrelmd.setLibSemestreLmd(null);
		objSemestrelmd.setLibLicence("");
	}
	
	public void actualiserList(){
			listSemestrelmd.clear();
			listSemestrelmd = getService().getObjects("SemestreLmd");
		}
	
	public void selectionner(){
		setSemestrelmd(selectedSemestrelmd);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
		btnModifier.setDisabled(false);
	}
	
	
	public void supprimer() {
		SemestreLmd semestrelmdTemp = new SemestreLmd();
		semestrelmdTemp.setCodeSemestreLmd(selectedSemestrelmd.getCodeSemestreLmd());
		semestrelmdTemp.setLibSemestreLmd(selectedSemestrelmd.getLibSemestreLmd());
		getService().deleteObject(semestrelmdTemp);
		vider(semestrelmdTemp);
		vider(semestrelmd);
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

	public SemestreLmd getSelectedSemestrelmd() {
		return selectedSemestrelmd;
	}

	public void setSelectedSemestrelmd(SemestreLmd selectedSemestrelmd) {
		this.selectedSemestrelmd = selectedSemestrelmd;
	}	

	public List getListSemestrelmd() {
		if (listSemestrelmd.isEmpty()) {
			listSemestrelmd = getService().getObjects("SemestreLmd");
		}
		return listSemestrelmd;
	}

	public void setListSemestrelmd(List listSemestrelmd) {
		this.listSemestrelmd = listSemestrelmd;
	}

	public SemestreLmd getSemestrelmd() {
		return semestrelmd;
	}

	public void setSemestrelmd(SemestreLmd semestrelmd) {
		this.semestrelmd = semestrelmd;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}
}
