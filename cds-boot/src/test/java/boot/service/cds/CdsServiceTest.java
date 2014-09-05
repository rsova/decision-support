package boot.service.cds;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import boot.Application;
import boot.service.mysql.rxnorm.RxnormService;
import boot.service.owl.reasoning.SwtReasonerService;
import boot.service.text.search.FullTextLookupService;
import boot.utils.Terms;

import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import com.complexible.stardog.api.search.SearchConnection;
import com.complexible.stardog.ext.spring.DataSource;
import com.complexible.stardog.ext.spring.DataSourceFactoryBean;
import com.complexible.stardog.reasoning.api.ReasoningType;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.runner.RunWith;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
//@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
//@IntegrationTest
public class CdsServiceTest {
	@Resource(name = "stardogDataSource")
	DataSource stardogDataSource = null;
	
	@Autowired
	javax.sql.DataSource dataSource = null;
	
	@Resource(name = "drugClassConcepts")
	Map<String,String> drugClassConcepts = null;

	CdsService service = null;
	
//	@BeforeClass
	@Before
	public  void setup() throws Exception {
//		DriverManagerDataSource dmds = new DriverManagerDataSource();
//		dmds.setDriverClassName("com.mysql.jdbc.Driver");
//		dmds.setUrl("jdbc:mysql://qlap-eleu24.dayton.qbase.us:3306/rxnorm");
//		dmds.setUrl("jdbc:mysql://web07.rtop02.localdomain:3306/rxnorm");
//		dmds.setUrl("jdbc:mysql://192.168.0.7:3306/rxnorm");
//		dmds.setUsername("user");
//		dmds.setPassword("p!ssw0rd");
//		dmds.setPassword("password");
	    
		service = new CdsService();
		service.rxnormService = new RxnormService();
		service.rxnormService.init(dataSource);
		
		service.fullTextLookupService = new FullTextLookupService();
		service.fullTextLookupService.setStardogDataSource(stardogDataSource);

		service.swtReasonerService = new SwtReasonerService();
		service.swtReasonerService.setStardogDataSource(stardogDataSource);
		service.swtReasonerService.setConcepts(drugClassConcepts);
		
	}

	@Test
	public void testHypnotic() throws Exception {
		System.out.println("---------------------Hypnotic-----------------------");

		String ndc = null;
		ndc = "54868077800"; //Temazepam 
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Hypnotic And Or Sedative"));//Hypnotics
		
		ndc = "54868082801"; //Halcion (Triazolam)
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Hypnotic And Or Sedative"));//Hypnotics
		ndc = "35356057430"; //Sonata (Zaleplon)  
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Hypnotic And Or Sedative"));//Hipnotics
		ndc = "59547031019"; //Doral (Quazepam) 
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Hypnotic And Or Sedative"));//Hypnotics
		
		ndc = "00024540131"; //Ambien (zolpidem)
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Hypnotic And Or Sedative")); //Sedative
		ndc = "35356009230"; //Roserum (Ramelteon) 
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Hypnotic And Or Sedative")); //Sedative
				
	}
	
	@Test
		public void testAnalgesic() throws Exception {
		System.out.println("---------------------Analgesic-----------------------");
		
		String ndc = null;
		//ndc = "16590088460"; //MS+Contin (Morphin)
		ndc = "35356033500"; //MS+Contin (Morphin) -- 892574
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Analgesic"));
		//assertFalse(true);
		ndc = "16590069060"; //OxyContin (Oxycodone)
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Analgesic"));
		ndc = "63481062770"; //Percocet (Oxycodone + aetaminophen)
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Analgesic"));
		ndc = "00054368663"; //Roxicet (Oxycodone / acetaminophen-roxicet)
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Analgesic"));
//		ndc = "00045052660"; //Tylox
//		ndc = "00045052660"; //Percodan
		ndc = "54868513700"; //Dilaudid (Hydromorphone)
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Analgesic"));
		ndc = "50458051570"; //Codeine (Acetaminophen 300 MG / Codeine Phosphate 60 MG Oral Tablet [Tylenol with Codeine])
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Analgesic"));
		ndc = "42747022304"; //Fentanyl Sublingual Tablet (Fentanyl 0.3 MG Sublingual Tablet [Abstral])
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Analgesic"));
		}
	
