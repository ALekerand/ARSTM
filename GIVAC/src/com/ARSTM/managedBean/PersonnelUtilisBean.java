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
import com.ARSTM.model.Fonction;
import com.ARSTM.model.Personnel;
import com.ARSTM.model.Sexe;
import com.ARSTM.model.Specialite;
import com.ARSTM.model.Statut;
import com.ARSTM.model.UserAuthentication;
import com.ARSTM.model.UserAuthorization;
import com.ARSTM.requetes.RequeteEnseignant;
import com.ARSTM.service.Iservice;

@Component
@Scope("session")
public class PersonnelUtilisBean {
	@Autowired
	Iservice service;
	
	private Personnel  personnel = new Personnel();
	private List<Personnel> listPersonnel = new ArrayList<>();
	private List listeSexe = new ArrayList<>();
	private Sexe chooseedSexe = new Sexe();
	private Fonction choosedFonction = new Fonction();
	private List listeFonction = new ArrayList<>();
	
	private UserAuthentication userAuthentication = new UserAuthentication();
	private UserAuthorization userAuthorization = new UserAuthorization();
	
	private String destination="C:\\GIVAC_RAPPORTS\\PERS_";

	// Contrôle de coposant
	private CommandButton btnValider = new CommandButton();
	private InputText inputVhOblig = new InputText();
	private OutputLabel outputVhOblig = new OutputLabel();

	public String enregistrer(){
		//enregistrer dans la table UserAuthentication
		userAuthentication.setUsername(personnel.getUsername());
		userAuthentication.setPassword(personnel.getPassword());
		userAuthentication.setEnabled(true);
		userAuthentication.setNom(personnel.getNom());
		userAuthentication.setPrenoms(personnel.getPrenoms());
		userAuthentication.setSexe(chooseedSexe);
		userAuthentication.setPhone1(personnel.getPhone1());
		userAuthentication.setPhone2(personnel.getPhone2());
		userAuthentication.setPhoto(personnel.getPhoto());
		userAuthentication.setLieuNais(personnel.getLieuNais());
		userAuthentication.setDateNais(personnel.getDateNais());
		userAuthentication.setEmail(personnel.getEmail());
		
		//enregistrer das la table Personnel
		
		personnel.setCodeSexe(chooseedSexe.getCodeSexe());
		personnel.setFonction(choosedFonction);
		personnel.setEnabled(true);
		getService().addObject(userAuthentication);
		personnel.setUserAuthentication(userAuthentication);
		getService().addObject(personnel);

		//Enregistrer l'autorisation
		userAuthorization.setUserAuthentication(userAuthentication);
		userAuthorization.setRole("ROLE_PERSONNEL");
		getService().addObject(userAuthorization);

		actualiserList();
		
		vider(personnel);
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement effcetué!", null));
		return "pages/enseignant?faces-redirect";
	}

	public void annuler() {
		btnValider.setDisabled(false);
		vider(personnel);
		actualiserList();
	}

	public void vider(Personnel objet) {
		objet.setDateNais(null);
		objet.setLieuNais(null);
		objet.setEmail(null);
		objet.setNom(null);
		objet.setPrenoms(null);
		objet.setPhone1(null);
		objet.setPhone2(null);
		objet.setUsername(null);
		objet.setPassword(null);
		objet.setPhoto(null);
	}

	@SuppressWarnings("unchecked")
	public void actualiserList(){
		listPersonnel.clear();
		listPersonnel = getService().getObjects("Personnel");
	}

	
public void upload(FileUploadEvent event) { 
		
		// Do what you want with the file       
		try {
		copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
		
		//Mis à jour dans la table enseignant
		personnel.setPhoto(destination);
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
		} catch (IOException e) {
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

	

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	@SuppressWarnings("unchecked")
	public List<Personnel> getListPersonnel() {
		if (listPersonnel.isEmpty()) {
			listPersonnel = getService().getObjects("Personnel");
		}
		return listPersonnel;
	}

	public void setListPersonnel(List<Personnel> listPersonnel) {
		this.listPersonnel = listPersonnel;
	}

	public Fonction getChoosedFonction() {
		return choosedFonction;
	}

	public void setChoosedFonction(Fonction choosedFonction) {
		this.choosedFonction = choosedFonction;
	}

	public List getListeFonction() {
		if (listeFonction.isEmpty()) {
			listeFonction = getService().getObjects("Fonction");
		}
		return listeFonction;
	}

	public void setListeFonction(List listeFonction) {
		this.listeFonction = listeFonction;
	}
}
