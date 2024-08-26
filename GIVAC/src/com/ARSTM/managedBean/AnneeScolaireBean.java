package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class AnneeScolaireBean {
	@Autowired
	private Iservice service;
	@Autowired
	ReqAnneeScolaire reqAnneeScolaire;

	private AnneesScolaire anneeScolaire = new AnneesScolaire();
	private List listAnneScol = new ArrayList<>();
	
	private AnneesScolaire selectedAnn = new AnneesScolaire();

	// Contr�le de coposant
	private CommandButton btnValider = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	private CommandButton btnSuprimer = new CommandButton();
		
	public void rafraichirTable() {
		listAnneScol = getService().getObjects("AnneesScolaire");
	}
	
	public void actualiserList(){
		listAnneScol.clear();
		listAnneScol = getService().getObjects("AnneesScolaire");
	}

	public void enregistrer() {
		anneeScolaire.setLibAnneeScolaire(this.anneeScolaire.getAnneesDebut()+" - "+this.anneeScolaire.getAnneesFin());
		anneeScolaire.setEtatAnneScolaire("OUVERTE");
		getService().addObject(anneeScolaire);
		
		//Cloturer l'ann�e pr�c�dente
		List listAnnescol = reqAnneeScolaire.recupAnneeScoByCodeDecrois();
		if(!listAnnescol.isEmpty()){
			AnneesScolaire anneesScoPrecedente = (AnneesScolaire) listAnnescol.get(1);
			anneesScoPrecedente.setEtatAnneScolaire("FERMEE");
			getService().updateObject(anneesScoPrecedente);
		}
		viderAnneScolaire(anneeScolaire);
		actualiserList();// For the DataTable
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetu�!", null));
	}

	public void supprimer() {
		AnneesScolaire annneeTemp = new AnneesScolaire();
		annneeTemp.setCodeAnnees(selectedAnn.getCodeAnnees());
		annneeTemp.setAnneesDebut(selectedAnn.getAnneesDebut());
		annneeTemp.setAnneesFin(selectedAnn.getAnneesFin());
		annneeTemp.setLibAnneeScolaire(selectedAnn.getLibAnneeScolaire());
		getService().deleteObject(annneeTemp);
		viderAnneScolaire(anneeScolaire);
		actualiserList();
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression effcetu�e!", null));
	}
	
	public void annuler() {
		btnValider.setDisabled(false);
		btnModifier.setDisabled(true);
		btnSuprimer.setDisabled(true);
		viderAnneScolaire(anneeScolaire);
		actualiserList();
	}
	
	public void modifier(){
		anneeScolaire.setLibAnneeScolaire(this.anneeScolaire.getAnneesDebut()+" - "+this.anneeScolaire.getAnneesFin());
		getService().updateObject(anneeScolaire);
		viderAnneScolaire(anneeScolaire);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetu�e!", null));
	}
	
	public void selectionnerAnneeScol() {
		setAnneeScolaire(selectedAnn);
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
	}

	public void viderAnneScolaire(AnneesScolaire objanneeScol) {
		objanneeScol.setAnneesDebut(null);
		objanneeScol.setAnneesFin(null);
		objanneeScol.setDateCommission(null);
		objanneeScol.setLibAnneeScolaire(null);
		objanneeScol.setSessionExamen(null);
	}
	
	public void verifAnecolaire(){
	}

	/****** Accesseurs ***********************************/
	public Iservice getService() {
		return service;
	}

	public void setService(Iservice service) {
		this.service = service;
	}

	public List getListAnneScol() {
		if (listAnneScol.isEmpty()) {
			listAnneScol = getService().getObjects("AnneesScolaire");
		}
		return listAnneScol;
	}

	public void setListAnneScol(List listAnneScol) {
		this.listAnneScol = listAnneScol;
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

	public AnneesScolaire getAnneeScolaire() {
		return anneeScolaire;
	}

	public void setAnneeScolaire(AnneesScolaire anneeScolaire) {
		this.anneeScolaire = anneeScolaire;
	}

	public AnneesScolaire getSelectedAnn() {
		return selectedAnn;
	}

	public void setSelectedAnn(AnneesScolaire selectedAnn) {
		this.selectedAnn = selectedAnn;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

	public ReqAnneeScolaire getReqAnneeScolaire() {
		return reqAnneeScolaire;
	}

	public void setReqAnneeScolaire(ReqAnneeScolaire reqAnneeScolaire) {
		this.reqAnneeScolaire = reqAnneeScolaire;
	}
}
