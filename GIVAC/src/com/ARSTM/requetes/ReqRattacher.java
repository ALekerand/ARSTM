/*package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Enseigner;
import com.ARSTM.model.Rattacher;


@Transactional
@Component
public class ReqRattacher {
	
	@Autowired
	SessionFactory sessionFactory;

	public List<Rattacher> recupRatacher(int codeSection, int codeMatiere){
String query = "SELECT `rattacher`.* FROM `rattacher` WHERE ((`rattacher`.`CODE_SECTION` ='"+codeSection+"') AND (`rattacher`.`CODE_MATIERE` ='"+codeMatiere+"'))";
		List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Rattacher.class).list();
		return liste;
	}
	
	
	public List<Rattacher> recupRatacherBySection(int codeSection){
		String query = "SELECT `rattacher`.* FROM `rattacher` WHERE ((`rattacher`.`CODE_SECTION` ='"+codeSection+"'))";
				List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Rattacher.class).list();
				return liste;
			}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	

}
*/