package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Enseignant;
import com.ARSTM.model.Semaine;

@Transactional
@Component
public class ReqSemaine {
	@Autowired
	SessionFactory sessionFactory;
	
	
	public List<Semaine> recupMaxNumSemaime(int codeAnEnCours){
		String query= "SELECT `semaine`.* FROM `semaine` WHERE (`semaine`.`CODE_ANNEES` ='"+codeAnEnCours+"') ORDER BY `semaine`.`NUM_SEMAINE` DESC";
	List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Semaine.class).list();		
	return list;
	}
	
	public List<Semaine> recupererSemaineByAn(int codeAnEnCours){
	String query= "SELECT `semaine`.* FROM `semaine` WHERE (`semaine`.`CODE_ANNEES` ='"+codeAnEnCours+"')";
	List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Semaine.class).list();		
	return list;
	}
	


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
