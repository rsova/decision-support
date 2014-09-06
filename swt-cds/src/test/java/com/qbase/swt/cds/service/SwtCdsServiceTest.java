package com.qbase.swt.cds.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import com.qbase.swt.cds.rules.ISwtRuleProcessor;
import com.qbase.swt.cds.rules.SwtRuleProcessor;
import com.qbase.swt.cds.service.resolvers.ISwtResourceResolver;
import com.qbase.swt.cds.service.sparql.ISwtQueryService;
import com.qbase.swt.cds.service.sparql.SwtSparqlService;
import com.qbase.swt.cds.service.writers.ISwtRdfWriter;
import com.qbase.swt.cds.service.writers.SwtSparqlWriter;

public class SwtCdsServiceTest {

	ISwtQueryService swtSparqlService;
	ISwtRdfWriter writer;
	ISwtResourceResolver resolver;
	ISwtRuleProcessor processor;
	SwtCdsService service;

	@Before
	public void setup() {
		swtSparqlService = new SwtSparqlService();
		writer = new SwtSparqlWriter(swtSparqlService);
		processor = new SwtRuleProcessor();
		service = new SwtCdsService( processor, writer);
		
		List<String> concepts = new ArrayList<String>();
		concepts.add("2160-0");
		concepts.add("3094-0");
		service.setConceptLoincList(concepts);
		
	}

	@Test
	public void testCreatnine() throws Exception {

		String rdf = IOUtils.toString(this.getClass().getResourceAsStream("/creatnine-rdf.xml"), "UTF-8");
		String rulesRdf = service.analyze(rdf);
//		System.out.println(rulesRdf);
		assertTrue(rulesRdf.contains("<rdfs:label>Lab result indicates a risk of Renal Insufficiency. Result: 1.3 is at or above the upper limit of the normal range</rdfs:label>"));

	}

	@Test
	public void testUrea() throws Exception {

		String rdf = IOUtils.toString(this.getClass().getResourceAsStream("/urea-rdf.xml"), "UTF-8");
		String rulesRdf = service.analyze(rdf);
		// System.out.println(rulesRdf);
		assertTrue(rulesRdf.contains("<rdfs:label>Lab result indicates a risk of Renal Insufficiency. Result: 24 is at or above the upper limit of the normal range</rdfs:label>"));
	}
	
	@Test
	public void test_NoRenalInsuff() throws Exception {
		
		String rdf = IOUtils.toString(this.getClass().getResourceAsStream("/creatnine-no-alert-rdf.xml"), "UTF-8");
		String rulesRdf = service.analyze(rdf);
		// System.out.println(rulesRdf);
		assertFalse(rulesRdf.contains("<rdfs:label>Lab result indicates a risk for Hyperlipidemia"));
	}
	
	@Test
	public void test_NotAResultRdf() throws Exception {
		
		String rdf = IOUtils.toString(this.getClass().getResourceAsStream("/model-60-173-rdf.xml"), "UTF-8");
		String rulesRdf = service.analyze(rdf);
		// System.out.println(rulesRdf);
		assertFalse(rulesRdf.contains("<rdfs:label>Lab result indicates a risk for Hyperlipidemia"));
	}

}
