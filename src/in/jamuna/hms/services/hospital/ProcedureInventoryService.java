package in.jamuna.hms.services.hospital;

import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.hospital.ProcedureStockDAO;
import in.jamuna.hms.dao.hospital.ProceduresDAO;
import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.ProcedureStockEntity;


@Service
public class ProcedureInventoryService {
	
	@Autowired
	ProcedureStockDAO procedureStockDAO;

	@Autowired
	ProceduresDAO proceduresDAO;
	
	private static final Logger LOGGER=Logger.getLogger(ProcedureInventoryService.class.getName());
	
	@Transactional
	public long addStock(int id, int qty) {
		long batchId=0;
		try {
			batchId=procedureStockDAO.addStock( proceduresDAO.findById(id), qty );
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		return batchId;
	}

	@Transactional
	public void deductFromStock(ProcedureRatesEntity procedure, int qty) {
		//check count of stocks where proc=procedure & qty>0
		long count=0;
		try {
			count=procedureStockDAO.countOfStockByProcedureAndQty(procedure,GlobalValues.getProcedureInvSearchQty());
			LOGGER.info("count:"+count);
			//if count ==0 create new stockEntity and set=-1
			if(count==0) {
				procedureStockDAO.addStock(procedure, -1);
			}
			else {
				 LOGGER.info("here 49");
				procedureStockDAO.getSingleAndDeductByQtyByProcedureAndQty(procedure,GlobalValues.getProcedureInvSearchQty());
				
			}
		}catch(Exception e) {
			LOGGER.info(e.toString());
		}
		
		
		
	}
	
}
