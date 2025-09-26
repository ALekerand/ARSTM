package com.ARSTM.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.Ecolages;
import com.ARSTM.model.EtablScolarite;
import com.ARSTM.model.Etudiants;
import com.ARSTM.model.FraisAnnexe;
import com.ARSTM.model.Inscriptions;
import com.ARSTM.model.Mention;
import com.ARSTM.model.TypeLogementNationalite;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.requetes.ReqEcolage;
import com.ARSTM.requetes.ReqEtudiant;
import com.ARSTM.requetes.ReqFraisAnnexes;
import com.ARSTM.requetes.ReqTypelogemTypeNation;
import com.ARSTM.requetes.RequeteInscription;
import com.ARSTM.service.Iservice;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Component
@Scope("session")
public class EtablisScolariteBean {
	@Autowired
	private Iservice service;
	@Autowired
	ReqAnneeScolaire reqAnneeScolaire;
	@Autowired
	ReqEtudiant reqEtudiant;
	@Autowired
	RequeteInscription requeteInscription;
	@Autowired
	ReqEcolage reqEcolage;
	@Autowired
	ReqFraisAnnexes reqFraisAnnexes;
	@Autowired
	ReqTypelogemTypeNation reqTypelogemTypeNation;

	private AnneesScolaire anneEncoure = new AnneesScolaire();
	private Etudiants etudiants = new Etudiants();
	private String matriculeRecherche;
	private Inscriptions inscriptions = new Inscriptions();
	private Inscriptions selectedInscription = new Inscriptions();
	private Mention  mention = new Mention();
	private Ecolages ecolage = new Ecolages();
	private FraisAnnexe fraisAnnexe = new FraisAnnexe();
	private EtablScolarite etablScolarite = new EtablScolarite();
	private boolean etatReduction;
	private BigDecimal totalfrais;
	private String pdfUrl;
	
	
	

	// Pour l'upload
	private String destination = "C:/GIVAC/photos/";
	private String cheminFinal ="";
	private	StreamedContent content = new DefaultStreamedContent();
	private List listEtudiant = new ArrayList<>();
	private List listInscription = new ArrayList<>();
	private List listeEtudiant = new ArrayList<>();
	private String nom_fichier;
	
	// Contr�le de coposant
	private CommandButton btnValider = new CommandButton();
	private CommandButton btnAnuler = new CommandButton();
	private InputText imputTaux = new InputText();
	private InputText imputMontant = new InputText();
	private InputText inputMontantEcolage = new InputText();
	private InputText imputTReduction = new InputText();
	private SelectBooleanCheckbox checkBox = new SelectBooleanCheckbox();
	private SelectBooleanCheckbox checkBoxExho = new SelectBooleanCheckbox();
	
