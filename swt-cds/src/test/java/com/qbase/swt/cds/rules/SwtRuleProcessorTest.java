package com.qbase.swt.cds.rules;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Derivation;
import com.hp.hpl.jena.reasoner.rulesys.RuleDerivation;

public class SwtRuleProcessorTest {

	private static final String CREATNINE_LAB_TEST = "http://eleu.qbase.com/60-173";

	//@Test
	public void testCreatnine_LabTest_WithUri() throws Exception {
		SwtRuleProcessor ruleProcessor = new SwtRuleProcessor();
		String rdf = "creatnine-rdf.xml";    
		Model model = ModelFactory.createDefaultModel();
		model.read(rdf);
		
		Model owlModel = ModelFactory.createDefaultModel();		
		owlModel.read("model/owlRdf.xml");
		model.add(owlModel);
		
		InfModel inf = ruleProcessor.generateStatements(model, CREATNINE_LAB_TEST);
		
		boolean hasStatements = false;
		List<Statement> statements = inf.listStatements().toList();
		 for (Statement statement : statements) {
		        for (Iterator<Derivation> item = inf.getDerivation(statement); item.hasNext(); ) {
					hasStatements = true;

		            //Derivation deriv = (Derivation) item.next();
		        	RuleDerivation derivation = (RuleDerivation)item.next();
		        	//System.out.println(derivation.getConclusion());
		        	assertEquals("http://eleu.qbase.com/63_07-173_6859573.8547_889 @http://datasets.caregraf.org/chcss/cds_advice http://schemes.caregraf.info/icd9cm584_9", derivation.getConclusion().toString());
		        	//System.out.println(derivation.getMatches());
		        	assertEquals("[http://eleu.qbase.com/63_07-173_6859573.8547_889 @http://datasets.caregraf.org/chcss/test-63_07 http://eleu.qbase.com/60-173, http://eleu.qbase.com/63_07-173_6859573.8547_889 @http://datasets.caregraf.org/chcss/alert-63_07 \"H\"]",derivation.getMatches().toString());
		        	//derivation.printTrace(out, true);
		        }
		    }
		    //out.flush();
		 	assertTrue(hasStatements);
//		    System.out.println("~~~~~~~~~~~~to rdf~~~~~~~~~~~~~~~~");
//		    inf.write(System.out, org.apache.jena.riot.Lang.RDFXML.getName());
	}	

	
	@Test
	public void testUrea_LabTest() throws Exception {
		SwtRuleProcessor ruleProcessor = new SwtRuleProcessor();
		String rdf = "urea-rdf.xml";    
		Model model = ModelFactory.createDefaultModel();
		model.read(rdf);
		
		Model owlModel = ModelFactory.createDefaultModel();		
		owlModel.read("model/owlRdf.xml");
		model.add(owlModel);
		
		InfModel inf = ruleProcessor.generateStatements(model,"3094-0" );
		boolean hasStatements = false;
		List<Statement> statements = inf.listStatements().toList();
		 for (Statement statement : statements) {
		        for (Iterator<Derivation> item = inf.getDerivation(statement); item.hasNext(); ) {
		        	hasStatements = true;
		        	RuleDerivation derivation = (RuleDerivation)item.next();
		        	//System.out.println(derivation.getConclusion());
		        	assertNotNull(derivation.getConclusion());
		        	assertEquals("http://eleu.qbase.com/63_07-174_6859497.8757_163 @rdf:type http://schemes.caregraf.info/icd9cm#584_9", derivation.getConclusion().toString());
		        	//System.out.println(derivation.getMatches());
		        	assertEquals("[http://eleu.qbase.com/63_07-174_6859497.8757_163 @rdf:type http://datasets.caregraf.org/chcss/63_07, http://eleu.qbase.com/63_07-174_6859497.8757_163 @http://datasets.caregraf.org/chcss/clinical_chemistry_loinc_code-63_07 \"3094-0\", http://eleu.qbase.com/63_07-174_6859497.8757_163 @http://datasets.caregraf.org/chcss/alert-63_07 \"H\"]",derivation.getMatches().toString());
		        }
		    }
		 
		 	assertTrue(hasStatements);
		    
//		    System.out.println("~~~~~~~~~~~~to rdf~~~~~~~~~~~~~~~~");
//		    inf.write(System.out, org.apache.jena.riot.Lang.RDFXML.getName());
	}	
	@Test
	public void testCreatnine_LabTest() throws Exception {
		SwtRuleProcessor ruleProcessor = new SwtRuleProcessor();
		String rdf = "creatnine-rdf.xml";    
		Model model = ModelFactory.createDefaultModel();
		model.read(rdf);
		
		Model owlModel = ModelFactory.createDefaultModel();		
		owlModel.read("model/owlRdf.xml");
		model.add(owlModel);
		
		InfModel inf = ruleProcessor.generateStatements(model,"2160-0" );
		boolean hasStatements = false;
		List<Statement> statements = inf.listStatements().toList();
		for (Statement statement : statements) {
			for (Iterator<Derivation> item = inf.getDerivation(statement); item.hasNext(); ) {
				hasStatements = true;
				RuleDerivation derivation = (RuleDerivation)item.next();
				//System.out.println(derivation.getConclusion());
				assertNotNull(derivation.getConclusion());
				assertEquals("http://eleu.qbase.com/63_07-173_6859573.8547_889 @rdf:type http://schemes.caregraf.info/icd9cm#584_9", derivation.getConclusion().toString());
				//System.out.println(derivation.getMatches());
				assertEquals("[http://eleu.qbase.com/63_07-173_6859573.8547_889 @rdf:type http://datasets.caregraf.org/chcss/63_07, http://eleu.qbase.com/63_07-173_6859573.8547_889 @http://datasets.caregraf.org/chcss/clinical_chemistry_loinc_code-63_07 \"2160-0\", http://eleu.qbase.com/63_07-173_6859573.8547_889 @http://datasets.caregraf.org/chcss/alert-63_07 \"H\"]",derivation.getMatches().toString());
			}
		}
		
		assertTrue(hasStatements);
		
//		    System.out.println("~~~~~~~~~~~~to rdf~~~~~~~~~~~~~~~~");
//		inf.write(System.out, org.apache.jena.riot.Lang.RDFXML.getName());
	}	

}
