package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Diplomes;
import com.ARSTM.service.Iservice;

@Component
public class DiplomeBean {
	@Autowired
	Iservice service;
	private Diplomes diplomes = new Diplomes();
	private Diplomes selectedDiplome = new Diplomes();
	private List listDiplome = new ArrayList<>();
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	
	public void enregistrer(){
		getService().addObject(diplomes);
		actualiserList();
		vider(diplomes);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		vider(diplomes);
		actualiserList();
	}
	
	public void vider(Diplomes objDiplome) {
		objDiplome.setLibDiplome(null);
		objDiplome.setAbrevDiplome(null);
	}
	
	public void actualiserList(){
		listDiplome.clear();
		listDiplome = getService().getObjects("Diplomes");
		}
	
	public void selectionner(){
		setDiplomes(selectedDiplome);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Diplomes diplomTemp = new Diplomes();
		diplomTemp.setCodeDiplome(selectedDiplome.getCodeDiplome());
		diplomTemp.setAbrevDiplome(selectedDiplome.getAbrevDiplome());
		getService().deleteObject(diplomTemp);
		vider(diplomTemp);
		actualiserList();
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
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

	public Diplomes getDiplomes() {
		return diplomes;
	}

	public void setDiplomes(Diplomes diplomes) {
		this.diplomes = diplomes;
	}

	public Diplomes getSelectedDiplome() {
		return selectedDiplome;
	}

	public void setSelectedDiplome(Diplomes selectedDiplome) {
		this.selectedDiplome = selectedDiplome;
	}

	public List getListDiplome() {
		if (this.listDiplome.isEmpty()) {
			listDiplome = getService().getObjects("Diplomes");
		}
		return listDiplome;
	}

	public void setListDiplome(List listDiplome) {
		this.listDiplome = listDiplome;
	}
}
