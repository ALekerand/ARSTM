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
public class RequeteInscription {
	@Autowired
	SessionFactory sessionFactory;
	
	
	public List<Etudiants> recupMaxNumetudiant(){
		String query = "SELECT `etudiants`.*, `etudiants`.`NUMETUDIANT` FROM `etudiants` ORDER BY `etudiants`.`NUMETUDIANT` DESC";
	List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Etudiants.class).list();		
	return list;
	}
	
	
	public List<Inscriptions> recupInscriptionByNumEtudiant(int numeroEtudiant, int codeAnneeScolaire){
		String query = "SELECT * FROM `inscriptions` WHERE (inscriptions.NUMETUDIANT = '"+numeroEtudiant+"') AND (inscriptions.CODE_ANNEES = '"+codeAnneeScolaire+"')";
	List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Inscriptions.class).list();		
	return list;
	}
	
	/**
	 * @author A.Lekerand
	 * Permet de recuperer l'inscription de l'année en cours du'un étudiant 
	 * dont le complement est fait
	 * @param numeroEtudiant
	 * @param codeAnneeScolaire
	 * @return
	 */
	public List<Inscriptions> recupInscriptionCompletByEtudiant(int numeroEtudiant, int codeAnneeScolaire){
	String query = "SELECT * FROM `inscriptions` WHERE ((inscriptions.NUMETUDIANT = '"+numeroEtudiant+"') AND (inscriptions.CODE_ANNEES = '"+codeAnneeScolaire+"') AND (inscriptions.ETAT_COMPLEMNT = '1'  ))";
	List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Inscriptions.class).list();		
	return list;
	}
	
	
	/**
	 * @author A.Lekerand
	 * Permet de recupérer l'inscription de l'étudiant dont la scolarité est établie
	 * @param numeroEtudiant
	 * @param codeAnneeScolaire
	 * @return
	 */
	public List<Inscriptions> recupInscriptionEtabScilariteByEtudiant(int numeroEtudiant, int codeAnneeScolaire){
		String query = "SELECT * FROM `inscriptions` WHERE ((inscriptions.NUMETUDIANT = '"+numeroEtudiant+"') AND (inscriptions.CODE_ANNEES = '"+codeAnneeScolaire+"') AND (ETAT_ETAB_SCOLARITE = '1'  ))";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Inscriptions.class).list();		
		return list;
		}
	
	/**
	 * Permet de donner la liste des inscription dont la scolarité est établie mais dont la scolarité est non soldée
	 * @author A.Lekerand
	 * @param codeAnneeScolaire
	 * @return
	 */
	public List<Inscriptions> recupListeInscriptionComplet(int codeAnneeScolaire){
		String query = "SELECT `inscriptions`.* FROM `inscriptions` WHERE ((`inscriptions`.`ETAT_COMPLEMNT` = '1') AND (`inscriptions`.`CODE_ANNEES` = '"+codeAnneeScolaire+"'))";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Inscriptions.class).list();		
		return list;
		}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public List<Inscriptions> recupListeComplement(int codeAnneeScolaire){
	String query = "SELECT `inscriptions`.* FROM `inscriptions` WHERE ((`inscriptions`.`ETAT_COMPLEMNT` = '0') AND (`inscriptions`.`CODE_ANNEES` = '"+codeAnneeScolaire+"'))";
	List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Inscriptions.class).list();		
	return list;
	}
	
	public List<Inscriptions> recupListeEtabScolarite(int codeAnneeScolaire){
		String query = "SELECT `inscriptions`.* FROM `inscriptions` WHERE ((`inscriptions`.`ETAT_ETAB_SCOLARITE` = '0') AND (`inscriptions`.`ETAT_COMPLEMNT` = '1') AND (`inscriptions`.`CODE_ANNEES` = '"+codeAnneeScolaire+"') AND ETAT_PAYEMENT_SCOLARITE ='0')";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Inscriptions.class).list();		
		return list;
		}
	
	public List<Inscriptions> recupererListeSection(int numEtudiant){
		String query = "SELECT `inscriptions`.* FROM `inscriptions` WHERE ((`inscriptions`.`NUMETUDIANT` = '"+numEtudiant+"'))";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(Inscriptions.class).list();		
		return list;
		}
	
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
