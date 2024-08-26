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
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.requetes.RequeteFiliere2;
import com.ARSTM.requetes.RequeteMention;
import com.ARSTM.requetes.RequeteSection;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class ParcourSectionBean {
	@Autowired
	Iservice service;
	
	@Autowired
	RequeteFiliere2 requeteFiliere2;
	@Autowired
	RequeteMention requeteMention;
	@Autowired
	RequeteSection requeteSection;
	
	private Section section = new Section();
	private Section selectedSection = new Section();
	private List listeSection = new ArrayList<>();
	
	private String cb_exam;
	
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
			//inputOption.setDisabled(false);
			//inputAbrevOption.setDisabled(false);
			chargerFiliere();
		}
		
	}
public void choisirExam(){
	switch (cb_exam) {
	case "Oui":
		section.setSectionExam("Oui");
		
		break;
	case "Non":
		section.setSectionExam("Non");
		
		break;

	default:
		break;
		
	}	
}
	
public void activerChamps2(){
		//Recuperer les section de la mention
	
	listeSection = requeteSection.recupSectionByMention(choosedMention.getCodeMention());
		/*if ((!(choosedEcole.getNomEcole().equals(null))) && (!(choosedFiliere.getNomFiliere().equals(null))))
				{
			inputOption.setDisabled(false);
			inputAbrevOption.setDisabled(false);
			chargerMention();
		}*/
		
	}
	
public void chargerFiliere(){
	listFiliere.clear();
	listFiliere = requeteFiliere2.recupFiliere2ByEcole(choosedEcole.getCodeEcole());
}

public void chargerMention(){
	listMention.clear();
	listMention = requeteMention.recupMentionByEcoleFiliere(choosedFiliere.getCodeFiliere());
	//System.out.println("------- Taille de la liste mention"+listMention.size());
}
		
	public void enregistrer(){
		//System.out.println("-----DEBUT Enregistrement OK---");
		section.setAbrevSection(getSection().getAbrevSection().toUpperCase());
		section.setMention(choosedMention);
		section.setSectionExam(cb_exam);
		getService().addObject(section);
		cb_exam ="";
		activerChamps2();
		vider(section);
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}
	
	public void modifier(){
		getService().updateObject(section);
		vider(section);
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}
	
	

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(section);
	}
	
	public void vider(Section objSection) {
		objSection.setNomSection(null);
		objSection.setAbrevSection(null);
	}
	
	public void selectionner(){
		setSection(selectedSection);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
		btnModifier.setDisabled(false);
	}
	
	
	public void supprimer() {
		//Ecole ecoleTemp = new Ecole();
		Section sectionTemp = new Section();		
		sectionTemp.setCodeSection(selectedSection.getCodeSection());
		sectionTemp.setNomSection(selectedSection.getNomSection());
		sectionTemp.setAbrevSection(selectedSection.getAbrevSection());
		sectionTemp.setMention(selectedSection.getMention());
		getService().deleteObject(sectionTemp);
		vider(sectionTemp);
		vider(section);
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

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Section getSelectedSection() {
		return selectedSection;
	}

	public void setSelectedSection(Section selectedSection) {
		this.selectedSection = selectedSection;
	}

	public String getCb_exam() {
		return cb_exam;
	}

	public void setCb_exam(String cb_exam) {
		this.cb_exam = cb_exam;
	}

	public List getListMention() {
		
		return listMention;
	}

	public void setListMention(List listMention) {
		this.listMention = listMention;
	}

	public List getListeSection() {
		return listeSection;
	}

	public void setListeSection(List listeSection) {
		this.listeSection = listeSection;
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
}
