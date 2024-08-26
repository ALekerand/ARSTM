package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.Typenationalite;

@Transactional
@Component
public class ReqTypeNationalite {
	
	@Autowired
	SessionFactory sessionFactory;

	public Typenationalite recupererTypeNationalite(int codeTypeNation){
		String query = "SELECT `typenationalite`.* FROM `typenationalite` where `typenationalite`.`CODE_TYPENATIONALITE`='"+codeTypeNation+"'";
		Typenationalite typenationalite = (Typenationalite) getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Typenationalite.class).uniqueResult();
		return typenationalite;
	}
	
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}

