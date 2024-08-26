package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Enseignant;
import com.ARSTM.model.Etudiants;
import com.ARSTM.model.Inscriptions;

@Transactional
@Component
public class RequeteVersement {
	@Autowired
	SessionFactory sessionFactory;
	
	/**
	 * @author A.Lekerand
	 * @param numeroEtudiant
	 * @param codeAnneeScolaire
	 * @return
	 */
	public List<Inscriptions> recupInscriptionByNumEtudiant(int numeroEtudiant, int codeAnneeScolaire){
		String query = "SELECT * FROM `inscriptions` WHERE (inscriptions.NUMETUDIANT = '"+numeroEtudiant+"') AND (inscriptions.CODE_ANNEES = '"+codeAnneeScolaire+"')";
	List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Inscriptions.class).list();		
	return list;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
