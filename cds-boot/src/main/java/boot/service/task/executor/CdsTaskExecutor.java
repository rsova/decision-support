package boot.service.task.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import boot.service.cds.CdsService;
import boot.service.task.CdsCallableTask;
import boot.utils.LexMed;

@Service
public class CdsTaskExecutor {
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private  CdsService service;
	 
	public List<LexMed> execute(List<String> ndcs, List<String>rxnorms) throws Exception{              
		List<Future<LexMed>> futures = new ArrayList<Future<LexMed>>();
		
		// Start concurrent processing 
		for (String ndc : ndcs) {
			CdsCallableTask cdsTask = new CdsCallableTask(service, ndc, null);
			Future<LexMed> future = taskExecutor.submit(cdsTask);
			futures.add(future);
		}
		
		for (String rxnorm : rxnorms) {
			CdsCallableTask cdsTask = new CdsCallableTask(service, null, rxnorm);
			Future<LexMed> future = taskExecutor.submit(cdsTask);
			futures.add(future);
		}

		// Get results when they ready
		List<LexMed> lexMeds = new ArrayList<LexMed>();
	    for ( Future<LexMed> future : futures) {  
	    	  lexMeds.add(future.get()); 
        }
	    
		return lexMeds;
	}

}
