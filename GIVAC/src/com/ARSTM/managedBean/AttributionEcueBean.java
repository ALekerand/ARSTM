package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.Cycle;
import com.ARSTM.model.Ecole;
import com.ARSTM.model.Ecue;
import com.ARSTM.model.Enseignant;
import com.ARSTM.model.Enseigner;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Mention;
import com.ARSTM.model.Section;
import com.ARSTM.model.SemestreLmd;
import com.ARSTM.model.Typeue;
import com.ARSTM.model.Ues;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.requetes.RequeteEcue;
import com.ARSTM.requetes.RequeteEnseignant;
import com.ARSTM.requetes.RequeteEnseigner;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.requetes.RequeteFiliere2;
import com.ARSTM.requetes.RequeteMention;
import com.ARSTM.requetes.RequeteSection;
import com.ARSTM.requetes.RequeteSection2;
import com.ARSTM.requetes.RequeteSemestreLmd;
import com.ARSTM.requetes.RequeteUes;
import com.ARSTM.service.Iservice;

@Component
public class AttributionEcueBean {
	@Autowired
	Iservice service;
	
	@Autowired
	RequeteFiliere requeteFiliere;
	@Autowired
	RequeteMention requeteMention;
	@Autowired
	RequeteSection2 requeteSection2;
	@Autowired
	RequeteUes requeteUes;
	@Autowired
	RequeteEcue requeteEcue;
	
	@Autowired
	RequeteSemestreLmd requeteSemestreLmd;
	@Autowired
	RequeteEnseignant requeteEnseignant;
	
	@Autowired
	ReqAnneeScolaire reqAnneeScolaire;
	
	@Autowired
	RequeteEnseigner requeteEnseigner;
	//New dclaration
	private Enseigner enseigner = new Enseigner();
	
	private AnneesScolaire anneEncoure = new AnneesScolaire();
	
	private SemestreLmd selectedSemestreLmd = new SemestreLmd();
	private List listSemestreLmd = new ArrayList<>();
	private Typeue selectedTypeUe = new Typeue();
	private List listTypeUe = new ArrayList<>();
	private Ues ues = new Ues();
	private String etatEcue;
	private Ecue ecue = new Ecue();
	private Section section = new Section();
	private Enseignant enseignant = new Enseignant();
	
	
	private Ues selectedUe = new Ues();
	private Section selectedSection = new Section();
	private Ecue selectedEcue = new Ecue();
	private Enseignant selectedEnseignant = new Enseignant();
	private List<Ues> listeUe = new ArrayList<Ues>();
	private List<Section> listeSection = new ArrayList<Section>();
	private List<Ecue> listeEcue = new ArrayList<Ecue>();
	private List<Enseignant> listeEnseignant = new ArrayList<Enseignant>();
	private Ecue ecueSelectionne = new Ecue();
	private List<Enseigner> listeEnseigner = new ArrayList<>();

	private int totalCreditEcue;
	
	
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
	btnSuprimer.setDisabled(true);
	btnModifier.setDisabled(true);
	recupererAnne();
}
	
	
	public AnneesScolaire recupererAnne(){
		anneEncoure = reqAnneeScolaire.recupererDerniereAnneeScolaire().get(0);
		System.out.println("Année:"+anneEncoure.getLibAnneeScolaire());
		return anneEncoure;
	}

	
  public void chargerSemestreEtSection(){
	listSemestreLmd.clear();
	listSemestreLmd = requeteSemestreLmd.recupSemestreByNiveau(choosedMention.getNiveauMention());
	listeSection.clear();
	listeSection = requeteSection2.recupSectionByMention(choosedMention.getCodeMention());
	}
  
  
 public void chargerListEcueAttibues(){
	listeEnseigner.clear();
	listeEnseigner = requeteEnseigner.recupEnsegnerBySection1(anneEncoure.getCodeAnnees(), selectedSection.getCodeSection());
 }
 
 
 public List<Ecue> chargerListECUEActualisee() {
	 List<Ecue> listTempo = new ArrayList<>();
	 List<Ecue> maList = new ArrayList<>();
	 
	 for (Ecue ecue : listeEcue) {
		 maList.add(ecue);
	}
	 
	 for (Ecue varEcue : maList) {
		 for (Enseigner varEnseig : listeEnseigner) {
			if (varEcue.getCodeEcue() == varEnseig.getEcue().getCodeEcue()) {
				listTempo.add(varEcue);
				//break;
			}
		}
	}
	 
	 
	 listeEcue.removeAll(listTempo); 
	 
	 
	 System.out.println("====Taille de la liste Tempom :"+listTempo.size());
	// listeEcue.remove(listTempo);
	 
	 
	 System.out.println("====Taille de la liste des Ecue :"+listeEcue.size());
	return listeEcue;

 }
		
	
public void chargerFiliere(){
	listMention.clear();
	listFiliere.clear();
	listSemestreLmd.clear();
	listeSection.clear();
	listeEcue.clear();
	listeEnseignant.clear();
	listeEnseigner.clear();
	
	 if (!(choosedEcole == null)) {
			listFiliere = requeteFiliere.recupFiliereByEcole2(choosedEcole.getCodeEcole());
}

}

