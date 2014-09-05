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

//TODO: --configure logger, convert sysouts to log
//TODO: --add/handle rxnorms
//TODO: --add/tighten up jdbc templates to do unique and first
//TODO: --create unit testing, add interfaces for mocking
//TODO: --add data sources to integration tests
//TODO: -- configure an error page/response
//TODO: configure and test external properties
//TODO: --port configuration
//TODO: profile, first hit delay
//TODO: --configure error vs success response: handle exceptions
//TODO: add multithreading to service

@RestController
public class CdsTaskController {
    @Autowired
    CdsTaskExecutor executor;
	private static Logger log = LoggerFactory.getLogger(CdsTaskController.class);
	
	@RequestMapping(value ="/riskyMeds", method = RequestMethod.GET)
	public Advice adviceRiskyMedsWithTasks(@RequestParam("ndcList") String ndcList, @RequestParam(value="rxnormList", required = false) String rxnormList ) throws Exception {
	   log.info("ndcList :" + ndcList + "rxnormList:" + rxnormList);
		Advice advice = new Advice();
		try {
			List<LexMed> lexMeds = executor.execute(parceParam(ndcList), parceParam(rxnormList));
			advice.lexMeds.addAll(lexMeds);
		} catch (Exception exp) {
			advice = handleErrors(exp);
		}

		advice.generateAdvice();
		return advice;
	}
		
	protected Advice handleErrors(Exception e) {
		logException(e);
		Advice advice = new Advice();
		advice.errors = Arrays.asList(new String[]{ ((e.getCause()!=null)?e.getCause().getMessage():e.getMessage())});
		return advice;
	}

	protected void logException(Exception e) {
		log.error("Catastrophic error", e);
	}

	protected List<String> parceParam(String ids) {
		return (ids !=null && !ids.isEmpty())?Arrays.asList(ids.split(";")):new ArrayList<String>(0);
	}
	
}
