package com.qbase.swt.cds.rules;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;

public interface ISwtRuleProcessor {

	public InfModel generateStatements(Model model, String testUri) throws Exception;

}
