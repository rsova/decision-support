package boot.service.owl.reasoning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.openrdf.model.URI;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.springframework.stereotype.Service;
import org.openrdf.query.BindingSet;

import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.ext.spring.DataSource;
import com.complexible.stardog.ext.spring.RowMapper;
import com.complexible.stardog.ext.spring.SnarlTemplatePlus;

@Service
public class SwtReasonerService {
	@Resource(name = "drugClassConcepts")
	Map<String,String> drugClassConcepts = null;
	
	@Resource(name = "stardogDataSource")
	DataSource stardogDataSource = null;
	
	public List<String> matchToRiskyDrugConcepts( String drugUri ) throws Exception {		
		List<String> classifications = new ArrayList<String>();
		URI uri = ValueFactoryImpl.getInstance().createURI(drugUri);
		
		for (String  concept : drugClassConcepts.keySet()) {
			
			URI medConcept = ValueFactoryImpl.getInstance().createURI(concept);
			
			SnarlTemplatePlus template = new SnarlTemplatePlus();
			template.setDataSource(stardogDataSource);
			
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("aUri", uri);
			args.put("aConcept", medConcept);
			
			//stop on the first match
			if(template.ask("ASK { ?aUri <http://www.w3.org/2000/01/rdf-schema#subClassOf> ?aConcept }", args)){				 
				classifications.add(drugClassConcepts.get(concept));
			}
			
		}
		return 	classifications;
	}
	
	public List<String> lookUpDrugConcepts(final String drugConceptUri) {
		List<String> classifications = new ArrayList<String>();
		
		URI uri = ValueFactoryImpl.getInstance().createURI(drugConceptUri);
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("aUri", uri);
			
			SnarlTemplatePlus template = new SnarlTemplatePlus();
			template.setDataSource(stardogDataSource);
			 String sparql = "SELECT * WHERE {\n"
							+ "?aUri <http://www.w3.org/2000/01/rdf-schema#subClassOf> ?aType; \n"
							+ "}";
			 
			List<Map<String,String>> results = template.query(sparql,args, new RowMapper<Map<String,String>>() {

				@Override
				public Map<String, String> mapRow(BindingSet bindingSet) {
					Map<String,String> map = new HashMap<String,String>();
//					map.put("uri", bindingSet.getValue("aUri").stringValue());
					map.put("type", bindingSet.getValue("aType").stringValue());
					return map;
				}
			 
			});
			
			for (Map<String, String> map : results) {
				String lbl = map.get("type").replaceAll("(http://www.ihtsdo.org/snomedct.owl#|http://www.w3.org/2002/07/owl#|_)", " ");
				classifications.add(lbl);
			}
			
		return classifications;
	}
		
	/*
	 * used for testing only
	 */
	public SwtReasonerService setConcepts(Map<String,String> conceptsMap) {
		drugClassConcepts = conceptsMap;
		return this;
	}

	public void setStardogDataSource(DataSource stardogDataSource) {
		this.stardogDataSource = stardogDataSource;
	}


}
