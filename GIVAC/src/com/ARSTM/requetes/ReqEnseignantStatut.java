package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.EnseignatStatut;

@Transactional
@Component
public class ReqEnseignantStatut {
	
	@Autowired
	SessionFactory sessionFactory;

	public List<EnseignatStatut> recupLastEnseignantStatut(){
		String query = "SELECT `enseignat_statut`.* FROM `enseignat_statut` ORDER BY `enseignat_statut`.`DATE_STATUT` DESC LIMIT 0 , 1";
		List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(EnseignatStatut.class).list();
		return liste;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}

