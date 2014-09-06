package com.qbase.swt.cds.service.writers;

import java.io.StringWriter;
import java.util.List;

import com.hp.hpl.jena.rdf.model.Model;
import com.qbase.swt.cds.service.sparql.ISwtQueryService;
import com.qbase.swt.cds.service.sparql.Queries;

public class SwtSparqlWriter implements ISwtRdfWriter {
//Lab result indicates a risk of Renal Insufficiency. Result: 200.0 is at or above the upper limit of the normal range"/>

	ISwtQueryService queryService;
	
	public SwtSparqlWriter(ISwtQueryService swtSparqlService) {
		queryService = swtSparqlService;
	}

	public String createResponseRdf(Model model) throws Exception {
		Model interpretation = generateInterpretation(null, model);
		
		StringWriter out = new StringWriter();
		model.add(interpretation);
		model.write(out);
		
		return out.toString();
	}

	protected Model generateInterpretation(List<String> sts, Model model) throws Exception {
		return queryService.createQuery(new Queries().createQueryForCdsDetails(), model);
	}
	
	List<String> lookupForAdvice(Model model) {
		return null;
	}

}