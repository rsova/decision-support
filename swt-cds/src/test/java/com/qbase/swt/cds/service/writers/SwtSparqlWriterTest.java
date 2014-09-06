package com.qbase.swt.cds.service.writers;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.helpers.SyslogQuietWriter;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.qbase.swt.cds.service.sparql.SwtSparqlService;

public class SwtSparqlWriterTest {

	@Test
	public void testLookup() {
		SwtSparqlWriter writer = new SwtSparqlWriter(new SwtSparqlService());
		Model model = ModelFactory.createDefaultModel();
		model.read("creatnine-rdf-with-advice.xml");

		List<String> rdfOut = writer.lookupForAdvice(model);
		//assertNotNull(rdfOut);
	}
	
	@Test
	public void testGenerateShortList() throws Exception {
		
		SwtSparqlWriter writer = new SwtSparqlWriter(new SwtSparqlService());
		Model model = ModelFactory.createDefaultModel();
		model.read("creatnine-rdf-with-advice.xml");
		
		Model outModel = writer.generateInterpretation(null, model);
		assertNotNull(outModel);
		//outModel.write(System.out, org.apache.jena.riot.Lang.RDFXML.getName());
	}

	@Test
	public void testWriteAdviceRdf() throws Exception {
		SwtSparqlWriter writer = new SwtSparqlWriter(new SwtSparqlService());
		Model model = ModelFactory.createDefaultModel();
		model.read("creatnine-rdf-with-advice.xml");

		String out = writer.createResponseRdf(model);
		assertNotNull(out);
		//System.out.println(out);
	}
	
}
