package com.ARSTM.managedBean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.Diplomes;
import com.ARSTM.model.Ecole;
import com.ARSTM.model.Enseignant;
import com.ARSTM.model.Etudiants;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Inscriptions;
import com.ARSTM.model.Matrimoniales;
import com.ARSTM.model.Mention;
import com.ARSTM.model.Nationalites;
import com.ARSTM.model.Niveaux;
import com.ARSTM.model.Pays;
import com.ARSTM.model.Regime;
import com.ARSTM.model.Residence;
import com.ARSTM.model.Santes;
import com.ARSTM.model.Section;
import com.ARSTM.model.Sexe;
import com.ARSTM.model.Tformation;
import com.ARSTM.model.TypeLogement;
import com.ARSTM.model.UserAuthorization;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.requetes.ReqTypeNationalite;
import com.ARSTM.requetes.RequeteFiliere;
import com.ARSTM.requetes.RequeteInscription;
import com.ARSTM.requetes.RequeteLogement;
import com.ARSTM.requetes.RequeteMention;
import com.ARSTM.requetes.RequeteSection;
import com.ARSTM.service.Iservice;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

@Component
@Scope("session")
public class InscriptionBean {
	@Autowired
	Iservice service;
	@Autowired
	RequeteInscription requeteInscription;
	@Autowired
	RequeteFiliere requeteFiliere;
	@Autowired
	RequeteMention requeteMention;
	@Autowired
	RequeteSection requeteSection;
	
	@Autowired
	ReqAnneeScolaire reqAnneeScolaire;
	
	@Autowired
	ReqTypeNationalite  reqTypeNationalite;
	
	@Autowired
	RequeteLogement requeteLogement;
	
	
	private Etudiants etudiants = new Etudiants();
	private Enseignant selectedEnseignant = new Enseignant();
	private Diplomes choosedDiplome = new Diplomes();
	private Sexe chooseedSexe = new Sexe();
	private Pays choosedPays =  new Pays();
	private Pays choosedPaysNaiss = new Pays();
	private Ecole choosedEcole = new Ecole();
	private Filieres choosedFiliere = new Filieres();
	private Mention choosedMention = new Mention();
	private Section choosedSection = new Section();
	private Regime choosedRegime = new Regime();
	private Matrimoniales choosedMatrimoniale = new Matrimoniales();
	private Santes choosedSante =new Santes();
	private Niveaux choosedNiveau = new Niveaux();
	private TypeLogement choosedTypeLogement = new TypeLogement();
	private Tformation choosedTformation = new Tformation();
	private Nationalites choosedNationalites = new Nationalites();
	private Inscriptions inscriptions = new Inscriptions();
	private Residence residence = new Residence();
	
	
	public Matrimoniales getChoosedMatrimoniale() {
		return choosedMatrimoniale;
	}

	public void setChoosedMatrimoniale(Matrimoniales choosedMatrimoniale) {
		this.choosedMatrimoniale = choosedMatrimoniale;
	}

	private List listeSexe = new ArrayList<>();
	private List listePays = new ArrayList<>();
	private List listeEcole = new ArrayList<>();
	private List listeTformation =new ArrayList<>();
	private List listeFiliere = new ArrayList<>();
	private List listeMention = new ArrayList<>();
	private List listeSection = new ArrayList<>();
	private List listeRegime = new ArrayList<>();
	private List listeTypeLogement = new ArrayList<>();
	private List listeNationalites = new ArrayList<>();
	private List listeNiveau = new ArrayList<>();
	private List listeSante = new ArrayList<>();
	private List listeMatrimoniale = new ArrayList<>();
	private List listeDiplome =  new ArrayList<>();
	private UserAuthorization userAuthorization = new UserAuthorization();
	private int maxNumeEtudiant = (int) 0;
	private String matricule;
	
	private AnneesScolaire anneEncoure = new AnneesScolaire();
	
