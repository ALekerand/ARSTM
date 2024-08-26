package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Filieres;
import com.ARSTM.model.Mention;
import com.ARSTM.model.Semestre;
import com.ARSTM.model.SemestreLmd;

@Transactional
@Component
public class RequeteSemestreLmd {
	@Autowired
	SessionFactory sessionFactory;
	
	
	public List<SemestreLmd> recupSemestreByNiveau(String niveauMention){
		
	String query = "SELECT `semestre_lmd`.* FROM `semestre_lmd`WHERE (`semestre_lmd`.`LIB_LICENCE` = '"+niveauMention+"')";
	List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(SemestreLmd.class).list();		
	return liste;
	}
	

	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
