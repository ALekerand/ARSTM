package com.ARSTM.requetes;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Ecolages;

@Transactional
@Component
public class ReqEcolage {
	@Autowired
	SessionFactory sessionFactory;
	
	public Ecolages recupEcolage(int codeMention, int codeanneescolaire, int codeTypeNationalite){
	String query = "SELECT * FROM `ecolages` WHERE ((CODE_MENTION = '"+codeMention+"') AND (CODE_ANNEES = '"+codeanneescolaire+"') AND (CODE_TYPENATIONALITE = '"+codeTypeNationalite+"'))";
	Ecolages object = (Ecolages) getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Ecolages.class).uniqueResult();		
	return object;
	}
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
