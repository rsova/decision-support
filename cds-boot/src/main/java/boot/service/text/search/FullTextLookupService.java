package boot.service.text.search;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.complexible.stardog.api.search.SearchResult;
import com.complexible.stardog.ext.spring.DataSource;
import com.complexible.stardog.ext.spring.SnarlTemplatePlus;

@Service
public class FullTextLookupService {
	@Resource(name = "stardogDataSource")
	DataSource stardogDataSource = null;
	
	private static final int limit = 1;
	private static Logger log = LoggerFactory.getLogger(FullTextLookupService.class);

	public String lookupByDrugFullName(String fullName) throws Exception {
		SnarlTemplatePlus template = new SnarlTemplatePlus();
		template.setDataSource(stardogDataSource);
		List<SearchResult> results = template.search(limit, "\"" + fullName + " (substance)\"", 5);
		return (results != null) ? results.get(0).getHit().stringValue() : null;
	}

	public void setStardogDataSource(DataSource stardogDataSource) {
		this.stardogDataSource = stardogDataSource;
	}
}
