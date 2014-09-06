package com.qbase.swt.cds.rules;

import java.io.BufferedReader;
import java.io.StringReader;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

public class SwtRuleProcessor implements ISwtRuleProcessor {

	public InfModel generateStatements(Model model, String param) throws Exception {
		
		String context = new Rules().getRenalInsufficiencyRuleForLoinc(param); 
		BufferedReader reader = new BufferedReader(new StringReader(context));
		Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(Rule.rulesParserFromReader(reader)));
		reasoner.setDerivationLogging(true);
		
		return ModelFactory.createInfModel(reasoner, model);
	}
}