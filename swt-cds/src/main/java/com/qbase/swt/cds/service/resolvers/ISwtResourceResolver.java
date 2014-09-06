package com.qbase.swt.cds.service.resolvers;

import com.hp.hpl.jena.rdf.model.Model;

public interface ISwtResourceResolver {
	String getLabTestUriForLoincUri(Model model, String uri) throws Exception;
	String getUriForLoinc(Model model, String loinc) throws Exception;
	String getResourceByUri(String uri);
	String getLabTestUriForLoinc(Model model, String loinc) throws Exception;
	String getLabTestUriForLoinc(String loinc) throws Exception;
}
