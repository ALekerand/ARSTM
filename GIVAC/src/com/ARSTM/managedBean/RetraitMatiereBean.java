/*package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.ARSTM.model.Enseigner;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Matiere;
import com.ARSTM.model.Rattacher;
import com.ARSTM.model.RattacherId;
import com.ARSTM.model.Section;
import com.ARSTM.requetes.ReqEnseigner;
import com.ARSTM.requetes.ReqRattacher;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.requetes.RequeteSection;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class RetraitMatiereBean {
	@Autowired
	Iservice service;
	@Autowired
	ReqRattacher reqRattacher;
	@Autowired
	ReqEnseigner  reqEnseigner;
	
	private Section section = new Section();
	private List<Rattacher> listRattachers = new ArrayList<>();
	private Rattacher selectedRattacher = new Rattacher();
	private Rattacher rattacher;
	private Section choosedSection = new Section();
	private List listSection = new ArrayList<>();


	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
		
		public void retirer(){
		//Vérifier que la matière n'est pas attribuée
			System.out.println("----Section "+rattacher.getSection().getCodeSection());//clean after
			System.out.println("----Matière "+rattacher.getMatiere().getCodeMatiere());//clean after
			List list = reqEnseigner.recupEnsegnerBySectMat(rattacher.getSection().getCodeSection(), rattacher.getMatiere().getCodeMatiere());
		if (list.isEmpty()) {
			
			// Suprimer
			getService().deleteObject(rattacher);
			actualiserList();
		}else{ 
			//Cas contraire envoyer un message
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Supression impossible. Cette matière est attribuée pour des cours. Veuillez contacter l'administrateur SVP !", null));
		}
		}
		
	
	public void actualiserList(){
		listRattachers.clear();
		listRattachers = reqRattacher.recupRatacherBySection(choosedSection.getCodeSection());

		}
	
	public void selectionner(){
		System.out.println("----------------Ligne selectionnée!");//clean after
		System.out.println("----------------RAttacher selectionné (matiere):" +selectedRattacher.getCodeMatLmd());//clean after
		System.out.println("----------------RAttacher selectionné (section):" +selectedRattacher.getSection().getCodeSection());//clean after
	    rattacher = new Rattacher();
	    rattacher.setId(selectedRattacher.getId());
	    rattacher.setMatiere(selectedRattacher.getMatiere());
	    rattacher.setSection(selectedRattacher.getSection());
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
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

	
	public Section getChoosedSection() {
		return choosedSection;
	}

	public void setChoosedSection(Section choosedSection) {
		this.choosedSection = choosedSection;
	}

	public void setListSection(List listSection) {
		this.listSection = listSection;
	}

	public ReqEnseigner getReqEnseigner() {
		return reqEnseigner;
	}

	public void setReqEnseigner(ReqEnseigner reqEnseigner) {
		this.reqEnseigner = reqEnseigner;
	}

	public Rattacher getSelectedRattacher() {
		return selectedRattacher;
	}

	public void setSelectedRattacher(Rattacher selectedRattacher) {
		this.selectedRattacher = selectedRattacher;
	}


	public ReqRattacher getReqRattacher() {
		return reqRattacher;
	}


	public void setReqRattacher(ReqRattacher reqRattacher) {
		this.reqRattacher = reqRattacher;
	}

}
*/