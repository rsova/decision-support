package boot.utils;

import java.util.List;

public class LexMed {
	//Med
	public String  ingredient;
	public String  ndc;
	public String  rxnorm;
	public List<String> classifications = null;

	public LexMed(){
		
	}

	public LexMed(String ingredient, String ndc, String rxnorm, List<String> classifications) {
		this.ingredient = ingredient;
		this.ndc = ndc;
		this.rxnorm = rxnorm;
		this.classifications = classifications;
	}

	@Override
	public String toString() {
		return "LexMed [ingredient=" + ingredient + ","
				+ " ndc=" + ndc
				+ ", rxnorm=" + rxnorm 
				+ ", classifications=" + classifications
				+ "]";
	}
	

}
