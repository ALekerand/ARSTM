package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Ecue;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Mention;
import com.ARSTM.model.Ues;

@Transactional
@Component
public class RequeteEcue {
	@Autowired
	SessionFactory sessionFactory;
	
	
	public List<Ecue> recupEcueByUe(int codeEu){
		String query ="SELECT `ecue`.* FROM `ecue` WHERE ((`ecue`.`CODE_EUS` ='"+codeEu+"') AND (`ecue`.`ETAT_ECUE` ='1'))";
		List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Ecue.class).list();		
	return liste;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