	@PostConstruct
	public AnneesScolaire recupererAnne(){
		//Charger l'année scolaire en cours
		anneEncoure = reqAnneeScolaire.recupererDerniereAnneeScolaire().get(0);
		//générer le matricule de l'étudiant
		genererMatricule();
		return anneEncoure;
	}
	
	
public String genererMatricule() {
		try {
			maxNumeEtudiant = requeteInscription.recupMaxNumetudiant().get(0).getNumetudiant();
			matricule = ((maxNumeEtudiant+1) +" - "+anneEncoure.getAnneesDebut());
		} catch (IndexOutOfBoundsException e) {
			// Cas ou la base de donnée est vide allors comencer la numérotation par 1
			matricule = ((1) +"-"+anneEncoure.getAnneesDebut());
		}
		
		return matricule;
	}
	

	// Contrôle de composant
	private CommandButton btnValider = new CommandButton();
	
	
	public void enregistrerTout() throws JRException, IOException {
		enregistrerEtudiant();
		enregistrerInscription();
		enregistrerResidence();
		
		//Effectuer l'enregistrement
		  genererFicheInscription();
		vider(etudiants);
	}
	
	public void enregistrerInscription() {
		inscriptions.setSection(choosedSection);
		inscriptions.setEtudiants(etudiants);
		inscriptions.setAnneesScolaire(anneEncoure);
		inscriptions.setRegime(choosedRegime);
		inscriptions.setDateInscription(new Date());
		inscriptions.setEtatComplemnt(false);
		inscriptions.setEtatEtabScolarite(false);
		inscriptions.setEtatPayementScolarite(false);
		inscriptions.setTypeLogement(choosedTypeLogement);
		getService().addObject(inscriptions);
		
	}
	
	
	public void enregistrerResidence() {
		residence.setAnneesScolaire(anneEncoure);
		residence.setEtudiants(etudiants);
		
		//Recuperer le type de logement
		residence.setLogement(requeteLogement.recupLogByTypeLogement(choosedTypeLogement.getCodetypeLogement()).get(0));
		getService().addObject(residence);
	}
	
	
	public void enregistrerEtudiant(){
		//enregistrer das la table Etudiants
		  etudiants.setMle(genererMatricule());
		  etudiants.setNomEtudiant(getEtudiants().getNomEtudiant().toUpperCase());
		  etudiants.setMatrimoniales(choosedMatrimoniale);
		  etudiants.setNationalites(choosedNationalites);
		  etudiants.setSexe(chooseedSexe);
		  etudiants.setSantes(choosedSante);
		  etudiants.setNiveaux(choosedNiveau);
		  etudiants.setPaysNaissanceEtudiant(choosedPaysNaiss.getLibpays());
		  etudiants.setPays(choosedPays);
		  etudiants.setSantes(choosedSante);
		  etudiants.setDiplomes(choosedDiplome);
		  
		//Si la nationalité est ivoirienne alors faire migner Typetionalité Lacal dans l'etudiant
			if (choosedNationalites.getCodenationalite()==1) {
				etudiants.setTypenationalite(reqTypeNationalite.recupererTypeNationalite(1));
			}else {
				etudiants.setTypenationalite(reqTypeNationalite.recupererTypeNationalite(2));
			}
		  
			//Effectuer l'enregistrement
		  getService().addObject(etudiants);
		  
		  //Recharger le Matricule pour un nouvel enregistrement
		  genererMatricule();
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}
	
	
	
	public void annuler() {
		btnValider.setDisabled(false);
		vider(etudiants);
	}

	public void vider(Etudiants objEtudiants) {
		
		  objEtudiants.setDatenais(null);
		  objEtudiants.setLieunais(null);
		  objEtudiants.setPrenomEtudiant(null);
		  objEtudiants.setNomEtudiant(null);
		  objEtudiants.setDiplomes(null);
		  objEtudiants.setMatrimoniales(null);
		  objEtudiants.setNbfreres(null);
		  objEtudiants.setNbsoeurs(null);
		  objEtudiants.setSantes(null);
		  objEtudiants.setSexe(null);
		  objEtudiants.setTelEtudiant(null);
		  objEtudiants.setNationalites(null);
		  objEtudiants.setNiveaux(null);
		 
	}
	
	public void chargerFiliere(){
		listeFiliere.clear();
		listeFiliere = requeteFiliere.recupFiliereByEcole(choosedEcole.getCodeEcole(),choosedTformation.getCodeTformation());
	}

