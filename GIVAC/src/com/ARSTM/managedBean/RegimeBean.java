package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Regime;
import com.ARSTM.model.Specialite;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class RegimeBean {
	@Autowired
	Iservice service;
	//private Specialite specialite = new Specialite();
	private Regime regime = new Regime();
	private Regime selectedRegime = new Regime();
	//private Specialite selectedSpecialite = new Specialite();
	private List listeRegime = new ArrayList<>();
	//private List listeSpecialite = new ArrayList<>();
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(regime);
		actualiserList();
		vider(regime);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(regime);
		actualiserList();
	}
	
public void modifier(){
		
		getService().updateObject(regime);
		vider(regime);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}
	
	public void vider(Regime objRegime) {
		objRegime.setLibRegime(null);
	}
	
	public void actualiserList(){
		listeRegime.clear();
		listeRegime = getService().getObjects("Regime");
		}
	
	public void selectionner(){
		setRegime(selectedRegime);
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Regime regimeTemp = new Regime();
		regimeTemp.setCodeRegime(selectedRegime.getCodeRegime());
		regimeTemp.setLibRegime(selectedRegime.getLibRegime());
		getService().deleteObject(regimeTemp);
		vider(regimeTemp);
		vider(regime);
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

	public Regime getRegime() {
		return regime;
	}

	public void setRegime(Regime regime) {
		this.regime = regime;
	}

	public Regime getSelectedRegime() {
		return selectedRegime;
	}

	public void setSelectedRegime(Regime selectedRegime) {
		this.selectedRegime = selectedRegime;
	}
	
	public List getListeRegime() {
		if (listeRegime.isEmpty()) {
			listeRegime = getService().getObjects("Regime");
		}
		return listeRegime;
	}

	public void setListeRegime(List listeRegime) {
		this.listeRegime = listeRegime;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}
}
