package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Pole;
import com.ARSTM.model.Sexe;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class PoleBean {
	@Autowired
	Iservice service;
	private Pole pole = new Pole();
	private Pole selectedPole = new Pole();
	private List listPole = new ArrayList<>();
	
	// Contr�le de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	
	public void enregistrerSexe(){
		getService().addObject(pole);
		actualiserList();
		vider(pole);
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetu�!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		vider(pole);
		actualiserList();
	}
	
	public void vider(Pole objpole) {
		objpole.setCodePole(null);
		objpole.setLibellePole(null);
		objpole.setMailPole(null);
		objpole.setTelephonePole(null);
		objpole.setCodePole(null);
	}
	
	public void actualiserList(){
			listPole.clear();
			listPole = getService().getObjects("Pole");
		}
	
	public void selectionner(){
		setPole(selectedPole);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Pole poletemp = new Pole();
		//poletemp.setCodeSexe(selectedSexe.getCodeSexe());
		//poletemp.setLibSexe(selectedSexe.getLibSexe());
		//getService().deleteObject(sexetemp);
		//viderSexe(sexe);
		actualiserList();
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression effcetu�e!", null));
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


	public Pole getPole() {
		return pole;
	}

	public void setPole(Pole pole) {
		this.pole = pole;
	}

	public Pole getSelectedPole() {
		return selectedPole;
	}

	public void setSelectedPole(Pole selectedPole) {
		this.selectedPole = selectedPole;
	}

	public List getListPole() {
		return listPole;
	}

	public void setListPole(List listPole) {
		this.listPole = listPole;
	}

}
