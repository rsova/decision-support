package boot;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.gson.GsonBuilder;

import boot.service.task.executor.CdsTaskExecutor;
import boot.utils.Advice;
import boot.utils.LexMed;

public class CdsTaskControllerTest {
	
	@Test
	public void testErrorsHandler() throws Exception {
		//CdsTaskController
		CdsTaskController controller = new CdsTaskController(){			
			@Override
			protected void logException(Exception e) {
				// do nothing
			}
		};
		controller.executor = new CdsTaskExecutor(){
			@Override
			public List<LexMed> execute(List<String> ndcs, List<String> rxnorms) throws Exception {
						throw new Exception("Bara Bing Bara Boom!");
			}

		};
		
		Advice advice = controller.adviceRiskyMedsWithTasks(null, null);
		//Advice advice = new GsonBuilder().create().fromJson(json, Advice.class);
		assertFalse(advice.success);
		assertFalse(advice.isRiskyMeds);
		assertNotNull(advice.errors);
		assertEquals("Bara Bing Bara Boom!", advice.errors.get(0));
	}

}
