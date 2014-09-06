package com.qbase.swt.cds.rules;

public class ReasoningModel {
		
	public  String getInferenceModel(){
		return	new StringBuffer("<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n")
		.append("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n")
		.append("xmlns:owl=\"http://www.w3.org/2002/07/owl#\">\n")
		.append("<rdf:Description rdf:about=\"http://schemes.caregraf.info/icd9cm#584_9\">\n")
		.append("<rdfs:label>RENAL INSUFFICIENCY ADVICE</rdfs:label>\n")
		.append("<rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Class\"/>\n")
		.append("</rdf:Description>\n")
		.append("</rdf:RDF>").toString();
	}
}