	public void chargerMention(){
		listeMention.clear();
		listeMention = requeteMention.recupMentionByEcoleFiliere(choosedFiliere.getCodeFiliere());
	}
	
	
	public void chargerSection(){
		listeSection.clear();
		listeSection = requeteSection.recupSectionByMention(choosedMention.getCodeMention());
	}
	
	
	public void genererFicheInscription() throws JRException, IOException {
		
		System.out.println("======= DEBUT METHODE POUR ETAT =========");  
		Map<String, Object> parametres= new HashMap<String, Object>();
		parametres.put("ecole",choosedEcole.getAbrevEcole());
		parametres.put("annee_academique",anneEncoure.getLibAnneeScolaire());
		parametres.put("non_prenoms",etudiants.getNomEtudiant()+" "+etudiants.getPrenomEtudiant());
		parametres.put("filiere",choosedFiliere.getAbrevFiliere());
		parametres.put("niveau",choosedNiveau.getAbrevNiveau());
		
		
		parametres.put("section",choosedSection.getAbrevSection() );
		
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/fiche_inscription.jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametres, new JREmptyDataSource());
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition","attachment; filename=fiche_inscription.pdf");
		ServletOutputStream outputStream = response.getOutputStream();
		
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		FacesContext.getCurrentInstance().getResponseComplete();
		
