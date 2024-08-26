/*package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import com.ARSTM.model.Sexe;
import com.ARSTM.model.Statut;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.requetes.ReqEnseignantStatut;
import com.ARSTM.requetes.ReqEnseigner;
import com.ARSTM.requetes.ReqRattacher;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class AttributionMatBean {
	
	@Autowired
	Iservice service;
	@Autowired
	RequeteFiliere requeteFiliere; 
	@Autowired
	ReqEnseigner reqEnseigner;
	@Autowired
	ReqAnneeScolaire reqAnneeScolaire;
	@Autowired
	ReqEnseignantStatut reqEnseignantStatut;
	@Autowired
	ReqRattacher reqRattacher;
	
	
	private Ecole choosedEcole = new Ecole();
	private List<Ecole> listEcole = new ArrayList<>();
	
	private Filieres choosedFiliere = new Filieres();
	private List<Filieres> listeFiliere = new ArrayList<>();
	
	private Section choosedSection = new Section();
	private List<Section> listeSection = new ArrayList<>();
	
	private Matiere selectedMatiere = new Matiere();
	private Rattacher selectedRattacher = new Rattacher();
	private List<Enseigner> listeAffecter = new ArrayList<>();
	
	private List<Enseignant> listEnseignant = new ArrayList<>();
	private Enseignant selectedEnseignant = new Enseignant();
	
	private Semestre choosedSemestre = new Semestre();
	private List<Semestre> listeSemestre = new ArrayList<>();
	

	private Enseignant enseignant = new Enseignant();
	private Enseigner enseigner = new Enseigner();
	private AnneesScolaire anneesScolaire = new AnneesScolaire();
	private List<Matiere> listeNonAffectee = new ArrayList<>();
	private List<Rattacher> listeRatachNonAffecte = new ArrayList<>();
	
	private Statut statut = new Statut();
	private Sexe sexe = new Sexe();
	private Rattacher rattacher = new Rattacher(); 
	
	// Contrôle de coposant
			private CommandButton btnValider = new CommandButton();
			private CommandButton btnAnnuler = new CommandButton();
	
	
	public void initiate(){
		btnValider.setDisabled(true);
		btnAnnuler.setDisabled(true); 
	}
	
	
	public void annuler(){
				// Vider les Objets
				viderEnseignant(enseignant);
				viderEnseigner(enseigner);	
				statut.setLibelleStatut(null);
				sexe.setLibSexe(null);
	}
	
	
	public void chargerFiliere(){
		//Vider les listes avent rechargement
		listeFiliere.clear();
		listeSection.clear();
		listeNonAffectee.clear();
		listeAffecter.clear();
		listeRatachNonAffecte.clear();
		//Charger la liste des filières concernées
		listeFiliere = requeteFiliere.recupFiliereByEcole(choosedEcole.getCodeEcole());
	}
	
	
	public void chargerRattacher(){
		try {
			rattacher = reqRattacher.recupRatacher(choosedSection.getCodeSection(), selectedMatiere.getCodeMatiere()).get(0);
		} catch (IndexOutOfBoundsException e) {
		}
	}
	
	public void chargerSection(){
		
		listeNonAffectee.clear();
		listeAffecter.clear();
		listeRatachNonAffecte.clear();
		//Vider la liste des sections
		listeSection.clear();
		for(Section varSection: choosedFiliere.getSections() ){
			listeSection.add(varSection);
		}
	}
	
	
	public List<Matiere> chargerListesMatiere(){
		//Recuperer la liste de toutes les matières de la section
		listeAffecter.clear();
		listeRatachNonAffecte.clear();
		List<Rattacher> listeTotaleRattacher = new ArrayList<>();
		for (Rattacher vaRattacher : choosedSection.getRattachers()) {
			listeTotaleRattacher.add(vaRattacher);
		}
		
		//Recuperer la liste des matières déjà affectées à des enseignants
		anneesScolaire = reqAnneeScolaire.recupererDerniereAnneeScolaire().get(0);
		
		for (Enseigner varEnseigner : reqEnseigner.recupEnsegnerBySection(choosedSection.getCodeSection(), anneesScolaire.getCodeAnnees() )) {
			listeAffecter.add(varEnseigner);
		}
		
		//Trier la liste totale des matiere pour en retenir que les non affectées
		for (Rattacher varRatachee : listeTotaleRattacher) {
			boolean etat = false;
			for (Enseigner varEnseigner : listeAffecter) {
				if(varRatachee.getMatiere().getCodeMatiere()==(varEnseigner.getMatiere().getCodeMatiere())){
					etat = true;
					break;//sortie pour passer à l'itérration suivante
				}
				}
			//Ajouter la matière à la liste à affecter
			if (etat==false) {
				listeRatachNonAffecte.add(varRatachee);
			}
		}
		return listeNonAffectee;
	}
	
	
	
	
	public void selectionnerEnseig(){
		
		//Recuperer l'enseigant
		enseignant.setUserId(selectedEnseignant.getUserId());
		enseignant.setSpecialite(selectedEnseignant.getSpecialite());
		enseignant.setUserAuthentication(selectedEnseignant.getUserAuthentication());
		enseignant.setCodeSexe(selectedEnseignant.getCodeSexe());
		enseignant.setUsername(selectedEnseignant.getUsername());
		enseignant.setPassword(selectedEnseignant.getPassword());
		enseignant.setEnabled(selectedEnseignant.getEnabled());
		enseignant.setEmail(selectedEnseignant.getEmail());
		enseignant.setNom(selectedEnseignant.getNom());
		enseignant.setPrenoms(selectedEnseignant.getPrenoms());
		enseignant.setPhone1(selectedEnseignant.getPhone1());
		enseignant.setPhone2(selectedEnseignant.getPhone2());
		enseignant.setPhoto(selectedEnseignant.getPhoto());
		enseignant.setLieuNais(selectedEnseignant.getLieuNais());
		enseignant.setDateNais(selectedEnseignant.getDateNais());
		enseignant.setVhObligatoireSemaine(selectedEnseignant.getVhObligatoireSemaine());
		enseignant.setEtatEnseignant(selectedEnseignant.getEtatEnseignant());
		enseignant.setMatriculeEns(selectedEnseignant.getMatriculeEns());
		//setEnseignant(selectedEnseignant);
		
		//Recupérer le statut de l'ensignant
		statut = reqEnseignantStatut.recupLastEnseignantStatut().get(0).getStatut();
				
		//Recuper le sexe de l'enseignant
		sexe = (Sexe) getService().getObjectById(selectedEnseignant.getCodeSexe(), "Sexe");
		
		btnValider.setDisabled(false);
		btnAnnuler.setDisabled(false); 
	}
	
	
	public void affecterEnseignant(){
		enseigner.setAnneesScolaire(reqAnneeScolaire.recupererDerniereAnneeScolaire().get(0));
		enseigner.setMatiere(selectedRattacher.getMatiere());
		enseigner.setEnseignant(selectedEnseignant);
		enseigner.setSection(choosedSection);
		enseigner.setDateEnseigner(Calendar.getInstance().getTime());
		enseigner.setEtatPlaning(false);
		getService().addObject(enseigner);
		
		// Vider les Objets
		viderEnseignant(enseignant);
		viderEnseigner(enseigner);	
		statut.setLibelleStatut(null);
		sexe.setLibSexe(null);
		
		//REATUALISER LES LISTES AFFECTEE ET NON AFFECTEE
		chargerListesMatiere();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}
	
	
	
	public void viderEnseignant(Enseignant objEnseignant) {
		objEnseignant.setMatriculeEns(null);
		objEnseignant.setDateNais(null);
		objEnseignant.setDateNais(null);
		objEnseignant.setLieuNais(null);
		objEnseignant.setEmail(null);
		objEnseignant.setNom(null);
		objEnseignant.setPrenoms(null);
		objEnseignant.setPhone1(null);
		objEnseignant.setPhone2(null);
		objEnseignant.setSpecialite(null);
	}
	
	
	public void viderEnseigner(Enseigner objEnseigner){
		objEnseigner.setAnneesScolaire(null);
		objEnseigner.setEnseignant(null);
		objEnseigner.setMatiere(null);
		objEnseigner.setSection(null);
		objEnseigner.setTauxHoraireEffectif(null);
		objEnseigner.setVhEffectif(null);
	}
	
	
	
	public void vider(Semestre objSemestre) {
		objSemestre.setLibSemestre(null);
		objSemestre.setEtatSemestre(false);
	}
	
	
	*//****************************************Accesseurs***********************************************//*	
		
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

		public ReqEnseigner getReqEnseigner() {
			return reqEnseigner;
		}

		public void setReqEnseigner(ReqEnseigner reqEnseigner) {
			this.reqEnseigner = reqEnseigner;
		}

		public ReqAnneeScolaire getReqAnneeScolaire() {
			return reqAnneeScolaire;
		}

		public void setReqAnneeScolaire(ReqAnneeScolaire reqAnneeScolaire) {
			this.reqAnneeScolaire = reqAnneeScolaire;
		}

		public Matiere getSelectedMatiere() {
			chargerRattacher();
			return selectedMatiere;
		}

		public void setSelectedMatiere(Matiere selectedMatiere) {
			this.selectedMatiere = selectedMatiere;
		}

		

		@SuppressWarnings("unchecked")
		public List<Enseignant> getListEnseignant() {
			if (listEnseignant.isEmpty()) {
				listEnseignant = getService().getObjects("Enseignant");
			}
			return listEnseignant;
		}

		public void setListEnseignant(List<Enseignant> listEnseignant) {
			this.listEnseignant = listEnseignant;
		}

		public Enseignant getSelectedEnseignant() {
			return selectedEnseignant;
		}

		public void setSelectedEnseignant(Enseignant selectedEnseignant) {
			this.selectedEnseignant = selectedEnseignant;
		}

		public CommandButton getBtnValider() {
			return btnValider;
		}

		public void setBtnValider(CommandButton btnValider) {
			this.btnValider = btnValider;
		}

		public List<Filieres> getListeFiliere() {
			return listeFiliere;
		}

		public void setListeFiliere(List<Filieres> listeFiliere) {
			this.listeFiliere = listeFiliere;
		}

		public List<Matiere> getListeNonAffectee() {
			return listeNonAffectee;
		}

		public void setListeNonAffectee(List<Matiere> listeNonAffectee) {
			this.listeNonAffectee = listeNonAffectee;
		}

		public Enseignant getEnseignant() {
			return enseignant;
		}

		public void setEnseignant(Enseignant enseignant) {
			this.enseignant = enseignant;
		}

		public List<Enseigner> getListeAffecter() {
			return listeAffecter;
		}

		public void setListeAffecter(List<Enseigner> listeAffecter) {
			this.listeAffecter = listeAffecter;
		}

		public Semestre getChoosedSemestre() {
			return choosedSemestre;
		}

		public void setChoosedSemestre(Semestre choosedSemestre) {
			this.choosedSemestre = choosedSemestre;
		}

		@SuppressWarnings("unchecked")
		public List<Semestre> getListeSemestre() {
			if (listeSemestre.isEmpty()) {
				listeSemestre = getService().getObjects("Semestre");
			}
			return listeSemestre;
		}

		public void setListeSemestre(List<Semestre> listeSemestre) {
			this.listeSemestre = listeSemestre;
		}

		public Enseigner getEnseigner() {
			return enseigner;
		}

		public void setEnseigner(Enseigner enseigner) {
			this.enseigner = enseigner;
		}

		public ReqEnseignantStatut getReqEnseignantStatut() {
			return reqEnseignantStatut;
		}

		public void setReqEnseignantStatut(ReqEnseignantStatut reqEnseignantStatut) {
			this.reqEnseignantStatut = reqEnseignantStatut;
		}

		public Statut getStatut() {
			return statut;
		}

		public void setStatut(Statut statut) {
			this.statut = statut;
		}
		
		public RequeteFiliere getRequeteFiliere() {
			return requeteFiliere;
		}

		public void setRequeteFiliere(RequeteFiliere requeteFiliere) {
			this.requeteFiliere = requeteFiliere;
		}

		public Sexe getSexe() {
			return sexe;
		}

		public void setSexe(Sexe sexe) {
			this.sexe = sexe;
		}
		
		public ReqRattacher getReqRattacher() {
			return reqRattacher;
		}

		public void setReqRattacher(ReqRattacher reqRattacher) {
			this.reqRattacher = reqRattacher;
		}


		public Rattacher getRattacher() {
			return rattacher;
		}


		public void setRattacher(Rattacher rattacher) {
			this.rattacher = rattacher;
		}


		public CommandButton getBtnAnnuler() {
			return btnAnnuler;
		}


		public void setBtnAnnuler(CommandButton btnAnnuler) {
			this.btnAnnuler = btnAnnuler;
		}


		public List<Rattacher> getListeRatachNonAffecte() {
			return listeRatachNonAffecte;
		}


		public void setListeRatachNonAffecte(List<Rattacher> listeRatachNonAffecte) {
			this.listeRatachNonAffecte = listeRatachNonAffecte;
		}


		public Rattacher getSelectedRattacher() {
			return selectedRattacher;
		}


		public void setSelectedRattacher(Rattacher selectedRattacher) {
			this.selectedRattacher = selectedRattacher;
		}
}
*/