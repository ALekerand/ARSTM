package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.hybride.semaineHybride;
import com.ARSTM.model.Semaine;
import com.ARSTM.requetes.ReqSemaine;
import com.ARSTM.service.Iservice;
import com.ARSTM.utilitares.DateCalculator;

@Component
@Scope("session")
public class SemaineBean {
	@Autowired
	Iservice service;
	@Autowired
	ManagedInitialisation initialisation;
	@Autowired
	DateCalculator dateCalculator;
	@Autowired
	ReqSemaine ReqSemaine;
	
	private Semaine semaine = new Semaine();
	private semaineHybride selectedObject = new semaineHybride();
	private List<Semaine> listeObject = new ArrayList<>();
	private ArrayList<semaineHybride> listSemaineHybride = new ArrayList<>();
	private int nbrJSemaine;
	private  int maxNumSemaine = 0;
	
	// Contrôle de coposant
		private CommandButton btnValider = new CommandButton();
		private CommandButton btnSuprimer = new CommandButton();
		private CommandButton btnModifier = new CommandButton();
	
	
	public void enregistrer(){
		semaine.setAnneesScolaire(initialisation.getAnneeScolaireEncours());
		semaine.setNumSemaine(maxNumSemaine);
		semaine.setLibSemaine(determinerJour(semaine.getDebutSem())+" Au "+determinerJour(semaine.getFinSem()));
		getService().addObject(semaine);
		actualiserList();
		vider(semaine);
		maxNumSemaine = 0;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}
	
	
	private StringBuffer determinerJour(Date dat){
		StringBuffer libel = new StringBuffer();
		GregorianCalendar calendrier = new GregorianCalendar();
				calendrier.setTime(dat);
				//calendrier.getTime();
		
		//Pour le jour
		libel.append(calendrier.get(Calendar.DAY_OF_MONTH));
		//Pour le mois
		int mois = calendrier.get(Calendar.MONTH);
		
		switch (mois) {
		case 0:
			libel.append(" Jan ");
			break;
			
		case 1:
			libel.append(" Fev ");
			break;
			
		case 2:
			libel.append(" Mars ");
			break;
			
			
		case 3:
			libel.append(" Avr ");
			break;
			
		case 4:
			libel.append(" Mai ");
			break;
			
			
		case 5:
			libel.append(" Juin ");
			break;
			
			
		case 6:
			libel.append(" Juil ");
			break;
			
			
		case 7:
			libel.append(" Août ");
			break;
			
			
		case 8:
			libel.append(" Sept ");
			break;
			
			
		case 9:
			libel.append(" Oct ");
			break;
			
			
		case 10:
			libel.append(" Nov ");
			break;
			
		case 11:
			libel.append(" Dec ");
			break;
		}
		
		//Pour l'année
		libel.append(calendrier.get(Calendar.YEAR));
		
		return libel;
		
	}

	public void annuler() {
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		vider(semaine);
		actualiserList();
	}
	
	public void vider(Semaine object) {
		object.setDebutSem(null);
		object.setFinSem(null);
		object.setNumSemaine(null);
	}
	
	public void actualiserList(){
		listeObject.clear();
		listSemaineHybride.clear();
		listeObject = ReqSemaine.recupererSemaineByAn(initialisation.getAnneeScolaireEncours().getCodeAnnees());
		for (Semaine var : listeObject) {
			
			semaineHybride semaineHybride = new semaineHybride();
			semaineHybride.setSemaine(var);
			int nbrJ =  dateCalculator.calculerDifference(var.getDebutSem(), var.getFinSem());
			semaineHybride.setNombreJour(nbrJ);
			listSemaineHybride.add(semaineHybride);
		}
		
		//Trier la liste par ordre des numéro de semaine
		Collections.sort(listSemaineHybride, new Comparator<semaineHybride>() {
		    @Override
		    public int compare(semaineHybride s1, semaineHybride s2) {
		        return s1.getSemaine().getNumSemaine().compareTo(s2.getSemaine().getNumSemaine());
		    }
		});
		
		}
	
	public void selectionner(){
		setSemaine(selectedObject.getSemaine());
		btnSuprimer.setDisabled(false);
		btnModifier.setDisabled(false);
		btnValider.setDisabled(true);
	}
	
	public void supprimer() {
		Semaine ObjectTemp = new Semaine ();
		ObjectTemp.setCodeSemaine(selectedObject.getSemaine().getCodeSemaine());
		ObjectTemp.setAnneesScolaire(selectedObject.getSemaine().getAnneesScolaire());
		getService().deleteObject(ObjectTemp);
		vider(semaine);
		maxNumSemaine = 0;
		actualiserList();
		btnValider.setDisabled(false);
		btnSuprimer.setDisabled(true);
		btnModifier.setDisabled(true);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression effcetuée!", null));
	}
	
	public void modifier(){
		getService().updateObject(semaine);
		vider(semaine);
		actualiserList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification effcetuée!", null));
	}
	
	
	
	
	
	/**************************ACCESSEURS*************************/
	
	public Iservice getService() {
		return service;
	}

	public void setService(Iservice service) {
		this.service = service;
	}

	public CommandButton getBtnValider() {
		return btnValider;
	}

	public void setBtnValider(CommandButton btnValider) {
		this.btnValider = btnValider;
	}

	public CommandButton getBtnSuprimer() {
		return btnSuprimer;
	}

	public void setBtnSuprimer(CommandButton btnSuprimer) {
		this.btnSuprimer = btnSuprimer;
	}


	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

	public Semaine getSemaine() {
		return semaine;
	}

	public void setSemaine(Semaine semaine) {
		this.semaine = semaine;
	}

	public ManagedInitialisation getInitialisation() {
		return initialisation;
	}

	public void setInitialisation(ManagedInitialisation initialisation) {
		this.initialisation = initialisation;
	}


	public List<Semaine> getListeObject() {
		
		return listeObject;
	}

	public void setListeObject(List<Semaine> listeObject) {
		this.listeObject = listeObject;
	}

	public ArrayList<semaineHybride> getListSemaineHybride() {
		if (listSemaineHybride.isEmpty()) {
			actualiserList();
		}
		return listSemaineHybride;
	}

	public void setListSemaineHybride(ArrayList<semaineHybride> listSemaineHybride) {
		this.listSemaineHybride = listSemaineHybride;
	}

	public semaineHybride getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(semaineHybride selectedObject) {
		this.selectedObject = selectedObject;
	}

	public int getNbrJSemaine() {
		return nbrJSemaine;
	}

	public void setNbrJSemaine(int nbrJSemaine) {
		this.nbrJSemaine = nbrJSemaine;
	}

	public ReqSemaine getReqSemaine() {
		return ReqSemaine;
	}

	public void setReqSemaine(ReqSemaine reqSemaine) {
		ReqSemaine = reqSemaine;
	}

	public int getMaxNumSemaine() {
		if (maxNumSemaine == 0) {
			try {
				maxNumSemaine = ReqSemaine.recupMaxNumSemaime(initialisation.getAnneeScolaireEncours().getCodeAnnees()).get(0).getNumSemaine();
				semaine.setNumSemaine(maxNumSemaine++);
			} catch (Exception e) {
				maxNumSemaine = 1;
			}
		}
		return maxNumSemaine;
	}

	public void setMaxNumSemaine(int maxNumSemaine) {
		this.maxNumSemaine = maxNumSemaine;
	}

}
