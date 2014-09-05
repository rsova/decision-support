package boot.service.mysql.rxnorm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import boot.Application;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RxnormServiceTest {
	
	@Autowired
	javax.sql.DataSource dataSource = null;
	
	@Test
	public void testOxazepam() throws Exception {
		RxnormService service = new RxnormService();
		service.init(dataSource);
		assertEquals("Oxazepam", service.resolveNdcToDrug("00172480560").ingredient);
	}

}