		 System.out.println("======= FIN METHODE POUR ETAT =========");
	}

	//**************************ACCESSEURS*************************//*

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
	public Etudiants getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(Etudiants etudiants) {
		this.etudiants = etudiants;
	}

	public Enseignant getSelectedEnseignant() {
		return selectedEnseignant;
	}

	public void setSelectedEnseignant(Enseignant selectedEnseignant) {
		this.selectedEnseignant = selectedEnseignant;
	}

	public List getListeSexe() {
		if (listeSexe.isEmpty()) {
			listeSexe = getService().getObjects("Sexe");
		}
		return listeSexe;
	}

	public void setListeSexe(List listeSexe) {
		this.listeSexe = listeSexe;
	}

	public Sexe getChooseedSexe() {

		return chooseedSexe;
	}

	public void setChooseedSexe(Sexe chooseedSexe) {
		this.chooseedSexe = chooseedSexe;
	}

	public Diplomes getChoosedDiplome() {
		return choosedDiplome;
	}

	public void setChoosedDiplome(Diplomes choosedDiplome) {
		this.choosedDiplome = choosedDiplome;
	}

	public Pays getChoosedPays() {
		return choosedPays;
	}

	public void setChoosedPays(Pays choosedPays) {
		this.choosedPays = choosedPays;
	}

	public Nationalites getChoosedNationalites() {
		return choosedNationalites;
	}

	public void setChoosedNationalites(Nationalites choosedNationalites) {
		this.choosedNationalites = choosedNationalites;
	}

	public Ecole getChoosedEcole() {
		return choosedEcole;
	}

	public void setChoosedEcole(Ecole choosedEcole) {
		this.choosedEcole = choosedEcole;
	}

	public Tformation getChoosedTformation() {
		return choosedTformation;
	}

	public void setChoosedTformation(Tformation choosedTformation) {
		this.choosedTformation = choosedTformation;
	}

	public Filieres getChoosedFiliere() {
		return choosedFiliere;
	}

	public void setChoosedFiliere(Filieres choosedFiliere) {
		this.choosedFiliere = choosedFiliere;
	}

	public Mention getChoosedMention() {
		return choosedMention;
	}

	public void setChoosedMention(Mention choosedMention) {
		this.choosedMention = choosedMention;
	}

	public Section getChoosedSection() {
		return choosedSection;
	}

	public void setChoosedSection(Section choosedSection) {
		this.choosedSection = choosedSection;
	}

	public Regime getChoosedRegime() {
		return choosedRegime;
	}

	public void setChoosedRegime(Regime choosedRegime) {
		this.choosedRegime = choosedRegime;
	}

	public TypeLogement getChoosedTypeLogement() {
		return choosedTypeLogement;
	}

	public void setChoosedTypeLogement(TypeLogement choosedTypeLogement) {
		this.choosedTypeLogement = choosedTypeLogement;
	}

	public Santes getChoosedSante() {
		return choosedSante;
	}

	public void setChoosedSante(Santes choosedSante) {
		this.choosedSante = choosedSante;
	}

	public Niveaux getChoosedNiveau() {
		return choosedNiveau;
	}

	public void setChoosedNiveau(Niveaux choosedNiveau) {
		this.choosedNiveau = choosedNiveau;
	}

	public List getListePays() {
		if (listePays.isEmpty()) {
			listePays = getService().getObjects("Pays");
		}
		return listePays;
	}

	public void setListePays(List listePays) {
		this.listePays = listePays;
	}

	public List getListeNationalites() {
		if (listeNationalites.isEmpty()) {
			listeNationalites = getService().getObjects("Nationalites");
		}
		return listeNationalites;
	}

	public void setListeNationalites(List listeNationalites) {
		this.listeNationalites = listeNationalites;
	}

	public List getListeEcole() {
		if (listeEcole.isEmpty()) {
			listeEcole = getService().getObjects("Ecole");
		}
		return listeEcole;
	}

	public void setListeEcole(List listeEcole) {
		this.listeEcole = listeEcole;
	}

	public List getListeTformation() {
		if (listeTformation.isEmpty()) {
			listeTformation = getService().getObjects("Tformation");
		}
		return listeTformation;
	}

	public void setListeTformation(List listeTformation) {
		this.listeTformation = listeTformation;
	}

	public List getListeFiliere() {
		return listeFiliere;
	}

	public void setListeFiliere(List listeFiliere) {
		this.listeFiliere = listeFiliere;
	}

	public List getListeMention() {
		return listeMention;
	}

	public void setListeMention(List listeMention) {
		this.listeMention = listeMention;
	}

	public List getListeSection() {
		return listeSection;
	}

	public void setListeSection(List listeSection) {
		this.listeSection = listeSection;
	}

	public List getListeRegime() {
		if (listeRegime.isEmpty()) {
			listeRegime = getService().getObjects("Regime");
		}
		return listeRegime;
	}

	public void setListeRegime(List listeRegime) {
		this.listeRegime = listeRegime;
	}

	public List getListeTypeLogement() {
		if (listeTypeLogement.isEmpty()) {
			listeTypeLogement = getService().getObjects("TypeLogement");
		}
		return listeTypeLogement;
	}

	public void setListeTypeLogement(List listeTypeLogement) {
		this.listeTypeLogement = listeTypeLogement;
	}

	public List getListeNiveau() {
		if (listeNiveau.isEmpty()) {
			listeNiveau = getService().getObjects("Niveaux");
		}
		return listeNiveau;
	}

	public void setListeNiveau(List listeNiveau) {
		this.listeNiveau = listeNiveau;
	}

	public List getListeMatrimoniale() {
		if (listeMatrimoniale.isEmpty()) {
			listeMatrimoniale = getService().getObjects("Matrimoniales");
		}
		return listeMatrimoniale;
	}

	public void setListeMatrimoniale(List listeMatrimoniale) {
		this.listeMatrimoniale = listeMatrimoniale;
	}

	public List getListeDiplome() {
		if (listeDiplome.isEmpty()) {
			listeDiplome = getService().getObjects("Diplomes");
		}
		return listeDiplome;
	}

	public void setListeDiplome(List listeDiplome) {
		this.listeDiplome = listeDiplome;
	}

	public List getListeSante() {
		if (listeSante.isEmpty()) {
			listeSante = getService().getObjects("Santes");
		}
		return listeSante;
	}

	public void setListeSante(List listeSante) {
		this.listeSante = listeSante;
	}


	public UserAuthorization getUserAuthorization() {
		return userAuthorization;
	}

	public void setUserAuthorization(UserAuthorization userAuthorization) {
		this.userAuthorization = userAuthorization;
	}

	
	public Inscriptions getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(Inscriptions inscriptions) {
		this.inscriptions = inscriptions;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public Pays getChoosedPaysNaiss() {
		return choosedPaysNaiss;
	}

	public void setChoosedPaysNaiss(Pays choosedPaysNaiss) {
		this.choosedPaysNaiss = choosedPaysNaiss;
	}
}