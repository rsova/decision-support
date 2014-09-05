package boot.service.task;

import java.util.concurrent.Callable;

import boot.service.cds.CdsService;
import boot.utils.LexMed;

public class CdsCallableTask implements Callable<LexMed> {
    protected  CdsService service;
    protected String ndc;
    protected String rxNorm;
    
	
	public CdsCallableTask(CdsService service, String ndc, String rxNorm) {
		this.service = service;
		this.ndc = ndc;
		this.rxNorm = rxNorm;
	}

	@Override
	public LexMed call() throws Exception{
		LexMed rm =  null;
		try {
			rm = (ndc!=null)?service.resolveNdcToRiskyMed(ndc):service.resolveRxnormToRiskyMed(rxNorm);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Terminology service failed processing this id: " + ((ndc!=null)?ndc+"(ndc)":rxNorm+"(rxNorm)"));
		}
		return rm;
	}
	
}
