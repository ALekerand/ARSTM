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

import com.ARSTM.model.Cycle;
import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Mention;
import com.ARSTM.model.Section;
import com.ARSTM.model.SemestreLmd;
import com.ARSTM.model.Typeue;
import com.ARSTM.model.Ues;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.requetes.RequeteFiliere2;
import com.ARSTM.requetes.RequeteMention;
import com.ARSTM.requetes.RequeteSection;
import com.ARSTM.requetes.RequeteSemestreLmd;
import com.ARSTM.requetes.RequeteUes;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class UeBean {
	@Autowired
	Iservice service;
	
	@Autowired
	RequeteFiliere requeteFiliere;
	@Autowired
	RequeteMention requeteMention;
	@Autowired
	RequeteSection requeteSection;
	@Autowired
	RequeteUes requeteUes;
	
	@Autowired
	RequeteSemestreLmd requeteSemestreLmd;
	
	//New dclaration
	private SemestreLmd selectedSemestreLmd = new SemestreLmd();
	private List listSemestreLmd = new ArrayList<>();
	private Typeue selectedTypeUe = new Typeue();
	private List listTypeUe = new ArrayList<>();
	private Ues ues = new Ues();
	private String etatUe;
	
	private Ues selectedUe = new Ues();
	private List<Ues> listeUe = new ArrayList<Ues>();
	
	private int totalCreditUe;
	
	
	//private Section selectedSection = new Section();
	//private List listeSection = new ArrayList<>();
	
	//private String cb_exam;
	
	private Ecole choosedEcole = new Ecole();
	private Filieres choosedFiliere = new Filieres();
	private Mention choosedMention = new Mention();
	
	private List listMention = new ArrayList<>();
	private List listEcole = new ArrayList<>();
	private List listFiliere = new ArrayList<>();
	
	// Contrôle de composant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
		
		private InputText inputOption = new InputText();
		private InputText inputAbrevOption = new InputText();
		
	
	@PostConstruct
public void initialiser(){
	//btnValider.setDisabled(false);
	btnSuprimer.setDisabled(true);
	btnModifier.setDisabled(true);
	/*inputOption.setDisabled(true);
	inputAbrevOption.setDisabled(true);*/
}
	
	public void activerChamps1(){
		
		if ((!(choosedEcole.getNomEcole().equals(null))))
				{
			chargerFiliere();
		}
		
	}
	
  public void chargerSemestreLmd(){
		
	listSemestreLmd = requeteSemestreLmd.recupSemestreByNiveau(choosedMention.getNiveauMention());
	
	}
		
	
	
public void chargerFiliere(){
	listFiliere.clear();
	listFiliere = requeteFiliere.recupFiliereByEcole2(choosedEcole.getCodeEcole());
}

public void chargerMention(){
	listMention.clear();
	listMention = requeteMention.recupMentionByEcoleFiliere(choosedFiliere.getCodeFiliere());
	//System.out.println("------- Taille de la liste mention"+listMention.size());
}

public void chargerUe(){
	listeUe.clear();
	listeUe = requeteUes.recupUesByMentionSemestre(choosedMention.getCodeMention(),selectedSemestreLmd.getCodeSemestreLmd());
	//Calculer le total credit
	totalCreditUe = 0;
		for (Ues objUE : listeUe) {
			totalCreditUe += objUE.getCreditUe();
		}
		}
			
			
	

		
	public void enregistrer(){
		ues.setCodeUeLmd(getUes().getCodeUeLmd().toUpperCase());
		ues.setMention(choosedMention);
		ues.setTypeue(selectedTypeUe);
		ues.setSemestreLmd(selectedSemestreLmd);
		ues.setEtatUe(true);
		service.addObject(ues);
		//vider(ues);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}
	
	public void modifier(){
		if (etatUe.equals("true")) {
			ues.setEtatUe(true);
		}
		
		if (etatUe.equals("false")) {
			ues.setEtatUe(false);
		}
		getService().updateObject(ues);
		vider(ues);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}
	
	

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(ues);
		actualiserList();
	}
	
	public void vider(Ues objUes) {
		objUes.setLibUes(null);
		objUes.setCodeUeLmd(null);
		objUes.getCreditUe();
	}
	
	public void actualiserList(){
		chargerUe();
		
		}
	
	
	
	
	public void selectionner(){
		setUes(selectedUe);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
		btnModifier.setDisabled(false);
	
	}
	
	
	public void supprimer() {
		Ues ueTemp = new Ues();
		ueTemp.setCodeEus(selectedUe.getCodeEus());
		ueTemp.setCodeUeLmd(selectedUe.getCodeUeLmd());
		ueTemp.setLibUes(selectedUe.getLibUes());
		ueTemp.setCreditUe(selectedUe.getCreditUe());
		ueTemp.setTypeue(selectedUe.getTypeue());
		ueTemp.setMention(selectedUe.getMention());
		ueTemp.setSemestreLmd(selectedUe.getSemestreLmd());
		getService().deleteObject(ueTemp);
		vider(ueTemp);
		vider(ues);
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

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

	public List getListMention() {
		/*if (listMention.isEmpty()) {
		listMention = getService().getObjects("Mention");
	}*/
		return listMention;
	}

	public void setListMention(List listMention) {
		this.listMention = listMention;
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

	public Mention getChoosedMention() {
		return choosedMention;
	}

	public void setChoosedMention(Mention choosedMention) {
		this.choosedMention = choosedMention;
	}

	public List getListFiliere() {
		/*if (listFiliere.isEmpty()) {
			listFiliere = getService().getObjects("Filieres");
		}*/
		return listFiliere;
	}

	public void setListFiliere(List listFiliere) {
		this.listFiliere = listFiliere;
	}

	public InputText getInputOption() {
		return inputOption;
	}

	public void setInputOption(InputText inputOption) {
		this.inputOption = inputOption;
	}

	public InputText getInputAbrevOption() {
		return inputAbrevOption;
	}

	public void setInputAbrevOption(InputText inputAbrevOption) {
		this.inputAbrevOption = inputAbrevOption;
	}
	
	
	
	//Recent
	public List getListSemestreLmd() {
		return listSemestreLmd ;
	}

	public void setListSemestreLmd(List listSemestreLmd) {
		this.listSemestreLmd = listSemestreLmd;
	}

	public SemestreLmd getSelectedSemestreLmd() {
		return selectedSemestreLmd;
	}

	public void setSelectedSemestreLmd(SemestreLmd selectedSemestreLmd) {
		this.selectedSemestreLmd = selectedSemestreLmd;
	}

	public Typeue getSelectedTypeUe() {
		return selectedTypeUe;
	}

	public void setSelectedTypeUe(Typeue selectedTypeUe) {
		this.selectedTypeUe = selectedTypeUe;
	}
	
	public List getListTypeUe() {
		listTypeUe = service.getObjects("Typeue");
		return listTypeUe;

	}

	public void setListTypeUe(List listTypeUe) {
		this.listTypeUe = listTypeUe;
	}

	public String getEtatUe() {
		return etatUe;
	}

	public void setEtatUe(String etatUe) {
		this.etatUe = etatUe;
	}

	public Ues getUes() {
		return ues;
	}

	public void setUes(Ues ues) {
		this.ues = ues;
	}

	public Ues getSelectedUe() {
		return selectedUe;
	}

	public void setSelectedUe(Ues selectedUe) {
		this.selectedUe = selectedUe;
	}
	
	

	public int getTotalCreditUe() {
		return totalCreditUe;
	}

	public void setTotalCreditUe(int totalCreditUe) {
		this.totalCreditUe = totalCreditUe;
	}

	public List<Ues> getListeUe() {
		return listeUe;
	}

	public void setListeUe(List<Ues> listeUe) {
		this.listeUe = listeUe;
	}

	
}
