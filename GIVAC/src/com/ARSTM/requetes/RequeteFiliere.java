package com.ARSTM.requetes;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.Filieres;

@Transactional
@Component
public class RequeteFiliere {
	@Autowired
	SessionFactory sessionFactory;
	
	public List<Filieres> recupFiliereByEcole(int codeEcole,int codeTformation){
	//String query =	"SELECT `filieres`.* FROM `filieres` WHERE (`filieres`.`CODE_ECOLE` = '"+codeEcole+"')";
	//String query = "SELECT `filieres`.* FROM `filieres` WHERE ((`filieres`.`CODE_ECOLE` ='"+codeEcole+"') AND (`filieres`.`CODE_TFORMATION` ='1'))";
	String query = "SELECT `filieres`.* FROM `filieres` WHERE ((`filieres`.`CODE_ECOLE` ='"+codeEcole+"') AND (`filieres`.`CODE_TFORMATION` ='"+codeTformation+"'))";
	List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Filieres.class).list();		
	return liste;
	}
	
	public List<Filieres> recupFiliereByEcole2(int codeEcole){
		String query = "SELECT `filieres`.* FROM `filieres` WHERE ((`filieres`.`CODE_ECOLE` ='"+codeEcole+"') AND (`filieres`.`CODE_TFORMATION` ='1'))";
		List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Filieres.class).list();		
		return liste;
		}
	
	public List<Filieres> recupFiliereByEcole3(int codeEcole){
		String query = "SELECT `filieres`.* FROM `filieres` WHERE ((`filieres`.`CODE_ECOLE` ='"+codeEcole+"') AND (`filieres`.`CODE_TFORMATION` ='2'))";
		List liste = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Filieres.class).list();		
		return liste;
		}
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
