package com.qbase.swt.cds.rules;


public class Rules {

	public String getRenalInsufficiencyRuleForTest(String parameter) {
		StringBuffer ruleBuffer = new StringBuffer("@prefix chcss: <http://datasets.caregraf.org/chcss/>.\n");
		ruleBuffer.append("@prefix dc: <http://purl.org/dc/elements/1.1/>.\n");
		ruleBuffer.append("@prefix fms: <http://datasets.caregraf.org/fms/>.\n");
		ruleBuffer.append("@prefix owl: <http://www.w3.org/2002/07/owl#>.\n");
		ruleBuffer.append("@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.\n");
		ruleBuffer.append("@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>.\n");
		ruleBuffer.append("@prefix xsd: <http://www.w3.org/2001/XMLSchema#>.\n");
		ruleBuffer.append("@prefix icd9cm: <http://schemes.caregraf.info/icd9cm>.\n");
		ruleBuffer.append("[ RenalInsufficiencyAdvice: (?d chcss:cds_advice icd9cm:584_9) <- (?d chcss:test-63_07 $x) (?d chcss:alert-63_07 'H') ]".replace("$x", parameter));
		return ruleBuffer.toString();	
	}
	
	//renal insufficiency advice = 1) clinical chemistry test with 2) specific loinc and 2) and alert set to high
	public String getRenalInsufficiencyRuleForLoinc(String parameter) {
		StringBuffer ruleBuffer = new StringBuffer("@prefix chcss: <http://datasets.caregraf.org/chcss/>.\n");
		ruleBuffer.append("@prefix dc: <http://purl.org/dc/elements/1.1/>.\n");
		ruleBuffer.append("@prefix fms: <http://datasets.caregraf.org/fms/>.\n");
		ruleBuffer.append("@prefix owl: <http://www.w3.org/2002/07/owl#>.\n");
		ruleBuffer.append("@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.\n");
		ruleBuffer.append("@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>.\n");
		ruleBuffer.append("@prefix xsd: <http://www.w3.org/2001/XMLSchema#>.\n");
		ruleBuffer.append("@prefix icd9cm: <http://schemes.caregraf.info/icd9cm>.\n");
		ruleBuffer.append("[ RenalInsufficiencyAdvice: (?d rdf:type icd9cm:#584_9) <- (?d rdf:type chcss:63_07)(?d chcss:clinical_chemistry_loinc_code-63_07 '$x') (?d chcss:alert-63_07 'H') ]".replace("$x", parameter));
//		ruleBuffer.append("[ RenalInsufficiencyAdvice: (?d chcss:cds_advice icd9cm:584_9) <- (?d chcss:clinical_chemistry_loinc_code-63_07 '$x') (?d chcss:alert-63_07 'H') ]".replace("$x", parameter));
		return ruleBuffer.toString();	
	}
}

