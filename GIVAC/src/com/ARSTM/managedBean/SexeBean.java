package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Sexe;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class SexeBean {
	@Autowired
	Iservice service;
	private Sexe sexe = new Sexe();
	private Sexe selectedSexe = new Sexe();
	private List listSexe = new ArrayList<>();
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	
	public void enregistrerSexe(){
		getService().addObject(sexe);
		actualiserList();
		viderSexe(sexe);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		viderSexe(sexe);
		actualiserList();
	}
	
	public void viderSexe(Sexe objSexe) {
		objSexe.setLibSexe(null);
	}
	
	public void actualiserList(){
			listSexe.clear();
			listSexe = getService().getObjects("Sexe");
		}
	
	public void selectionnerSexe(){
		setSexe(selectedSexe);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Sexe sexetemp = new Sexe();
		sexetemp.setCodeSexe(selectedSexe.getCodeSexe());
		sexetemp.setLibSexe(selectedSexe.getLibSexe());
		getService().deleteObject(sexetemp);
		viderSexe(sexe);
		actualiserList();
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
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



	public List getListSexe() {
		if(listSexe.isEmpty()){
			listSexe = getService().getObjects("Sexe");
		}
		return listSexe;
	}



	public void setListSexe(List listSexe) {
		this.listSexe = listSexe;
	}
	
	public Sexe getSexe() {
		return sexe;
	}

	public void setSexe(Sexe sexe) {
		this.sexe = sexe;
	}

	public Sexe getSelectedSexe() {
		return selectedSexe;
	}

	public void setSelectedSexe(Sexe selectedSexe) {
		this.selectedSexe = selectedSexe;
	}

}
