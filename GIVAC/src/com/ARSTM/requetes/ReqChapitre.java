package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Chapitre;

@Transactional
@Component
public class ReqChapitre {
	@Autowired
	SessionFactory sessionFactory;
	
	
	public List<Chapitre> recupMaxNumChapitre(int codeProgression){
		
		String query = "SELECT `chapitre`.* FROM `chapitre` WHERE (`chapitre`.`CODE_PROGRESSION` ='"+codeProgression+"') ORDER BY `chapitre`.`NUMERO_CHAPITRE` DESC";
		//String query= "SELECT `semaine`.* FROM `semaine` WHERE (`semaine`.`CODE_ANNEES` ='"+codeAnEnCours+"') ORDER BY `semaine`.`NUM_SEMAINE` DESC";
	List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Chapitre.class).list();		
	return list;
	}
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
