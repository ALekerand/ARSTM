package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Filieres;
import com.ARSTM.model.Mention;

@Transactional
@Component
public class RequeteMention {
	@Autowired
	SessionFactory sessionFactory;
	
	
	public List<Mention> recupMentionByEcoleFiliere(int codeFiliere){
	//String query =	"SELECT `filieres`.* FROM `filieres` WHERE (`filieres`.`CODE_ECOLE` = '"+codeEcole+"')";
	String query = "SELECT `mention`.* FROM `mention` WHERE ((`mention`.`CODE_FILIERE` ='"+codeFiliere+"'))";
	List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Mention.class).list();		
	return liste;
	}
	

	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
