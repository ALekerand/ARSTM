package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Antenne;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class AntenneBean {
	@Autowired
	Iservice service;
	@Autowired
	GenerationCodeBean generationCodeBean;
	
	private Antenne antenne = new Antenne();
	private Antenne selectedAntenne = new Antenne();
	private List listAntenne = new ArrayList<>();
	
	// Contr�le de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
	
	
	public void enregistrer(){
		antenne.setCodePole(generationCodeBean.genererCode("Antenne", "ANT"));
		service.addObject(antenne);
		actualiserList();
		vider(antenne);
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		vider(antenne);
		actualiserList();
	}
	
	public void vider(Antenne objAntenne) {
		objAntenne.setCodePole(null);
		objAntenne.setLibellePole(null);
		objAntenne.setMailPole(null);
		objAntenne.setTelephonePole(null);
		objAntenne.setCodePole(null);
	}
	
	public void actualiserList(){
			listAntenne.clear();
			listAntenne = getService().getObjects("Antenne");
		}
	
	public void selectionner(){
		setAntenne(selectedAntenne);
		btnSuprimer.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Antenne antenneTemp = new Antenne();
		actualiserList();
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression effcetu�e!", null));
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

	public Antenne getAntenne() {
		return antenne;
	}

	public void setAntenne(Antenne antenne) {
		this.antenne = antenne;
	}

	public Antenne getSelectedAntenne() {
		return selectedAntenne;
	}

	public void setSelectedAntenne(Antenne selectedAntenne) {
		this.selectedAntenne = selectedAntenne;
	}

	public List getListAntenne() {
		return listAntenne = service.getObjects("Antenne");
	}

	public void setListAntenne(List listAntenne) {
		this.listAntenne = listAntenne;
	}
}
