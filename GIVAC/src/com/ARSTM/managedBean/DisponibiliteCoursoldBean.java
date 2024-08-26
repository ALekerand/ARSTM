package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.spinner.Spinner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.hybride.PlannigHybryde;
import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.Ecole;
import com.ARSTM.model.Emploitemps;
import com.ARSTM.model.EmploitempsId;
import com.ARSTM.model.Enseigner;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.JourSemaine;
import com.ARSTM.model.Seance;
import com.ARSTM.model.Section;
//import com.ARSTM.requetes.ReqEnseigner;
import com.ARSTM.requetes.ReqProgression;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.service.Iservice;
import com.sun.faces.taglib.html_basic.OutputTextTag;

@Component
@Scope("session")
public class DisponibiliteCoursoldBean {
	
	@Autowired
	Iservice service;
	//ReqEnseigner reqEnseigner;
	@Autowired
	ManagedInitialisation initialisation;
	@Autowired
	ReqProgression reqProgression;
	@Autowired
	RequeteFiliere requeteFiliere; 
	
	
	private Section choosedSection = new Section();
	private Enseigner choosedEnseigner = new Enseigner();
	private List<Section> listeSection = new ArrayList<>();
	private Enseigner cbEnseigner = new Enseigner();
	private List list_cbEnseigner = new ArrayList<>();

	private List<Enseigner> listeEnseigner = new ArrayList<>();
	private List<PlannigHybryde> listPlanningHyb = new ArrayList<>(); 
	
	private Ecole choosedEcole = new Ecole();
	private List<Ecole> listEcole = new ArrayList<>();
	
	private Filieres choosedFiliere = new Filieres();
	private List<Filieres> listeFiliere = new ArrayList<>();
	
	private int vhLundi;
	
	
	
	//private Date dateModule;
	private boolean valLundi;  
    private boolean valMardi;
    private boolean valMercred;  
    private boolean valJeudi;
    private boolean valVendred;  
    private boolean valSamed;
    
	private int hDebut1;
	private int hdebut2;
	private int hdebut3;
	private int hdebut4;
	private int hdebut5;
	private int hdebut6;
	
	private int hfin1;
	private int hfin2;
	private int hfin3;
	private int hfin4;
	private int hfin5;
	private int hfin6;
	
	private InputText inputTextLundi = new InputText();
	private InputText inputTextMardi = new InputText() ;
	private InputText inputTextMercredi = new InputText();
	private InputText inputTextJeudi = new InputText();
	private InputText inputTextVendredi = new InputText();
	private InputText inputTextSamedi = new InputText();
	
	
	private AnneesScolaire anneesScolaire = new AnneesScolaire();
	private List<Emploitemps> listEmploitemps = new ArrayList<>();
	private ArrayList<Seance> listSeance = new ArrayList<>();

	
	// Contrôle de coposant
			private CommandButton btnValider = new CommandButton();
			private CommandButton btnAnnuler = new CommandButton();
			private Spinner spinnerDebLun  = new Spinner();
			private Spinner spinnerFinLun  = new Spinner();
			private Spinner spinnerDebMard = new Spinner();
			private Spinner spinnerFinMard = new Spinner();
			private Spinner spinnerDebMerc = new Spinner();
			private Spinner spinnerFinMerc = new Spinner();
			private Spinner spinnerDebJeud = new Spinner();
			private Spinner spinnerFinJeud = new Spinner();
			private Spinner spinnerDebVend = new Spinner();
			private Spinner spinnerFinVend = new Spinner();
			private Spinner spinnerDebSam  = new Spinner();
			private Spinner spinnerFinSam  = new Spinner();
			
			
	
			@PostConstruct
			public void initialiser(){
				spinnerDebLun.setDisabled(true);
				spinnerFinLun.setDisabled(true);
				spinnerDebMard.setDisabled(true);
				spinnerFinMard.setDisabled(true);
				spinnerDebMerc.setDisabled(true);
				spinnerFinMerc.setDisabled(true);
				spinnerDebJeud.setDisabled(true);
				spinnerFinJeud.setDisabled(true);
				spinnerDebVend.setDisabled(true);
				spinnerFinVend.setDisabled(true);
				spinnerDebSam.setDisabled(true);
				spinnerFinSam.setDisabled(true);
				
				inputTextLundi.setDisabled(true);
				inputTextMardi.setDisabled(true);
				inputTextMercredi.setDisabled(true);
				inputTextJeudi.setDisabled(true);
				inputTextVendredi.setDisabled(true);
				inputTextSamedi.setDisabled(true);
				
			}
	
