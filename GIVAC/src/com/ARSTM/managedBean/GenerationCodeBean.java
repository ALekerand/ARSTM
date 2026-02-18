package com.ARSTM.managedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ARSTM.service.Iservice;


@Component
@Scope("session")
public class GenerationCodeBean {
	
	@Autowired
	private Iservice iservice;
	
	/**
	 * @author A. Lekerand
	 * @param Classe
	 * @param prefix
	 * @return le code de l'objet
	 */
	public String genererCode(String Classe, String prefix) {
		//String prefix="";
		int nbEnregistrement = this.iservice.getObjects(Classe).size();
		if(nbEnregistrement < 10)
			prefix += "00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix += "0" ;
		/*
		 * if (nbEnregistrement > 100) prefix = "NA" ;
		 */
		//this.nature.setCodeNature(prefix+(nbEnregistrement+1));
		return prefix+(nbEnregistrement+1);
	}

}
