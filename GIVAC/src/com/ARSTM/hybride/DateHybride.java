package com.ARSTM.hybride;

import java.util.Date;

import org.joda.time.DateTime;

import com.ARSTM.model.Semaine;

public class DateHybride {
	private Semaine semaine;
	private int numDate;
	private Date date;
	//private DateTime date;
	private StringBuffer jourSemaine;
	
	
	
	/**********************ACCESSEURS*******************/

	public Semaine getSemaine() {
		return semaine;
	}

	public void setSemaine(Semaine semaine) {
		this.semaine = semaine;
	}

	public int getNumDate() {
		return numDate;
	}

	public void setNumDate(int numDate) {
		this.numDate = numDate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public StringBuffer getJourSemaine() {
		return jourSemaine;
	}

	public void setJourSemaine(StringBuffer jourSemaine) {
		this.jourSemaine = jourSemaine;
	}

}
