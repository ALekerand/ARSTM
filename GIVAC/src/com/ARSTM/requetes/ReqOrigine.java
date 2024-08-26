package com.ARSTM.requetes;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ARSTM.model.Origine;

@Transactional
@Component
public class ReqOrigine {
	
	@Autowired
	SessionFactory sessionFactory;

	public Origine recupOrigineById(int codeOrigine){
		String query = "SELECT * FROM `origine` WHERE origine.CODE_ORIGINR = '"+codeOrigine+"'";
		Origine origine = (Origine) getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Origine.class).uniqueResult();
		return origine;
	}
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}

