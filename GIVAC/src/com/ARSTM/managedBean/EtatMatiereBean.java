/*package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.Ecole;
import com.ARSTM.model.Enseignant;
import com.ARSTM.model.Enseigner;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Matiere;
import com.ARSTM.model.Rattacher;
import com.ARSTM.model.Section;
import com.ARSTM.model.Semestre;
import com.ARSTM.model.Statut;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.requetes.ReqEnseignantStatut;
import com.ARSTM.requetes.ReqEnseigner;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class EtatMatiereBean {
	
	@Autowired
	Iservice service;
	@Autowired
	RequeteFiliere requeteFiliere; 
	@Autowired
	ReqAnneeScolaire reqAnneeScolaire;
	
	
	private AnneesScolaire anneesScolaire = new AnneesScolaire();
	
	private Ecole choosedEcole = new Ecole();
	private List<Ecole> listEcole = new ArrayList<>();
	
	private Filieres choosedFiliere = new Filieres();
	private List<Filieres> listeFiliere = new ArrayList<>();
	
	private Section choosedSection = new Section();
	private List<Section> listeSection = new ArrayList<>();
	
	private List<Rattacher> listeRattacherSection = new ArrayList<>();
	private int vhTotal;
	private int coefTotal;
	
	// Contrôle de coposant
	private CommandButton btnValider = new CommandButton();
	

			@PostConstruct
			public void initialiser(){
				try {
					setAnneesScolaire(reqAnneeScolaire.recupererDerniereAnneeScolaire().get(0));
				} catch (IndexOutOfBoundsException e) {
					
				}
			}
			
	public void chargerFiliere(){
		//Vider les listes avent rechargement
		listeFiliere.clear();
		listeSection.clear();
		listeRattacherSection.clear();
		vhTotal=0;
		coefTotal=0;
	//	listeNonAffectee.clear();
	//	listeAffecter.clear();
		//Charger la liste des filières concernées
		listeFiliere = requeteFiliere.recupFiliereByEcole(choosedEcole.getCodeEcole());
	}
	
	public void chargerSection(){
		
	//	listeNonAffectee.clear();
		//listeAffecter.clear();
		//Vider la liste des sections
		listeSection.clear();
		listeRattacherSection.clear();
		vhTotal=0;
		coefTotal=0;
		for(Section varSection: choosedFiliere.getSections() ){
			listeSection.add(varSection);
		}
	}
	
	public List<Rattacher> chargerListesRattacher(){
		//Initialisation
		listeRattacherSection.clear();
		vhTotal = 0;
		coefTotal = 0;
		for (Rattacher varRatach : choosedSection.getRattachers()) {
			//Ajout de l'objet rarttacher ds la liste
			listeRattacherSection.add(varRatach);
			//Calculer le VHT
			vhTotal = vhTotal + varRatach.getVolumeHoraire();
			coefTotal = coefTotal + varRatach.getCoefMatiere();
		}
		return listeRattacherSection;
	}
	
	
	
	
	
	*//*************************************************************************************************************//*	
		//Accesseurs
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
		public Iservice getService() {
			return service;
		}
		public void setService(Iservice service) {
			this.service = service;
		}
		public Filieres getChoosedFiliere() {
			return choosedFiliere;
		}
		public void setChoosedFiliere(Filieres choosedFiliere) {
			this.choosedFiliere = choosedFiliere;
		}

		public Section getChoosedSection() {
			return choosedSection;
		}

		public void setChoosedSection(Section choosedSection) {
			this.choosedSection = choosedSection;
		}

		public List<Section> getListeSection() {
			return listeSection;
		}

		public void setListeSection(List<Section> listeSection) {
			this.listeSection = listeSection;
		}

		public ReqAnneeScolaire getReqAnneeScolaire() {
			return reqAnneeScolaire;
		}

		public void setReqAnneeScolaire(ReqAnneeScolaire reqAnneeScolaire) {
			this.reqAnneeScolaire = reqAnneeScolaire;
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

		public List<Rattacher> getListeRattacherSection() {
			return listeRattacherSection;
		}

		public void setListeRattacherSection(List<Rattacher> listeRattacherSection) {
			this.listeRattacherSection = listeRattacherSection;
		}

		public AnneesScolaire getAnneesScolaire() {
			return anneesScolaire;
		}

		public void setAnneesScolaire(AnneesScolaire anneesScolaire) {
			this.anneesScolaire = anneesScolaire;
		}

		public int getVhTotal() {
			return vhTotal;
		}

		public void setVhTotal(int vhTotal) {
			this.vhTotal = vhTotal;
		}

		public CommandButton getBtnValider() {
			return btnValider;
		}

		public void setBtnValider(CommandButton btnValider) {
			this.btnValider = btnValider;
		}

		public int getCoefTotal() {
			return coefTotal;
		}

		public void setCoefTotal(int coefTotal) {
			this.coefTotal = coefTotal;
		}
}
*/