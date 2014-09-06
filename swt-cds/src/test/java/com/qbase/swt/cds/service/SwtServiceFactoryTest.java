package com.qbase.swt.cds.service;

import static org.junit.Assert.*;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class SwtServiceFactoryTest {

	@Test
	public void testFactory() throws Exception {
		String rdf = IOUtils.toString(this.getClass().getResourceAsStream("/both-with-loincs-rdf.xml"), "UTF-8");
		String rulesRdf = new SwtServiceFactory().create().analyze(rdf);
		//System.out.println(rulesRdf);
		assertTrue(rulesRdf.contains("<rdfs:label>Lab result indicates a risk of Renal Insufficiency. Result: 1.4 is at or above the upper limit of the normal range</rdfs:label>"));
		assertTrue(rulesRdf.contains("<rdfs:label>Lab result indicates a risk of Renal Insufficiency. Result: 24 is at or above the upper limit of the normal range</rdfs:label>"));
	}

}