	// M�thodes
	@PostConstruct
	public AnneesScolaire recupererAnne(){
		//Charger l'ann�e scolaire en cours
		anneEncoure = reqAnneeScolaire.recupererDerniereAnneeScolaire().get(0);
		fraisAnnexe = reqFraisAnnexes.recupFraisAnexByTypeNation(anneEncoure.getCodeAnnees(), 1);
		imputMontant.setDisabled(true);
		imputTaux.setDisabled(true);
		imputTReduction.setDisabled(true);
		return anneEncoure;
	}
	
	
	public void chargerfrais() {
		//Frais annexes concernés
		etablScolarite.setFraisInscriptionSco(fraisAnnexe.getFraisInscription());    
		etablScolarite.setFraisAssuranceSco(new BigDecimal(fraisAnnexe.getFraisAssurance()));
		etablScolarite.setFraisElearningSco(new BigDecimal(fraisAnnexe.getFraisElearning()));
		etablScolarite.setFraisTenueCompletSco(new BigDecimal(fraisAnnexe.getFraisTenueComplet()));
		etablScolarite.setFraisTenueSportSco(new BigDecimal(fraisAnnexe.getFraisTenueSport()));
		etablScolarite.setFraisVisiteMedicSco(new BigDecimal(fraisAnnexe.getFraisVisiteMedic()));
		etablScolarite.setFraisRestaurationSco(new BigDecimal(fraisAnnexe.getFraisRestauration()));
		etablScolarite.setFraisOrdinateurSco(new BigDecimal(fraisAnnexe.getFraisOrdinateur()));
		etablScolarite.setAutreFraisSco(new BigDecimal(fraisAnnexe.getAutreFrais()));
		
		//Pour le logement
		chargerMontantLogement();
				//Totaliser les frais
		totalfrais = etablScolarite.getFraisInscriptionSco().add(etablScolarite.getFraisAssuranceSco().add(etablScolarite.getFraisElearningSco().add(etablScolarite.getFraisTenueCompletSco().add(etablScolarite.getFraisTenueSportSco().add(etablScolarite.getFraisVisiteMedicSco().add(etablScolarite.getFraisRestaurationSco().add(etablScolarite.getFraisOrdinateurSco().add(etablScolarite.getAutreFraisSco().add(etablScolarite.getMontantLogementSco())))))))));

		
		if(inscriptions.getRegime().getCodeRegime() == 2) {
			//Activer l'ecoage
			checkBox.setDisabled(false);
			imputMontant.setDisabled(true);
			imputTaux.setDisabled(true);
			inputMontantEcolage.setDisabled(false);
			
			//Ecolage concern� en fonction du type de Nationalit�
			ecolage = reqEcolage.recupEcolage(inscriptions.getSection().getMention().getCodeMention(), anneEncoure.getCodeAnnees(), etudiants.getTypenationalite().getCodeTypenationalite());
			etablScolarite.setMontantEcolageSco(ecolage.getMontantEcolage());
			
			//Pour l'ecolage et echeance
			etablScolarite.setMtEchance1Sco(ecolage.getMtEchance1());
			etablScolarite.setMtEchance2Eco(ecolage.getMtEchance2());
			etablScolarite.setMtEchance3Sco(ecolage.getMtEchance3());
			etablScolarite.setMtEchance4Eco(ecolage.getMtEchance4());
			
			etablScolarite.setDateEchance1Sco(ecolage.getDateEchance1());
			etablScolarite.setDateEchance2Eco(ecolage.getDateEchance2());
			etablScolarite.setDateEchance3Sco(ecolage.getDateEchance3());
			etablScolarite.setDateEchance4Eco(ecolage.getDateEchance4());
			etablScolarite.setDateEtablissementSco(new Date());
			
			//Totaliser les frais et actualiser le montant du premier versement
			
			etablScolarite.setMtEchance1Sco(etablScolarite.getMtEchance1Sco().add(totalfrais)) ;
			//chargerMontantLogement();
			
	
		}else {
			
			desactiverEcolage();
			
			//Pour l'ecolage et echeance
			etablScolarite.setMtEchance1Sco(totalfrais);
			etablScolarite.setMtEchance2Eco(null);
			etablScolarite.setMtEchance3Sco(null);
			etablScolarite.setMtEchance4Eco(null);
			etablScolarite.setDateEchance1Sco(ecolage.getDateEchance1());
			etablScolarite.setDateEchance2Eco(ecolage.getDateEchance2());
			etablScolarite.setDateEchance3Sco(ecolage.getDateEchance3());
			etablScolarite.setDateEchance4Eco(ecolage.getDateEchance4());
			etablScolarite.setDateEtablissementSco(new Date());
		}
		chargerMontantLogement();
	}
	
	
	
