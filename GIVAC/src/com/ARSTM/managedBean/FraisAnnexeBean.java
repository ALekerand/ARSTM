package com.ARSTM.managedBean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FlowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.FraisAnnexe;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.requetes.ReqTypeNationalite;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class FraisAnnexeBean {
	
	@Autowired
	Iservice service;
	
	//RequeteFiliere requeteFiliere;
	//RequeteMention requeteMention;
	@Autowired
	ReqAnneeScolaire reqAnneeScolaire;
	@Autowired
	ReqTypeNationalite reqTypeNationalite;
	
	private FraisAnnexe fraisAnnexeNation = new FraisAnnexe();
	private FraisAnnexe fraisAnnexeNonNation = new FraisAnnexe();
	
	private AnneesScolaire anneEncoure = new AnneesScolaire();
	
	@PostConstruct
	public AnneesScolaire recupererAnne(){
		anneEncoure = reqAnneeScolaire.recupererDerniereAnneeScolaire().get(0);
		System.out.println("Année:"+anneEncoure.getLibAnneeScolaire());
		return anneEncoure;
		
	}
	
	public String enregistrer(){
		enregistrerFraisAnnexe();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
		return "fraisannexe.xhtml";
	}
	
	public void enregistrerFraisAnnexe(){
		//Setter les type de nationalite
		fraisAnnexeNation.setTypenationalite(reqTypeNationalite.recupererTypeNationalite(1));
		fraisAnnexeNation.setAnneesScolaire(anneEncoure);
		fraisAnnexeNonNation.setTypenationalite(reqTypeNationalite.recupererTypeNationalite(2));
		fraisAnnexeNonNation.setAnneesScolaire(anneEncoure);
		
		//Faire l'enregistrement
		service.addObject(fraisAnnexeNation);
		service.addObject(fraisAnnexeNonNation);
	}
	
		public Iservice getService() {
		return service;
	}
	
	 public String onFlowProcess(FlowEvent event) {
	            return event.getNewStep();
	    }
	
	
	
	public void setService(Iservice service) {
		this.service = service;
	}

	public FraisAnnexe getFraisAnnexeNation() {
		return fraisAnnexeNation;
	}

	public void setFraisAnnexeNation(FraisAnnexe fraisAnnexeNation) {
		this.fraisAnnexeNation = fraisAnnexeNation;
	}

	public FraisAnnexe getFraisAnnexeNonNation() {
		return fraisAnnexeNonNation;
	}

	public void setFraisAnnexeNonNation(FraisAnnexe fraisAnnexeNonNation) {
		this.fraisAnnexeNonNation = fraisAnnexeNonNation;
	}		
}
