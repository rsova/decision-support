package com.qbase.swt.cds.service.resolvers;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.qbase.swt.cds.service.sparql.SwtSparqlService;


public class SwtResolverTest {
	final static String CREATNINE_TEST_URI = "http://eleu.qbase.com/8188-2527";
	final static String UREA_TEST_URI = "http://eleu.qbase.com/8188-3556";
	
	@Test
	//8188-2527 Creatnine
	public void testGetUriForLoinc_2160_0() throws Exception{
		ISwtResourceResolver resolver = new SwtResourceResolver(new SwtSparqlService());
		
		String rdf = "model-60-173-rdf.xml";
		Model model = ModelFactory.createDefaultModel();
		model.read(rdf);
		
		String creatnineLabTestUri = resolver.getUriForLoinc(model, "2160-0");
		assertEquals("http://eleu.qbase.com/8188-2527", creatnineLabTestUri);
	}
	
	@Test
	//8188-3556 urea nitrogen
	public void testGetUriForLoinc_3094_0() throws Exception {
		ISwtResourceResolver resolver = new SwtResourceResolver(new SwtSparqlService());
		
		String rdf = "model-60-174-rdf.xml";
		Model model = ModelFactory.createDefaultModel();
		model.read(rdf);
		
		String creatnineLabTestUri = resolver.getUriForLoinc(model, "3094-0");
		assertEquals("http://eleu.qbase.com/8188-3556", creatnineLabTestUri);
	}

	@Test
	//60-173 cratnine test
	public void testGetLabTestUriForLoincUri_Creatnine() throws Exception {
		ISwtResourceResolver resolver = new SwtResourceResolver(new SwtSparqlService());
		
		String rdf = "model-60-173-rdf.xml";
		Model model = ModelFactory.createDefaultModel();
		model.read(rdf);
		
		String creatnineLabTestUri = resolver.getLabTestUriForLoincUri(model, CREATNINE_TEST_URI);
		assertEquals("http://eleu.qbase.com/60-173", creatnineLabTestUri);
	}
	
	@Test
	//60-174 urea nitrogen test
	public void testGetLabTestUriForLoincUri_Urea() throws Exception {
		ISwtResourceResolver resolver = new SwtResourceResolver(new SwtSparqlService());
		
		String rdf = "model-60-174-rdf.xml";
		Model model = ModelFactory.createDefaultModel();
		model.read(rdf);
		
		String creatnineLabTestUri = resolver.getLabTestUriForLoincUri(model, UREA_TEST_URI);
		assertEquals("http://eleu.qbase.com/60-174", creatnineLabTestUri);
	}

	@Test
	public void testGetTestUriForLoinc_Creatnine() throws Exception {
		ISwtResourceResolver resolver = new SwtResourceResolver(new SwtSparqlService());
		
		String rdf = "model-60-173-rdf.xml";
		Model model = ModelFactory.createDefaultModel();
		model.read(rdf);
		
		String creatnineLabTestUri = resolver.getLabTestUriForLoinc(model, "2160-0");
		assertEquals("http://eleu.qbase.com/60-173", creatnineLabTestUri);
	}
	
	@Test
	public void testGetTestUriForLoinc_Urea() throws Exception {
		ISwtResourceResolver resolver = new SwtResourceResolver(new SwtSparqlService());
		
		String rdf = "model-60-174-rdf.xml";
		Model model = ModelFactory.createDefaultModel();
		model.read(rdf);
		
		String creatnineLabTestUri = resolver.getLabTestUriForLoinc(model, "3094-0");
		assertEquals("http://eleu.qbase.com/60-174", creatnineLabTestUri);
		
	}
}
//| creatnineLoincUri                 | creatnineTestUri               | bunLoincUri                       | bunTestUri                     |
//===========================================================================================================================================
//| <http://eleu.qbase.com/8188-2527> | <http://eleu.qbase.com/60-173> | <http://eleu.qbase.com/8188-3556> | <http://eleu.qbase.com/60-174> |
//-------------------------------------------------------------------------------------------------------------------------------------------
