package com.ARSTM.hybride;

import org.springframework.stereotype.Component;

@Component
public class JourWeek {
	
	private String  libeleJour;
	private int codeJour ;
	
	public JourWeek() {
	
	}
	
	public JourWeek(String libeleJour, int codeJour) {
		super();
		this.libeleJour = libeleJour;
		this.codeJour = codeJour;
	}
	
	
	
	
	
	/*********************ACCESSEURS*******************************************/
	public String getLibeleJour() {
		return libeleJour;
	}
	public void setLibeleJour(String libeleJour) {
		this.libeleJour = libeleJour;
	}
	public int getCodeJour() {
		return codeJour;
	}
	public void setCodeJour(int codeJour) {
		this.codeJour = codeJour;
	}
}
