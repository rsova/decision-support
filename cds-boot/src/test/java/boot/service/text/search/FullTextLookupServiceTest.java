package boot.service.text.search;

import static org.junit.Assert.*;

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

public class FullTextLookupServiceTest {
	@Resource(name = "stardogDataSource")
	DataSource stardogDataSource = null;
	
	FullTextLookupService service = null;
	
	@Before
	public void setup() {
		service = new FullTextLookupService();
		service.setStardogDataSource(stardogDataSource);
	}
	
	@Test
	public void testLookupByDrugFullName() throws Exception {
		assertEquals("http://www.ihtsdo.org/snomedct.owl#Oxycodone",service.lookupByDrugFullName("Oxycodone"));
	}
}
