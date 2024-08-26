package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Filieres;
import com.ARSTM.model.Mention;
import com.ARSTM.model.Ues;

@Transactional
@Component
public class RequeteUes {
	@Autowired
	SessionFactory sessionFactory;
	
	
	public List<Ues> recupUesByMentionSemestre(int codeMention, int codeSemestreLmd){
		String query ="SELECT `ues`.* FROM `ues` WHERE ((`Ues`.`CODE_MENTION` ='"+codeMention+"') AND (`Ues`.`ETAT_UE` ='1') AND (`Ues`.`CODE_SEMESTRE_LMD` ='"+codeSemestreLmd+"'))";
		List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Ues.class).list();		
	return liste;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
