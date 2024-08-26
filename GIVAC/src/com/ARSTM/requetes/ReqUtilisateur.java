package com.ARSTM.requetes;

import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ARSTM.model.UserAuthentication;


@Transactional
@Component
@Scope("session")
public class ReqUtilisateur {
	private static Logger logger = Logger.getLogger(ReqUtilisateur.class);
	private SessionFactory sessionFactory;

	// private Utilisateur utilisateur = new Utilisateur();

	/**
	 * Méthode pour l'utilisateur de la session
	 * 
	 * @return utilisateur
	 * @throws HibernateException
	 */
	public UserAuthentication RecupererUtilisateurCourrant() throws HibernateException {
		// Recupération du login de l'utilisateur courant
		String paramLogin = "";
		if (FacesContext.getCurrentInstance().getExternalContext()
				.getUserPrincipal() != null) {
			paramLogin = FacesContext.getCurrentInstance().getExternalContext()
					.getUserPrincipal().getName();

		}
		//String query = "SELECT * FROM `utilisateur` WHERE `LOGIN_UTILISATEUR`='"+ paramLogin + "'";
		String query = "SELECT * FROM `user_authentication` WHERE (`user_authentication`.`USERNAME` ='"+paramLogin+"')";
		UserAuthentication utilisateur = new UserAuthentication();
		try {
			utilisateur = (UserAuthentication) getSessionFactory().getCurrentSession()
					.createSQLQuery(query).addEntity(UserAuthentication.class).uniqueResult();
		} catch (Exception e) {
			logger.error(" Erreur sur la recupération de l'utilisateur");
		}
		return utilisateur;
	}

	/**
	 * 
	 * @param paramLogin
	 * @return 
	 * 
	 */
	public boolean chercherLogin(String paramLogin) {
		boolean etat;
		String str = paramLogin;
		etat = false;
		try {

			String query = "SELECT * FROM `user_authentication` WHERE (`user_authentication`.`USERNAME``='"+ str + "'";
			List list = (List) getSessionFactory().getCurrentSession().createSQLQuery(query).addEntity(UserAuthentication.class).list();
			if (list.size() >= 1) {
				etat = true;
			}
			System.out.println("Etat de la requête:" + etat);
		} catch (Exception e) {
			logger.error(" Problème de Base de données", e);
		}
		return etat;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
