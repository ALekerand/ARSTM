package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Filieres;
import com.ARSTM.model.Logement;

@Transactional
@Component
public class RequeteLogement {
	@Autowired
	SessionFactory sessionFactory;
	
	
	public List<Logement> recupLogByTypeLogement(int codetypeLogement){
	String query = "SELECT `logement`.* FROM `logement` WHERE (`logement`.`CODETYPE_LOGEMENT` ='"+codetypeLogement+"')";
	List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Logement.class).list();		
	return liste;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
