package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.Etudiants;

@Transactional
@Component
public class ReqEtudiant {
	
	@Autowired
	SessionFactory sessionFactory;

	public List<Etudiants> recupererEtudiantByMlle( String matricule){
		String query = "SELECT * FROM `etudiants` WHERE etudiants.MLE = '"+matricule+"'";
		List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Etudiants.class).list();
		return liste;
	}
	
	
	public List<Etudiants> recupererEtudiant(){
		String query = "SELECT * FROM `etudiants`";
		List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Etudiants.class).list();
		return liste;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}

