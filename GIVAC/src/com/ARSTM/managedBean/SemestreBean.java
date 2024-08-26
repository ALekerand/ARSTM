package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Semestre;
import com.ARSTM.model.Sexe;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class SemestreBean {
	@Autowired
	Iservice service;
	private Semestre semestre = new Semestre();
	private Semestre selectedSemestre = new Semestre();
	private List<Semestre> listSemestre = new ArrayList<>();
	private boolean console;  
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
		
	public void enregistrer(){
		getService().addObject(semestre);
		actualiserList();
		vider(semestre);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		vider(semestre);
		actualiserList();
	}
	
	public void vider(Semestre objSemestre) {
		objSemestre.setLibSemestre(null);
		objSemestre.setEtatSemestre(false);
	}
	
	@SuppressWarnings("unchecked")
	public void actualiserList(){
			listSemestre.clear();
			listSemestre = getService().getObjects("Semestre");
		}
	
	public void selectionnerRow(){
		setSemestre(selectedSemestre);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Semestre semestreTemp = new Semestre();
		semestreTemp.setCodeSemestre(selectedSemestre.getCodeSemestre());
		semestreTemp.setEtatSemestre(selectedSemestre.isEtatSemestre());
		getService().deleteObject(selectedSemestre);
		vider(semestre);
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

	public Semestre getSemestre() {
		return semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}

	@SuppressWarnings("unchecked")
	public List<Semestre> getListSemestre() {
		if(listSemestre.isEmpty()){
			listSemestre = getService().getObjects("Semestre");
		}
		return listSemestre;
	}

	public void setListSemestre(List<Semestre> listSemestre) {
		this.listSemestre = listSemestre;
	}

	public Semestre getSelectedSemestre() {
		return selectedSemestre;
	}

	public void setSelectedSemestre(Semestre selectedSemestre) {
		this.selectedSemestre = selectedSemestre;
	}

	public boolean isConsole() {
		return console;
	}

	public void setConsole(boolean console) {
		this.console = console;
	}

}
