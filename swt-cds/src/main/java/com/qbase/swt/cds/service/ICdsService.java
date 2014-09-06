package com.qbase.swt.cds.service;


public interface ICdsService {
	final static String CREATNINE = "2160-0";
	final static String UREA_NITROGEN = "3094-0";

	/**
	 * Clinical decision support based on patient clinical chemistry tests to
	 * identify and produce renal insufficiency advice.
	 * Based on loincs for Creatinine and Urea nitrogen.
	 * 
	 * @param rdf
	 * @return
	 * @throws Exception 
	 */
	public String analyze(String rdf) throws Exception;

}
