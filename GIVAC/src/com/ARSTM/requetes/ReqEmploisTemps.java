package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Emploitemps;
import com.ARSTM.model.Filieres;

@Transactional
@Component
public class ReqEmploisTemps {
	@Autowired
	SessionFactory sessionFactory;
	
	
	public List<Filieres> recupEmploisTByEnsengner(int codeEnseigner){
	String query = "SELECT `emploitemps`.* FROM `emploitemps` WHERE (`emploitemps`.`CODE_ENSEIGNER` ='codeEnseigner') ORDER BY `emploitemps`.`CODE_JOUR` DESC";
	List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Emploitemps.class).list();		
	return liste;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
