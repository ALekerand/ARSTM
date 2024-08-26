/*package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Cycle;
import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Matiere;
import com.ARSTM.model.Section;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.requetes.RequeteSection;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class SectionBean {
	@Autowired
	Iservice service;
	@Autowired
	RequeteFiliere requeteFiliere; 
	@Autowired
	RequeteSection requeteSection;
	
	private Section section = new Section();
	private Section selectedSection = new Section();
	private List listeMatieres = new ArrayList<>();
	private List<Matiere> selectedMatieres = new ArrayList<>();
	//private List<Rattacher> listRattachers = new ArrayList<>();
	private Cycle choosedCycle = new Cycle();
	private Filieres choosedFiliere = new Filieres();
	private Ecole ecole = new Ecole();
	private List listeFiliere = new ArrayList<>();
	private List listCycle = new ArrayList<>();
	private Boolean sectionExam;
	private Ecole choosedEcole = new Ecole();
	private List<Ecole> listEcole = new ArrayList<>();
	private List<Section> listSection = new ArrayList<>();
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	
	public String enregistrer(){
		section.setAbrevSection(section.getAbrevSection().toUpperCase());
		section.setSectionExam(getSectionExam());
		//section.setFilieres(choosedFiliere);
		//section.setCycle(choosedCycle);
		section.setDateCreaSection(Calendar.getInstance().getTime());
		getService().addObject(section);
		//Ajouter dans la table rattacher
		for(Rattacher VarRattacher: getListRattachers()){
			RattacherId rattacherId = new RattacherId(section.getCodeSection(), VarRattacher.getMatiere().getCodeMatiere());
			VarRattacher.setId(rattacherId);
			VarRattacher.setCodeMatLmd(VarRattacher.getCodeMatLmd().toUpperCase());
			VarRattacher.setDateSectionMatiere(Calendar.getInstance().getTime());
			getService().addObject(VarRattacher);
		}
		actualiserList();
		vider(section);
		listeMatieres.clear();
		selectedMatieres.clear();
		chargerSection();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
		return "pages/section?faces-redirect";
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		vider(section);
		actualiserList();
	}
	
	public void vider(Section objSection) {
		objSection.getCodeSection();
		objSection.setNomSection(null);
		objSection.setAbrevSection(null);
		//objSection.setFilieres(null);
		objSection.setSectionExam(null);
		//objSection.setCycle(null);
	}
	
	public void actualiserList(){
		listSection.clear();
		listSection = getService().getObjects("Section");
		}
	
	public void selectionner(){
		setSection(selectedSection);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Section sectionTemp = new Section();
		sectionTemp.setCodeSection(selectedSection.getCodeSection());
		sectionTemp.setNomSection(selectedSection.getNomSection());
		sectionTemp.setAbrevSection(selectedSection.getAbrevSection());
		sectionTemp.setSectionExam(selectedSection.getSectionExam());	
		getService().deleteObject(sectionTemp);
		vider(sectionTemp);
		actualiserList();
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression effcetuée!", null));
	}
	
	
	
	public void chargerListeEditable(){
		listRattachers.clear();
		for(Matiere varMat: selectedMatieres){
			RattacherId rattacherId = new RattacherId(section.getCodeSection(), varMat.getCodeMatiere());
			Rattacher rattacher = new Rattacher(rattacherId, varMat, section);
			listRattachers.add(rattacher);
		}
	}
	
	public void chargerSection(){
		listSection.clear();
		listSection = requeteSection.recupSectionByFiliere(choosedFiliere.getCodeFiliere());
	}
	
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
	
	public void chargerFiliere(){
		//Vider les listes avent rechargement
		listeFiliere.clear();
		listeFiliere = getService().getObjects("Filieres");
		listSection.clear();
		//Charger la liste des filières concernées
		try {
			listeFiliere = requeteFiliere.recupFiliereByEcole(choosedEcole.getCodeEcole());
		} catch (NullPointerException npe) {
			// TODO Auto-generated catch block
			 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Le choix d'école est nécessaire", null);
	            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	
	
	*//**************************ACCESSEURS*************************//*
	
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

	public Boolean getSectionExam() {
		return sectionExam;
	}

	public void setSectionExam(Boolean sectionExam) {
		this.sectionExam = sectionExam;
	}

	public List getListeMatieres() {
		if (listeMatieres.isEmpty()) {
			listeMatieres = getService().getObjects("Matiere");
		}
		return listeMatieres;
	}

	public void setListeMatieres(List listeMatieres) {
		this.listeMatieres = listeMatieres;
	}

	public List getSelectedMatieres() {
		return selectedMatieres;
	}

	public void setSelectedMatieres(List selectedMatieres) {
		this.selectedMatieres = selectedMatieres;
	}

	public Filieres getChoosedFiliere() {
		return choosedFiliere;
	}

	public void setChoosedFiliere(Filieres choosedFiliere) {
		this.choosedFiliere = choosedFiliere;
	}

	public List getListeFiliere() {
		if (listeFiliere.isEmpty()) {
			listeFiliere = getService().getObjects("Filieres");
		}
		return listeFiliere;
	}

	public void setListeFiliere(List listeFiliere) {
		this.listeFiliere = listeFiliere;
	}

	public Ecole getEcole() {
		return ecole;
	}

	public void setEcole(Ecole ecole) {
		this.ecole = ecole;
	}

	
	public List<Rattacher> getListRattachers() {
		return listRattachers;
	}

	public void setListRattachers(List<Rattacher> listRattachers) {
		this.listRattachers = listRattachers;
	}

	public Ecole getChoosedEcole() {
		return choosedEcole;
	}

	public void setChoosedEcole(Ecole choosedEcole) {
		this.choosedEcole = choosedEcole;
	}

	@SuppressWarnings("unchecked")
	public List<Ecole> getListEcole() {
		if (listEcole.isEmpty()) {
			listEcole = getService().getObjects("Ecole");
		}
		return listEcole;
	}

	public void setListEcole(List<Ecole> listEcole) {
		this.listEcole = listEcole;
	}

	public RequeteFiliere getRequeteFiliere() {
		return requeteFiliere;
	}

	public void setRequeteFiliere(RequeteFiliere requeteFiliere) {
		this.requeteFiliere = requeteFiliere;
	}

	public List<Section> getListSection() {
		return listSection;
	}

	public void setListSection(List<Section> listSection) {
		this.listSection = listSection;
	}

	public RequeteSection getRequeteSection() {
		return requeteSection;
	}

	public void setRequeteSection(RequeteSection requeteSection) {
		this.requeteSection = requeteSection;
	}

}
*/