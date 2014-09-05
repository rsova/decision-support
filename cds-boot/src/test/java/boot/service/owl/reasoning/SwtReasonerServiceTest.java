package boot.service.owl.reasoning;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import boot.Application;

import com.complexible.stardog.ext.spring.DataSource;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SwtReasonerServiceTest {
	@Resource(name = "stardogDataSource")
	DataSource stardogDataSource = null;
	
	@Resource(name = "drugClassConcepts")
	Map<String,String> drugClassConcepts = null;

	private SwtReasonerService service;
	
	@Before
	public void setup() {
		service = new SwtReasonerService();
		service.setConcepts(drugClassConcepts);
		service.setStardogDataSource(stardogDataSource);
	}
	
	@Test
	public void testAnalgesic() throws Exception {
			assertEquals("Analgesic",service.matchToRiskyDrugConcepts("http://www.ihtsdo.org/snomedct.owl#Oxycodone").get(0));
		}
	@Test
	public void testAnalgesicClasses() throws Exception {
		List<String> results = service.lookUpDrugConcepts("http://www.ihtsdo.org/snomedct.owl#Oxycodone");
		System.out.println(results.toString());

	}
	
	@Test
	public void testLookup() throws Exception {
		@SuppressWarnings("unused")
		List<String> riskyDrugClasses = service.lookUpDrugConcepts("http://www.ihtsdo.org/snomedct.owl#Oxycodone");
		assertNotNull(riskyDrugClasses);
	}

}
