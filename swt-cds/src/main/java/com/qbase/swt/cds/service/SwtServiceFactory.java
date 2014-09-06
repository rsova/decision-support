package com.qbase.swt.cds.service;

import java.util.ArrayList;
import java.util.Arrays;

import com.qbase.swt.cds.rules.ISwtRuleProcessor;
import com.qbase.swt.cds.rules.SwtRuleProcessor;
import com.qbase.swt.cds.service.sparql.ISwtQueryService;
import com.qbase.swt.cds.service.sparql.SwtSparqlService;
import com.qbase.swt.cds.service.writers.ISwtRdfWriter;
import com.qbase.swt.cds.service.writers.SwtSparqlWriter;

public class SwtServiceFactory implements IServiceFactory{
	
	public  SwtServiceFactory() {
		super();
	}

	public ICdsService create(){
		ISwtQueryService swtSparqlService = new SwtSparqlService();
		ISwtRdfWriter writer = new SwtSparqlWriter(swtSparqlService);
		ISwtRuleProcessor processor = new SwtRuleProcessor();
		return  new SwtCdsService(processor, writer).setConceptLoincList(new ArrayList<String>(Arrays.asList(new String[] {ICdsService.CREATNINE, ICdsService.UREA_NITROGEN})));
	};
}
