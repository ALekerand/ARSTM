package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Journonouvre;
import com.ARSTM.model.Semaine;
import com.ARSTM.requetes.ReqSemaine;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class JourNonOuvreBean {
	@Autowired
	Iservice service;
	@Autowired
	ReqSemaine reqSemaine;
	@Autowired
	ManagedInitialisation initialisation;
	
	

	private Journonouvre journonouvre = new Journonouvre();
	private Journonouvre selectedObject = new Journonouvre();
	private Semaine choosedSemaine = new Semaine();
	private List listeObject = new ArrayList<>();
	private List<Semaine> listSemaine = new ArrayList<>();
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
	
	
	public void enregistrer(){
		journonouvre.setSemaine(choosedSemaine);
		getService().addObject(journonouvre);
		actualiserList();
		vider(journonouvre);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(journonouvre);
		actualiserList();
	}
	
	public void vider(Journonouvre object) {
		object.setDate(null);
		object.setMotif(null);
	}
	
	public void vider(Semaine object) {
		object.setDebutSem(null);
		object.setFinSem(null);
		object.setNumSemaine(null);
	}
	
	public void actualiserList(){
		listeObject.clear();
		listSemaine.clear();
		listSemaine = reqSemaine.recupererSemaineByAn(initialisation.getAnneeScolaireEncours().getCodeAnnees());
		for (Semaine varSemaine : listSemaine) {
			//Ajouter le JNO de la semaine
			for (Journonouvre varJNO : varSemaine.getJournonouvres()) {
				listeObject.add(varJNO);
			}
		}
		}
	
	public void selectionner(){
		setJournonouvre(selectedObject);
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Journonouvre ObjectTemp = new Journonouvre();
		ObjectTemp.setCodeFerier(selectedObject.getCodeFerier());
		ObjectTemp.setSemaine(choosedSemaine);
		
		
		getService().deleteObject(ObjectTemp);
		vider(journonouvre);
		vider(choosedSemaine);
		actualiserList();
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression effcetuée!", null));
	}
	
	public void modifier(){
		getService().updateObject(journonouvre);
		vider(journonouvre);
		vider(choosedSemaine);
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

	public Journonouvre getJournonouvre() {
		return journonouvre;
	}

	public void setJournonouvre(Journonouvre journonouvre) {
		this.journonouvre = journonouvre;
	}

	public Journonouvre getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Journonouvre selectedObject) {
		this.selectedObject = selectedObject;
	}

	public List getListeObject() {
		if (listeObject.isEmpty()) {
			for (Semaine varSemaine : listSemaine) {
				//Ajouter le JNO de la semaine
				for (Journonouvre varJNO : varSemaine.getJournonouvres()) {
					listeObject.add(varJNO);
				}
			}
		}
		return listeObject;
	}

	public void setListeObject(List listeObject) {
		this.listeObject = listeObject;
	}

	public Semaine getChoosedSemaine() {
		return choosedSemaine;
	}

	public void setChoosedSemaine(Semaine choosedSemaine) {
		this.choosedSemaine = choosedSemaine;
	}

	public List getListSemaine() {
		if (listSemaine.isEmpty()) {
			listSemaine = reqSemaine.recupererSemaineByAn(initialisation.getAnneeScolaireEncours().getCodeAnnees());
		}
		return listSemaine;
	}

	public void setListSemaine(List listSemaine) {
		this.listSemaine = listSemaine;
	}

	public ReqSemaine getReqSemaine() {
		return reqSemaine;
	}

	public void setReqSemaine(ReqSemaine reqSemaine) {
		this.reqSemaine = reqSemaine;
	}
	
	public ManagedInitialisation getInitialisation() {
		return initialisation;
	}

	public void setInitialisation(ManagedInitialisation initialisation) {
		this.initialisation = initialisation;
	}

	

}
