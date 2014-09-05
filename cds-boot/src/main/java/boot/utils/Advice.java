package boot.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advice {
	private static final int TWO_OR_MORE = 1;
	public Boolean success = null;
	public Boolean isRiskyMeds = null;
	public List<LexMed> lexMeds = new NullSafeArray<LexMed>();
	public List<String> errors =  null;

	public Advice addLexMed(LexMed med){
		lexMeds.add(med);
		return this;
	}
	
	/* 
	 * Medication history result set contains two or more dispensed medications with two or more distinct SNOMED drug classes.
	 */
	public Advice generateAdvice() {
		boolean isRisky = false;
		if (lexMeds.size() > TWO_OR_MORE) { 
			
			Set<String> set = new HashSet<String>();
			int size = lexMeds.get(0).classifications.size();
			
			for (LexMed lexMed : lexMeds) {
				set.addAll(lexMed.classifications);
				isRisky = (isRisky)||(size != set.size());
				
				if(isRisky){break;}
				
				//keep going
				size = set.size();	
			}
			
			// 2 or more unique classes
			if(set.size() > TWO_OR_MORE){
				isRiskyMeds = isRisky;
			}
		}
		if (errors == null){
			success = true;
		}
		
		return this;
	};
}

@SuppressWarnings("hiding")
class NullSafeArray<Object> extends ArrayList<Object> {
	private static final long serialVersionUID = -3156846792058380170L;

	@Override
	public boolean add(Object e) {
		return (e!=null)?super.add(e):false;
	}
	
		
	@Override
	public boolean addAll(Collection<? extends Object> c) {
		//Remove all null objects
		c.removeAll(Collections.singleton(null)); 
		return super.addAll(c);
	}
}
