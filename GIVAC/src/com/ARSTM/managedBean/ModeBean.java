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
import com.ARSTM.model.Sexe;
import com.ARSTM.model.Statut;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class ModeBean {
	@Autowired
	Iservice service;
	private Mode mode = new Mode();
	private Mode selectedmode = new Mode();
	private List listmode =new ArrayList<>();
	
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(mode);
		actualiserList();
		vider(mode);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(mode);
		actualiserList();
	}
	
	public void vider(Mode objMode) {
		objMode.setLibMode(null);
	}
	
	public void actualiserList(){
			listmode.clear();
			listmode= getService().getObjects("Mode");
		}
	
	public void selectionner(){
		setMode(selectedmode);
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void modifier(){
		
		getService().updateObject(mode);
		vider(mode);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}
	
	
	public void supprimer() {
		Mode modeTemp = new Mode();
		modeTemp.setCodeMode(selectedmode.getCodeMode());
		modeTemp.setLibMode(selectedmode.getLibMode());
		getService().deleteObject(modeTemp);
		vider(modeTemp);
		vider(mode);
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

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public Mode getSelectedmode() {
		return selectedmode;
	}

	public void setSelectedmode(Mode selectedmode) {
		this.selectedmode = selectedmode;
	}

	public List getListmode() {
		if (listmode.isEmpty()) {
			listmode = getService().getObjects("Mode");
		}
		return listmode;
	}

	public void setListmode(List listmode) {
		this.listmode = listmode;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

}
