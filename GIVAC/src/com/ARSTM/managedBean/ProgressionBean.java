package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.Enseignant;
import com.ARSTM.model.Enseigner;
import com.ARSTM.model.Section;
import com.ARSTM.model.Semestre;
import com.ARSTM.model.Sexe;
import com.ARSTM.model.UserAuthentication;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.requetes.ReqUtilisateur;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class ProgressionBean {
	@Autowired
	Iservice service;

	//ReqEnseigner reqEnseigner;
	@Autowired
	ReqAnneeScolaire reqAnneeScolaire;
	@Autowired
	ManagedInitialisation managedInitialisation;
	
	
	private AnneesScolaire anneesScolaire = new AnneesScolaire();
	private Enseignant enseignant = new Enseignant();
	private List<Enseigner> listEnseigner = new ArrayList<>();
	private Enseigner selectedEnseigner = new Enseigner();
	private Section choosedSection = new Section();
	private List<Section> listeSection = new ArrayList<>();
	
	
	public void chargerListeMatEnseigner(){
		listEnseigner.clear();
		//listEnseigner = reqEnseigner.recupEnseignerByEnseignant(choosedSection.getCodeSection(), managedInitialisation.getAnneeScolaireEncours().getCodeAnnees(), managedInitialisation.getUtilisateurCourant().getUserId());
	}
	
	
	
	
	public Enseigner getSelectedEnseigner() {
		return selectedEnseigner;
	}


	public void setSelectedEnseigner(Enseigner selectedEnseigner) {
		this.selectedEnseigner = selectedEnseigner;
	}

		// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
		
	
		public String editer(){
			return "/pages/editionProgression.xhtml";
			
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

	public List<Enseigner> getListEnseigner() {
		if (listEnseigner.isEmpty()) {

		}
		return listEnseigner;
	}

	public void setListEnseigner(List<Enseigner> listEnseigner) {
		this.listEnseigner = listEnseigner;
	}

	public ReqAnneeScolaire getReqAnneeScolaire() {
		return reqAnneeScolaire;
	}

	public void setReqAnneeScolaire(ReqAnneeScolaire reqAnneeScolaire) {
		this.reqAnneeScolaire = reqAnneeScolaire;
	}

	public AnneesScolaire getAnneesScolaire() {
		return anneesScolaire;
	}

	public void setAnneesScolaire(AnneesScolaire anneesScolaire) {
		this.anneesScolaire = anneesScolaire;
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}


	public ManagedInitialisation getManagedInitialisation() {
		return managedInitialisation;
	}

	public void setManagedInitialisation(ManagedInitialisation managedInitialisation) {
		this.managedInitialisation = managedInitialisation;
	}

	public Section getChoosedSection() {
		return choosedSection;
	}

	public void setChoosedSection(Section choosedSection) {
		this.choosedSection = choosedSection;
	}
	
	@SuppressWarnings("unchecked")
	public List<Section> getListeSection() {
		if (listeSection.isEmpty()) {
			listeSection = getService().getObjects("Section");
		}
		return listeSection;
	}

	public void setListeSection(List<Section> listeSection) {
		this.listeSection = listeSection;
	}

}
