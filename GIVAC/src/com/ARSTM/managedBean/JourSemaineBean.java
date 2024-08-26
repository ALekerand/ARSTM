package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.JourSemaine;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class JourSemaineBean {
	@Autowired
	Iservice service;
	
	private JourSemaine jourSemaine = new JourSemaine();
	private JourSemaine selectedObject = new JourSemaine();
	private List listJour =  new ArrayList<>();
		
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(jourSemaine);
		actualiserList();
		vider(jourSemaine);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(jourSemaine);
		actualiserList();
	}
	
	public void vider(JourSemaine object) {
		object.setJour(null);
	}
	
	public void actualiserList(){
		listJour.clear();
		listJour = getService().getObjects("JourSemaine");
		}
	
	public void selectionner(){
		setJourSemaine(selectedObject);
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		JourSemaine ObjectTemp = new JourSemaine();
		ObjectTemp.setCodeJour(selectedObject.getCodeJour());
		getService().deleteObject(ObjectTemp);
		vider(jourSemaine);
		actualiserList();
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression effcetuée!", null));
	}
	
	public void modifier(){
		getService().updateObject(jourSemaine);
		vider(jourSemaine);
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


	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}


	public JourSemaine getJourSemaine() {
		return jourSemaine;
	}

	public void setJourSemaine(JourSemaine jourSemaine) {
		this.jourSemaine = jourSemaine;
	}

	public JourSemaine getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(JourSemaine selectedObject) {
		this.selectedObject = selectedObject;
	}

	public List getListJour() {
		if (listJour.isEmpty()) {
			listJour = getService().getObjects("JourSemaine");
		}
		return listJour;
	}

	public void setListJour(List listJour) {
		this.listJour = listJour;
	}

}
