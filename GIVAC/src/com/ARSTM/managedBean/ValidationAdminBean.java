/*package com.ARSTM.managedBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.AnneesScolaire;
import com.ARSTM.model.Chapitre;
import com.ARSTM.model.Enseignant;
import com.ARSTM.model.Enseigner;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Matiere;
import com.ARSTM.model.Progression;
import com.ARSTM.model.Rattacher;
import com.ARSTM.model.Seance;
import com.ARSTM.model.Section;
import com.ARSTM.requetes.ReqAnneeScolaire;
import com.ARSTM.requetes.ReqEnseigner;
import com.ARSTM.requetes.ReqProgression;
import com.ARSTM.requetes.ReqRattacher;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class ValidationAdminBean {
	@Autowired
	Iservice service;
	@Autowired
	ReqAnneeScolaire reqAnneeScolaire;
	@Autowired
	ReqProgression reqProgression;
	
	
	
	
	
	@Autowired
	ReqRattacher reqRattacher;
	@Autowired 
	ReqEnseigner reqEnseigner;
	
	
	
	private Section choosedSection = new Section();
	private List<Section> listeSection = new ArrayList<>();
	private Enseignant chooseedEnseignant = new Enseignant();
	private List<Enseignant> listeEnseignant = new ArrayList<>();
	private ArrayList<Seance> listSeance = new ArrayList<>();
	private ArrayList<Enseigner> listEseignerEnseignant = new ArrayList<>();
	private List<Enseigner> listeEnseigner = new ArrayList<>();
	private Seance seanceValide = new Seance();
	





	
	
	
	
	
	
	private AnneesScolaire anneesScolaire = new AnneesScolaire();
	
	
	
	private Chapitre chapitre = new Chapitre();
	private List<Seance> listSeanceEdit = new ArrayList<>();
	private Progression progression;
	private Matiere choosedMatiere= new Matiere();
	private Rattacher choosedRattacher = new Rattacher();
	private int VHchapAttribue;
	private int VHchapRestant;
	private Date dateModule;
	
	private List listEnseignant = new ArrayList<>();
	
	private Enseigner choosedEnseigner = new Enseigner();
	 
	// Contrôle de coposant
	private DataTable dataTable = new DataTable();
	private CommandButton btnValider = new CommandButton();
	private CommandButton btnSuprimer = new CommandButton();
	
			public List<Enseigner> chargerListeMatEnseigner(){
				//Reinitialiser les différentes liste
				listeEnseignant.clear();
				listEseignerEnseignant.clear();
				listSeance.clear();
				
				anneesScolaire = reqAnneeScolaire.recupererDerniereAnneeScolaire().get(0);
				listeEnseigner.clear();
				listeEnseigner = reqEnseigner.recupEnsegnerBySectAnneEtatplan(anneesScolaire.getCodeAnnees(), choosedSection.getCodeSection(), 1);
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
				//Reinitialiser les différentes liste
				listEseignerEnseignant.clear();
				listSeance.clear();
				
				listEseignerEnseignant.clear();
				for (Enseigner varEnseigner : listeEnseigner) {
					if (varEnseigner.getEnseignant().getMatriculeEns().equals(chooseedEnseignant.getMatriculeEns())) {
						listEseignerEnseignant.add(varEnseigner);
					}
				}
				
			}
			
			
			
			public void chargerListeSeances(){
				Progression progression = new Progression();
				progression = reqProgression.recupProgressionByEnseigner(choosedEnseigner.getCodeEnseigner()).get(0);
				listSeance.clear();
				for (Chapitre chap : progression.getChapitres()) {
					//Recuperer les séances de chapitre
					for (Seance seance : chap.getSeances()) {
						if (seance.getValidationSeance()== null) {
							listSeance.add(seance);
						}
						
					}
				}
				
				//Trier la liste par ordre des dates prévues
				Collections.sort(listSeance, new Comparator<Seance>() {
				    @Override
				    public int compare(Seance s1, Seance s2) {
				        return s1.getDatePrevue().compareTo(s2.getDatePrevue());
				    }
				});
			}
			
			public void validerSeance(){
				//MAJ dans la  BD
				if (listSeance.get(0)==seanceValide) {
					seanceValide.setValidationSeance(true);
				seanceValide.setDateEffective(Calendar.getInstance().getTime());
				getService().updateObject(seanceValide);
				//Actualiser la liste de séance
				listSeance.remove(seanceValide);
				FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Séance de cours validée!", null));
				}else {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "La validation des séances doit se faire dans l'ordre!", null));
				}
				
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
	
	
	public void editerSeance(){
		dataTable.setRendered(true);
	}
	
	
	public void decompteVolumeHoraire(){
		VHchapAttribue=0;
		VHchapRestant = chapitre.getVolumeHoraireChap();
		//Calcul du VH attribué
		for (Seance varseance : listSeanceEdit) {
			VHchapAttribue += varseance.getVolumeHoraireSeance();
		}
		//Calcul du vh restant
		VHchapRestant = chapitre.getVolumeHoraireChap()-VHchapAttribue;
	}
	
	
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cellule éditée", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
	
		public void enregistrerSeance(){
			
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
				//Mise à jour de la date de début de module
				choosedEnseigner.setDateDebutcours(dateModule);
				getService().updateObject(choosedEnseigner);
				
				//Enregistrer capitre & séance
				enregistrerChapitre();
				break;
			
			case "true"://existence de progression dc juste save seance et chapitre
				enregistrerChapitre();
				break;

			default:
				break;
			}
			
			return ;
			
		}
		
		public Boolean verifierExistanceProgression(){
		    if (!(choosedEnseigner.getProgression() == null)) {
				return true;
			}else {
				return false;
			}			
		}
		
		
		public void enregistrerChapitre(){
			//Enregistrement du chapitre
			chapitre.setProgression(choosedEnseigner.getProgression());
			getService().addObject(chapitre);
			//Enregistrement des seances de cours prévues
			for (Seance varSeance : listSeanceEdit) {
				varSeance.setChapitre(chapitre);
				getService().addObject(varSeance);
			}
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement de chapitre et séances effcetué!", null));
			
			//Vider le liste
			listSeanceEdit.clear();
			viderChapitre(chapitre);
			VHchapAttribue=0;
			VHchapRestant=0;
			listSeanceEdit.clear();
		}
		
	public void viderChapitre(Chapitre objectChap){
		objectChap.setLibelleChap(null);
		objectChap.setNumeroChapitre(null);
		objectChap.setProgression(null);
		objectChap.setVolumeHoraireChap(null);
	}
	
	
	
	
	
	
	
	
	
	*//**************************ACCESSEURS*************************//*
	
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


	public Rattacher getChoosedRattacher() {
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

	public Date getDateModule() {
		return dateModule;
	}

	public void setDateModule(Date dateModule) {
		this.dateModule = dateModule;
	}


	public AnneesScolaire getAnneesScolaire() {
		return anneesScolaire;
	}

	public void setAnneesScolaire(AnneesScolaire anneesScolaire) {
		this.anneesScolaire = anneesScolaire;
	}

	public ReqEnseigner getReqEnseigner() {
		return reqEnseigner;
	}
	
	public void setReqEnseigner(ReqEnseigner reqEnseigner) {
		this.reqEnseigner = reqEnseigner;
	}

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


	public ArrayList<Seance> getListSeance() {
		return listSeance;
	}


	public void setListSeance(ArrayList<Seance> listSeance) {
		this.listSeance = listSeance;
	}


	public ArrayList<Enseigner> getListEseignerEnseignant() {
		return listEseignerEnseignant;
	}


	public void setListEseignerEnseignant(ArrayList<Enseigner> listEseignerEnseignant) {
		this.listEseignerEnseignant = listEseignerEnseignant;
	}


	public ReqProgression getReqProgression() {
		return reqProgression;
	}


	public void setReqProgression(ReqProgression reqProgression) {
		this.reqProgression = reqProgression;
	}


	public Seance getSeanceValide() {
		return seanceValide;
	}


	public void setSeanceValide(Seance seanceValide) {
		this.seanceValide = seanceValide;
	}

}
*/