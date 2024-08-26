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

import com.ARSTM.model.Domaine;
import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Tformation;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class FiliereBean {
	@Autowired
	Iservice service;
	@Autowired
	RequeteFiliere requeteFiliere;
	
	private Filieres filieres = new Filieres();
	private Filieres selectedFiliere = new Filieres();
	private List listFiliere = new ArrayList<>();
	private List listeFiliereByEcole = new ArrayList<>();
	private Ecole choosedEcole = new Ecole();
	private List listEcole = new ArrayList<>();
	private int codeDomaine;
	private Domaine choosedDoamine = new Domaine();
	private List listeDomaine = new ArrayList<>();
	
	private Tformation choosedTformation = new Tformation();
	private List listTformation = new ArrayList<>();
	
	
	
	
	// Contr�le de composant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
		private InputText inputFiliere = new InputText();
		private InputText inputAbrevFiliere = new InputText();
		private InputText inputFiliere2 = new InputText();
		private InputText inputAbrevFiliere2 = new InputText();
	
		@PostConstruct
	public void initiate(){
		/*
		 * inputFiliere.setDisabled(true); inputAbrevFiliere.setDisabled(true);
		 * inputFiliere2.setDisabled(true); inputAbrevFiliere2.setDisabled(true);
		 */
			btnSuprimer.setDisabled(true);
			btnModifier.setDisabled(true);
	}
		
/*
	  public void activerChamps(){ 
		if ((!(choosedEcole.getNomEcole().equals(null)))&& (!(choosedDoamine.getLibDomaine().equals(null))) &&(!(choosedTformation.getAbrevTformation().equals(null)))) {
		  	inputFiliere.setDisabled(false); 
		  	inputAbrevFiliere.setDisabled(false);
		  	inputFiliere2.setDisabled(false); 
		  	inputAbrevFiliere2.setDisabled(false);
		  	chargerListFilbyEcole(); 
		  	}
		} */
		
		
		public List<Filieres> chargerListFilbyEcole(){
			listeFiliereByEcole.clear();
			for (Filieres filObject : requeteFiliere.recupFiliereByEcole(choosedEcole.getCodeEcole(), choosedTformation.getCodeTformation())){
				listeFiliereByEcole.add(filObject);
			}
			return listeFiliereByEcole;
		}
	
	public void enregistrer(){
		filieres.setAbrevFiliere(filieres.getAbrevFiliere().toUpperCase());//mettre l'attribut en majuscule
		filieres.setAbev2(filieres.getAbev2().toUpperCase());
		filieres.setEcole(choosedEcole);
		filieres.setTformation(choosedTformation);
		filieres.setDomaine((Domaine) getService().getObjectById(codeDomaine, "Domaine"));
		//filieres.setDomaine(choosedDoamine);
		getService().addObject(filieres);
		actualiserList();
		vider(filieres);
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetu�!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(filieres);
		actualiserList();
	}
	
	public void modifier(){
		filieres.setAbrevFiliere(filieres.getAbrevFiliere().toUpperCase());//mettre l'attribut en majuscule
		getService().updateObject(filieres);
		vider(filieres);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetu�e!", null));
	}
	
	public void vider(Filieres objFiliere) {
		
		objFiliere.setAbrevFiliere(null);
		objFiliere.setNomFiliere(null);
		objFiliere.setAbev2(null);
		objFiliere.setNomFiliere2(null);
		
	}
	
	public void actualiserList(){
			listFiliere.clear();
			listFiliere = getService().getObjects("Filieres");
			chargerListFilbyEcole();
		}
	
	public void selectionner(){
		setFilieres(selectedFiliere);
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
		
	}
	
	public void supprimer() {
		Filieres filieresTemp = new Filieres();
		filieresTemp.setAbrevFiliere(selectedFiliere.getAbrevFiliere());
		filieresTemp.setNomFiliere(selectedFiliere.getNomFiliere());
		filieresTemp.setCodeFiliere(selectedFiliere.getCodeFiliere());
		filieresTemp.setEcole(selectedFiliere.getEcole());
		
		//	matrimonialetemp = selectedMatrimoniale;
		getService().deleteObject(filieresTemp);
		vider(filieresTemp);
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

	

	public Filieres getSelectedFiliere() {
		return selectedFiliere;
	}

	public void setSelectedFiliere(Filieres selectedFiliere) {
		this.selectedFiliere = selectedFiliere;
	}

	public Filieres getFilieres() {
		if (listFiliere.isEmpty()) {
			listFiliere = getService().getObjects("Filieres");
		}
		return filieres;
	}

	public void setFilieres(Filieres filieres) {
		this.filieres = filieres;
	}

	public List getListFiliere() {
		return listFiliere;
	}

	public void setListFiliere(List listFiliere) {
		this.listFiliere = listFiliere;
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

	public InputText getInputFiliere() {
		return inputFiliere;
	}

	public void setInputFiliere(InputText inputFiliere) {
		this.inputFiliere = inputFiliere;
	}

	public InputText getInputFiliere2() {
		return inputFiliere2;
	}






	public void setInputFiliere2(InputText inputFiliere2) {
		this.inputFiliere2 = inputFiliere2;
	}






	public InputText getInputAbrevFiliere() {
		return inputAbrevFiliere;
	}

	public void setInputAbrevFiliere(InputText inputAbrevFiliere) {
		this.inputAbrevFiliere = inputAbrevFiliere;
	}

	public InputText getInputAbrevFiliere2() {
		return inputAbrevFiliere2;
	}






	public void setInputAbrevFiliere2(InputText inputAbrevFiliere2) {
		this.inputAbrevFiliere2 = inputAbrevFiliere2;
	}






	public List getListeFiliereByEcole() {
		return listeFiliereByEcole;
	}

	public void setListeFiliereByEcole(List listeFiliereByEcole) {
		this.listeFiliereByEcole = listeFiliereByEcole;
	}

	public RequeteFiliere getRequeteFiliere() {
		return requeteFiliere;
	}

	public void setRequeteFiliere(RequeteFiliere requeteFiliere) {
		this.requeteFiliere = requeteFiliere;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}






	public Domaine getChoosedDoamine() {
		
		return choosedDoamine;
	}






	public void setChoosedDoamine(Domaine choosedDoamine) {
		this.choosedDoamine = choosedDoamine;
	}






	public List getListeDomaine() {
			listeDomaine = getService().getObjects("Domaine");
		return listeDomaine;
	}






	public void setListeDomaine(List listeDomaine) {
		this.listeDomaine = listeDomaine;
	}






	public Tformation getChoosedTformation() {
		return choosedTformation;
	}

	public void setChoosedTformation(Tformation choosedTformation) {
		this.choosedTformation = choosedTformation;
	}

	public List getListTformation() {
		if (listTformation.isEmpty()) {
			listTformation = getService().getObjects("Tformation");
		}
		return listTformation;
	}


	public void setListTformation(List listTformation) {
		this.listTformation = listTformation;
	}

	
	public int getCodeDomaine() {
		return codeDomaine;
	}

	public void setCodeDomaine(int codeDomaine) {
		this.codeDomaine = codeDomaine;
	}

}
