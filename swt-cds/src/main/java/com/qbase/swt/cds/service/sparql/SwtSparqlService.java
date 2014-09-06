package com.qbase.swt.cds.service.sparql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

public class SwtSparqlService implements ISwtQueryService {

	public Map<String, String> executeQuery(List<String> keys, String context, Model model) throws Exception {
		Query query = QueryFactory.create(context);
		ResultSet resultSet = QueryExecutionFactory.create(query, model).execSelect();
		Map<String, String> resultMap = new HashMap<String, String>();
		
		for (; resultSet.hasNext();) {
			QuerySolution soulution = resultSet.nextSolution();
			for (String key : keys) {
				Resource resource = soulution.getResource(key);
				resultMap.put(key, resource.getURI());
			}
		}
		return resultMap;
	}

	public Model createQuery(String query, Model model) throws Exception {
		return QueryExecutionFactory.create(new Queries().createQueryForCdsDetails(), model).execConstruct();
	}
}
