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
import com.ARSTM.model.Statut;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class StatutBean {
	@Autowired
	Iservice service;
	private Statut statut = new Statut();
	private Statut selectedstatut = new Statut();
	private List liststatut = new ArrayList<>();
	
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(statut);
		actualiserList();
		vider(statut);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(statut);
		actualiserList();
	}
	
	public void vider(Statut objStatut) {
		objStatut.setLibelleStatut(null);
	}
	
	public void actualiserList(){
			liststatut.clear();
			liststatut = getService().getObjects("Statut");
		}
	
	public void selectionner(){
		setStatut(selectedstatut);
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void modifier(){
		
		getService().updateObject(statut);
		vider(statut);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}
	
	
	public void supprimer() {
		
		Statut statutTemp = new Statut();
		statutTemp.setCodeStatut(selectedstatut.getCodeStatut());
		statutTemp.setLibelleStatut(selectedstatut.getLibelleStatut());
		getService().deleteObject(statutTemp);
		vider(statutTemp);
		vider(statut);
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

	public Statut getStatut() {
		
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public Statut getSelectedstatut() {
		return selectedstatut;
	}

	public void setSelectedstatut(Statut selectedstatut) {
		this.selectedstatut = selectedstatut;
	}

	public List getListstatut() {
		if (liststatut.isEmpty()) {
			liststatut = getService().getObjects("Statut");
		}
		return liststatut;
	}

	public void setListstatut(List liststatut) {
		this.liststatut = liststatut;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

}
