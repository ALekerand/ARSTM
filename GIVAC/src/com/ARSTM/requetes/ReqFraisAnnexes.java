package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.FraisAnnexe;
import com.ARSTM.model.Seance;

@Transactional
@Component
public class ReqFraisAnnexes {
	@Autowired
	SessionFactory sessionFactory;
	
	
	public FraisAnnexe recupFraisAnexByTypeNation(int codeAnneScolaire, int codeTypeNationalite){
		String query = "SELECT * FROM `frais_annexe` WHERE ((CODE_ANNEES ='"+codeAnneScolaire+"') AND (CODE_TYPENATIONALITE = '"+codeTypeNationalite+"'))";
	FraisAnnexe objet = (FraisAnnexe) getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(FraisAnnexe.class).uniqueResult();		
	return objet;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


}
