package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Seance;

@Transactional
@Component
public class ReqSeance {
	@Autowired
	SessionFactory sessionFactory;
	
	
	public List<Seance> recupMaxOrdre(){
		String query = "SELECT `seance`.* FROM `seance` ORDER BY `seance`.`ORDRE_SEANCE` DESC";
		//String query = "SELECT `enseignant`.*, `enseignant`.`MATRICULE_ENS` FROM `enseignant` ORDER BY `enseignant`.`MATRICULE_ENS` DESC";
	List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Seance.class).list();		
	return list;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


}