	@Test
	public void testAntidepressant() throws Exception {
		System.out.println("---------------------Antidepressant-----------------------");
		
		String ndc = null;
		ndc = "52959066530"; //Prozac (Fluoxetine)
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Antidepressant"));
//		ndc = "00247078728"; //Paxil (Paroxetine 20 MG Oral Tablet [Paxil]) -- Wrong classification : Anxiolytic
//		assertTrue(boot.service.isDrugInRiskyPtsdDrugClassConcept(ndc));
		ndc = "00029321113"; //Paxil (Paroxetine 20 MG Oral Tablet [Paxil]) -- Wrong classification : Anxiolytic
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Antidepressant"));
		ndc = "00068002001"; //Norpramin (Desipramine Hydrochloride 100 MG Oral Tablet [Norpramin])
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Antidepressant"));
		ndc = "00049490066"; //Zoloft (Sertraline)- (Sertraline 50 MG Oral Tablet [Zoloft])
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Antidepressant"));
		ndc = "00173094755"; //Wellbutrin (Bupropion) -12 HR Bupropion Hydrochloride 100 MG Extended Release Tablet [Wellbutrin]
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Antidepressant"));
		ndc = "51285059502"; //Vivactil - Protriptyline Hydrochloride 5 MG Oral Tablet [Vivactil]
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Antidepressant"));
		ndc = "52959035020"; //Desyrel (Trazodone) -Trazodone Hydrochloride 50 MG Oral Tablet [Desyrel]
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Antidepressant"));
		ndc = "00406992303"; //Imipramine - Imipramine pamoate 75 MG Oral Capsule [Tofranil-PM]
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Antidepressant"));
		ndc = "00456210108"; //Lexapro (Escitalopram) - Escitalopram 1 MG/ML Oral Solution [Lexapro] -- Wrong classification : Anxiolytic
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Antidepressant"));
		ndc = "42847010303"; //Silenor (Doxepin) - Doxepin 3 MG Oral Tablet [Silenor] -- Wrong classification : Anxiolytic
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Antidepressant"));
		ndc = "00002323533"; //Cymbalta (Duloxetine) - Duloxetine 20 MG Delayed Release Oral Capsule [Cymbalta]  -- Wrong classification : Anxiolytic
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Antidepressant"));
		//ndc = ""; //Elavil (Amitriptyline) not in rxNorm !!!!!!!
		//ndc = "00591571630"; //Asendin (Amoxapine)
		ndc = "00591571630"; //Asendin (Amoxapine) not in rxnorm, instead  - Amoxapine 150 MG Oral Tablet
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Antidepressant"));
	}
	
	@Test
	public void tesAnxiolytic() throws Exception {
		System.out.println("---------------------Anxiolytic-----------------------");

		String ndc = null;
		ndc = "00140000401"; //Valium (Diazepam) -- Diazepam 2 MG Oral Tablet [Valium]
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Anxiolytic"));
		ndc = "00009005501"; //Xanax (Alprazolam)(Niravam) -- Alprazolam 0.5 MG Oral Tablet [Xanax]
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Anxiolytic"));
		ndc = "55292030301"; //Tranxene (Clorazepate) -- Clorazepate Dipotassium 15 MG Oral Tablet [Tranxene]
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Anxiolytic"));
		ndc = "00187375810"; //Librium (Chlordiazepoxide) -- Chlordiazepoxide Hydrochloride 25 MG Oral Capsule [Librium]
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Anxiolytic"));
		ndc = "00004005801"; //Klonopin (Clonazepam) Panic Disorder) -- Clonazepam 1 MG Oral Tablet [Klonopin]
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Anxiolytic"));
		ndc = "00172480560"; //Serax (Oxazepam) not in rxnorm, instead - Oxazepam 15 MG Oral Capsule
		assertTrue(service.resolveNdcToRiskyMed(ndc).classifications.contains("Anxiolytic"));
	}
	
	@Test
	public void testNoRisky() throws Exception {
		System.out.println("---------------------No Risk-----------------------");

		String ndc = null;
		ndc = "00009364203"; // Dimenhydrinate 50 MG Oral Tablet [Dramamine];  rxNorm 201716
		assertNull(service.resolveNdcToRiskyMed(ndc));
		ndc = "00363055707"; // 24 HR Fexofenadine hydrochloride 180 MG / Pseudoephedrine Hydrochloride; rxNorm 1190334
		assertNull(service.resolveNdcToRiskyMed(ndc));
		ndc = "61958080201"; // Ambrisentan 10 MG Oral Tablet [Letairis]; rxNorm 722120
		assertNull(service.resolveNdcToRiskyMed(ndc));
	}
		
//    public static ConnectionConfiguration intSearchConnectionConfiguration() {
//		return ConnectionConfiguration.
//				to("snomed").
//				server("http://localhost:5820").
////				server("http://web07.rtop02.localdomain:5820").
//				credentials("admin","admin");
//    }
//    
//    public static ConnectionConfiguration initOwlConnectionConfiguration() {
//    	return ConnectionConfiguration.
//				to("snomed").
//				server("http://localhost:5820").
////				to("snomed").
//				//server("http://web07.rtop02.localdomain:5820").
//				credentials("admin","admin").
//    			reasoning(ReasoningType.QL);
//    }
    
//	private static Map<String, String> createMap() {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put(Terms.ANALGESIC_CONCEPT, Terms.ANALGESIC_LBL);
//		map.put(Terms.HYPNOTIC_SEDATIVE_CONCEPT, Terms.HYPNOTIC_SEDATIVE_LBL);
//		map.put(Terms.ANTIDEPRESSANT_CONCEPT, Terms.ANTIDEPRESSANT_LBL);
//		map.put(Terms.ANXIOLYTIC_CONCEPT, Terms.ANXIOLYTIC_LBL);
//		return map;
//	}


}
