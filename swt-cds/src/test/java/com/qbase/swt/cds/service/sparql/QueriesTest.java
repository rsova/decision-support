package com.qbase.swt.cds.service.sparql;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueriesTest {

	@Test
	public void testLoincUriLookup() {
		assertTrue(new Queries().lookupUriForLoinc("abc").endsWith("SELECT * WHERE {  ?uri rdfs:label \"abc\".}"));
	}
	@Test
	public void testCreate() {
		assertTrue(new Queries().createQueryForCdsDetails().endsWith("BIND (CONCAT(CONCAT(\"Lab result indicates a risk of Renal Insufficiency. Result: \",?r),\" is at or above the upper limit of the normal range\" )as ?x)}"));
		//System.out.println(Queries.createQueryForCdsDetails(null));
	}

}
