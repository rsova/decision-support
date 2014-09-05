package boot.service.cds;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import boot.service.mysql.rxnorm.RxnormService;
import boot.service.owl.reasoning.SwtReasonerService;
import boot.service.text.search.FullTextLookupService;
import boot.utils.Advice;
import boot.utils.LexMed;

@Service
public class CdsService {
	@Autowired
	protected RxnormService rxnormService = null;
	
	@Autowired
	protected FullTextLookupService fullTextLookupService = null;
	
	@Autowired
	protected SwtReasonerService swtReasonerService = null;
	
	private static Logger log = LoggerFactory.getLogger(CdsService.class);
	
	public  LexMed resolveNdcToRiskyMed(String ndc) throws Exception {
		LexMed lexMed = rxnormService.resolveNdcToDrug(ndc);
		resolveRiskyMedToClassifications(lexMed);
		return (lexMed.classifications == null || lexMed.classifications.isEmpty())?null:lexMed;
	}
	
	public  LexMed resolveRxnormToRiskyMed(String rxNorm) throws Exception {
		LexMed lexMed = rxnormService.resolveRxToDrug(rxNorm);
		resolveRiskyMedToClassifications(lexMed);
		return (lexMed.classifications==null||lexMed.classifications.isEmpty())?null:lexMed;
	}
	
	protected void resolveRiskyMedToClassifications(LexMed lexMed) throws Exception {
		String drugConceptUri = fullTextLookupService.lookupByDrugFullName(lexMed.ingredient);
		List<String> riskyDrugClasses = swtReasonerService.matchToRiskyDrugConcepts(drugConceptUri);
		lexMed.classifications = riskyDrugClasses;
		log.info(lexMed.toString());
	}
	
	public Advice lookupClassesForDrugName(String drugName) throws Exception{
		LexMed lexMed = new LexMed();
		lexMed.ingredient = drugName;
		String drugConceptUri = fullTextLookupService.lookupByDrugFullName(drugName);
		List<String> riskyDrugClasses = swtReasonerService.lookUpDrugConcepts(drugConceptUri);
		lexMed.classifications = riskyDrugClasses;
		return new Advice().addLexMed(lexMed) ;
	}
	
}
