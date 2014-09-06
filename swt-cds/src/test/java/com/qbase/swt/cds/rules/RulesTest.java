package com.qbase.swt.cds.rules;

import static org.junit.Assert.*;

import org.junit.Test;

public class RulesTest {

	@Test
	public void testGetRenalInsufficiencyRuleForTest() {
		String actual = new Rules().getRenalInsufficiencyRuleForTest("http://eleu.qbase.com/60-173");
		 //System.out.println(actual);
		assertTrue(actual.endsWith("[ RenalInsufficiencyAdvice: (?d chcss:cds_advice icd9cm:584_9) <- (?d chcss:test-63_07 http://eleu.qbase.com/60-173) (?d chcss:alert-63_07 'H') ]"));
	}
	@Test
	public void testGetRenalInsufficiencyRuleRorLoinc() {
		String actual = new Rules().getRenalInsufficiencyRuleForLoinc("3094-0");
		//System.out.println(actual);
		assertTrue(actual.endsWith("[ RenalInsufficiencyAdvice: (?d rdf:type icd9cm:#584_9) <- (?d rdf:type chcss:63_07)(?d chcss:clinical_chemistry_loinc_code-63_07 '3094-0') (?d chcss:alert-63_07 'H') ]"));
	}

}
