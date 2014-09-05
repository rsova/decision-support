package boot.service.mysql.rxnorm;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import boot.utils.LexMed;

@Service
public class RxnormService {
	private static final String NDC_TO_RX_SQL="SELECT distinct rxcui FROM rxnorm.rxnsat where atn='NDC' and atv = ? ";	
	private static final String RX_TO_DRUG_SQL="SELECT str FROM rxnconso where rxnconso.RXCUI in (select distinct rxcui2 from rxnrel where rxcui1 in (SELECT distinct rxcui2 FROM rxnorm.rxnrel where rxcui1 = ?)) and tty = 'IN' and sab = 'RXNORM'";	
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	public LexMed resolveNdcToDrug(String ndc) throws Exception {
    	final String rxNorm = (String)this.jdbcTemplate.queryForObject(NDC_TO_RX_SQL,new Object[]{ndc}, String.class); 
    	LexMed lexMed = resolveRxToDrug(rxNorm);
    	lexMed.ndc = ndc;
    	return lexMed;
    }
	
	public LexMed resolveRxToDrug( String rxNorm) throws Exception {
		final String ingredient = this.jdbcTemplate.queryForList(RX_TO_DRUG_SQL, new Object[]{rxNorm}, String.class).get(0); 
		return new LexMed(ingredient, null, rxNorm, null);
	}

}
