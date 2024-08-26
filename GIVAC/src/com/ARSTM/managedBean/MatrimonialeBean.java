package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Matrimoniales;
import com.ARSTM.service.Iservice;

@Component
public class MatrimonialeBean {
	@Autowired
	Iservice service;
	private Matrimoniales matrimoniale = new Matrimoniales();
	private Matrimoniales selectedMatrimoniale = new Matrimoniales();
	private List listMatrimoniale = new ArrayList<>();
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(matrimoniale);
		actualiserList();
		vider(matrimoniale);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		vider(matrimoniale);
		actualiserList();
	}
	
	public void vider(Matrimoniales objMatrimoniale) {
		objMatrimoniale.setLibmatrimoniale(null);
	}
	
	public void actualiserList(){
			listMatrimoniale.clear();
			listMatrimoniale = getService().getObjects("Matrimoniales");
		}
	
	public void selectionner(){
		setMatrimoniale(selectedMatrimoniale);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Matrimoniales matrimonialetemp = new Matrimoniales();
		matrimonialetemp.setCodematrimoniale(selectedMatrimoniale.getCodematrimoniale());
		matrimonialetemp.setLibmatrimoniale(selectedMatrimoniale.getLibmatrimoniale());
	//	matrimonialetemp = selectedMatrimoniale;
		getService().deleteObject(matrimonialetemp);
		vider(matrimoniale);
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

	public Matrimoniales getMatrimoniale() {
		return matrimoniale;
	}

	public void setMatrimoniale(Matrimoniales matrimoniale) {
		this.matrimoniale = matrimoniale;
	}

	public Matrimoniales getSelectedMatrimoniale() {
		return selectedMatrimoniale;
	}

	public void setSelectedMatrimoniale(Matrimoniales selectedMatrimoniale) {
		this.selectedMatrimoniale = selectedMatrimoniale;
	}

	public List getListMatrimoniale() {
		if(listMatrimoniale.isEmpty()){
			listMatrimoniale = getService().getObjects("Matrimoniales");
		}
		return listMatrimoniale;
	}

	public void setListMatrimoniale(List listMatrimoniale) {
		this.listMatrimoniale = listMatrimoniale;
	}
}
