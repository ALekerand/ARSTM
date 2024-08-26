package com.ARSTM.managedBean;

import java.util.ArrayList;
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

import com.ARSTM.model.Chapitre;
import com.ARSTM.model.Enseignant;
import com.ARSTM.model.Enseigner;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Matiere;
import com.ARSTM.model.Progression;
import com.ARSTM.model.Seance;
import com.ARSTM.model.Section;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class ChapitreSeanceBean {
	@Autowired
	Iservice service;
	@Autowired
	ProgressionBean progressionBean;
	@Autowired
	ManagedInitialisation initialisation;
	
	private Chapitre chapitre = new Chapitre();
	private List<Seance> listSeanceEdit = new ArrayList<>();
	private Progression progression;
	private Matiere choosedMatiere= new Matiere();
	private Section choosedSection = new Section();
	private Filieres choosedFilieres = new Filieres();
	private int VHchapAttribue;
	private int VHchapRestant;
	private Date dateModule;
	
	private Enseignant chooseedEnseignant = new Enseignant();
	private List listEnseignant = new ArrayList<>();
	private List listEseigner = new ArrayList<>();
	//private List listeMatiere = new ArrayList<>();
	//private Matiere chooseedMatiere = new Matiere();
	private Enseigner choosedEnseigner = new Enseigner();
	 
	// Contrôle de coposant
	private DataTable dataTable = new DataTable();
			private CommandButton btnValider = new CommandButton();
			private CommandButton btnSuprimer = new CommandButton();
	
	
	
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
		System.out.println("--------- decompte lancé");//Clean after
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
				progression.setEnseigner(progressionBean.getSelectedEnseigner());
				System.out.println("-------Code de enseigner: "+progressionBean.getSelectedEnseigner().getCodeEnseigner());//clean after
				
				//Update progression par la clé de enseigner
				getService().addObject(progression);
				
				//Update Enseigner par la clé de la progression
				progressionBean.getSelectedEnseigner().setProgression(progression);
				//Mise à jour de la date de début de module
				progressionBean.getSelectedEnseigner().setDateDebutcours(dateModule);
				getService().updateObject(progressionBean.getSelectedEnseigner());
				
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
		    if (!(progressionBean.getSelectedEnseigner().getProgression() == null)) {
				return true;
			}else {
				return false;
			}			
		}
		
		
		public void enregistrerChapitre(){
			//Enregistrement du chapitre
			chapitre.setProgression(progressionBean.getSelectedEnseigner().getProgression());
			System.out.println("-------Code de enseigner: "+progressionBean.getSelectedEnseigner().getCodeEnseigner());//clean after
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
	
	public void chargerListEnseigner(){
		listEseigner.clear();

		for (Enseigner enseig : chooseedEnseignant.getEnseigners()) {
			if(enseig.getAnneesScolaire().getCodeAnnees() == initialisation.getAnneeScolaireEncours().getCodeAnnees());
			listEseigner.add(enseig);
		}
		
	}
	
	
	
	
	
	
	
	/**************************ACCESSEURS*************************/
	
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

	public ProgressionBean getProgressionBean() {
		return progressionBean;
	}

	public void setProgressionBean(ProgressionBean progressionBean) {
		this.progressionBean = progressionBean;
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

	public Filieres getChoosedFilieres() {
		return choosedFilieres;
	}

	public void setChoosedFilieres(Filieres choosedFilieres) {
		this.choosedFilieres = choosedFilieres;
	}

	/*public Rattacher getChoosedRattacher() {
		//Recuperer l'associationtion matiere-section par la requette
		int codeSection = progressionBean.getSelectedEnseigner().getSection().getCodeSection();
		int codeMatiere = progressionBean.getSelectedEnseigner().getMatiere().getCodeMatiere();
		choosedRattacher = reqRattacher.recupRatacher(codeSection, codeMatiere).get(0);
		return choosedRattacher;
	}*/

	

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

	public List getListEseigner() {
		
		return listEseigner;
	}

	public void setListEseigner(List listEseigner) {
		this.listEseigner = listEseigner;
	}

	public ManagedInitialisation getInitialisation() {
		return initialisation;
	}

	public void setInitialisation(ManagedInitialisation initialisation) {
		this.initialisation = initialisation;
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

}
