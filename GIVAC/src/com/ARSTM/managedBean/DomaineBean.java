package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Domaine;
import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class DomaineBean {
	@Autowired
	Iservice service;
	private Domaine domaine = new Domaine();
	private Domaine selectedDomaine = new Domaine();
	private List listDomaine = new ArrayList<>();
				
	// Contrôle de composant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	@PostConstruct
public void initialiser(){
	//btnValider.setDisabled(false);
	btnSuprimer.setDisabled(true);
	btnModifier.setDisabled(true);
	
}
		
	public void enregistrer(){
		domaine.setLibDomaine(getDomaine().getLibDomaine().toUpperCase());
		getService().addObject(domaine);
		actualiserList();
		vider(domaine);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}
	
	public void modifier(){
		getService().updateObject(domaine);
		vider(domaine);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(domaine);
		actualiserList();
	}
	
	public void vider(Domaine objDomaine) {
		objDomaine.setLibDomaine(null);
	}
	
	public void actualiserList(){
			listDomaine.clear();
			listDomaine = getService().getObjects("Domaine");
		}
	
	public void selectionner(){
		setDomaine(selectedDomaine);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
		btnModifier.setDisabled(false);
	}
	
	
	public void supprimer() {
		Domaine domaineTemp = new Domaine();
		domaineTemp.setCodeDomaine(selectedDomaine.getCodeDomaine());
		domaineTemp.setLibDomaine(selectedDomaine.getLibDomaine());
		getService().deleteObject(domaineTemp);
		vider(domaineTemp);
		vider(domaine);
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

	public Domaine getDomaine() {
		return domaine;
	}

	public void setDomaine(Domaine domaine) {
		this.domaine = domaine;
	}

	public Domaine getSelectedDomaine() {
		return selectedDomaine;
	}

	public void setSelectedDomaine(Domaine selectedDomaine) {
		this.selectedDomaine = selectedDomaine;
	}

	public List getListDomaine() {
		if (listDomaine.isEmpty()) {
			listDomaine = getService().getObjects("Domaine");
		}
		return listDomaine;
	}

	public void setListDomaine(List listDomaine) {
		this.listDomaine = listDomaine;
	}
}
