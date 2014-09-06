package com.qbase.swt.cds.service.writers;

import com.hp.hpl.jena.rdf.model.Model;

public interface ISwtRdfWriter {

	String createResponseRdf(Model model) throws Exception;

}
