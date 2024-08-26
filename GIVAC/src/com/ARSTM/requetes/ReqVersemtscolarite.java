package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.VersementScolarite;

@Transactional
@Component
public class ReqVersemtscolarite {
	
	@Autowired
	SessionFactory sessionFactory;

	public List<VersementScolarite> recupVersemtbyEtudiantAnne( int numEtudiant, int codeAnneescolaire){
		String query = "SELECT * FROM `versement_scolarite` WHERE ( (versement_scolarite.NUMETUDIANT = '"+numEtudiant+"') AND (versement_scolarite.CODE_ANNEES = '"+codeAnneescolaire+"'))";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(VersementScolarite.class).list();
		return list;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}

