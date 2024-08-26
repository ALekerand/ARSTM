package com.ARSTM.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.cfg.beanvalidation.BeanValidationIntegrator;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.Diplomes;
import com.ARSTM.model.Ecolages;
import com.ARSTM.model.EtablScolarite;
import com.ARSTM.model.Etudiants;
import com.ARSTM.model.FraisAnnexe;
import com.ARSTM.model.Inscriptions;
import com.ARSTM.model.Matrimoniales;
import com.ARSTM.model.Mention;
import com.ARSTM.model.Mode;
import com.ARSTM.model.Nationalites;
import com.ARSTM.model.Niveaux;
import com.ARSTM.model.Regime;
import com.ARSTM.model.Santes;
import com.ARSTM.model.VersementScolarite;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.requetes.ReqEcolage;
import com.ARSTM.requetes.ReqEtablissementScolarite;
import com.ARSTM.requetes.ReqEtudiant;
import com.ARSTM.requetes.ReqFraisAnnexes;
import com.ARSTM.requetes.ReqOrigine;
import com.ARSTM.requetes.ReqVersemtscolarite;
import com.ARSTM.requetes.RequeteInscription;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class VersementScolariteBean {
	@Autowired
	private Iservice service;
	@Autowired
	ReqAnneeScolaire reqAnneeScolaire;
	@Autowired
	ReqEtudiant reqEtudiant;
	@Autowired
	RequeteInscription requeteInscription;
	@Autowired
	ReqEtablissementScolarite reqEtablissementScolarite;
	@Autowired
	ReqVersemtscolarite reqVersemtscolarite;
	@Autowired
	ReqOrigine reqOrigine;
	
	private AnneesScolaire anneEncoure = new AnneesScolaire();
	private Etudiants etudiants = new Etudiants();
	private String matriculeRecherche;
	private Inscriptions inscriptions = new Inscriptions();
	private Inscriptions selectedInscription = new Inscriptions();
	private BigDecimal totalScolarite = new BigDecimal(0);
	private BigDecimal totalVersement = new BigDecimal(0);
	private BigDecimal resteVersement = new BigDecimal(0);
	private EtablScolarite etablScolarite = new EtablScolarite();
	private VersementScolarite versementScolarite = new VersementScolarite();
	private List listMode = new ArrayList<>();
	//private Mode choosedMode = new Mode();
	private int codeMode;

	
	// Pour l'upload
	private String destination = "C:/photo/";
	private String cheminFinal ="";
	private	StreamedContent content = new DefaultStreamedContent() ;
	
	//private List listEtudiant = new ArrayList<>();
	private List listInscription = new ArrayList<>();
	
	// Contrôle de coposant
	private CommandButton btnValider = new CommandButton();
	private CommandButton btnAnuler = new CommandButton();
	private List listeEtudiant = new ArrayList<>();
	
	// Méthodes
	@PostConstruct
	public AnneesScolaire recupererAnne(){
		//Charger l'année scolaire en cours
	anneEncoure = reqAnneeScolaire.recupererDerniereAnneeScolaire().get(0);
		return anneEncoure;
	}
	
	
	public void rechercher() throws FileNotFoundException {
		annuler();
		try {
			etudiants = reqEtudiant.recupererEtudiantByMlle(matriculeRecherche).get(0);
		} catch (IndexOutOfBoundsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Recherche infructueuse. Veuillez vérifier le matricule", null));
		}
		
		if (etudiants.getMle()!= null) {
			inscriptions = requeteInscription.recupInscriptionEtabScilariteByEtudiant(etudiants.getNumetudiant(),anneEncoure.getCodeAnnees()).get(0);
			//mention = inscriptions.getSection().getMention();
			
			//Ecolage
			chargerPhoto();
			
		}
	}
	
	
	public void chargerMontant() {
			//Calculet le total de la scolarité
		if (inscriptions.getRegime().getCodeRegime() == 1) {
			//setTotalScolarite(new BigDecimal(etablScolarite.getMtEchance1Sco()));
			setTotalScolarite(etablScolarite.getMtEchance1Sco());
		} else {
			setTotalScolarite(etablScolarite.getMtEchance1Sco().add((etablScolarite.getMtEchance2Eco()).add(etablScolarite.getMtEchance3Sco()).add(etablScolarite.getMtEchance4Eco())));
		}

		//Calculer les montants déja versés
		List<VersementScolarite> listeVersement = reqVersemtscolarite.recupVersemtbyEtudiantAnne(etudiants.getNumetudiant(), anneEncoure.getCodeAnnees());
			System.out.println("==================Taille liste versement"+listeVersement.size());
			double monTo = 0;
			for (VersementScolarite var : listeVersement){
			System.out.println("============ Montant de la scolarité"+var.getMontantVersementScolarite());
			 monTo += var.getMontantVersementScolarite().doubleValue(); 
			//totalVersement.add(var.getMontantVersementScolarite());
		}
		System.out.println("============ Total versement"+monTo);
		
		setTotalVersement(new BigDecimal(monTo));
		//setTotalScolarite(new BigDecimal(monTo));
		System.out.println("=========Total versement:"+totalVersement);
		
		//Calculer le reste à payer
		setResteVersement(totalScolarite.subtract(new BigDecimal(monTo)));
		
	}
	

	public void selectionner() throws FileNotFoundException {
		annuler();
		inscriptions = selectedInscription;
		etudiants = selectedInscription.getEtudiants();
		chargerPhoto();
		
		//Charger les informations sur l'établissement
		etablScolarite =  reqEtablissementScolarite.recupEtablisScolarite(etudiants.getNumetudiant(),anneEncoure.getCodeAnnees());
		System.out.println("======= Etablissement"+etablScolarite.getMtEchance1Sco());
		//Charger les montants
		chargerMontant();
	}
	
	public void enregistrer() throws FileNotFoundException {
		
		
		int mtPositif = versementScolarite.getMontantVersementScolarite().compareTo(BigDecimal.ZERO);
		int mtpayeExact = versementScolarite.getMontantVersementScolarite().compareTo(resteVersement);
		
		//Vérifier si le montant n'est pas null ou superieur aureste à payer
		if ((mtPositif == 1) && (mtpayeExact != 1)){
			//Faire l'enregistrement
			//Enregistrement du versement
			versementScolarite.setAnneesScolaire(anneEncoure);
			versementScolarite.setEtudiants(etudiants);
			versementScolarite.setMode((Mode) service.getObjectById(codeMode, "Mode"));
			versementScolarite.setOrigine(reqOrigine.recupOrigineById(1));
			versementScolarite.setDateVersementSco(new Date());
			service.addObject(versementScolarite);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Réglement effectué!", null));

			//Vider le champs
			annuler();
			
			
		
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veuillez vérifier le montant du versement!", null));

			
		}
		
	}
	
	
	public void annuler() throws FileNotFoundException {
		//Info personnelle étudiant
		etudiants.setNomEtudiant(null);
		etudiants.setPrenomEtudiant(null);
		etudiants.setTelEtudiant(null);
		etudiants.setNationalites(null);
		
		inscriptions.setRegime(null);
		inscriptions.setSection(null);
		
		setTotalVersement(new BigDecimal(0));
		setTotalScolarite(new BigDecimal(0));
		setResteVersement(new BigDecimal(0));
		versementScolarite.setMontantVersementScolarite(new BigDecimal(0));
		
		setCodeMode(0);
		matriculeRecherche = "";
		viderPhoto();
		
	}
	
	
public StreamedContent viderPhoto() throws FileNotFoundException {
    		setCheminFinal(destination + "avatar.jpg");
 			InputStream is = new FileInputStream(cheminFinal);
 			//is.close();  
 			content	= new DefaultStreamedContent(is);
		  return content;	 
	}
	
	
//************************Pour le traitement de la photo
	
	public void upload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Photo validée!");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
 
    public void copyFile(String fileName, InputStream in) {
        try {
        //lE CHEMIN
        	cheminFinal = destination + fileName;
            OutputStream out = new FileOutputStream(new File(destination + fileName));
 
            int read = 0;
            byte[] bytes = new byte[1024];
 
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
 
            in.close();
            out.flush();
            out.close();
            
 // Charger le fichier dans le graphique image
            getContent();
            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
       
    }
    
    
    public void chargerPhoto() throws FileNotFoundException {
    	cheminFinal = getEtudiants().getPhotoEtudiant();
    	InputStream is = new FileInputStream(cheminFinal);
			//is.close();  
			content	= new DefaultStreamedContent(is);
			
    	
    }
    
    /****** Accesseurs ***********************************/
    
    //Pour charger le graphiqueImage
    public StreamedContent getContent() {
		  return content;	 
	}
    
	public void setContent(StreamedContent content) {
		this.content = content;
	}

	public String getCheminFinal() {
		return cheminFinal;
	}

	public void setCheminFinal(String cheminFinal) {
		this.cheminFinal = cheminFinal;
	}
	
	
	public List getListeEtudiant() {
		return listeEtudiant;
	}

	public void setListeEtudiant(List listeEtudiant) {
		this.listeEtudiant = listeEtudiant;
	}

	public void actualiserList(){
	}

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


	public ReqAnneeScolaire getReqAnneeScolaire() {
		return reqAnneeScolaire;
	}

	public void setReqAnneeScolaire(ReqAnneeScolaire reqAnneeScolaire) {
		this.reqAnneeScolaire = reqAnneeScolaire;
	}

	public Etudiants getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(Etudiants etudiants) {
		this.etudiants = etudiants;
	}

	
	/*
	 * public Nationalites getNationalites() { return nationalites; }
	 * 
	 * public void setNationalites(Nationalites nationalites) { this.nationalites =
	 * nationalites; }
	 * 
	 * public Regime getRegime() { return regime; }
	 * 
	 * public void setRegime(Regime regime) { this.regime = regime; }
	 */

	public String getMatriculeRecherche() {
		return matriculeRecherche;
	}



	public void setMatriculeRecherche(String matriculeRecherche) {
		this.matriculeRecherche = matriculeRecherche;
	}



	public Inscriptions getInscriptions() {
		return inscriptions;
	}



	public void setInscriptions(Inscriptions inscriptions) {
		this.inscriptions = inscriptions;
	}


	public CommandButton getBtnAnuler() {
		return btnAnuler;
	}


	public void setBtnAnuler(CommandButton btnAnuler) {
		this.btnAnuler = btnAnuler;
	}


	public List getListInscription() {
		listInscription.clear();
		listInscription = requeteInscription.recupListeInscriptionComplet(anneEncoure.getCodeAnnees());

		//System.out.println("Taille du fichier:"+listInscription.size());
		return listInscription;
	}


	public void setListInscription(List listInscription) {
		this.listInscription = listInscription;
	}


	public Inscriptions getSelectedInscription() {
		return selectedInscription;
	}


	public void setSelectedInscription(Inscriptions selectedInscription) {
		this.selectedInscription = selectedInscription;
	}


	public EtablScolarite getEtablScolarite() {
		return etablScolarite;
	}

	public void setEtablScolarite(EtablScolarite etablScolarite) {
		this.etablScolarite = etablScolarite;
	}


	public VersementScolarite getVersementScolarite() {
		return versementScolarite;
	}

	public void setVersementScolarite(VersementScolarite versementScolarite) {
		this.versementScolarite = versementScolarite;
	}

	public BigDecimal getTotalScolarite() {
		return totalScolarite;
	}

	public void setTotalScolarite(BigDecimal totalScolarite) {
		this.totalScolarite = totalScolarite;
	}

	public BigDecimal getTotalVersement() {
		return totalVersement;
	}

	public void setTotalVersement(BigDecimal totalVersement) {
		this.totalVersement = totalVersement;
	}

	public BigDecimal getResteVersement() {
		return resteVersement;
	}

	public void setResteVersement(BigDecimal resteVersement) {
		this.resteVersement = resteVersement;
	}

	public List getListMode() {
		listMode = service.getObjects("Mode");
		return listMode;
	}

	public void setListMode(List listMode) {
		this.listMode = listMode;
	}

	public int getCodeMode() {
		return codeMode;
	}

	public void setCodeMode(int codeMode) {
		this.codeMode = codeMode;
	}

}
