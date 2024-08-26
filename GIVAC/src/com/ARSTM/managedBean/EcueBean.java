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
import com.ARSTM.model.Ecue;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Mention;
import com.ARSTM.model.Section;
import com.ARSTM.model.SemestreLmd;
import com.ARSTM.model.Typeue;
import com.ARSTM.model.Ues;
import com.ARSTM.requetes.RequeteEcue;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.requetes.RequeteFiliere2;
import com.ARSTM.requetes.RequeteMention;
import com.ARSTM.requetes.RequeteSection;
import com.ARSTM.requetes.RequeteSemestreLmd;
import com.ARSTM.requetes.RequeteUes;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class EcueBean {
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
	RequeteEcue requeteEcue;
	
	@Autowired
	RequeteSemestreLmd requeteSemestreLmd;
	
	//New dclaration
	private SemestreLmd selectedSemestreLmd = new SemestreLmd();
	private List listSemestreLmd = new ArrayList<>();
	private Typeue selectedTypeUe = new Typeue();
	private List listTypeUe = new ArrayList<>();
	private Ues ues = new Ues();
	private String etatEcue;
	private Ecue ecue = new Ecue();
	
	
	private Ues selectedUe = new Ues();
	private Ecue selectedEcue = new Ecue();
	private List<Ues> listeUe = new ArrayList<Ues>();
	private List<Ecue> listeEcue = new ArrayList<Ecue>();

	private int totalCreditEcue;
	
	
	
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
		}

public void chargerEcue(){
	listeEcue.clear();
	listeEcue = requeteEcue.recupEcueByUe(selectedUe.getCodeEus());
	//Calculer le total credit
	totalCreditEcue = 0;
		for (Ecue objUE : listeEcue) {
			totalCreditEcue += objUE.getCreditEcue();
		}
		}
	//repartion des credits ecue
public void calculer() {
	System.out.println("Je suis dans la méthode");
	ecue.setTpeEcue((long) (ecue.getCoursEcue()+ ecue.getTpEcue()));
	//System.out.println("-------  la valeur TPE"+ecue.getTpeEcue());
	ecue.setCttEcue((long) (ecue.getTpeEcue()+ ecue.getCoursEcue()));
	//System.out.println("-------  la valeur CTT"+ecue.getCttEcue());
	ecue.setCreditEcue((long)ecue.getCttEcue()/25);
	//System.out.println("-------  la valeur CREDIT"+ecue.getCreditEcue());
	ecue.setCoefEcue(ecue.getCreditEcue());
	//System.out.println("-------  la valeur COEF"+ecue.getCoefEcue());
}
			
	public void enregistrer(){
		ecue.setUes(selectedUe);
		ecue.setAbrevEcue(getEcue().getAbrevEcue().toUpperCase());
		ecue.setEtatEcue(true);
		service.addObject(ecue);
		chargerEcue();
		vider(ecue);
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}
	
	public void modifier(){
		if (etatEcue.equals("true")) {
			ecue.setEtatEcue(true);
		}
		
		if (etatEcue.equals("false")) {
			ecue.setEtatEcue(false);
		}
		ecue.setUes(selectedUe);
		ecue.setAbrevEcue(getEcue().getAbrevEcue().toUpperCase());
		getService().updateObject(ecue);
		vider(ecue);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}
	
	

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(ecue);
	}
	
	public void vider(Ecue objEcue) {
		objEcue.setAbrevEcue(null);
		objEcue.setLibEcue(null);
		objEcue.setCoursEcue(null);
		objEcue.setCreditEcue(null);
		objEcue.setCoefEcue(null);
		objEcue.setCttEcue(null);
		objEcue.setTpEcue(null);
		objEcue.setTpeEcue(null);
	}
	
	public void actualiserList(){
		chargerUe();
		
		}
	
	public void selectionner(){
		setEcue(selectedEcue);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
		btnModifier.setDisabled(false);
	
	}
	
	
	public void supprimer() {
		Ecue EcueTemp = new Ecue();
		EcueTemp.setCodeEcue(selectedEcue.getCodeEcue());
		//EcueTemp.setUes(selectedEcue.getUes().getCodeEus());
		//EcueTemp.setUes(selectedUe.getCodeEus());
		EcueTemp.setUes(selectedUe);
		EcueTemp.setLibEcue(selectedEcue.getLibEcue());
		EcueTemp.setAbrevEcue(selectedEcue.getAbrevEcue());
		EcueTemp.setCoursEcue(selectedEcue.getCoursEcue());
		EcueTemp.setCoefEcue(selectedEcue.getCoefEcue());
		EcueTemp.setCreditEcue(selectedEcue.getCreditEcue());
		EcueTemp.setCttEcue(selectedEcue.getCttEcue());
		EcueTemp.setTpEcue(selectedEcue.getTpEcue());
		EcueTemp.setTpeEcue(selectedEcue.getTpeEcue());
		getService().deleteObject(EcueTemp);
		vider(EcueTemp);
		vider(ecue);
		chargerEcue();
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

	public String getEtatEcue() {
		return etatEcue;
	}

	public void setEtatEcue(String etatEcue) {
		this.etatEcue = etatEcue;
	}

	public Ues getUes() {
		return ues;
	}

	public void setUes(Ues ues) {
		this.ues = ues;
	}

	public Ecue getEcue() {
		return ecue;
	}

	public void setEcue(Ecue ecue) {
		this.ecue = ecue;
	}

	public Ues getSelectedUe() {
		return selectedUe;
	}

	public void setSelectedUe(Ues selectedUe) {
		this.selectedUe = selectedUe;
	}
	
	public Ecue getSelectedEcue() {
		return selectedEcue;
	}

	public void setSelectedEcue(Ecue selectedEcue) {
		this.selectedEcue = selectedEcue;
	}

	public int getTotalCreditEcue() {
		return totalCreditEcue;
	}

	public void setTotalCreditEcue(int totalCreditEcue) {
		this.totalCreditEcue = totalCreditEcue;
	}

	public List<Ues> getListeUe() {
		return listeUe;
	}

	public void setListeUe(List<Ues> listeUe) {
		this.listeUe = listeUe;
	}

	public List<Ecue> getListeEcue() {
		return listeEcue;
	}

	public void setListeEcue(List<Ecue> listeEcue) {
		this.listeEcue = listeEcue;
	}

	
}
