package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Filieres;
import com.ARSTM.model.Section;

@Transactional
@Component
public class RequeteSection2 {
	@Autowired
	SessionFactory sessionFactory;
	
	public List<Section> recupSectionByMention(int codeMention){
		String query =  "SELECT `section`.* FROM `section` WHERE (`section`.`CODE_MENTION` ='"+codeMention+"')";
		List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Section.class).list();		
		return liste;
		}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
