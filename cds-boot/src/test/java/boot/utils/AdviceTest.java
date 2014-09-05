package boot.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class AdviceTest {
	
	@Test
	public void testAdviceTrue() {
		Advice advice = new Advice();
		
		LexMed lexMed = new LexMed("Temazepam","","", Arrays.asList(new String [] {"Anxiolytic", "Hypnotic_AND/OR_sedative"}));
		advice.lexMeds.add(lexMed);
		lexMed = new LexMed("Diazepam","","", Arrays.asList(new String [] {"Antidepressant"}));
		advice.lexMeds.add(lexMed);
		advice.generateAdvice();
		assertTrue(advice.isRiskyMeds);
		//System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(advice));

	}
	
	@Test
	public void testHandleNulls() throws Exception {
		
		Advice advice = new Advice();
		LexMed lexMed = new LexMed("Temazepam","","", Arrays.asList(new String [] {"Anxiolytic", "Hypnotic_AND/OR_sedative"}));
		advice.lexMeds.add(lexMed);
		assertEquals(1, advice.lexMeds.size());
		advice.lexMeds.add(null);
		assertEquals(1, advice.lexMeds.size());
		advice.generateAdvice();
		assertFalse(advice.isRiskyMeds);
	}
	
	@Test
	public void testAdviceTrue_Multiple() {
		Advice advice = new Advice();
		
		LexMed lexMed = new LexMed("Temazepam","","", Arrays.asList(new String [] {"Anxiolytic", "Hypnotic_AND/OR_sedative"}));
		advice.lexMeds.add(lexMed);
		lexMed = new LexMed("Diazepam","","", Arrays.asList(new String [] {"Antidepressant"}));
		advice.lexMeds.add(lexMed);
		lexMed = new LexMed("Diazepam","","", Arrays.asList(new String [] {"Antidepressant"}));
		advice.lexMeds.add(lexMed);
		advice.generateAdvice();
		assertTrue(advice.isRiskyMeds);
		//log.info(new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(advice));
		
	}
	@Test
	public void testAdviceFalse_OneDrug() {
		Advice advice = new Advice();
		
		LexMed lexMed = new LexMed("Temazepam","","", Arrays.asList(new String [] {"Anxiolytic", "Hypnotic_AND/OR_sedative"}));
		advice.lexMeds.add(lexMed);
		
		//log.info(new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(advice));
		assertFalse(advice.isRiskyMeds);

	}
	
	@Test
	public void testAdviceFalse_SameGroup() {
		Advice advice = new Advice();
		
		LexMed lexMed = new LexMed("Temazepam","","", Arrays.asList(new String [] {"Anxiolytic", "Hypnotic_AND/OR_sedative"}));
		advice.lexMeds.add(lexMed);
		lexMed = new LexMed("Triazolam","","", Arrays.asList(new String [] {"Anxiolytic", "Hypnotic_AND/OR_sedative"}));
		advice.lexMeds.add(lexMed);
		advice.generateAdvice();
		
		//log.info(new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(advice));
		assertFalse(advice.isRiskyMeds);
		
	}

	@Test
	public void testAdviceFalse_SuccessFalse1() {
		Advice advice = new Advice();
		
		LexMed lexMed = new LexMed("Temazepam","","", Arrays.asList(new String [] {"Anxiolytic", "Hypnotic_AND/OR_sedative"}));
		advice.errors = Arrays.asList(new String[]{"Boom"});
		advice.lexMeds.add(lexMed);

		advice.generateAdvice();
		
		//log.info(new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(advice));
		assertFalse(advice.isRiskyMeds);
		assertFalse(advice.success);
		
	}
	
	@Test
	public void testAdviceFalse_SuccessFalse2() {
		Advice advice = new Advice();
		advice.errors = Arrays.asList(new String[]{"Boom"});

		advice.generateAdvice();
		
		//System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(advice));
		assertFalse(advice.isRiskyMeds);
		assertFalse(advice.success);
	}

}
