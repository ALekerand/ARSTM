package com.ARSTM.utilitares;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

@Component
public class DateCalculator {
	private static  Logger logger=Logger.getLogger(DateCalculator.class);
	
	private long duree1heure = 1000L*60L*60L;//1000 millisecondes * 60 secondes * 60 minutes = 1 heure
	private long duree1jour = duree1heure*24L; //1 heure * 24 = 1 jour
	private int resulteComparDate;
	private int nombreJour;
	
	
	public int calculerDifference(Date date1, Date date2) {//By ALekerand
		long tpsMiliSeconde = Math.abs(date1.getTime() - date2.getTime()); //calcul difference entre les deux dates
		 try {
			
			setNombreJour((int) ((tpsMiliSeconde)/duree1jour));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Erreur lors du calcul de la difference de deux dates",e);
		}
		return nombreJour ;
		}
	
	
	public int comparerDate(Date date1, Date date2){//By ALekerand
		try {
			setResulteComparDate(date1.compareTo(date2));
		} catch (Exception e) {
			// TODO Auto-generated catch block
		//	logger.error("Erreur lors de la comparaison de deux dates",e);
		}
		
		return resulteComparDate;
	}



	public int getResulteComparDate() {
		return resulteComparDate;
	}



	public void setResulteComparDate(int resulteComparDate) {
		this.resulteComparDate = resulteComparDate;
	}


	public int getNombreJour() {
		return nombreJour;
	}


	public void setNombreJour(int nombreJour) {
		this.nombreJour = nombreJour;
	}

}
