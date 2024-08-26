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
import com.ARSTM.model.Rattacher;
import com.ARSTM.model.RattacherId;
import com.ARSTM.model.Section;
import com.ARSTM.requetes.ReqRattacher;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.requetes.RequeteSection;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class AjoutMatiereBean {
	@Autowired
	Iservice service;
	@Autowired
	RequeteFiliere requeteFiliere; 
	@Autowired
	RequeteSection requeteSection;
	@Autowired
	ReqRattacher reqRattacher;
	private Section choosedSection = new Section();
	private List<Section> listSection = new ArrayList<>();
	private List<Matiere> listeMatieres = new ArrayList<>();
	private List<Matiere> selectedMatieres = new ArrayList<>();
	private List<> listRattachers = new ArrayList<>();//

	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
		@SuppressWarnings("unchecked")
		public List<Matiere> chargerListesMatiere(){
		//Recuperer la liste de toutes les matières 
		listRattachers.clear();
		listeMatieres.clear();
		listeMatieres = getService().getObjects("Matiere");
		
		//Recuperer la liste des matières déjà affectée à la section
		try {
			listRattachers = reqRattacher.recupRatacherBySection(choosedSection.getCodeSection());
		} catch (NullPointerException e) {
			
		}
		
		//Trier la liste totale des matiere pour en retenir celles déjà afectées à la section
		for (Rattacher varRattacher : listRattachers) {
			for (Matiere varMatieres : listeMatieres) {
				if(varMatieres.getCodeMatiere() == varRattacher.getMatiere().getCodeMatiere()){
					listeMatieres.remove(varMatieres);
					break;
				}
				}
		}
		return listeMatieres;
	}
		
	public String enregistrer(){
		for(Rattacher VarRattacher: getListRattachers()){
			RattacherId rattacherId = new RattacherId(choosedSection.getCodeSection(), VarRattacher.getMatiere().getCodeMatiere());
			VarRattacher.setId(rattacherId);
			VarRattacher.setDateSectionMatiere(Calendar.getInstance().getTime());
			getService().addObject(VarRattacher);
		}
		actualiserList();
		listeMatieres.clear();
		selectedMatieres.clear();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Matières ajoutées à la section!", null));
		return "pages/ajout_matieres?faces-redirect";
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		actualiserList();
	}
	
	public void actualiserList(){
		listRattachers.clear();
		listRattachers = reqRattacher.recupRatacherBySection(choosedSection.getCodeSection());
		}
	
	
	public void selectionner(){
		setSection(choosedSection);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void chargerListeEditable(){
		listRattachers.clear();
		for(Matiere varMat: selectedMatieres){
			RattacherId rattacherId = new RattacherId(choosedSection.getCodeSection(), varMat.getCodeMatiere());
			Rattacher rattacher = new Rattacher(rattacherId, varMat, choosedSection);
			listRattachers.add(rattacher);
		}
	}
	
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
	
	
	//**************************ACCESSEURS*************************//*
	
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

	public List getListeMatieres() {
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

	public List<Rattacher> getListRattachers() {
		return listRattachers;
	}

	public void setListRattachers(List<Rattacher> listRattachers) {
		this.listRattachers = listRattachers;
	}

	@SuppressWarnings("unchecked")
	public List<Section> getListSection() {
		if (listSection.isEmpty()) {
			listSection = getService().getObjects("Section");
		}
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

	public ReqRattacher getReqRattacher() {
		return reqRattacher;
	}

	public void setReqRattacher(ReqRattacher reqRattacher) {
		this.reqRattacher = reqRattacher;
	}

	public Section getChoosedSection() {
		return choosedSection;
	}

	public void setChoosedSection(Section choosedSection) {
		this.choosedSection = choosedSection;
	}

}*/