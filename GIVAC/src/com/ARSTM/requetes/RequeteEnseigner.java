package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Enseigner;


@Transactional
@Component
public class RequeteEnseigner {
	
	@Autowired
	SessionFactory sessionFactory;
	
		public List<Enseigner> recupEnsegnerBySection1(int codeAnneeScolaire, int codeSection){
		String query  = "SELECT `enseigner`.* FROM `enseigner` WHERE ((`enseigner`.`CODE_ANNEES` = '"+codeAnneeScolaire+"') AND (`enseigner`.`CODE_SECTION` ='"+codeSection+"' ))";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Enseigner.class).list();
			return list;
		}
		
		public List<Enseigner> recupEnseignerBySection(int codeAnneeScolaire, int codeSection,int userid){
			
			String query = "SELECT `enseigner`.* FROM `enseigner` WHERE ((`enseigner`.`CODE_ANNEES` ='"+codeAnneeScolaire+"') AND (`enseigner`.`CODE_SECTION` ='"+codeSection+"') AND (`enseigner`.`USER_ID` ='"+userid+"') AND (`enseigner`.`ETAT_DISPO`<> '1'))";
			//String query  = "SELECT `enseigner`.* FROM `enseigner` WHERE ((`enseigner`.`CODE_ANNEES` = '"+codeAnneeScolaire+"') AND (`enseigner`.`CODE_SECTION` ='"+codeSection+"') AND (`enseigner`.`USER_ID` ='"+userid+"' ) AND (`enseigner`.`ETAT_DISPO ='1')";
			List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Enseigner.class).list();
				return list;
			}
	
		
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	

}