	public void chargerMontantLogement() {
		//Recuperer les frais logement
				TypeLogementNationalite typeLogementNationalite = reqTypelogemTypeNation.recupTypelogeTypenation(inscriptions.getTypeLogement().getCodetypeLogement(), etudiants.getTypenationalite().getCodeTypenationalite(), anneEncoure.getCodeAnnees());
				
				//Pour le logemment
				etablScolarite.setMontantLogementSco(typeLogementNationalite.getMontantTypeLogement());
				etablScolarite.setCautionLogementSco(typeLogementNationalite.getCautionTypeLogement());
		
	}
	
	
	public void desactiverEcolage() {
		checkBox.setDisabled(true);
		imputMontant.setDisabled(true);
		imputTaux.setDisabled(true);
		inputMontantEcolage.setDisabled(true);
	}
	
	
	public void selectionner() throws FileNotFoundException {
		annuler();
		inscriptions = selectedInscription;
		etudiants = selectedInscription.getEtudiants();
		chargerPhoto();
		
		//Charger les frais
		chargerfrais();
	}
	
	
	public void calculerMtreduction(){
	etablScolarite.setMtReductionSco((etablScolarite.getMontantEcolageSco().multiply(new BigDecimal(etablScolarite.getTauxReduction()))).divide(new BigDecimal(100)));
	}
	
	
	public void calculerPourcentage() {
		etablScolarite.setTauxReduction(etablScolarite.getMtReductionSco().multiply(new BigDecimal(100)).divide(etablScolarite.getMontantEcolageSco()).longValue());
	}
	
	public void activerChamp() {
		if (checkBox.isSelected()) {
			imputMontant.setDisabled(false);
			imputTaux.setDisabled(false);
			imputTReduction.setDisabled(false);
			etablScolarite.setMtReductionSco(null);
			etablScolarite.setTauxReduction(null);
		}else {
			imputMontant.setDisabled(true);
			imputTaux.setDisabled(true);
			imputTReduction.setDisabled(true);
			//vider la r�duction
		}
	}
	
	
	public void exhonererFrais() {
		if(checkBoxExho.isSelected()){
			etablScolarite.setFraisInscriptionSco(new BigDecimal(0));    
			etablScolarite.setFraisAssuranceSco(new BigDecimal(0));
			etablScolarite.setFraisElearningSco(new BigDecimal(0));
			etablScolarite.setFraisTenueCompletSco(new BigDecimal(0));
			etablScolarite.setFraisTenueSportSco(new BigDecimal(0));
			etablScolarite.setFraisVisiteMedicSco(new BigDecimal(0));
			etablScolarite.setFraisRestaurationSco(new BigDecimal(0));
			etablScolarite.setFraisOrdinateurSco(new BigDecimal(0));
			etablScolarite.setAutreFraisSco(new BigDecimal(0));
			
			//Pour l'ecolage et echeance
			etablScolarite.setMtEchance1Sco(new BigDecimal(0));
			etablScolarite.setMtEchance2Eco(new BigDecimal(0));
			etablScolarite.setMtEchance3Sco(new BigDecimal(0));
			etablScolarite.setMtEchance4Eco(new BigDecimal(0));
			
			viderEcheance();
			
			//Pour le logemment
			etablScolarite.setMontantLogementSco(new BigDecimal(0));
			etablScolarite.setCautionLogementSco(new BigDecimal(0));
			
		}else {
			chargerfrais();
		}
	}
	
	
	
