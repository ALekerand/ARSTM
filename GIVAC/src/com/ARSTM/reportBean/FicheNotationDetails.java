package com.ARSTM.reportBean;

public class FicheNotationDetails {
	
	private String nom_ecole;
	private String section;
	private String annee_academique;
	private String parcours;
	private Integer effectif;
	
	
	// ACCESSEUR MUTATEUR
	public String getNom_ecole() {
		return nom_ecole;
	}
	public void setNom_ecole(String nom_ecole) {
		this.nom_ecole = nom_ecole;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getAnnee_academique() {
		return annee_academique;
	}
	public void setAnnee_academique(String annee_academique) {
		this.annee_academique = annee_academique;
	}
	public String getParcours() {
		return parcours;
	}
	public void setParcours(String parcours) {
		this.parcours = parcours;
	}
	public Integer getEffectif() {
		return effectif;
	}
	public void setEffectif(Integer effectif) {
		this.effectif = effectif;
	}

}