			public void chargerFiliere(){
				//Vider les listes avant rechargement
				listeFiliere.clear();
				listeSection.clear();
				//Charger la liste des filières concernées
				//listeFiliere = requeteFiliere.recupFiliereByEcole(choosedEcole.getCodeEcole());
			}
			
			
			public void chargerSection(){	
				//Vider la liste des sections
				//listeSection.clear();
				//for(Section varSection: choosedFiliere.getSections() ){
					//listeSection.add(varSection);
				//}
			}
			
	public List<Enseigner> chargerListeEnseignant(){
		list_cbEnseigner.clear();
		listeEnseigner.clear();
		//list_cbEnseigner = reqEnseigner.recupEnsegnerBySection(choosedSection.getCodeSection(), getAnneesScolaire().getCodeAnnees());
		return list_cbEnseigner;
	}
	
	
	
	public List<Enseigner> chargerMatiere(){
		listeEnseigner.clear();
		//listeEnseigner = reqEnseigner.recupEnsegnerBySectAnneEnseignant(getAnneesScolaire().getCodeAnnees(), choosedSection.getCodeSection(),cbEnseigner.getEnseignant().getUserId());
		initialiser();
		return listeEnseigner;
	}
	
	
	
	public void ajouterPlanning(){
		listEmploitemps.clear();
		chargerPlanning();
		
		for (PlannigHybryde planHyb : listPlanningHyb) {
			Emploitemps emploiT = new Emploitemps();
			EmploitempsId emploitempsId = new EmploitempsId();
			
			emploitempsId.setCodeEnseigner(choosedEnseigner.getCodeEnseigner());
			emploitempsId.setCodeJour(planHyb.getJourSemaine()-1);//On ajoute pcq calendar commence par 0
			emploiT.setId(emploitempsId);
			
			emploiT.setEnseigner(getChoosedEnseigner());
			emploiT.setJourSemaine((JourSemaine) getService().getObjectById((planHyb.getJourSemaine()-1), "JourSemaine"));
			//emploiT.setHeureDebut(planHyb.getHeureDebut());
			//emploiT.setHeureFin(planHyb.getHeureFin());
			listEmploitemps.add(emploiT);
		}
	}
	
	
	public void enregistrerEmploiT(){
		for (Emploitemps varEmploiT : listEmploitemps) {
			getService().addObject(varEmploiT);
			choosedEnseigner.setEtatDispo(true);//Pour marquer que le planning hebdo de l'enseignant est ok
			getService().updateObject(choosedEnseigner);
		}
		//Reinitialiser la page
		initialiser();//désactiver les champs
		listeEnseigner.clear();
		listEmploitemps.clear();
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void chargerPlanning(){
		listPlanningHyb.clear();
		
		//Pour le Lundi
		if(isValLundi()==true){
			PlannigHybryde plannigHybryde = new PlannigHybryde();
			plannigHybryde.setValeurEtat(isValLundi());
			plannigHybryde.setHeureDebut(hDebut1);
			plannigHybryde.setHeureFin(hfin1);
			plannigHybryde.setJourSemaine(Calendar.MONDAY);
			listPlanningHyb.add(plannigHybryde);
		}
		
		//Pour le Mardi
				if(isValMardi()==true){
					PlannigHybryde plannigHybryde = new PlannigHybryde();
					plannigHybryde.setValeurEtat(isValMardi());
					plannigHybryde.setHeureDebut(hdebut2);
					plannigHybryde.setHeureFin(hfin2);
					plannigHybryde.setJourSemaine(Calendar.TUESDAY);
					listPlanningHyb.add(plannigHybryde);
				}
				
				
				//Pour le Mercredi
				if(isValMercred()==true){
					PlannigHybryde plannigHybryde = new PlannigHybryde();
					plannigHybryde.setValeurEtat(isValMercred());
					plannigHybryde.setHeureDebut(hdebut3);
					plannigHybryde.setHeureFin(hfin3);
					plannigHybryde.setJourSemaine(Calendar.WEDNESDAY);
					listPlanningHyb.add(plannigHybryde);
				}
				
				
				//Pour le Jeudi
				if(isValJeudi()==true){
					PlannigHybryde plannigHybryde = new PlannigHybryde();
					plannigHybryde.setValeurEtat(isValJeudi());
					plannigHybryde.setHeureDebut(hdebut4);
					plannigHybryde.setHeureFin(hfin4);
					plannigHybryde.setJourSemaine(Calendar.THURSDAY);
					listPlanningHyb.add(plannigHybryde);
				}
				
				
				//Pour le Vendredi
				if(isValVendred()==true){
					PlannigHybryde plannigHybryde = new PlannigHybryde();
					plannigHybryde.setValeurEtat(isValVendred());
					plannigHybryde.setHeureDebut(hdebut5);
					plannigHybryde.setHeureFin(hfin5);
					plannigHybryde.setJourSemaine(Calendar.FRIDAY);
					listPlanningHyb.add(plannigHybryde);
				}
				
				
				//Pour le Samedi
				if(isValSamed()==true){
					PlannigHybryde plannigHybryde = new PlannigHybryde();
					plannigHybryde.setValeurEtat(isValSamed());
					plannigHybryde.setHeureDebut(hdebut6);
					plannigHybryde.setHeureFin(hfin6);
					plannigHybryde.setJourSemaine(Calendar.SATURDAY);
					listPlanningHyb.add(plannigHybryde);
				}
	}
	
	public void activerLundi(){
		//pour Luidi
		if (isValLundi()==true) {
			spinnerDebLun.setDisabled(false);
			spinnerFinLun.setDisabled(false);
			inputTextLundi.setDisabled(false);
			
		} else {
			spinnerDebLun.setDisabled(true);
			spinnerFinLun.setDisabled(true);
			inputTextLundi.setDisabled(true);
		}
		
		
	}
	
	public void activerMardi(){
				if (isValMardi()==true) {
					spinnerDebMard.setDisabled(false);
					spinnerFinMard.setDisabled(false);
					inputTextMardi.setDisabled(false);
				} else {
					spinnerDebMard.setDisabled(true);
					spinnerFinMard.setDisabled(true);
					inputTextMardi.setDisabled(true);
				}
	}
	
	public void activerMercredi(){
		if (isValMercred()==true) {
			spinnerDebMerc.setDisabled(false);
			spinnerFinMerc.setDisabled(false);
			inputTextMercredi.setDisabled(false);
		} else {
			spinnerDebMerc.setDisabled(true);
			spinnerFinMerc.setDisabled(true);
			inputTextMercredi.setDisabled(true);
		}
}
	
	
	public void activerJeudi(){
		if (isValJeudi()==true) {
			spinnerDebJeud.setDisabled(false);
			getSpinnerFinJeud().setDisabled(false);
			inputTextJeudi.setDisabled(false);
		} else {
			spinnerDebJeud.setDisabled(true);
			spinnerFinJeud.setDisabled(true);
			inputTextJeudi.setDisabled(true);
		}
}
	
	
	
	public void activerVendredi(){
		if (isValVendred()==true) {
			spinnerDebVend.setDisabled(false);
			spinnerFinVend.setDisabled(false);
			inputTextVendredi.setDisabled(false);
		} else {
			spinnerDebVend.setDisabled(true);
			spinnerFinVend.setDisabled(true);
			inputTextVendredi.setDisabled(true);
		}
}
	
	
	public void activerSamedi(){
		if (isValSamed()==true) {
			spinnerDebSam.setDisabled(false);
			spinnerFinSam.setDisabled(false);
			inputTextSamedi.setDisabled(false);
		} else {
			spinnerDebSam.setDisabled(true);
			spinnerFinSam.setDisabled(true);
			inputTextSamedi.setDisabled(true);
		}
}
	
	
	public int calculerVHLundi(){
		System.out.println(">>>>>>>>>>>>>>> Méthode de calcul de VH calculé");//Clean after
		vhLundi=0;
		vhLundi = getHfin1() - gethDebut1();
		System.out.println(">>>>> Debut: " +hDebut1 );//Clean after
		System.out.println(">>>>> Fin: " +hfin1 );//Clean after
		System.out.println(">>>>> Resultat: " +vhLundi );//Clean after
		return vhLundi;
	}

	//******************************Accesseurs****************************************//*	
		
		
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

	/*	public ReqEnseigner getReqEnseigner() {
			return reqEnseigner;
		}

		public void setReqEnseigner(ReqEnseigner reqEnseigner) {
			this.reqEnseigner = reqEnseigner;
		}*/

		
		public CommandButton getBtnValider() {
			return btnValider;
		}

		public void setBtnValider(CommandButton btnValider) {
			this.btnValider = btnValider;
		}
		
		public CommandButton getBtnAnnuler() {
			return btnAnnuler;
		}


		public void setBtnAnnuler(CommandButton btnAnnuler) {
			this.btnAnnuler = btnAnnuler;
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

		public boolean isValLundi() {
			return valLundi;
		}

		public void setValLundi(boolean valLundi) {
			this.valLundi = valLundi;
		}

		public boolean isValMardi() {
			return valMardi;
		}

		public void setValMardi(boolean valMardi) {
			this.valMardi = valMardi;
		}

		public boolean isValMercred() {
			return valMercred;
		}

		public void setValMercred(boolean valMercred) {
			this.valMercred = valMercred;
		}

		public boolean isValVendred() {
			return valVendred;
		}

		public void setValVendred(boolean valVendred) {
			this.valVendred = valVendred;
		}

		public boolean isValSamed() {
			return valSamed;
		}

		public void setValSamed(boolean valSamed) {
			this.valSamed = valSamed;
		}

		public boolean isValJeudi() {
			return valJeudi;
		}

		public void setValJeudi(boolean valJeudi) {
			this.valJeudi = valJeudi;
		}

		public int gethDebut1() {
			return hDebut1;
		}

		public void sethDebut1(int hDebut1) {
			this.hDebut1 = hDebut1;
		}

		public int getHdebut2() {
			return hdebut2;
		}

		public void setHdebut2(int hdebut2) {
			this.hdebut2 = hdebut2;
		}

		public int getHfin1() {
			return hfin1;
		}

		public void setHfin1(int hfin1) {
			this.hfin1 = hfin1;
		}

		public List<PlannigHybryde> getListPlanningHyb() {
			return listPlanningHyb;
		}

		public void setListPlanningHyb(List<PlannigHybryde> listPlanningHyb) {
			this.listPlanningHyb = listPlanningHyb;
		}

		public Spinner getSpinnerDebLun() {
			return spinnerDebLun;
		}

		public void setSpinnerDebLun(Spinner spinnerDebLun) {
			this.spinnerDebLun = spinnerDebLun;
		}


		public Spinner getSpinnerFinLun() {
			return spinnerFinLun;
		}


		public void setSpinnerFinLun(Spinner spinnerFinLun) {
			this.spinnerFinLun = spinnerFinLun;
		}


		public Spinner getSpinnerDebMard() {
			return spinnerDebMard;
		}


		public void setSpinnerDebMard(Spinner spinnerDebMard) {
			this.spinnerDebMard = spinnerDebMard;
		}


		public Spinner getSpinnerFinMard() {
			return spinnerFinMard;
		}


		public void setSpinnerFinMard(Spinner spinnerFinMard) {
			this.spinnerFinMard = spinnerFinMard;
		}


		public Spinner getSpinnerDebMerc() {
			return spinnerDebMerc;
		}


		public void setSpinnerDebMerc(Spinner spinnerDebMerc) {
			this.spinnerDebMerc = spinnerDebMerc;
		}


		public Spinner getSpinnerFinMerc() {
			return spinnerFinMerc;
		}


		public void setSpinnerFinMerc(Spinner spinnerFinMerc) {
			this.spinnerFinMerc = spinnerFinMerc;
		}


		public Spinner getSpinnerDebJeud() {
			return spinnerDebJeud;
		}


		public void setSpinnerDebJeud(Spinner spinnerDebJeud) {
			this.spinnerDebJeud = spinnerDebJeud;
		}


		public Spinner getSpinnerFinJeud() {
			return spinnerFinJeud;
		}


		public void setSpinnerFinJeud(Spinner spinnerFinJeud) {
			this.spinnerFinJeud = spinnerFinJeud;
		}


		public Spinner getSpinnerDebVend() {
			return spinnerDebVend;
		}


		public void setSpinnerDebVend(Spinner spinnerDebVend) {
			this.spinnerDebVend = spinnerDebVend;
		}


		public Spinner getSpinnerFinVend() {
			return spinnerFinVend;
		}


		public void setSpinnerFinVend(Spinner spinnerFinVend) {
			this.spinnerFinVend = spinnerFinVend;
		}


		public int getHdebut3() {
			return hdebut3;
		}


		public void setHdebut3(int hdebut3) {
			this.hdebut3 = hdebut3;
		}


		

		public Spinner getSpinnerDebSam() {
			return spinnerDebSam;
		}


		public void setSpinnerDebSam(Spinner spinnerDebSam) {
			this.spinnerDebSam = spinnerDebSam;
		}


		public Spinner getSpinnerFinSam() {
			return spinnerFinSam;
		}


		public void setSpinnerFinSam(Spinner spinnerFinSam) {
			this.spinnerFinSam = spinnerFinSam;
		}


		public int getHdebut4() {
			return hdebut4;
		}


		public void setHdebut4(int hdebut4) {
			this.hdebut4 = hdebut4;
		}


		public int getHdebut5() {
			return hdebut5;
		}


		public void setHdebut5(int hdebut5) {
			this.hdebut5 = hdebut5;
		}


		public int getHdebut6() {
			return hdebut6;
		}


		public void setHdebut6(int hdebut6) {
			this.hdebut6 = hdebut6;
		}


		public int getHfin2() {
			return hfin2;
		}


		public void setHfin2(int hfin2) {
			this.hfin2 = hfin2;
		}


		public int getHfin3() {
			return hfin3;
		}


		public void setHfin3(int hfin3) {
			this.hfin3 = hfin3;
		}


		public int getHfin4() {
			return hfin4;
		}


		public void setHfin4(int hfin4) {
			this.hfin4 = hfin4;
		}


		public int getHfin5() {
			return hfin5;
		}


		public void setHfin5(int hfin5) {
			this.hfin5 = hfin5;
		}


		public int getHfin6() {
			return hfin6;
		}


		public void setHfin6(int hfin6) {
			this.hfin6 = hfin6;
		}

		public Enseigner getCbEnseigner() {
			return cbEnseigner;
		}


		public void setCbEnseigner(Enseigner cbEnseigner) {
			this.cbEnseigner = cbEnseigner;
		}


		public List getList_cbEnseigner() {
			return list_cbEnseigner;
		}


		public void setList_cbEnseigner(List list_cbEnseigner) {
			this.list_cbEnseigner = list_cbEnseigner;
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
		
		public List<Filieres> getListeFiliere() {
			return listeFiliere;
		}

		public void setListeFiliere(List<Filieres> listeFiliere) {
			this.listeFiliere = listeFiliere;
		}

		public RequeteFiliere getRequeteFiliere() {
			return requeteFiliere;
		}

		public void setRequeteFiliere(RequeteFiliere requeteFiliere) {
			this.requeteFiliere = requeteFiliere;
		}

		public Filieres getChoosedFiliere() {
			return choosedFiliere;
		}

		public void setChoosedFiliere(Filieres choosedFiliere) {
			this.choosedFiliere = choosedFiliere;
		}

		public InputText getInputTextLundi() {
			return inputTextLundi;
		}

		public void setInputTextLundi(InputText inputTextLundi) {
			this.inputTextLundi = inputTextLundi;
		}

		public InputText getInputTextMardi() {
			return inputTextMardi;
		}

		public void setInputTextMardi(InputText inputTextMardi) {
			this.inputTextMardi = inputTextMardi;
		}

		public InputText getInputTextMercredi() {
			return inputTextMercredi;
		}

		public void setInputTextMercredi(InputText inputTextMercredi) {
			this.inputTextMercredi = inputTextMercredi;
		}

		public InputText getInputTextJeudi() {
			return inputTextJeudi;
		}

		public void setInputTextJeudi(InputText inputTextJeudi) {
			this.inputTextJeudi = inputTextJeudi;
		}

		public InputText getInputTextSamedi() {
			return inputTextSamedi;
		}

		public void setInputTextSamedi(InputText inputTextSamedi) {
			this.inputTextSamedi = inputTextSamedi;
		}

		public InputText getInputTextVendredi() {
			return inputTextVendredi;
		}

		public void setInputTextVendredi(InputText inputTextVendredi) {
			this.inputTextVendredi = inputTextVendredi;
		}

		public int getVhLundi() {
			return vhLundi;
		}

		public void setVhLundi(int vhLundi) {
			this.vhLundi = vhLundi;
		}
}