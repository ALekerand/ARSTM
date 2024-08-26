package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Domaine;
import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Logement;
import com.ARSTM.model.Tformation;
import com.ARSTM.model.TypeLogement;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.requetes.RequeteLogement;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class LogementBean {
	@Autowired
	Iservice service;
	@Autowired
	RequeteLogement requeteLogement;
	
	private Logement selectedLogement = new Logement();
	private Logement logement = new Logement();
	private List listLogement = new ArrayList<>();
	private List listeLogByLogement = new ArrayList<>();
	private TypeLogement typelogement = new TypeLogement();
	private TypeLogement choosedTypeLogement = new TypeLogement();
	private List listTypeLogement = new ArrayList<>();
	

		// Contrôle de composant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
		private InputText inputFiliere = new InputText();
		private InputText inputAbrevFiliere = new InputText();
		private InputText inputFiliere2 = new InputText();
		private InputText inputAbrevFiliere2 = new InputText();
		private InputText inputCapacite = new InputText();
	
		@PostConstruct
	public void initiate(){
			inputFiliere.setDisabled(true);
			inputAbrevFiliere.setDisabled(true);
			inputFiliere2.setDisabled(true);
			inputAbrevFiliere2.setDisabled(true);
			inputCapacite.setDisabled(true);
			btnSuprimer.setDisabled(true);
			btnModifier.setDisabled(true);
	}
		
		
			
		
			
		
		public void activerChamps(){
			
			if ((!(choosedTypeLogement.getLibtypeLogement().equals(null)))) {
				inputFiliere.setDisabled(false);
				inputAbrevFiliere.setDisabled(false);
				inputFiliere2.setDisabled(false);
				inputAbrevFiliere2.setDisabled(false);
				inputCapacite.setDisabled(false);
				chargerListLogbyLogement();
			}
			
		}
		
		public List<Logement> chargerListLogbyLogement(){
			listeLogByLogement.clear();
			for (Logement filObject : requeteLogement.recupLogByTypeLogement(choosedTypeLogement.getCodetypeLogement())) {
				listeLogByLogement.add(filObject);
			}
			return listeLogByLogement;
		}
	
	public void enregistrer(){
		//System.out.println("------>>>> Formation"+choosedTformation.getCodeTformation());
		logement.setAbrevAmpusLoge(logement.getAbrevAmpusLoge().toUpperCase());
		logement.setTypeLogement(choosedTypeLogement);
		getService().addObject(logement);
		actualiserList();
		vider(logement);
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(logement);
		actualiserList();
	}
	
	public void modifier(){
		logement.setAbrevAmpusLoge(logement.getAbrevAmpusLoge().toUpperCase());
		getService().updateObject(logement);
		vider(logement);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}
	
	public void vider(Logement objLogement) {
		objLogement.setAbrevAmpusLoge(null);
		objLogement.setLibCampusLoge(null);
		objLogement.setLibDetailCampusLoge(null);
		objLogement.setCapacite(null);
		
	}
	
	public void actualiserList(){
			listLogement.clear();
			listLogement = getService().getObjects("Logement");
			chargerListLogbyLogement();
		}
	
	public void selectionner(){
		setLogement(selectedLogement);
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
		
	}
	
	public void supprimer() {
		Logement logementTemp = new Logement();
		logementTemp.setAbrevAmpusLoge(selectedLogement.getAbrevAmpusLoge());
		logementTemp.setLibCampusLoge(selectedLogement.getLibCampusLoge());
		logementTemp.setLibDetailCampusLoge(selectedLogement.getLibDetailCampusLoge());
		logementTemp.setCodeLoge(selectedLogement.getCodeLoge());
		logementTemp.setTypeLogement(selectedLogement.getTypeLogement());
		logementTemp.setCapacite(selectedLogement.getCapacite());
		getService().deleteObject(logementTemp);
		vider(logementTemp);
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

	public TypeLogement getChoosedTypeLogement() {
		return choosedTypeLogement;
	}


	public void setChoosedTypeLogement(TypeLogement choosedTypeLogement) {
		this.choosedTypeLogement = choosedTypeLogement;
	}

	public InputText getInputFiliere() {
		return inputFiliere;
	}

	public void setInputFiliere(InputText inputFiliere) {
		this.inputFiliere = inputFiliere;
	}

	public InputText getInputFiliere2() {
		return inputFiliere2;
	}

	public void setInputFiliere2(InputText inputFiliere2) {
		this.inputFiliere2 = inputFiliere2;
	}

	public InputText getInputAbrevFiliere() {
		return inputAbrevFiliere;
	}

	public void setInputAbrevFiliere(InputText inputAbrevFiliere) {
		this.inputAbrevFiliere = inputAbrevFiliere;
	}

	public InputText getInputAbrevFiliere2() {
		return inputAbrevFiliere2;
	}

	public void setInputAbrevFiliere2(InputText inputAbrevFiliere2) {
		this.inputAbrevFiliere2 = inputAbrevFiliere2;
	}

	public InputText getInputCapacite() {
		return inputCapacite;
	}






	public void setInputCapacite(InputText inputCapacite) {
		this.inputCapacite = inputCapacite;
	}






	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

	public RequeteLogement getRequeteLogement() {
		return requeteLogement;
	}


	public void setRequeteLogement(RequeteLogement requeteLogement) {
		this.requeteLogement = requeteLogement;
	}


	public List getListeLogByLogement() {
		return listeLogByLogement;
	}

	public void setListeLogByLogement(List listeLogByLogement) {
		this.listeLogByLogement = listeLogByLogement;
	}
	
	public List getListTypeLogement() {
		if (listTypeLogement.isEmpty()) {
			listTypeLogement = getService().getObjects("TypeLogement");
		}
		return listTypeLogement;
	}

	public void setListTypeLogement(List listTypeLogement) {
		this.listTypeLogement = listTypeLogement;
	}
	
	public TypeLogement getTypelogement() {
		return typelogement;
	}
	
	public void setTypelogement(TypeLogement typelogement) {
		this.typelogement = typelogement;
	}

	public Logement getLogement() {
		return logement;
	}

	public void setLogement(Logement logement) {
		this.logement = logement;
	}


	public List getListLogement() {
		return listLogement;
	}

	public void setListLogement(List listLogement) {
		this.listLogement = listLogement;
	}

	public Logement getSelectedLogement() {
		return selectedLogement;
	}

	public void setSelectedLogement(Logement selectedLogement) {
		this.selectedLogement = selectedLogement;
	}
	
}
