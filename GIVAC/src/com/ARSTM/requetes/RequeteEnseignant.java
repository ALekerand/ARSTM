package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Enseignant;

@Transactional
@Component
public class RequeteEnseignant {
	@Autowired
	SessionFactory sessionFactory;
	
	
	public List<Enseignant> recupMaxMatricule(){
		String query = "SELECT `enseignant`.*, `enseignant`.`MATRICULE_ENS` FROM `enseignant` ORDER BY `enseignant`.`MATRICULE_ENS` DESC";
	List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Enseignant.class).list();		
	return list;
	}
	
	public List<Enseignant> recupEnseignant(){
		String query = "SELECT `enseignant`.* FROM `enseignant` WHERE (`enseignant`.`ETAT_ENSEIGNANT` ='1') ORDER BY `enseignant`.`NOM` ASC";
	List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Enseignant.class).list();		
	return list;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


}
