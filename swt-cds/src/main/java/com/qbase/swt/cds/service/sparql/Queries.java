package com.qbase.swt.cds.service.sparql;

import com.hp.hpl.jena.query.ParameterizedSparqlString;

public class Queries {
	
	public String lookupUriForLoinc(String loinc) {
		ParameterizedSparqlString pss = new ParameterizedSparqlString();
		pss.setCommandText("SELECT * WHERE {  ?uri rdfs:label ?.}");
		pss.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		pss.setLiteral(0, loinc);
		return pss.toString();	
	}
	
	public String createQueryForCdsDetails() {
		ParameterizedSparqlString pss = new ParameterizedSparqlString();
		pss.setCommandText("CONSTRUCT { ?s rdfs:label ?x.\n");
		pss.append("?s rdfs:context ?a.}\n");
		pss.append("WHERE {?a rdf:type <http://schemes.caregraf.info/icd9cm#584_9>; \n");
		pss.append("chcss:result-63_07 ?r;\n");
		pss.append("BIND (URI(\"http://eleu.qbase.com/4ae2efdc-d23f-11e3-b774-1a514932ac01\") AS ?s).\n"); 
		pss.append("BIND (CONCAT(CONCAT(\"Lab result indicates a risk of Renal Insufficiency. Result: \",?r),\" is at or above the upper limit of the normal range\" )as ?x)}");		
		pss.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		pss.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		pss.setNsPrefix("chcss", "http://datasets.caregraf.org/chcss/");
		return pss.toString();	
	}
	
	
		
}
