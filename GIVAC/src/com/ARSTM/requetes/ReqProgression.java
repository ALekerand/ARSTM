package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Filieres;
import com.ARSTM.model.Progression;

@Transactional
@Component
public class ReqProgression {
	@Autowired
	SessionFactory sessionFactory;
	
	
	public List<Progression> recupProgressionByEnseigner(int codeEnseigner){
	String query = "SELECT `progression`.* FROM `progression` WHERE (`progression`.`CODE_ENSEIGNER` ='"+codeEnseigner+"')";
	List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Progression.class).list();		
	return liste;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
