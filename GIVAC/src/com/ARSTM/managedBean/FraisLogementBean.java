package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FlowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.TypeLogement;
import com.ARSTM.model.TypeLogementNationalite;
import com.ARSTM.model.Typenationalite;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.requetes.ReqTypeNationalite;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class FraisLogementBean {
	
	@Autowired
	Iservice service;
	@Autowired
	ReqTypeNationalite reqTypeNationalite;
	@Autowired
	ReqAnneeScolaire reqAnneeScolaire;
	
	private TypeLogement choosedTypeLogement = new TypeLogement();
	private List listTypeLogement = new ArrayList<>();
	private List listTypeLogementNationalite = new ArrayList<>();
	
	private TypeLogementNationalite selectedIns = new TypeLogementNationalite();
	
	private TypeLogementNationalite typeLogementNation = new TypeLogementNationalite();
	private TypeLogementNationalite typeLogementNonNation = new TypeLogementNationalite();
	private TypeLogementNationalite SelectedTypeLogementNationalite = new TypeLogementNationalite();
	private Typenationalite typenationalite = new Typenationalite();
	
	private AnneesScolaire anneEncoure = new AnneesScolaire();
	
	@PostConstruct
	public AnneesScolaire recupererAnne(){
		anneEncoure = reqAnneeScolaire.recupererDerniereAnneeScolaire().get(0);
		return anneEncoure;
	}
	
	public String enregistrer(){		
		enregistrerTypeLogement();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetu�!", null));
		return "frais_logement.xhtml";
	}
	
	
	public void enregistrerTypeLogement(){
		typeLogementNation.setTypenationalite(reqTypeNationalite.recupererTypeNationalite(1));
		typeLogementNation.setAnneesScolaire(anneEncoure);
		typeLogementNation.setTypeLogement(choosedTypeLogement);
		
		typeLogementNonNation.setTypenationalite(reqTypeNationalite.recupererTypeNationalite(2));
		typeLogementNonNation.setAnneesScolaire(anneEncoure);
		typeLogementNonNation.setTypeLogement(choosedTypeLogement);
		//Faire l'enregistrement
		service.addObject(typeLogementNation);
		service.addObject(typeLogementNonNation);
	}
	
	/**************************ACCESSEURS*************************/
	

	public Iservice getService() {
		return service;
	}
	
	
	 public String onFlowProcess(FlowEvent event) {
	            return event.getNewStep();
	    }
	
	
	public void setService(Iservice service) {
		this.service = service;
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

	
	public Typenationalite getTypenationalite() {
		return typenationalite;
	}

	public void setTypenationalite(Typenationalite typenationalite) {
		this.typenationalite = typenationalite;
	}

	public TypeLogementNationalite getTypeLogementNation() {
		return typeLogementNation;
	}

	public void setTypeLogementNation(TypeLogementNationalite typeLogementNation) {
		this.typeLogementNation = typeLogementNation;
	}

	public TypeLogementNationalite getTypeLogementNonNation() {
		return typeLogementNonNation;
	}

	public void setTypeLogementNonNation(TypeLogementNationalite typeLogementNonNation) {
		this.typeLogementNonNation = typeLogementNonNation;
	}

	public TypeLogement getChoosedTypeLogement() {
		return choosedTypeLogement;
	}

	public void setChoosedTypeLogement(TypeLogement choosedTypeLogement) {
		this.choosedTypeLogement = choosedTypeLogement;
	}

	public List getListTypeLogementNationalite() {
		listTypeLogementNationalite = service.getObjects("TypeLogementNationalite");
		return listTypeLogementNationalite;
	}

	public void setListTypeLogementNationalite(List listTypeLogementNationalite) {
		this.listTypeLogementNationalite = listTypeLogementNationalite;
	}


}
