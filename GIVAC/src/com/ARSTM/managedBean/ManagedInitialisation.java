package com.ARSTM.managedBean;

import javax.annotation.PostConstruct;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.UserAuthentication;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.requetes.ReqUtilisateur;

@Component
@Scope("globalSession")
public class ManagedInitialisation {
	@Autowired
	ReqAnneeScolaire reqAnneeScolaire;
	@Autowired
	ReqUtilisateur reqUtilisateur;
	
	private AnneesScolaire anneeScolaireEncours = new AnneesScolaire();
	private UserAuthentication utilisateurCourant = new UserAuthentication();
	
	@PostConstruct
	public void recupAnneScolaireEncours(){
		try {
			setAnneeScolaireEncours(reqAnneeScolaire.recupererDerniereAnneeScolaire().get(0));
		} catch (org.hibernate.exception.JDBCConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PostConstruct
	public void chargerUtilisateur(){
		try {
			setUtilisateurCourant(getReqUtilisateur().RecupererUtilisateurCourrant());
			System.out.println("Utilisateur: "+utilisateurCourant.getUsername());//clean after
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/********************ACCESSEURS************************/

	public ReqAnneeScolaire getReqAnneeScolaire() {
		return reqAnneeScolaire;
	}

	public void setReqAnneeScolaire(ReqAnneeScolaire reqAnneeScolaire) {
		this.reqAnneeScolaire = reqAnneeScolaire;
	}

	public AnneesScolaire getAnneeScolaireEncours() {
		return anneeScolaireEncours;
	}

	public void setAnneeScolaireEncours(AnneesScolaire anneeScolaireEncours) {
		this.anneeScolaireEncours = anneeScolaireEncours;
	}


	public ReqUtilisateur getReqUtilisateur() {
		return reqUtilisateur;
	}


	public void setReqUtilisateur(ReqUtilisateur reqUtilisateur) {
		this.reqUtilisateur = reqUtilisateur;
	}


	public UserAuthentication getUtilisateurCourant() {
		return utilisateurCourant;
	}


	public void setUtilisateurCourant(UserAuthentication utilisateurCourant) {
		this.utilisateurCourant = utilisateurCourant;
	}
	

}
