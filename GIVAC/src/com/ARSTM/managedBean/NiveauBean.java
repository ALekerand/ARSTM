package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Niveaux;
import com.ARSTM.service.Iservice;

@Component
public class NiveauBean {
	@Autowired
	Iservice service;
	private Niveaux niveau = new Niveaux();
	private Niveaux selectedNiveau = new Niveaux();
	private List listNiveau = new ArrayList<>();
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(niveau);
		actualiserList();
		vider(niveau);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		vider(niveau);
		actualiserList();
	}
	
	public void vider(Niveaux objNiveau) {
		objNiveau.setLibNiveau(null);
		objNiveau.setAbrevNiveau(null);
	}
	
	public void actualiserList(){
			listNiveau.clear();
			listNiveau = getService().getObjects("Niveaux");
		}
	
	public void selectionner(){
		setNiveau(selectedNiveau);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Niveaux nivotemp = new Niveaux();
		nivotemp.setCodeniveau(selectedNiveau.getCodeniveau());
		nivotemp.setLibNiveau(selectedNiveau.getLibNiveau());
		
		
		getService().deleteObject(nivotemp);
		vider(niveau);
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

	public Niveaux getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveaux niveau) {
		this.niveau = niveau;
	}

	public Niveaux getSelectedNiveau() {
		return selectedNiveau;
	}

	public void setSelectedNiveau(Niveaux selectedNiveau) {
		this.selectedNiveau = selectedNiveau;
	}

	public List getListNiveau() {
		if(listNiveau.isEmpty()){
			listNiveau = getService().getObjects("Niveaux");
		}
		return listNiveau;
	}

	public void setListNiveau(List listNiveau) {
		this.listNiveau = listNiveau;
	}
}
