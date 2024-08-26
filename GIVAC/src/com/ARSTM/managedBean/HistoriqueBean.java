package com.ARSTM.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.Diplomes;
import com.ARSTM.model.Etudiants;
import com.ARSTM.model.Inscriptions;
import com.ARSTM.model.Matrimoniales;
import com.ARSTM.model.Niveaux;
import com.ARSTM.model.Santes;
import com.ARSTM.model.Section;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.requetes.ReqEtudiant;
import com.ARSTM.requetes.RequeteInscription;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class HistoriqueBean {
	@Autowired
	private Iservice service;
	@Autowired
	ReqEtudiant reqEtudiant;
	@Autowired
	RequeteInscription requeteInscription;

	private Etudiants etudiants = new Etudiants();
	private Matrimoniales choosedMatrimoniales;
	private Santes chooseedsantes = new Santes();
	private Niveaux choosedNiveaux = new Niveaux();
	private Diplomes choosedDiplomes = new Diplomes();
	private String matriculeRecherche;
	private Inscriptions inscriptions = new Inscriptions();
	private Inscriptions selectedInscription = new Inscriptions();
	private Etudiants selectedEtudiant =new Etudiants();
	private Section section = new Section();

	
	
	// Pour l'upload
	private String destination = "C:/photo/";
	private String cheminFinal ="";
	private	StreamedContent content = new DefaultStreamedContent() ;
	
	
	private List listMatrimonale = new ArrayList<>();
	private List listeSante = new ArrayList<>();
	private List listeNiveaux = new ArrayList<>();
	private List listDiplome = new ArrayList<>();
	private List listInscription = new ArrayList<>();
	private List listeSection = new ArrayList<>();
	private List listeEtudiant = new ArrayList<>();
	
	// Contrôle de coposant
	private CommandButton btnValider = new CommandButton();
	private CommandButton btnAnuler = new CommandButton();
	
	
	
	
	public void rechercher() throws FileNotFoundException {
		try {
			etudiants = reqEtudiant.recupererEtudiantByMlle(matriculeRecherche).get(0);
		} catch (IndexOutOfBoundsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Recherche infructueuse. Veuillez vérifier le matricule", null));
		}
		
		if (etudiants.getMle()!= null) {
			inscriptions = requeteInscription.recupererListeSection(etudiants.getNumetudiant()).get(0);
			chargerPhoto();
			chargerSection();
			
		}
	}
	
	
	public void selectionner() throws FileNotFoundException {
		etudiants = selectedEtudiant;
		chargerPhoto();
		chargerSection();
		
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
    
    public void chargerSection() {
    	listInscription.clear();
		 listInscription= requeteInscription.recupererListeSection(etudiants.getNumetudiant());	
    }
    
    
    public void chargerPhoto() throws FileNotFoundException {
    	//System.out.println("Chemin:======="+getEtudiants().getPhotoEtudiant());
    	cheminFinal = getEtudiants().getPhotoEtudiant();
    	InputStream is = new FileInputStream(cheminFinal);
			//is.close();  
			content	= new DefaultStreamedContent(is);
			
    	
    }
    
    public void annuler() throws FileNotFoundException {
		etudiants.setNomEtudiant(null);
		etudiants.setMle(null);
		etudiants.setPrenomEtudiant(null);
		etudiants.setDatenais(null);
		etudiants.setLieunais(null);
	//	etudiants.setSections(null);
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
		inscriptions.setAnneesScolaire(null);
		matriculeRecherche = "";
		listInscription.clear();
		viderPhoto();
		
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
		listeEtudiant.clear();
		listeEtudiant = reqEtudiant.recupererEtudiant();
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

	public Etudiants getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(Etudiants etudiants) {
		this.etudiants = etudiants;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public List getListMatrimonale() {
		listMatrimonale = service.getObjects("Matrimoniales");
		return listMatrimonale;
	}

	public void setListMatrimonale(List listMatrimonale) {
		this.listMatrimonale = listMatrimonale;
	}

	public Matrimoniales getChoosedMatrimoniales() {
		return choosedMatrimoniales;
	}

	public void setChoosedMatrimoniales(Matrimoniales choosedMatrimoniales) {
		this.choosedMatrimoniales = choosedMatrimoniales;
	}

	public Santes getChooseedsantes() {
		return chooseedsantes;
	}

	public void setChooseedsantes(Santes chooseedsantes) {
		this.chooseedsantes = chooseedsantes;
	}

	public List getListeSante() {
		listeSante = service.getObjects("Santes");
		return listeSante;
	}

	public void setListeSante(List listeSante) {
		this.listeSante = listeSante;
	}

	public Niveaux getChoosedNiveaux() {
		return choosedNiveaux;
	}

	public void setChoosedNiveaux(Niveaux choosedNiveaux) {
		this.choosedNiveaux = choosedNiveaux;
	}

	public List getListeNiveaux() {
		listeNiveaux = service.getObjects("Niveaux");
		return listeNiveaux;
	}

	public void setListeNiveaux(List listeNiveaux) {
		this.listeNiveaux = listeNiveaux;
	}

	public Diplomes getChoosedDiplomes() {
		return choosedDiplomes;
	}

	public void setChoosedDiplomes(Diplomes choosedDiplomes) {
		this.choosedDiplomes = choosedDiplomes;
	}

	public List getListDiplome() {
		listDiplome = service.getObjects("Diplomes");
		return listDiplome;
	}

	public void setListDiplome(List listDiplome) {
		this.listDiplome = listDiplome;
	}



	public List getListeSection() {
		return listeSection;
	}

	public void setListeSection(List listeSection) {
		this.listeSection = listeSection;
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

	public CommandButton getBtnAnuler() {
		return btnAnuler;
	}


	public void setBtnAnuler(CommandButton btnAnuler) {
		this.btnAnuler = btnAnuler;
	}


	public List getListInscription() {
		  //listInscription.clear();
		  //listInscription= requeteInscription.recupererListeSection(etudiants.getNumetudiant());	 
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


	public Etudiants getSelectedEtudiant() {
		return selectedEtudiant;
	}


	public void setSelectedEtudiant(Etudiants selectedEtudiant) {
		this.selectedEtudiant = selectedEtudiant;
	}

	
}
