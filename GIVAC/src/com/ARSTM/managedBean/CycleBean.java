package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Cycle;
import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Matiere;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class CycleBean {
	@Autowired
	Iservice service;
	
	private Cycle cycle = new Cycle();
	private Cycle selectedCycle = new Cycle();
	private List listeCycle = new ArrayList<>();
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(cycle);
		actualiserList();
		vider(cycle);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(cycle);
		actualiserList();
	}
	
	public void vider(Cycle objCycle) {
		objCycle.setNomCycle(null);
		objCycle.setTauxHoraire(null);
	}
	
	public void actualiserList(){
		listeCycle.clear();
		listeCycle = getService().getObjects("Cycle");
		}
	
	public void selectionner(){
		setCycle(selectedCycle);
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Cycle cycleTemp = new Cycle();
		cycleTemp.setCodeCycle(selectedCycle.getCodeCycle());
		cycleTemp.setNomCycle(selectedCycle.getNomCycle());
		cycleTemp.setTauxHoraire(selectedCycle.getTauxHoraire());
		
		getService().deleteObject(selectedCycle);
		vider(selectedCycle);
		actualiserList();
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression effcetuée!", null));
	}
	
	public void modifier(){
		getService().updateObject(cycle);
		vider(cycle);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
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

	public Cycle getCycle() {
		return cycle;
	}

	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}

	public Cycle getSelectedCycle() {
		return selectedCycle;
	}

	public void setSelectedCycle(Cycle selectedCycle) {
		this.selectedCycle = selectedCycle;
	}

	public List getListeCycle() {
		if (listeCycle.isEmpty()) {
			listeCycle = getService().getObjects("Cycle");
		}
		return listeCycle;
	}

	public void setListeCycle(List listeCycle) {
		this.listeCycle = listeCycle;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

}
