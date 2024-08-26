package com.ARSTM.hybride;

import com.ARSTM.model.AnneesScolaire;

public class AnneScoHybryde {
	private AnneesScolaire  anneesScolaire = new AnneesScolaire();
	private String etat ;
	
	
	
	/****************************ACCESSEURS************************/
	public AnneesScolaire getAnneesScolaire() {
		return anneesScolaire;
	}
	public void setAnneesScolaire(AnneesScolaire anneesScolaire) {
		this.anneesScolaire = anneesScolaire;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}

}
