package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Matiere;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class MatiereBean {
	@Autowired
	Iservice service;
	
	private Matiere matiere = new Matiere();
	private Matiere selectedMatiere = new Matiere();
	private List listMatiere = new ArrayList<>();
	Matiere matiereTemp = new Matiere();
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
	
		
		@PostConstruct
		public void initiate(){
				btnSuprimer.setDisabled(true);
				btnModifier.setDisabled(true);
		}
	
	public void enregistrer(){
		matiere.setAbrevMatiere(matiere.getAbrevMatiere().toUpperCase());
		//matiere.setCodelmd(matiere.getCodelmd().toUpperCase());
		getService().addObject(matiere);
		actualiserList();
		vider(matiere);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(matiere);
		actualiserList();
	}
	
	public void vider(Matiere objMatiere) {
		objMatiere.setLibMatiere(null);
		objMatiere.setAbrevMatiere(null);
	//	objMatiere.setCodelmd(null);
	}
	
	public void actualiserList(){
		listMatiere.clear();
		listMatiere = getService().getObjects("Matiere");
		}
	
	public void selectionner(){
		setMatiere(selectedMatiere);
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Matiere matiereTemp = new Matiere();
		matiereTemp.setCodeMatiere(selectedMatiere.getCodeMatiere());
		matiereTemp.setLibMatiere(selectedMatiere.getLibMatiere());
		matiereTemp.setAbrevMatiere(selectedMatiere.getAbrevMatiere());
	//	matiereTemp.setCodelmd(selectedMatiere.getCodelmd());
		getService().deleteObject(matiereTemp);
		vider(matiereTemp);
		vider(matiere);
		actualiserList();
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression effcetuée!", null));
	}
	
	
	public void modifier(){
		matiere.setAbrevMatiere(matiere.getAbrevMatiere().toUpperCase());
		getService().updateObject(matiere);
		vider(matiere);
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


	public Matiere getSelectedMatiere() {
		return selectedMatiere;
	}

	public void setSelectedMatiere(Matiere selectedMatiere) {
		this.selectedMatiere = selectedMatiere;
	}

	public Matiere getMatiere() {
		
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public List getListMatiere() {
		if (listMatiere.isEmpty()) {
			listMatiere = getService().getObjects("Matiere");
		}
		return listMatiere;
	}

	public void setListMatiere(List listMatiere) {
		this.listMatiere = listMatiere;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

}