	public void enregistrer() throws JRException, IOException {
		etablScolarite.setEtudiants(etudiants);
		etablScolarite.setAnneesScolaire(anneEncoure);
		etablScolarite.setSection(inscriptions.getSection());
		inscriptions.setEtatEtabScolarite(true);
		if (checkBox.isSelected()) {
			etablScolarite.setDateReduction(new Date());
		}
		
		getService().updateObject(inscriptions);
		getService().addObject(etablScolarite);
		checkBoxExho.setSelected(false);
		
		//Generer Facture
		genererFacture();
		ouvrirPDF();
		annuler();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès!", "Scolarité de l'étudiant établie."));
		//viderPhoto();
	}
	
	
public void genererFacture() throws JRException, IOException {
	
		String numeroFacture = "fact_"+etudiants.getMle()+"_"+anneEncoure.getAnneesFin();
		nom_fichier = numeroFacture+".pdf";
		
		// ✅ 1. Charger le fichier .jasper déjà compilé
        File reportFile = new File("C:GIVAC/etats/facture_scolarite.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
		
		Map<String,Object> parameters = new HashMap<String,Object>();		
		  parameters.put("num_facture",numeroFacture);
		  //parameters.put("annee_academique",anneEncoure.getLibAnneeScolaire());
		  parameters.put("nom",etudiants.getNomEtudiant()+" "+etudiants.getPrenomEtudiant());
		  parameters.put("prenoms",etudiants.getPrenomEtudiant());
		  parameters.put("filiere","");
		  parameters.put("section",selectedInscription.getSection().getAbrevSection());
		 
		  parameters.put("versement_1",ecolage.getMtEchance1());
		  parameters.put("versement_2",ecolage.getMtEchance1() );
		  parameters.put("versement_3",ecolage.getMtEchance1());
		  parameters.put("versement_4",ecolage.getMtEchance1());
		
		  parameters.put("date_1",ecolage.getDateEchance1());
		  parameters.put("date_2",ecolage.getDateEchance2());
		  parameters.put("date_3",ecolage.getDateEchance3());
		  parameters.put("date_4",ecolage.getDateEchance4());
		 
		
		// Remplissage du rapport compilé
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, new JREmptyDataSource());
		
		// Visualisation, exportation ou impression 
	    JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/GIVAC/etats/"+nom_fichier);
		 	}


public void ouvrirPDF() throws IOException {
    try {
		//String code = nom_fichier  ;
    	
        File source = new File("C:/GIVAC/etats/"+nom_fichier);

        //On copie vers un dossier du projet web accessible via HTTP
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        File destDir = new File(contextPath + "resources/pdf/");
        if (!destDir.exists()) destDir.mkdirs();

        File destFile = new File(destDir, nom_fichier);

        // Copie physique
        Files.copy(source.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

     // Stocker l'URL pour l'ouvrir côté JSF
        String webPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        pdfUrl = webPath + "/resources/pdf/" + nom_fichier;
        
     
     // ✅ Utiliser RequestContext pour exécuter JavaScript dans PrimeFaces 6.x
        RequestContext.getCurrentInstance().execute("window.open('" + pdfUrl + "', '_blank');");
       
        
    } catch (IOException e) {
        e.printStackTrace();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Impossible d'ouvrir le fichier PDF."));
    }catch (NullPointerException e) {
    	 e.printStackTrace();
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez selectionner la ligne puis ouvrir le fichier."));
	}
}




	public void viderEtudiant() {
		//Info personnelle �tudiant
				etudiants.setNomEtudiant(null);
				etudiants.setPrenomEtudiant(null);
				etudiants.setDatenais(null);
				etudiants.setLieunais(null);
				etudiants.setMailEtudiant(null);
				etudiants.setTelEtudiant(null);
				etudiants.setNumcni(null);
				etudiants.setEcoleAncienneEtudiant(null);
				etudiants.setNomPrenomsPere(null);
				etudiants.setFonctionPere(null);
				etudiants.setTelPere(null);
				etudiants.setNomPrenomsMere(null);
				etudiants.setFonctionMere(null);
				etudiants.setTelMere(null);
				etudiants.setNomPrenomsTuteur(null);
				etudiants.setTelTuteur(null);
				etudiants.setNomPrenomsDocteur(null);
				etudiants.setTelDocteur(null);
				inscriptions.setSection(null);
				inscriptions.setRegime(null);
				matriculeRecherche = "";
	}
	
	public void viderFrais() {
		//Les frais
				etablScolarite.setMontantEcolageSco(null);
				etablScolarite.setTauxReduction(null);
				etablScolarite.setFraisInscriptionSco(null);
				etablScolarite.setFraisAssuranceSco(null);
				etablScolarite.setFraisElearningSco(null);
				etablScolarite.setFraisOrdinateurSco(null);
				etablScolarite.setFraisRestaurationSco(null);
				etablScolarite.setFraisTenueCompletSco(null);
				etablScolarite.setFraisTenueSportSco(null);
				etablScolarite.setFraisVisiteMedicSco(null);
				etablScolarite.setAutreFraisSco(null);
				etablScolarite.setMontantLogementSco(null);
				etablScolarite.setCautionLogementSco(null);
				viderEcheance();
	}
	
	public void viderEcheance() {
		//les �ch�ances
		etablScolarite.setMtEchance1Sco(null);
		etablScolarite.setMtEchance2Eco(null);
		etablScolarite.setMtEchance3Sco(null);
		etablScolarite.setMtEchance4Eco(null);
		etablScolarite.setDateEchance1Sco(null);
		etablScolarite.setDateEchance2Eco(null);
		etablScolarite.setDateEchance3Sco(null);
		etablScolarite.setDateEchance4Eco(null);
		etablScolarite.setDateEtablissementSco(null);
	}
		

	public void annuler() throws FileNotFoundException {
		viderEtudiant();
		viderFrais();
		viderPhoto();
	}
	
	
	
	
public StreamedContent viderPhoto() throws FileNotFoundException {
    		setCheminFinal(destination + "avatar.jpg");
 			InputStream is = new FileInputStream(cheminFinal);
 			//is.close();  
 			content	= new DefaultStreamedContent(is);
		  return content;	 
	}
	
	
//************************Pour le traitement de la photo*********************************
	
	public void upload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Photo valid�e!");
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
	
	
	public List<Object> getListeEtudiant() {
		return listeEtudiant;
	}

	public void setListeEtudiant(List<Object> listeEtudiant) {
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

	public List<Object> getListEtudiant() {
		return listEtudiant;
	}

	public void setListEtudiant(List<Object> listEtudiant) {
		this.listEtudiant = listEtudiant;
	}

	public CommandButton getBtnAnuler() {
		return btnAnuler;
	}


	public void setBtnAnuler(CommandButton btnAnuler) {
		this.btnAnuler = btnAnuler;
	}


	public List<Object> getListInscription() {
		listInscription.clear();
		listInscription = requeteInscription.recupListeEtabScolarite(anneEncoure.getCodeAnnees());
		return listInscription;
	}


	public void setListInscription(List<Object> listInscription) {
		this.listInscription = listInscription;
	}


	public Inscriptions getSelectedInscription() {
		return selectedInscription;
	}


	public void setSelectedInscription(Inscriptions selectedInscription) {
		this.selectedInscription = selectedInscription;
	}

	public Mention getMention() {
		return mention;
	}

	public void setMention(Mention mention) {
		this.mention = mention;
	}

	public EtablScolarite getEtablScolarite() {
		return etablScolarite;
	}

	public void setEtablScolarite(EtablScolarite etablScolarite) {
		this.etablScolarite = etablScolarite;
	}

	public boolean isEtatReduction() {
		return etatReduction;
	}

	public void setEtatReduction(boolean etatReduction) {
		this.etatReduction = etatReduction;
	}

	public InputText getImputTaux() {
		return imputTaux;
	}

	public void setImputTaux(InputText imputTaux) {
		this.imputTaux = imputTaux;
	}

	public InputText getImputMontant() {
		return imputMontant;
	}

	public void setImputMontant(InputText imputMontant) {
		this.imputMontant = imputMontant;
	}

	public InputText getImputTReduction() {
		return imputTReduction;
	}

	public void setImputTReduction(InputText imputTReduction) {
		this.imputTReduction = imputTReduction;
	}

	public SelectBooleanCheckbox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(SelectBooleanCheckbox checkBox) {
		this.checkBox = checkBox;
	}

	public InputText getInputMontantEcolage() {
		return inputMontantEcolage;
	}

	public void setInputMontantEcolage(InputText inputMontantEcolage) {
		this.inputMontantEcolage = inputMontantEcolage;
	}
	
	public String getPdfUrl() {
		return pdfUrl;
	}


	public SelectBooleanCheckbox getCheckBoxExho() {
		return checkBoxExho;
	}


	public void setCheckBoxExho(SelectBooleanCheckbox checkBoxExho) {
		this.checkBoxExho = checkBoxExho;
	}
}
