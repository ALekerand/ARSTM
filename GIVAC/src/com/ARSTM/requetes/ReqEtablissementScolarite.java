package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.EtablScolarite;

@Transactional
@Component
public class ReqEtablissementScolarite {
	
	@Autowired
	SessionFactory sessionFactory;

	public EtablScolarite recupEtablisScolarite(int numEtudiant, int codeAnneescolaire){
		
	String query = "SELECT * FROM `etabl_scolarite` WHERE ((etabl_scolarite.NUMETUDIANT = '"+numEtudiant+"') AND (etabl_scolarite.CODE_ANNEES = '"+codeAnneescolaire+"'))";
		EtablScolarite  etablScolarite = (EtablScolarite) getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(EtablScolarite.class).uniqueResult();
		return etablScolarite;
	}
	
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}

