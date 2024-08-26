package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Mention;
import com.ARSTM.model.SemestreLmd;
import com.ARSTM.model.Typeue;
import com.ARSTM.model.Ues;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.service.Iservice;

@Component
public class UeBean_old {
	
	@Autowired
	Iservice service;
	@Autowired
	RequeteFiliere requeteFiliere;
	
	private Ecole choosedEcole = new Ecole();
	private Filieres choosedFiliere = new Filieres();
	private Mention choosedMention = new Mention();
	private SemestreLmd selectedSemestreLmd = new SemestreLmd();
	private Ues ues = new Ues();
	private Ues selectedUe = new Ues();
	
	private boolean etatue;
	private Typeue selectedTypeUe = new Typeue();
	//private List<Ecole> listEcole = new ArrayList<>();
	private List<Ecole> listEcole = new ArrayList<>();
	private List listFiliere = new ArrayList<>();
	private List listMention = new ArrayList<>();
	private List listSemestreLmd = new ArrayList<>();
	private List listTypeUe = new ArrayList<>();
	private List listeUe = new ArrayList<>();
	
	
	// Contrôle de coposant
	private CommandButton btnValider = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	private CommandButton btnSuprimer = new CommandButton();
	
	// Méthodes
	public void chargerFiliere(){
		/*listFiliere.clear();
		listFiliere = requeteFiliere.recupFiliereByEcole2(choosedEcole.getCodeEcole());*/
		tester();
	}
	
	public void tester(){
		System.out.println("Mention selectionnee:"+choosedMention.getLibMention());
		System.out.println("Filière selectionnee:"+choosedFiliere.getNomFiliere());
		System.out.println("Ecole selectionnee:"+choosedEcole.getNomEcole());
	}
	
	public void enregistrer(){
		ues.setEtatUe(true);
	}
	
public void annuler(){
		
	}

public void supprimer(){
	
}
	

	
	
	
//ACCESSEURS
	public Ecole getChoosedEcole() {
		return choosedEcole;
	}

	public void setChoosedEcole(Ecole choosedEcole) {
		this.choosedEcole = choosedEcole;
	}

	/*public List getListEcole() {
		
		return listEcole = service.getObjects("Ecole");
	}

	public void setListEcole(List listEcole) {
		this.listEcole = listEcole;
	}
*/
	public Filieres getChoosedFiliere() {
		return choosedFiliere;
	}

	public void setChoosedFiliere(Filieres choosedFiliere) {
		this.choosedFiliere = choosedFiliere;
	}

	public List getListFiliere() {
		listFiliere = service.getObjects("Filieres");
		return listFiliere;
	}

	public void setListFiliere(List listFiliere) {
		this.listFiliere = listFiliere;
	}

	public Mention getChoosedMention() {
		return choosedMention;
	}

	public void setChoosedMention(Mention choosedMention) {
		this.choosedMention = choosedMention;
	}

	public List getListMention() {
		listMention = service.getObjects("Mention");
		return listMention ;
	}

	public void setListMention(List listMention) {
		this.listMention = listMention;
	}

	public SemestreLmd getSelectedSemestreLmd() {
		return selectedSemestreLmd;
	}

	public void setSelectedSemestreLmd(SemestreLmd selectedSemestreLmd) {
		this.selectedSemestreLmd = selectedSemestreLmd;
	}

	public List getListSemestreLmd() {
		listSemestreLmd = service.getObjects("SemestreLmd");
		return listSemestreLmd ;
	}

	public void setListSemestreLmd(List listSemestreLmd) {
		this.listSemestreLmd = listSemestreLmd;
	}

	public List getListTypeUe() {
		listTypeUe = service.getObjects("Typeue");
		return listTypeUe;

	}

	public void setListTypeUe(List listTypeUe) {
		this.listTypeUe = listTypeUe;
	}

	public CommandButton getBtnValider() {
		return btnValider;
	}

	public void setBtnValider(CommandButton btnValider) {
		this.btnValider = btnValider;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

	public CommandButton getBtnSuprimer() {
		return btnSuprimer;
	}

	public void setBtnSuprimer(CommandButton btnSuprimer) {
		this.btnSuprimer = btnSuprimer;
	}

	public Typeue getSelectedTypeUe() {
		return selectedTypeUe;
	}

	public void setSelectedTypeUe(Typeue selectedTypeUe) {
		this.selectedTypeUe = selectedTypeUe;
	}

	public Ues getUes() {
		return ues;
	}

	public void setUes(Ues ues) {
		this.ues = ues;
	}

	public boolean isEtatue() {
		return etatue;
	}

	public void setEtatue(boolean etatue) {
		this.etatue = etatue;
	}

	public Ues getSelectedUe() {
		return selectedUe;
	}

	public void setSelectedUe(Ues selectedUe) {
		this.selectedUe = selectedUe;
	}

	public List getListeUe() {
		listeUe = service.getObjects("Ues");
		return listeUe;
	}

	public void setListeUe(List listeUe) {
		this.listeUe = listeUe;
	}

	public List<Ecole> getListEcole() {
		listEcole = service.getObjects("Ecole");
		return listEcole;
	}

	public void setListEcole(List<Ecole> listEcole) {
		this.listEcole = listEcole;
	}

}
