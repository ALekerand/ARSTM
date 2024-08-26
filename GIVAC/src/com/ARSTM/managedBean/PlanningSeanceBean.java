/*package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.hybride.DateHybride;
import com.ARSTM.hybride.PlannigHybryde;
import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.Chapitre;
import com.ARSTM.model.Ecole;
import com.ARSTM.model.Emploitemps;
import com.ARSTM.model.EmploitempsId;
import com.ARSTM.model.Enseigner;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.JourSemaine;
import com.ARSTM.model.Progression;
import com.ARSTM.model.Seance;
import com.ARSTM.model.Section;
import com.ARSTM.model.Semaine;
import com.ARSTM.requetes.ReqEmploisTemps;
import com.ARSTM.requetes.ReqEnseigner;
import com.ARSTM.requetes.ReqProgression;
import com.ARSTM.requetes.ReqSemaine;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.service.Iservice;
import com.ARSTM.utilitares.DateCalculator;

@Component
@Scope("session")
public class PlanningSeanceBean {
	
	@Autowired
	Iservice service;
	@Autowired 
	ReqEnseigner reqEnseigner;
	@Autowired
	ManagedInitialisation initialisation;
	@Autowired
	ReqProgression reqProgression;
	@Autowired
	ReqSemaine reqSemaine;
	@Autowired
	DateCalculator dateCalculator;
	@Autowired
	ReqEmploisTemps reqEmploisTemps;
	
	@Autowired
	RequeteFiliere requeteFiliere;
	
	
	//Nouveau 
	private List<DateHybride> listDateSemaine = new ArrayList<>();
	private List<DateHybride> selectedDates = new ArrayList<>();
	private List <DateHybride> listDateAfectation = new ArrayList<>();
	private List<Semaine> listSemaine = new ArrayList<>();
	//private SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
	private Date dateModule;
	private int nombreSeance;
	private int nombreSeanceDate;
	private int nombreSeanceRest;
	private int nombreDateSelect;
	
	 
	
	
	private Ecole choosedEcole = new Ecole();
	private List<Ecole> listEcole = new ArrayList<>();
	private Filieres choosedFiliere = new Filieres();
	private List<Filieres> listeFiliere = new ArrayList<>();
	private List list_cbEnseigner = new ArrayList<>();
	private Enseigner cbEnseigner = new Enseigner();
	private Semaine choosedSemaine = new Semaine();
	
	private Section choosedSection = new Section();
	private Enseigner choosedEnseigner = new Enseigner();
	private List<Section> listeSection = new ArrayList<>();
	private List<Enseigner> listeEnseigner = new ArrayList<>();
	private List<PlannigHybryde> listPlanningHyb = new ArrayList<>(); 
	
	
	
	

	private AnneesScolaire anneesScolaire = new AnneesScolaire();
	private List<Emploitemps> listEmploitemps = new ArrayList<>();
	private ArrayList<Seance> listSeance = new ArrayList<>();

	
	// Contrôle de coposant

	public void chargerFiliere(){
		//Vider les listes avant rechargement
		listeFiliere.clear();
		listeSection.clear();
		//Charger la liste des filières concernées
		listeFiliere = requeteFiliere.recupFiliereByEcole(choosedEcole.getCodeEcole());
	}	
	
	public void chargerSection(){	
		//Vider la liste des sections
		listeSection.clear();
		for(Section varSection: choosedFiliere.getSections() ){
			listeSection.add(varSection);
		}
	}
		
	public List<Enseigner> chargerListeEnseignant(){
		list_cbEnseigner.clear();
		listeEnseigner.clear();
		list_cbEnseigner = reqEnseigner.recupEnsegnerBySection(choosedSection.getCodeSection(), getAnneesScolaire().getCodeAnnees());
		return list_cbEnseigner;
	}
	
	
	public List<Enseigner> chargerMatiereDispo(){
		listeEnseigner.clear();
		listeEnseigner = reqEnseigner.recupEnsegnerBySectAnneEtatDispoSeancePlan(getAnneesScolaire().getCodeAnnees(), choosedSection.getCodeSection(), 1, 1, 0);
		return listeEnseigner;
	}
	
	
	public void chargerDate(){
		listDateSemaine.clear();
		//Construction et ajout de la date début à la liste
		DateHybride dateHybride = new DateHybride();
		dateHybride.setDate(choosedSemaine.getDebutSem());
		dateHybride.setSemaine(choosedSemaine);
		dateHybride.setJourSemaine(findJourSemaine(choosedSemaine.getDebutSem()));
		dateHybride.setNumDate(1);
		listDateSemaine.add(dateHybride);

		Date datRef = new Date();
		datRef = choosedSemaine.getDebutSem();
		int nbrJ =  dateCalculator.calculerDifference(choosedSemaine.getDebutSem(), choosedSemaine.getFinSem());
		System.out.println("la date de réfference depart : "+ datRef);//clean after
	
		for (int i = 0; i < (nbrJ); i++) {
			//Ajouter un jour sur la date
			GregorianCalendar calendrier = new GregorianCalendar();
			calendrier.setTime(datRef);
			calendrier.add(Calendar.DATE, 1);
			
			//Costruire la dateHybride
			DateHybride dateHybride1 = new DateHybride();
			dateHybride1.setDate(calendrier.getTime());
			dateHybride1.setSemaine(choosedSemaine);
			dateHybride1.setJourSemaine(findJourSemaine(calendrier.getTime()));
			dateHybride1.setNumDate(i+1);
			//Changer la date de reférence par celle qui vient d'etre ajouter ds la liste
			listDateSemaine.add(dateHybride1);
			datRef = calendrier.getTime();
			System.out.println("la date de réfference: "+ datRef);//clean after
	}
	}
	
	private StringBuffer findJourSemaine(Date dat){
		StringBuffer libel = new StringBuffer();
		GregorianCalendar calendrier = new GregorianCalendar();
				calendrier.setTime(dat);
		
		//Pour le jour
		int jourSem = calendrier.get(Calendar.DAY_OF_WEEK);
		
		switch (jourSem) {
		case 1:
			libel.append("Dimande");
			break;
		
		case 2:
			libel.append("Lundi");
			break;
			
		case 3:
			libel.append("Mardi");
			break;
			
		case 4:
			libel.append("Mercredi");
			break;
			
		case 5:
			libel.append("Jeudi");
			break;
			
		case 6:
			libel.append("Vendredi");
			break;
			
		case 7:
			libel.append("Samedi");
			break;
			}
		return libel;	
		
		}
	
	
	public void validerCheckedDate(){
		nombreDateSelect = 0;
		//Deverser la liste des dates selectionnées dans celle à affecter
		for (DateHybride dateHybride : selectedDates) {
			listDateAfectation.add(dateHybride);
			//Vider par la suite celle contenant la selection
		}
		affecterDates();
		listDateSemaine.clear();
		nombreDateSelect = listDateAfectation.size();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void ajouterPlanning(){
		listEmploitemps.clear();
	//	chargerPlanning();
		for (PlannigHybryde planHyb : listPlanningHyb) {
			Emploitemps emploiT = new Emploitemps();
			EmploitempsId emploitempsId = new EmploitempsId();
			
			emploitempsId.setCodeEnseigner(choosedEnseigner.getCodeEnseigner());
			emploitempsId.setCodeJour(planHyb.getJourSemaine()-1);//On ajoute pcq calendar commence par 0
			emploiT.setId(emploitempsId);
			
			emploiT.setEnseigner(getChoosedEnseigner());
			emploiT.setJourSemaine((JourSemaine) getService().getObjectById((planHyb.getJourSemaine()-1), "JourSemaine"));
			emploiT.setHeureDebut(planHyb.getHeureDebut());
			emploiT.setHeureFin(planHyb.getHeureFin());
			listEmploitemps.add(emploiT);
		}
		
		
	}
	
	
	public void planifierSeance(){
		Progression progression = new Progression();
		progression = reqProgression.recupProgressionByEnseigner(choosedEnseigner.getCodeEnseigner()).get(0);
		listSeance.clear();
		for (Chapitre chap : progression.getChapitres()) {
			//Recuperer les séances de chapitre
			for (Seance seance : chap.getSeances()) {
					listSeance.add(seance);
			}
		}
		//Mise à jour de la date de début de module
			choosedEnseigner.setDateDebutcours(dateModule);
			
			//Trier la liste par ordre des numeros de seance
			Collections.sort(listSeance, new Comparator<Seance>() {
			    @Override
			    public int compare(Seance s1, Seance s2) {
			        return s1.getOrdreSeance().compareTo(s2.getOrdreSeance());
			    }
			});
		
			
		//Affecter les dates à chaque séance
			for (int i = 0; i < Math.min(listSeance.size(), listDateAfectation.size()); i++) {
				//Affecter les dates selon l'ordre de recuperation et celui des séances
				listSeance.get(i).setDatePrevue(listDateAfectation.get(i).getDate());
				System.out.println("La date est " + listSeance.get(i).getDatePrevue());//Clean after
			}
	}
	
	
	public void chargerSeance(){
		nombreSeance=0;
		nombreSeanceDate = 0;
		nombreSeanceRest = 0;
		Progression progression = new Progression();
		progression = reqProgression.recupProgressionByEnseigner(choosedEnseigner.getCodeEnseigner()).get(0);
		listSeance.clear();
		for (Chapitre chap : progression.getChapitres()) {
			//Recuperer les séances de chapitre
			for (Seance seance : chap.getSeances()) {
					listSeance.add(seance);
			}
		}
		
		//Affecter le nombre de séances éditées
		nombreSeance = listSeance.size();
		
		//Recupérer le nombre de séances datées
		List<Seance> listSeanceDate = new ArrayList<>();
		for(Seance sc: listSeance){
			if (!(sc.getDatePrevue() == null)) {
				listSeanceDate.add(sc);
			}
			
		}
		
		//Affecter le nombre de séance Datées et restantes
		nombreSeanceDate = listSeanceDate.size();
		nombreSeanceRest = nombreSeance-nombreSeanceDate;
		
		
		
		//Mise à jour de la date de début de module
		choosedEnseigner.setDateDebutcours(dateModule);
	
	//Trier la liste par ordre des numeros de seance
	Collections.sort(listSeance, new Comparator<Seance>() {
	    @Override
	    public int compare(Seance s1, Seance s2) {
	        return s1.getDatePrevue().compareTo(s2.getDatePrevue());
	    }
	});
		
	}
	
	public void affecterDates(){
		//Affecter les dates à chaque séance
		for (int i = 0; i < Math.min(listSeance.size(), listDateAfectation.size()); i++) {
			//Affecter les dates selon l'ordre de recuperation et celui des séances
			listSeance.get(i).setDatePrevue(listDateAfectation.get(i).getDate());
			System.out.println("La date est " + listSeance.get(i).getDatePrevue());//Clean after
		}
		
	}
	
	
	
	
	
	
			
	
	
	public void EnregistrerPlanning(){
		for (Seance varseance : listSeance) { 
			//Affecter les dates uniquement daté
			if (!(varseance.getDatePrevue() == null)) {
				getService().updateObject(varseance);
			}
			
			//Reinitier les infos
			nombreSeance=0;
			nombreSeanceDate = 0;
			nombreSeanceRest = 0;
			
		}
		
		//Actualiser Enseigner sur le planning effectif
		choosedEnseigner.setEtatPlaning(true);
		getService().updateObject(choosedEnseigner);
		
		//vider la liste de planning de seance
		listSeance.clear();
		listEmploitemps.clear();
		listeEnseigner.clear();
	}
	
	
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cellule éditée", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
	
	
	
	
	*//******************************Accesseurs****************************************//*	
		
		
		public Iservice getService() {
			return service;
		}
		public void setService(Iservice service) {
			this.service = service;
		}
		
		public Section getChoosedSection() {
			return choosedSection;
		}

		public void setChoosedSection(Section choosedSection) {
			this.choosedSection = choosedSection;
		}

		@SuppressWarnings("unchecked")
		public List<Section> getListeSection() {

			return listeSection;
		}

		public void setListeSection(List<Section> listeSection) {
			this.listeSection = listeSection;
		}

		public ReqEnseigner getReqEnseigner() {
			return reqEnseigner;
		}

		public void setReqEnseigner(ReqEnseigner reqEnseigner) {
			this.reqEnseigner = reqEnseigner;
		}

		public List<Enseigner> getListeEnseigner() {
			return listeEnseigner;
		}

		public void setListeEnseigner(List<Enseigner> listeEnseigner) {
			this.listeEnseigner = listeEnseigner;
		}

		public ManagedInitialisation getInitialisation() {
			return initialisation;
		}

		public void setInitialisation(ManagedInitialisation initialisation) {
			this.initialisation = initialisation;
		}

		public AnneesScolaire getAnneesScolaire() {
			anneesScolaire = initialisation.getAnneeScolaireEncours();
			return anneesScolaire;
		}

		public void setAnneesScolaire(AnneesScolaire anneesScolaire) {
			this.anneesScolaire = anneesScolaire;
		}

		public Emploitemps getEmploitemps() {
			return emploitemps;
		}

		public void setEmploitemps(Emploitemps emploitemps) {
			this.emploitemps = emploitemps;
		}

		public List<Emploitemps> getListEmploitemps() {
			return listEmploitemps;
		}

		public void setListEmploitemps(List<Emploitemps> listEmploitemps) {
			this.listEmploitemps = listEmploitemps;
		}

		

		public Enseigner getChoosedEnseigner() {
			return choosedEnseigner;
		}

		public void setChoosedEnseigner(Enseigner choosedEnseigner) {
			this.choosedEnseigner = choosedEnseigner;
		}

		
		public ReqProgression getReqProgression() {
			return reqProgression;
		}

		public void setReqProgression(ReqProgression reqProgression) {
			this.reqProgression = reqProgression;
		}

		public ArrayList<Seance> getListSeance() {
			return listSeance;
		}

		public void setListSeance(ArrayList<Seance> listSeance) {
			this.listSeance = listSeance;
		}

		

		public List<PlannigHybryde> getListPlanningHyb() {
			return listPlanningHyb;
		}

		public void setListPlanningHyb(List<PlannigHybryde> listPlanningHyb) {
			this.listPlanningHyb = listPlanningHyb;
		}

	
		public ReqEmploisTemps getReqEmploisTemps() {
			return reqEmploisTemps;
		}

		public void setReqEmploisTemps(ReqEmploisTemps reqEmploisTemps) {
			this.reqEmploisTemps = reqEmploisTemps;
		}



		public Ecole getChoosedEcole() {
			return choosedEcole;
		}



		public void setChoosedEcole(Ecole choosedEcole) {
			this.choosedEcole = choosedEcole;
		}



		public List<Ecole> getListEcole() {
			if (listEcole.isEmpty()) {
				listEcole = getService().getObjects("Ecole");
			}
			return listEcole;
		}



		public void setListEcole(List<Ecole> listEcole) {
			this.listEcole = listEcole;
		}

		public Filieres getChoosedFiliere() {
			return choosedFiliere;
		}

		public void setChoosedFiliere(Filieres choosedFiliere) {
			this.choosedFiliere = choosedFiliere;
		}

		public List<Filieres> getListeFiliere() {
			return listeFiliere;
		}

		public void setListeFiliere(List<Filieres> listeFiliere) {
			this.listeFiliere = listeFiliere;
		}

		public List getList_cbEnseigner() {
			return list_cbEnseigner;
		}

		public void setList_cbEnseigner(List list_cbEnseigner) {
			this.list_cbEnseigner = list_cbEnseigner;
		}

		public Enseigner getCbEnseigner() {
			return cbEnseigner;
		}

		public void setCbEnseigner(Enseigner cbEnseigner) {
			this.cbEnseigner = cbEnseigner;
		}

		public ReqSemaine getReqSemaine() {
			return reqSemaine;
		}

		public void setReqSemaine(ReqSemaine reqSemaine) {
			this.reqSemaine = reqSemaine;
		}

		public Semaine getChoosedSemaine() {
			return choosedSemaine;
		}

		public void setChoosedSemaine(Semaine choosedSemaine) {
			this.choosedSemaine = choosedSemaine;
		}

		public DateCalculator getDateCalculator() {
			return dateCalculator;
		}

		public void setDateCalculator(DateCalculator dateCalculator) {
			this.dateCalculator = dateCalculator;
		}

		public List<DateHybride> getListDateSemaine() {
			return listDateSemaine;
		}

		public void setListDateSemaine(List<DateHybride> listDateSemaine) {
			this.listDateSemaine = listDateSemaine;
		}

		public List<Semaine> getListSemaine() {
			if (listSemaine.isEmpty()) {
				listSemaine = reqSemaine.recupererSemaineByAn(initialisation.getAnneeScolaireEncours().getCodeAnnees());
			}
			return listSemaine;
		}

		public void setListSemaine(List<Semaine> listSemaine) {
			this.listSemaine = listSemaine;
		}

		public List<DateHybride> getSelectedDates() {
			return selectedDates;
		}

		public void setSelectedDates(List<DateHybride> selectedDates) {
			this.selectedDates = selectedDates;
		}

		public Date getDateModule() {
			return dateModule;
		}

		public void setDateModule(Date dateModule) {
			this.dateModule = dateModule;
		}

		public List<DateHybride> getListDateAfectation() {
			return listDateAfectation;
		}

		public void setListDateAfectation(List<DateHybride> listDateAfectation) {
			this.listDateAfectation = listDateAfectation;
		}

		public int getNombreSeance() {
			return nombreSeance;
		}

		public void setNombreSeance(int nombreSeance) {
			this.nombreSeance = nombreSeance;
		}

		public int getNombreSeanceDate() {
			return nombreSeanceDate;
		}

		public void setNombreSeanceDate(int nombreSeanceDate) {
			this.nombreSeanceDate = nombreSeanceDate;
		}

		public int getNombreSeanceRest() {
			return nombreSeanceRest;
		}

		public void setNombreSeanceRest(int nombreSeanceRest) {
			this.nombreSeanceRest = nombreSeanceRest;
		}

		public int getNombreDateSelect() {
			return nombreDateSelect;
		}

		public void setNombreDateSelect(int nombreDateSelect) {
			this.nombreDateSelect = nombreDateSelect;
		}

		
}
*/