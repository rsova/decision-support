package com.qbase.swt.cds.service.resolvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.qbase.swt.cds.service.sparql.ISwtQueryService;

//Class retired after loincs were added to the result rdf.
// No need anymore to resolve loinc codes into test uris. 
@Deprecated
public class SwtResourceResolver implements ISwtResourceResolver {
	ISwtQueryService queryService = null;

	public SwtResourceResolver(ISwtQueryService queryService) {
		super();
		this.queryService = queryService;
	}

	public String getResourceByUri(String uri) {
		http://10.255.166.11/fmqlEP?fmql=DESCRIBE%208188-2527&format=RDF
		return null;
	}

	public String getLabTestUriForLoincUri(Model model, String uri) throws Exception {
		String param = "uri";
		String spqrql = IOUtils.toString(this.getClass().getResourceAsStream("/sparql/labTestUriLookup.rq"), "UTF-8");
		List<String> params = new ArrayList<String>(Arrays.asList(new String[] {param}));
		Map<String, String> results = queryService.executeQuery(params, spqrql.replace("$LOINC_URI", uri), model);
		
		return results.get(param);
	}

	public String getUriForLoinc(Model model, String loinc) throws Exception {
		String param = "uri";
		String spqrql = IOUtils.toString(this.getClass().getResourceAsStream("/sparql/loincUriLookup.rq"), "UTF-8");
		List<String> params = new ArrayList<String>(Arrays.asList(new String[] {param}));
		Map<String, String> results = queryService.executeQuery(params, spqrql.replace("$LOINC", loinc), model);
		
		return results.get(param);
	}
	
	public String getLabTestUriForLoinc(Model model, String loinc) throws Exception {
		return getLabTestUriForLoincUri(model,getUriForLoinc(model, loinc));
	}

	public String getLabTestUriForLoinc(String loinc) throws Exception {
		String rdf = "model-60-173-rdf.xml";
		Model model = ModelFactory.createDefaultModel();
		model.read(rdf);
		return getLabTestUriForLoinc(model, loinc);
	}

}
