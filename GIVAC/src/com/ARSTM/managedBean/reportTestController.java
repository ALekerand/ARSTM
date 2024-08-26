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
public class reportTestController {
	@Autowired
	Iservice service;
	

	public void genererFicheInscription() throws JRException, IOException {
		
		Map<String, Object> parametres= new HashMap<String, Object>();
		
		
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/fiche_inscription.jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametres, new JREmptyDataSource());
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition","attachment; filename=template.pdf");
		ServletOutputStream outputStream = response.getOutputStream(); 
		
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		FacesContext.getCurrentInstance().getResponseComplete();
		 
	     System.out.println("======= FIN METHODE =========");
	}

	//**************************ACCESSEURS*************************//*

	public Iservice getService() {
		return service;
	}

	public void setService(Iservice service) {
		this.service = service;
	}


}