public void chargerMention(){
	listMention.clear();
	listSemestreLmd.clear();
	listeSection.clear();
	listeEcue.clear();
	listeEnseignant.clear();
	listeEnseigner.clear();
	
	 if (!(choosedFiliere == null)) {
			listMention = requeteMention.recupMentionByEcoleFiliere(choosedFiliere.getCodeFiliere());
}
	
}

	

public void chargerEnseignant(){
	listeEnseignant.clear();
	listeEnseignant = requeteEnseignant.recupEnseignant();
}


public void chargerEcueMention(){
	listeUe.clear();
	listeEcue.clear();
	listeUe = requeteUes.recupUesByMentionSemestre(choosedMention.getCodeMention(),selectedSemestreLmd.getCodeSemestreLmd());
	totalCreditEcue = 0;
	for (Ues varUE : listeUe) {
		for (Ecue varEcue : varUE.getEcues()) {
			listeEcue.add(varEcue);
			totalCreditEcue += varEcue.getCreditEcue();
		}
	}
	System.out.println("===Taille liste ECUE:"+listeEcue.size());
}


			
	public void enregistrer(){
		enseigner.setEcue(ecueSelectionne);
		enseigner.setEnseignant(selectedEnseignant);
		enseigner.setSection(selectedSection);
		enseigner.setTauxHoraireEffectif(selectedSection.getMention().getCycle().getTauxHoraire());
		try {
			enseigner.setVhEffectif((int) (ecueSelectionne.getCoursEcue()+ ecueSelectionne.getTpEcue()));
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veuillez choisir l'ECUE!", null));
		}
		enseigner.setAnneesScolaire(anneEncoure);
		enseigner.setEtatDispo(false);
		enseigner.setDateEnseigner(new Date());
		service.addObject(enseigner);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
		chargerListEcueAttibues();
		chargerListECUEActualisee();
		
		viderEnseignant(selectedEnseignant);
		viderSemetres(selectedSemestreLmd);
	}
		
	public void lierSemestre(){

	}
	
	

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		viderEnseigner(enseigner);
		

		
	}
	
	public void viderEnseigner(Enseigner objEnseig) {
		objEnseig.setSemestres(null);
		objEnseig.setEcue(null);
		objEnseig.setEnseignant(null);
		objEnseig.setSection(null);
		objEnseig.setAnneesScolaire(null);
		objEnseig.setTauxHoraireEffectif(null);
	}
	
	
	public void viderEcole(Ecole objetEcole) {
		objetEcole.setAbrevEcole(null);
		objetEcole.setCodeEcole(0);
		objetEcole.setFiliereses(null);
		objetEcole.setNomEcole(null);
	}
	
	public void viderFiliere(Filieres objetFiliere) {
		objetFiliere.setNomFiliere(null);
		objetFiliere.setAbrevFiliere(null);
	}
	
	
	public void viderSemetres(SemestreLmd objetSemestre) {
		objetSemestre.setCodeSemestreLmd(0);	
		}
	
	public void viderEnseignant(Enseignant objet) {
		objet.setMatriculeEns(null);
		}
	
	
	
	
	public void chargerListeEcue(){
		chargerEcueMention();
		chargerEnseignant();
		chargerListEcueAttibues();
		chargerListECUEActualisee();
		}
	
	public void selectionner(){
		setEcue(selectedEcue);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
		btnModifier.setDisabled(false);
	
	}
	
	
	public void supprimer() {
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

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
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

	public Section getSelectedSection() {
		return selectedSection;
	}

	public void setSelectedSection(Section selectedSection) {
		this.selectedSection = selectedSection;
	}

	public Enseignant getSelectedEnseignant() {
		return selectedEnseignant;
	}

	public void setSelectedEnseignant(Enseignant selectedEnseignant) {
		this.selectedEnseignant = selectedEnseignant;
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

	public List<Section> getListeSection() {
		return listeSection;
	}

	public void setListeSection(List<Section> listeSection) {
		this.listeSection = listeSection;
	}

	public List<Enseignant> getListeEnseignant() {
		return listeEnseignant;
	}

	public void setListeEnseignant(List<Enseignant> listeEnseignant) {
		this.listeEnseignant = listeEnseignant;
	}

	public Enseigner getEnseigner() {
		return enseigner;
	}

	public void setEnseigner(Enseigner enseigner) {
		this.enseigner = enseigner;
	}




	public Ecue getEcueSelectionne() {
		return ecueSelectionne;
	}


	public void setEcueSelectionne(Ecue ecueSelectionne) {
		this.ecueSelectionne = ecueSelectionne;
	}


	public List<Enseigner> getListeEnseigner() {
		return listeEnseigner;
	}


	public void setListeEnseigner(List<Enseigner> listeEnseigner) {
		this.listeEnseigner = listeEnseigner;
	}

	
}
