package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.Chapitre;
import com.ARSTM.model.Enseignant;
import com.ARSTM.model.Enseigner;
import com.ARSTM.model.Matiere;
import com.ARSTM.model.Progression;
//import com.ARSTM.model.Rattacher;
import com.ARSTM.model.Seance;
import com.ARSTM.model.Section;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.requetes.ReqChapitre;
//import com.ARSTM.requetes.ReqEnseigner;
import com.ARSTM.requetes.ReqProgression;
//import com.ARSTM.requetes.ReqRattacher;
import com.ARSTM.requetes.ReqSeance;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class EditionSeanceBean {
	@Autowired
	Iservice service;
	
	//ReqRattacher reqRattacher;

	//ReqEnseigner reqEnseigner;
	@Autowired
	ReqAnneeScolaire reqAnneeScolaire;
	@Autowired
	ReqChapitre reqChapitre;
	@Autowired
	ReqProgression reqProgression;
	@Autowired ReqSeance reqSeance;
	
	
	private AnneesScolaire anneesScolaire = new AnneesScolaire();
	private List<Enseigner> listeEnseigner = new ArrayList<>();
	private List<Enseignant> listeEnseignant = new ArrayList<>();
	private List<Section> listeSection = new ArrayList<>();
	private List listEseignerEnseignant = new ArrayList<>();
	private int maxOrdre;
	
	
	
	private Chapitre chapitre = new Chapitre();
	private int numeroSeance = 0;
	private List<Seance> listSeanceEdit = new ArrayList<>();
	private List<Seance> listeRecapSeance = new ArrayList<>();
	private Progression progression;
	private Matiere choosedMatiere= new Matiere();
	private Section choosedSection = new Section();
	//private Rattacher choosedRattacher = new Rattacher();
	private int VHchapAttribue;
	private int VHchapRestant;
	private int VHEdite;
	private int VHRestant;
	private Enseignant chooseedEnseignant = new Enseignant();
	private List listEnseignant = new ArrayList<>();
	
	private Enseigner choosedEnseigner = new Enseigner();
	 
	// Contrôle de coposant
	private DataTable dataTable = new DataTable();
	private CommandButton btnValider = new CommandButton();
	private CommandButton btnSuprimer = new CommandButton();
	
	
			public List<Seance> chargerRecapSeance(){
				listeRecapSeance.clear();
				VHEdite = 0;
				Progression  progres = reqProgression.recupProgressionByEnseigner(choosedEnseigner.getCodeEnseigner()).get(0);
					//Recupération des chapitres de la progresssion
				for (Chapitre varChap : progres.getChapitres()) {
						//Recupération des seances de chaque chapitre
					for (Seance varSeance : varChap.getSeances()) {
						listeRecapSeance.add(varSeance);
						VHEdite += varSeance.getVolumeHoraireSeance();
					}
				}
				
				VHRestant = choosedEnseigner.getVhEffectif() - VHEdite;
				
				//Trier la liste par ordre des numeros de seance
				Collections.sort(listeRecapSeance, new Comparator<Seance>() {
				    @Override
				    public int compare(Seance s1, Seance s2) {
				        return s1.getOrdreSeance().compareTo(s2.getOrdreSeance());
				    }
				});
								
				return listeRecapSeance;
				
			}
			
			
			public List<Enseigner> chargerListeMatEnseigner(){
				anneesScolaire = reqAnneeScolaire.recupererDerniereAnneeScolaire().get(0);
				listeEnseigner.clear();
				listEseignerEnseignant.clear();
				
				//listeEnseigner = reqEnseigner.recupEnsegnerBySectAnneEtatplan(anneesScolaire.getCodeAnnees(), choosedSection.getCodeSection(), 0);
				chargerListEnseignant();
				return listeEnseigner;
			}
			
			public void chargerListEnseignant(){
				listeEnseignant.clear();
				for(Enseigner varEnseigner: listeEnseigner){
					//Vérifier si l'enseigant n'est pas dans la liste
					if(!(listEnseignant.contains(varEnseigner.getEnseignant()))){
						listeEnseignant.add(varEnseigner.getEnseignant());
					}
				}
				
			}
			
			public void chargerListEnseigner(){
				listEseignerEnseignant.clear();
				//listEseignerEnseignant = reqEnseigner.recupEnseignerByEnseignant(choosedSection.getCodeSection(), anneesScolaire.getCodeAnnees(), chooseedEnseignant.getUserId());
			}
			
		
	public void ajouterSeance(){
		Seance seanceAjoute= new Seance();
		seanceAjoute.setNumSeance(listSeanceEdit.size()+1);
		listSeanceEdit.add(seanceAjoute);
	}
	
	public void supprimerSeance(){
		int indice = listSeanceEdit.size()-1;
		Seance seanceSuprimer = listSeanceEdit.get(indice);
		listSeanceEdit.remove(seanceSuprimer);
		decompteVolumeHoraire();
	}
	
	public void annuler(){
		listSeanceEdit.clear();
		viderChapitre(chapitre);
		generCodeChapitre();
		VHchapAttribue=0;
		VHchapRestant=0;
	}
	
	
	public void decompteVolumeHoraire(){
		VHchapAttribue=0;
		VHchapRestant = chapitre.getVolumeHoraireChap();
		//Calcul du VH attribué
		for (Seance varseance : listSeanceEdit) {
			try {
				VHchapAttribue += varseance.getVolumeHoraireSeance();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		//Calcul du vh restant
		VHchapRestant = chapitre.getVolumeHoraireChap()-VHchapAttribue;
	}
	
	
	
	
	
	public void onRowEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Ligne de séance éditée", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
		decompteVolumeHoraire();
    	if (VHchapAttribue > chapitre.getVolumeHoraireChap()) {
    		FacesContext.getCurrentInstance().addMessage(null,
    				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vérifiez le volume horaire entré dans la seance", null));
		}
        
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Fermerture de l'Edition", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
		public void enregistrerSeance(){
			if (!(VHchapAttribue == chapitre.getVolumeHoraireChap())) {//
				FacesContext.getCurrentInstance().addMessage(null,
	    				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vérifiez le volume horaire total des séances", null));
			}else { //Exécuter l'enregistrement
				
				//On verifie que la progression est deja enregistrer;
				switch (verifierExistanceProgression().toString()) {
				
				case "false"://inexsitence de progression
					//On enregistre en commençant par la progression
					progression = new Progression();
					progression.setEnseigner(choosedEnseigner);
					
					//Update progression par la clé de enseigner
					getService().addObject(progression);
					
					//Update Enseigner par la clé de la progression
					choosedEnseigner.setProgression(progression);
					getService().updateObject(choosedEnseigner);
					
					//Enregistrer capitre & séance
					enregistrerChapitre();
					//Update la Table Enseigner par l'etat des séances
					choosedEnseigner.setEtatSeance(true);
					getService().updateObject(choosedEnseigner);
					
					//Afficher les séances du module
					chargerRecapSeance();
					
					rafraichirPage();
					
					//Générer un code de chapitre après enregistrement des séances
					generCodeChapitre();
					break;
				
				case "true"://existence de progression dc juste save seance et chapitre
					enregistrerChapitre();
					//Afficher les séances du module
					chargerRecapSeance();
					
					rafraichirPage();
					
					//Générer un code de chapitre après enregistrement des séances
					generCodeChapitre();

					
					break;
				}
				
				return ;
				
			}
			
			
		}
		
		public Boolean verifierExistanceProgression(){
		    if (!(choosedEnseigner.getProgression() == null)) {
				return true;
			}else {
				return false;
			}			
		}
		
		
		public void enregistrerChapitre(){
			//Recuperer le numero d'ordre maxi
			maxOrdre = 0;
				try {
					maxOrdre =  reqSeance.recupMaxOrdre().get(0).getOrdreSeance();
					//maxOrdre++;
				} catch (Exception e) {
					//maxOrdre =  1;
				}
			
			//Enregistrement du chapitre
			chapitre.setProgression(choosedEnseigner.getProgression());
			getService().addObject(chapitre);
			//Enregistrement des seances de cours prévues
			for (Seance varSeance : listSeanceEdit) {
				varSeance.setChapitre(chapitre);
				//Setter le numéro d'ordre
				maxOrdre++;
				varSeance.setOrdreSeance(maxOrdre);
				
				//Enregistrement de la séance
				getService().addObject(varSeance);
			}
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement de chapitre et séances effcetué!", null));
			
		}
		
		public void rafraichirPage(){
			//Vider le liste
			listSeanceEdit.clear();
			viderChapitre(chapitre);
			VHchapAttribue=0;
			VHchapRestant=0;
		}
		
		
		
	public void viderChapitre(Chapitre objectChap){
		objectChap.setLibelleChap(null);
		objectChap.setNumeroChapitre(null);
		objectChap.setProgression(null);
		objectChap.setVolumeHoraireChap(null);
	}
	
	
	public int generCodeChapitre(){
		switch (verifierExistanceProgression().toString()) {
		
		case "false"://inexsitence de progression
			//On met automatique le code à 1
			chapitre.setNumeroChapitre(1);
			break;
		
		case "true"://existence de progression
			//Chapitre chapitre = reqChapitre.recupMaxNumChapitre(choosedEnseigner.getProgression().getCodeProgression()).get(0);
			chapitre.setNumeroChapitre(reqChapitre.recupMaxNumChapitre(choosedEnseigner.getProgression().getCodeProgression()).get(0).getNumeroChapitre());
			//Incrémentation du numero de chapitre
			chapitre.setNumeroChapitre(chapitre.getNumeroChapitre()+1);
			break;

		}
		return numeroSeance;
		
	}
	
	public void choisirEnseigner(){
		rafraichirPage();
		generCodeChapitre();
		//getChoosedRattacher();
		chargerRecapSeance();
		
		
	}
	
	
	//**************************ACCESSEURS*************************
	
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


	public Chapitre getChapitre() {
		return chapitre;
	}


	public void setChapitre(Chapitre chapitre) {
		this.chapitre = chapitre;
	}


	public List<Seance> getListSeanceEdit() {
		return listSeanceEdit;
	}


	public void setListSeanceEdit(List<Seance> listSeanceEdit) {
		this.listSeanceEdit = listSeanceEdit;
	}

	public DataTable getDataTable() {
		return dataTable;
	}


	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public Matiere getChoosedMatiere() {
		
		return choosedMatiere;
	}

	public void setChoosedMatiere(Matiere choosedMatiere) {
		this.choosedMatiere = choosedMatiere;
	}

	public Section getChoosedSection() {
		return choosedSection;
	}

	public void setChoosedSection(Section choosedSection) {
		this.choosedSection = choosedSection;
	}

	
	/*public Rattacher getChoosedRattacher() {
		//Recuperer l'associationtion matiere-section par la requette
		int codeSection = choosedEnseigner.getSection().getCodeSection();
		int codeMatiere = choosedEnseigner.getMatiere().getCodeMatiere();
		choosedRattacher = reqRattacher.recupRatacher(codeSection, codeMatiere).get(0);
		return choosedRattacher;
	}

	public void setChoosedRattacher(Rattacher choosedRattacher) {
		this.choosedRattacher = choosedRattacher;
	}

	public ReqRattacher getReqRattacher() {
		return reqRattacher;
	}

	public void setReqRattacher(ReqRattacher reqRattacher) {
		this.reqRattacher = reqRattacher;
	}
*/
	public int getVHchapAttribue() {
		return VHchapAttribue;
	}

	public void setVHchapAttribue(int vHchapAttribue) {
		VHchapAttribue = vHchapAttribue;
	}

	public int getVHchapRestant() {
		return VHchapRestant;
	}

	public void setVHchapRestant(int vHchapRestant) {
		VHchapRestant = vHchapRestant;
	}

	public Enseignant getChooseedEnseignant() {
		return chooseedEnseignant;
	}

	public void setChooseedEnseignant(Enseignant chooseedEnseignant) {
		this.chooseedEnseignant = chooseedEnseignant;
	}

	public List getListEnseignant() {
		if(listEnseignant.isEmpty()){
			listEnseignant = getService().getObjects("Enseignant");
	}
		return listEnseignant;
	}

	public void setListEnseignant(List listEnseignant) {
		this.listEnseignant = listEnseignant;
	}

	public Enseigner getChoosedEnseigner() {
		return choosedEnseigner;
	}

	public void setChoosedEnseigner(Enseigner choosedEnseigner) {
		this.choosedEnseigner = choosedEnseigner;
	}

	public AnneesScolaire getAnneesScolaire() {
		return anneesScolaire;
	}

	public void setAnneesScolaire(AnneesScolaire anneesScolaire) {
		this.anneesScolaire = anneesScolaire;
	}

	/*public ReqEnseigner getReqEnseigner() {
		return reqEnseigner;
	}
	
	public void setReqEnseigner(ReqEnseigner reqEnseigner) {
		this.reqEnseigner = reqEnseigner;
	}*/

	public List<Enseignant> getListeEnseignant() {
		return listeEnseignant;
	}

	public void setListeEnseignant(List<Enseignant> listeEnseignant) {
		this.listeEnseignant = listeEnseignant;
	}

	public ReqAnneeScolaire getReqAnneeScolaire() {
		return reqAnneeScolaire;
	}

	public void setReqAnneeScolaire(ReqAnneeScolaire reqAnneeScolaire) {
		this.reqAnneeScolaire = reqAnneeScolaire;
	}

	@SuppressWarnings("unchecked")
	public List<Section> getListeSection() {
		if (listeSection.isEmpty()) {
			listeSection = getService().getObjects("Section");
		}
		return listeSection;
	}

	public void setListeSection(List<Section> listeSection) {
		this.listeSection = listeSection;
	}

	public List getListEseignerEnseignant() {
		return listEseignerEnseignant;
	}

	public void setListEseignerEnseignant(List listEseignerEnseignant) {
		this.listEseignerEnseignant = listEseignerEnseignant;
	}

	public List<Seance> getListeRecapSeance() {
		return listeRecapSeance;
	}

	public void setListeRecapSeance(List<Seance> listeRecapSeance) {
		this.listeRecapSeance = listeRecapSeance;
	}


	public int getNumeroSeance() {
		
		return numeroSeance;
	}


	public void setNumeroSeance(int numeroSeance) {
		this.numeroSeance = numeroSeance;
	}


	public ReqChapitre getReqChapitre() {
		return reqChapitre;
	}


	public void setReqChapitre(ReqChapitre reqChapitre) {
		this.reqChapitre = reqChapitre;
	}


	public ReqProgression getReqProgression() {
		return reqProgression;
	}


	public void setReqProgression(ReqProgression reqProgression) {
		this.reqProgression = reqProgression;
	}


	public int getVHEdite() {
		return VHEdite;
	}


	public void setVHEdite(int vHEdite) {
		VHEdite = vHEdite;
	}


	public int getVHRestant() {
		return VHRestant;
	}


	public void setVHRestant(int vHRestant) {
		VHRestant = vHRestant;
	}


	public int getMaxOrdre() {
		return maxOrdre;
	}


	public void setMaxOrdre(int maxOrdre) {
		this.maxOrdre = maxOrdre;
	}
	
	
	public ReqSeance getReqSeance() {
		return reqSeance;
	}


	public void setReqSeance(ReqSeance reqSeance) {
		this.reqSeance = reqSeance;
	}



}
