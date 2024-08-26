package com.ARSTM.managedBean;

import java.math.BigDecimal;
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
import com.ARSTM.model.Ecolages;
import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Mention;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.requetes.ReqTypeNationalite;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.requetes.RequeteMention;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class FraisEcolageBean {
	
	@Autowired
	Iservice service;
	
	@Autowired
	RequeteFiliere requeteFiliere;
	@Autowired
	RequeteMention requeteMention;
	@Autowired
	ReqAnneeScolaire reqAnneeScolaire;
	@Autowired
	ReqTypeNationalite reqTypeNationalite;
	
	private Ecole choosedEcole = new Ecole();
	private List listEcole = new ArrayList<>();
	private Filieres choosedFiliere = new Filieres();
	private List listFiliere = new ArrayList<>();
	private Mention choosedMention = new Mention();
	private List listeMention = new ArrayList<>();
	private List<Ecolages> listeEcolage = new ArrayList<Ecolages>();
	private BigDecimal fraisExam;
	
	private Ecolages ecolageNation = new Ecolages();
	private Ecolages selectedObject = new Ecolages();
	private Ecolages ecolageNonNation = new Ecolages();
	private AnneesScolaire anneEncoure = new AnneesScolaire();
	
	@PostConstruct
	public AnneesScolaire recupererAnne(){
		anneEncoure = reqAnneeScolaire.recupererDerniereAnneeScolaire().get(0);
		System.out.println("Année:"+anneEncoure.getLibAnneeScolaire());
		return anneEncoure;
		
	}
	
	public String enregistrer(){
		enregistrerEcolage();
		//enregistrerMentionEcolage();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
		return "frais_scolaire2.xhtml";
	}
	
	public void enregistrerEcolage(){
		//Setter les type de nationalite
		ecolageNation.setTypenationalite(reqTypeNationalite.recupererTypeNationalite(1));
		ecolageNation.setFraisExam(fraisExam);
		ecolageNation.setAnneesScolaire(anneEncoure);
		ecolageNation.setMention(choosedMention);
		
		ecolageNonNation.setTypenationalite(reqTypeNationalite.recupererTypeNationalite(2));
		ecolageNonNation.setFraisExam(fraisExam);
		ecolageNonNation.setAnneesScolaire(anneEncoure);
		ecolageNonNation.setMention(choosedMention);
		
		
		//Faire l'enregistrement
		service.addObject(ecolageNation);
		service.addObject(ecolageNonNation);
	}
	
	public void repartirVersNation() {
		ecolageNation.setMtEchance1((ecolageNation.getMontantEcolage().multiply(new BigDecimal("0.4"))));
		ecolageNation.setMtEchance2((ecolageNation.getMontantEcolage().multiply(new BigDecimal("0.3"))));
		ecolageNation.setMtEchance3((ecolageNation.getMontantEcolage().multiply(new BigDecimal("0.2"))));
		ecolageNation.setMtEchance4((ecolageNation.getMontantEcolage().multiply(new BigDecimal("0.1"))));
	}
	
	public void repartirVersNonNation() {
		ecolageNonNation.setMtEchance1((ecolageNonNation.getMontantEcolage().multiply(new BigDecimal("0.4"))));
		ecolageNonNation.setMtEchance2((ecolageNonNation.getMontantEcolage().multiply(new BigDecimal("0.3"))));
		ecolageNonNation.setMtEchance3((ecolageNonNation.getMontantEcolage().multiply(new BigDecimal("0.2"))));
		ecolageNonNation.setMtEchance4((ecolageNonNation.getMontantEcolage().multiply(new BigDecimal("0.1"))));
	}
	
	public void enregistrerMentionEcolage() {
		System.out.println("VRIFICATION Année:"+anneEncoure.getLibAnneeScolaire());
		
	}
	
	public void selectionner() {
		
	}

	public Iservice getService() {
		return service;
	}
	
	public void chargerFiliere(){
		listFiliere.clear();
		listFiliere = requeteFiliere.recupFiliereByEcole2(choosedEcole.getCodeEcole());
		
		
		
	}
	public void chargerMention(){
		listeMention.clear();
		listeMention = requeteMention.recupMentionByEcoleFiliere(choosedFiliere.getCodeFiliere());
	}
	
	
	
	 public String onFlowProcess(FlowEvent event) {
	            return event.getNewStep();
	    }
	
	
	
	public void setService(Iservice service) {
		this.service = service;
	}
	public Ecole getChoosedEcole() {
		return choosedEcole;
	}
	public void setChoosedEcole(Ecole choosedEcole) {
		this.choosedEcole = choosedEcole;
	}
	public List getListEcole() {
		if (listEcole.isEmpty()) {
			listEcole = getService().getObjects("Ecole");
		}
		return listEcole;
	}
	public void setListEcole(List listEcole) {
		this.listEcole = listEcole;
	}
	public Filieres getChoosedFiliere() {
		return choosedFiliere;
	}
	public void setChoosedFiliere(Filieres choosedFiliere) {
		this.choosedFiliere = choosedFiliere;
	}
	public List getListFiliere() {
		return listFiliere;
	}
	public void setListFiliere(List listFiliere) {
		this.listFiliere = listFiliere;
	}
	public Mention getChoosedMention() {
		return choosedMention;
	}
	public void setChoosedMention(Mention choosedMention) {
		this.choosedMention = choosedMention;
	}
	public List getListeMention() {
		return listeMention;
	}
	public void setListeMention(List listeMention) {
		this.listeMention = listeMention;
	}

	public Ecolages getEcolageNation() {
		return ecolageNation;
	}

	public void setEcolageNation(Ecolages ecolageNation) {
		this.ecolageNation = ecolageNation;
	}

	public Ecolages getEcolageNonNation() {
		return ecolageNonNation;
	}

	public Ecolages getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Ecolages selectedObject) {
		this.selectedObject = selectedObject;
	}

	public void setEcolageNonNation(Ecolages ecolageNonNation) {
		this.ecolageNonNation = ecolageNonNation;
	}

	public BigDecimal getFraisExam() {
		return fraisExam;
	}

	public void setFraisExam(BigDecimal fraisExam) {
		this.fraisExam = fraisExam;
	}

	public List<Ecolages> getListeEcolage() {
		return listeEcolage = service.getObjects("Ecolages");
	}

	public void setListeEcolage(List<Ecolages> listeEcolage) {
		this.listeEcolage = listeEcolage;
	}

}
