package com.qbase.swt.cds.service.sparql;

import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Model;

public interface ISwtQueryService {
	public Map<String, String> executeQuery(List<String> values, String query, Model model) throws Exception;
	public Model createQuery(String query, Model model) throws Exception;
}
