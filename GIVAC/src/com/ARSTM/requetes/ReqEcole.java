package com.ARSTM.requetes;


import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Ecole;
import com.ARSTM.model.Origine;

@Transactional
@Component
public class ReqEcole {
	
	@Autowired
	SessionFactory sessionFactory;

	/**
	 * 
	 * @param idPole
	 * @return listEcole
	 */
	public List<Ecole> recupEcoleByPole(int idPole){
		String query = "SELECT * FROM `ecole` WHERE ID_POLE = '"+idPole+"'";
		List listEcole = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Ecole.class).list();
		return listEcole;
	}
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}

