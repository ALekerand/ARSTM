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
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.requetes.RequeteFiliere2;
import com.ARSTM.requetes.RequeteMention;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class MentionBean {
	@Autowired
	Iservice service;
	
	@Autowired
	RequeteFiliere requeteFiliere;
	@Autowired
	RequeteMention requeteMention;
	@Autowired
	RequeteFiliere2 requeteFiliere2;
	
	
	
	private Mention mention = new Mention();
	private Mention selectedMention = new Mention();
	private Ecole choosedEcole = new Ecole();
	private Filieres choosedFiliere = new Filieres();
	private Cycle choosedCycle = new Cycle();
	
	
	
	private List listMention = new ArrayList<>();
	private List listEcole = new ArrayList<>();
	private List listFiliere = new ArrayList<>();
	private List listCycle = new ArrayList<>();
	
	private String cb_niveau ;
	
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
	
	public void activerChamps(){
		
		if ((!(choosedEcole.getNomEcole().equals(null))) && (!(choosedFiliere.getNomFiliere().equals(null))))
				{
			inputOption.setDisabled(false);
			inputAbrevOption.setDisabled(false);
			//chargerFiliere2();
			//chargerListFilbyEcole();
			chargerMention();
		}
		
	}
	
public void chargerFiliere(){
	listFiliere.clear();
	listFiliere = requeteFiliere.recupFiliereByEcole2(choosedEcole.getCodeEcole());
	
	
}

public void chargerFiliere2(){
	listFiliere.clear();
	listFiliere = requeteFiliere2.recupFiliere2ByEcole(choosedEcole.getCodeEcole());
	
	
}

public void chargerMention(){
	listMention.clear();
	listMention = requeteMention.recupMentionByEcoleFiliere(choosedFiliere.getCodeFiliere());
}
		
	public void enregistrer(){
		//System.out.println("-----DEBUT Enregistrement OK---");
		mention.setAbrevMention(getMention().getAbrevMention().toUpperCase());
		mention.setNiveauMention(getCb_niveau());
		mention.setCycle(choosedCycle);
		mention.setFilieres(choosedFiliere);
		choisirAnne();
		getService().addObject(mention);
		actualiserList();
		vider(mention);
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}
	
	public void enregistrer2(){
		mention.setAbrevMention(getMention().getAbrevMention().toUpperCase());
		mention.setNiveauMention(getCb_niveau());
		mention.setCycle(choosedCycle);
		mention.setFilieres(choosedFiliere);
		choisirAnne();
		getService().addObject(mention);
		actualiserList();
		vider(mention);
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}
	
		public void choisirAnne(){
		switch (cb_niveau) {
		case "Licence 1":
			mention.setAnneeMention("1ère Année");
			
			break;
			
		case "Licence 2":
			mention.setAnneeMention("2ème Année");
			
			break;
			
		case "Licence 3":
			mention.setAnneeMention("3ème Année");
			
			break;
			
		case "Master 1":
			mention.setAnneeMention("4ème Année");
			
			break;
			
		case "Master 2":
			mention.setAnneeMention("5ème Année");
			
			break;
			
		case "Doctorat 1":
			mention.setAnneeMention("6ème Année");
			
			break;
			
		case "Doctorat 2":
			mention.setAnneeMention("7ème Année");
			
			break;
		case "Doctorat 3":
			mention.setAnneeMention("8ème Année");
			
			break;

		default:
			break;
		}
	}
	public void modifier(){
		getService().updateObject(mention);
		vider(mention);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}
	
	

	public void annuler() {
		
		
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(mention);
		actualiserList();
	}
	
	public void vider(Mention objMention) {
		objMention.setLibMention(null);;
		objMention.setAbrevMention(null);
	}
	
	public void actualiserList(){
		chargerMention();
		}
	
	public void selectionner(){
		setMention(selectedMention);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
		btnModifier.setDisabled(false);
	}
	
	
	public void supprimer() {
		//Ecole ecoleTemp = new Ecole();
		Mention mentionTemp = new Mention();		
		mentionTemp.setCodeMention(selectedMention.getCodeMention());
		mentionTemp.setLibMention(selectedMention.getLibMention());
		mentionTemp.setAbrevMention(selectedMention.getAbrevMention());
		getService().deleteObject(mentionTemp);
		vider(mentionTemp);
		vider(mention);
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

	public Mention getMention() {
		return mention;
	}

	public void setMention(Mention mention) {
		this.mention = mention;
	}

	public Mention getSelectedMention() {
		return selectedMention;
	}

	public void setSelectedMention(Mention selectedMention) {
		this.selectedMention = selectedMention;
	}

	public List getListMention() {
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

	public List getListFiliere() {
		/*if (listFiliere.isEmpty()) {
			listFiliere = getService().getObjects("Filieres");
		}*/
		return listFiliere;
	}

	public void setListFiliere(List listFiliere) {
		this.listFiliere = listFiliere;
	}

	public String getCb_niveau() {
		return cb_niveau;
	}

	public void setCb_niveau(String cb_niveau) {
		this.cb_niveau = cb_niveau;
	}

	public Cycle getChoosedCycle() {
		return choosedCycle;
	}

	public void setChoosedCycle(Cycle choosedCycle) {
		this.choosedCycle = choosedCycle;
	}

	public List getListCycle() {
		if (listCycle.isEmpty()) {
			listCycle = getService().getObjects("Cycle");
		}
		return listCycle;
	}

	public void setListCycle(List listCycle) {
		this.listCycle = listCycle;
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
