package com.ARSTM.reportBean;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.model.Ecole;
import com.ARSTM.model.Filieres;
import com.ARSTM.model.Mention;
import com.ARSTM.model.Section;
import com.ARSTM.requetes.RequeteFiliere2;
import com.ARSTM.requetes.RequeteMention;
import com.ARSTM.requetes.RequeteSection;
import com.ARSTM.service.Iservice;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

@Component
@Scope("session")
public class FicheNotationReportBean {
	@Autowired
	private Iservice service;
	
	//copie se parcourssectionbean 
	@Autowired
	RequeteFiliere2 requeteFiliere2;
	@Autowired
	RequeteMention requeteMention;
	@Autowired
	RequeteSection requeteSection;
	
	private Section selectedSection = new Section();
	private List listeSection = new ArrayList<>();
	
	private String cb_exam_value = "";
	private Ecole choosedEcole = new Ecole();
	private Filieres choosedFiliere = new Filieres();
	private Mention choosedMention = new Mention();
	private Section choosedSection = new Section();
	
	private List listMention = new ArrayList<>();
	private List listEcole = new ArrayList<>();
	private List listFiliere = new ArrayList<>();
	
	
	public void chargerFiliere(){
		listFiliere.clear();
		listFiliere = requeteFiliere2.recupFiliere2ByEcole(choosedEcole.getCodeEcole());
	}

	public void chargerMention(){
		listMention.clear();
		listMention = requeteMention.recupMentionByEcoleFiliere(choosedFiliere.getCodeFiliere());
	}
	
	public void chargerSection() {
		listeSection.clear();
		for (Section objectSection : choosedMention.getSections()) {
			listeSection.add(objectSection);
		}
	}
		
	
public List getListMention() {
		
		return listMention;
	}

	public void setListMention(List listMention) {
		this.listMention = listMention;
	}

	public List getListeSection() {
		return listeSection;
	}

	public void setListeSection(List listeSection) {
		this.listeSection = listeSection;
	}

	public Ecole getChoosedEcole() {
		return choosedEcole;
	}

	public void setChoosedEcole(Ecole choosedEcole) {
		this.choosedEcole = choosedEcole;
	}

	public List getListEcole() {
		if (listEcole.isEmpty()) {
			listEcole = service.getObjects("Ecole");
		}
		return listEcole;
	}

	public void setListEcole(List listEcole) {
		this.listEcole = listEcole;
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

	public List getListFiliere() {
		/*if (listFiliere.isEmpty()) {
			listFiliere = getService().getObjects("Filieres");
		}*/
		return listFiliere;
	}

	public void setListFiliere(List listFiliere) {
		this.listFiliere = listFiliere;
	}
	
	
	
	
	
	
	// fin
	
	
	public void genererEtat() {
		
		try {
			
			JasperDesign jasperDesign = JRXmlLoader.load("E:\\mesRapports\\fichenotation.jrxml");
			//Compilation du fichier
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("nomecole", choosedEcole.getNomEcole());
			parameters.put("annee_academique", "2020-2021");
			parameters.put("parcours",choosedMention.getAbrevMention());
			parameters.put("section", choosedSection.getAbrevSection());
			parameters.put("effectif1", 20);
			
			
			System.out.println("======== Parametre sette=======");//Clean after
				// Remplissage du rapport compilé
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, new JREmptyDataSource());
			System.out.println("======== Remplissage =======");//Clean after
			
			// Visualisation, exportation ou impression 
			long instant = System.currentTimeMillis();
			System.out.println("L'instant ======="+instant);
		    JasperExportManager.exportReportToPdfFile(jasperPrint, "E:\\mesRapports\\fichenotation"+instant+""+".pdf");
			
			System.out.println("======== Toust est exécuté");//Clean after
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Section getChoosedSection() {
		return choosedSection;
	}

	public void setChoosedSection(Section choosedSection) {
		this.choosedSection = choosedSection;
	}

	public String getCb_exam_value() {
		return cb_exam_value;
	}

	public void setCb_exam_value(String cb_exam_value) {
		this.cb_exam_value = cb_exam_value;
	}

	public Section getSelectedSection() {
		return selectedSection;
	}

	public void setSelectedSection(Section selectedSection) {
		this.selectedSection = selectedSection;
	}

}
