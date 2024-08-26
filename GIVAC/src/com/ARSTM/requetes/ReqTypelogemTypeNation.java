package com.ARSTM.requetes;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.TypeLogementNationalite;

@Transactional
@Component
public class ReqTypelogemTypeNation {
	@Autowired
	SessionFactory sessionFactory;
	
	public TypeLogementNationalite recupTypelogeTypenation(int typeLogement, int typeNationalite, int codeAnne){
	String query = "SELECT * FROM `type_logement_nationalite` WHERE ((`CODETYPE_LOGEMENT`='"+typeLogement+"') AND (`CODE_TYPENATIONALITE` ='"+typeNationalite+"') AND (`CODE_ANNEES`='"+codeAnne+"'))";
	TypeLogementNationalite object = (TypeLogementNationalite) getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(TypeLogementNationalite.class).uniqueResult();		
	return object;
	}
	
	
	
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
