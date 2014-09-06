package com.qbase.swt.cds.service;

import static org.apache.jena.riot.Lang.RDFXML;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.qbase.swt.cds.rules.ISwtRuleProcessor;
import com.qbase.swt.cds.rules.ReasoningModel;
import com.qbase.swt.cds.service.writers.ISwtRdfWriter;

public class SwtCdsService  implements ICdsService {
	private ISwtRuleProcessor ruleProcessor = null;
	private ISwtRdfWriter writer = null;
	private List<String> conceptLoincList = null;
	
	public SwtCdsService(ISwtRuleProcessor ruleProcessor, ISwtRdfWriter writer) {
		super();
		this.ruleProcessor = ruleProcessor;
		this.writer = writer;
		conceptLoincList = new ArrayList<String>();
	}	

	public List<String> getConceptLoincList() {
		return conceptLoincList;
	}

	// It's ok, it's called chain of command :) 
	public SwtCdsService setConceptLoincList(List<String> conceptLoincList) {
		this.conceptLoincList = conceptLoincList;
		return this;
	}

	public String analyze(String rdf)  throws Exception {
		
		// Load data into model
		Model model = ModelFactory.createDefaultModel();		
		model.read(new ByteArrayInputStream(rdf.getBytes()),null,RDFXML.getName());
		
		// Add additional model used by reasoner  
		Model owlModel = getReasoningModel();
		model.add(owlModel);
		
		// Generate clinical decision support statements, based on rules
		InfModel infModel = generateStatementsForConcepts(model);
		
		// Create response rdf
		return writer.createResponseRdf(infModel);
	}

	protected Model getReasoningModel() {
		Model owlModel = ModelFactory.createDefaultModel();
		owlModel.read(new ByteArrayInputStream(new ReasoningModel().getInferenceModel().getBytes()),null,RDFXML.getName());
		return owlModel;
	}

	protected InfModel generateStatementsForConcepts(Model model) throws Exception {
		InfModel infModel = null;

		for (String loinc : getConceptLoincList()) {
			InfModel loincModel = ruleProcessor.generateStatements(model, loinc);
			infModel = (InfModel)((infModel == null)?loincModel: infModel.add(loincModel));
		}
		return infModel;
	}

}
