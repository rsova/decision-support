package boot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boot.service.cds.CdsService;
import boot.service.task.executor.CdsTaskExecutor;
import boot.utils.Advice;
import boot.utils.LexMed;

import com.google.gson.GsonBuilder;


@RestController
public class CdsLookupController {
    @Autowired
    CdsService service = null;
	private static Logger log = LoggerFactory.getLogger(CdsLookupController.class);
	
	@RequestMapping(value ="/classesAll", method = RequestMethod.GET)
	//public Advice lookup(@RequestParam("drug") String drug ) throws Exception {
	public String lookup(@RequestParam("drug") String drug ) throws Exception {
	   log.info("drug :" + drug );
	   Advice advice = null;
		try {
			advice = service.lookupClassesForDrugName(drug);
			advice.success = true;
		} catch (Exception exp) {
			advice = handleErrors(exp);
		}
		return new GsonBuilder().setPrettyPrinting().create().toJson(advice);
//		return advice;
	}
		
	protected Advice handleErrors(Exception e) {
		logException(e);
		Advice advice = new Advice();
		advice.errors = Arrays.asList(new String[]{ ((e.getCause()!=null)?e.getCause().getMessage():e.getMessage())});
		advice.success = false;
		return advice;
	}

	protected void logException(Exception e) {
		log.error("Catastrophic error", e);
	}

	protected List<String> parceParam(String ids) {
		return (ids !=null && !ids.isEmpty())?Arrays.asList(ids.split(";")):new ArrayList<String>(0);
	}
	
}
