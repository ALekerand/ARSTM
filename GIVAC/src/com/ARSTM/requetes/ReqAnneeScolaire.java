package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.AnneesScolaire;

@Transactional
@Component
public class ReqAnneeScolaire {
	
	@Autowired
	SessionFactory sessionFactory;

	public List<AnneesScolaire> recupererDerniereAnneeScolaire(){
		String query = "SELECT `annees_scolaire`.* FROM `annees_scolaire` ORDER BY `annees_scolaire`.`CODE_ANNEES` DESC LIMIT 0 , 1";
		List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(AnneesScolaire.class).list();
		return liste;
	}
	
	public List<AnneesScolaire> recupAnneeScoByCodeDecrois(){
		String query = "SELECT `annees_scolaire`.* FROM `annees_scolaire` ORDER BY `annees_scolaire`.`CODE_ANNEES` DESC";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(AnneesScolaire.class).list();
		return list;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}

