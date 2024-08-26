package com.ARSTM.managedBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Enseignant;
import com.ARSTM.model.EnseignatStatut;
import com.ARSTM.model.EnseignatStatutId;
import com.ARSTM.model.Sexe;
import com.ARSTM.model.Specialite;
import com.ARSTM.model.Statut;
import com.ARSTM.model.UserAuthentication;
import com.ARSTM.model.UserAuthorization;
import com.ARSTM.requetes.RequeteEnseignant;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class EnseignantBean {
	@Autowired
	Iservice service;
	@Autowired
	RequeteEnseignant   requeteEnseignant;
	
	private Enseignant enseignant = new Enseignant();
	private Enseignant selectedEnseignant = new Enseignant();
	private List<Object> listEnseignant = new ArrayList<>();
	private Statut choosedStatut = new Statut();
	private Sexe chooseedSexe = new Sexe();
	private Specialite choosedSpecialite = new Specialite();
	private List<Object> listStatut = new ArrayList<>();
	private List<Object> listeSexe = new ArrayList<>();
	private List<Object> listSpecialite = new ArrayList<>();
	private UserAuthentication userAuthentication = new UserAuthentication();
	private UserAuthorization userAuthorization = new UserAuthorization();
	private Long maxMatricule = (long) 0;
	
	private String destination="C:\\GIVAC\\PHOTO";

	// Contrôle de coposant
	private CommandButton btnValider = new CommandButton();
	private InputText inputVhOblig = new InputText();
	private OutputLabel outputVhOblig = new OutputLabel();

	public void enregistrer(){
		//enregistrer dans la table UserAuthentication
		userAuthentication.setUsername(enseignant.getUsername());
		userAuthentication.setPassword(enseignant.getPassword());
		userAuthentication.setEnabled(true);
		userAuthentication.setNom(enseignant.getNom());
		userAuthentication.setPrenoms(enseignant.getPrenoms());
		userAuthentication.setSexe(chooseedSexe);
		userAuthentication.setPhone1(enseignant.getPhone1());
		userAuthentication.setPhone2(enseignant.getPhone2());
		userAuthentication.setPhoto(enseignant.getPhoto());
		userAuthentication.setLieuNais(enseignant.getLieuNais());
		userAuthentication.setDateNais(enseignant.getDateNais());
		userAuthentication.setEmail(enseignant.getEmail());
		
		//enregistrer das la table Enseignant
		enseignant.setMatriculeEns(maxMatricule);
		enseignant.setCodeSexe(chooseedSexe.getCodeSexe());
		enseignant.setSpecialite(choosedSpecialite);
		enseignant.setEnabled(true);
		enseignant.setEtatEnseignant(true);
		getService().addObject(userAuthentication);
		enseignant.setUserAuthentication(userAuthentication);
		getService().addObject(enseignant);

		//enregistrer dans la table Enseigant_Statut		
		EnseignatStatutId enseignatStatutId = new EnseignatStatutId(enseignant.getUserId(), choosedStatut.getCodeStatut());
		EnseignatStatut enseignatStatut = new EnseignatStatut(enseignatStatutId, enseignant, choosedStatut);
		getService().addObject(enseignatStatut);

		//Enregistrer l'autorisation
		userAuthorization.setUserAuthentication(userAuthentication);
		userAuthorization.setRole("ROLE_ENSEIGNANT");
		getService().addObject(userAuthorization);

		actualiserList();
		
		vider(enseignant);
		maxMatricule = (long) 0;
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
	}

	public void annuler() {
		btnValider.setDisabled(false);
		vider(enseignant);
		actualiserList();
	}

	public void vider(Enseignant objEnseignant) {
		objEnseignant.setDateNais(null);
		objEnseignant.setLieuNais(null);
		objEnseignant.setEmail(null);
		objEnseignant.setNom(null);
		objEnseignant.setPrenoms(null);
		objEnseignant.setPhone1(null);
		objEnseignant.setPhone2(null);
		objEnseignant.setUsername(null);
		objEnseignant.setPassword(null);
		objEnseignant.setVhObligatoireSemaine(null);
	}
	

	public void actualiserList(){
		listEnseignant.clear();
		listEnseignant = getService().getObjects("Enseignant");
	}

	
	
public void upload(FileUploadEvent event) { 
		       
		try {
		copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
		
		//Mis à jour dans la table enseignant
		enseignant.setPhoto(destination);
		} catch (IOException e) {
		e.printStackTrace();
		}
		} 
	
	
		public void copyFile(String fileName, InputStream in) {
		try {
		// write the inputStream to a FileOutputStream
		OutputStream out = new FileOutputStream(new File(destination + fileName));
		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = in.read(bytes)) != -1) {
		out.write(bytes, 0, read);
		}
		in.close();
		out.flush();
		out.close();
		System.out.println("New file created!");//Clean after
		} catch (IOException e) {
		System.out.println(e.getMessage());//Clean after

		}
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

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	public Enseignant getSelectedEnseignant() {
		return selectedEnseignant;
	}

	public void setSelectedEnseignant(Enseignant selectedEnseignant) {
		this.selectedEnseignant = selectedEnseignant;
	}

	public List<Object> getListEnseignant() {
		if (listEnseignant.isEmpty()) {
			listEnseignant = getService().getObjects("Enseignant");
		}
		return listEnseignant;
	}

	public void setListEnseignant(List<Object> listEnseignant) {
		this.listEnseignant = listEnseignant;
	}

	public Statut getChoosedStatut() {
		return choosedStatut;
	}

	public void setChoosedStatut(Statut choosedStatut) {
		this.choosedStatut = choosedStatut;
	}

	public List<Object> getListStatut() {
		if (listStatut.isEmpty()) {
			listStatut = getService().getObjects("Statut");
		}
		return listStatut;
	}

	public void setListStatut(List<Object> listStatut) {
		this.listStatut = listStatut;
	}

	public List<Object> getListeSexe() {
		if (listeSexe.isEmpty()) {
			listeSexe = getService().getObjects("Sexe");
		}
		return listeSexe;
	}

	public void setListeSexe(List<Object> listeSexe) {
		this.listeSexe = listeSexe;
	}

	public Sexe getChooseedSexe() {

		return chooseedSexe;
	}

	public void setChooseedSexe(Sexe chooseedSexe) {
		this.chooseedSexe = chooseedSexe;
	}

	public Specialite getChoosedSpecialite() {
		return choosedSpecialite;
	}

	public void setChoosedSpecialite(Specialite choosedSpecialite) {
		this.choosedSpecialite = choosedSpecialite;
	}

	public List<Object> getListSpecialite() {
		if (listSpecialite.isEmpty()) {
			listSpecialite = getService().getObjects("Specialite");
		}
		return listSpecialite;
	}

	public void setListSpecialite(List<Object> listSpecialite) {
		this.listSpecialite = listSpecialite;
	}

	public InputText getInputVhOblig() {
		return inputVhOblig;
	}

	public void setInputVhOblig(InputText inputVhOblig) {
		this.inputVhOblig = inputVhOblig;
	}

	public OutputLabel getOutputVhOblig() {
		return outputVhOblig;
	}

	public void setOutputVhOblig(OutputLabel outputVhOblig) {
		this.outputVhOblig = outputVhOblig;
	}

	public UserAuthorization getUserAuthorization() {
		return userAuthorization;
	}

	public void setUserAuthorization(UserAuthorization userAuthorization) {
		this.userAuthorization = userAuthorization;
	}

	public Long getMaxMatricule() {
		if (maxMatricule == 0) {
			try {
				maxMatricule = requeteEnseignant.recupMaxMatricule().get(0).getMatriculeEns();
				enseignant.setMatriculeEns(maxMatricule++);
			} catch (Exception e) {
				maxMatricule = (long) 1;
			}
		}
		return maxMatricule;
	}

	public void setMaxMatricule(Long maxMatricule) {
		this.maxMatricule = maxMatricule;
	}
}