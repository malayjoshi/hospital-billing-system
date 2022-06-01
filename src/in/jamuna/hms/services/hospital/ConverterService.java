package in.jamuna.hms.services.hospital;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.jamuna.hms.dto.BillDTO;
import in.jamuna.hms.dto.cart.CartItemDTO;
import in.jamuna.hms.dto.common.CommonIdAndNameDto;
import in.jamuna.hms.entities.hospital.ProcedureBillEntity;
import in.jamuna.hms.entities.hospital.VisitBillEntity;
import in.jamuna.hms.entities.hospital.VisitTypeEntity;

@Service
public class ConverterService {
	private static Logger LOGGER=Logger.getLogger(ConverterService.class.getName());
	@Autowired
	ModelMapper mapper;
	
	public CommonIdAndNameDto convert(VisitTypeEntity visit) {
		return new CommonIdAndNameDto(visit.getId(),visit.getVisit());
	}
	
	public BillDTO convert(VisitBillEntity bill) {
		// int tid, String doctor, String patient, String guardian, Date billingDate, BillDTO refund,
	//	int fees
		LOGGER.info("22");
		if(bill.getRefund() != null)
			return new BillDTO(bill.getTid(),bill.getDoctor().getName(),bill.getPatient().getFname()+" "+bill.getPatient().getLname()
				,bill.getPatient().getGuardian(),bill.getBillingDate(),convert(bill.getRefund()),bill.getFees());
		return new BillDTO(bill.getTid(),bill.getDoctor().getName(),bill.getPatient().getFname()+" "+bill.getPatient().getLname()
				,bill.getPatient().getGuardian(),bill.getBillingDate(),bill.getFees());
	}

	public BillDTO convert(ProcedureBillEntity bill) {
		
		if(bill.getRefund() != null) {
			return new BillDTO(bill.getTid(),bill.getDoctor().getName(), 
					bill.getBillItems().stream().map( item -> mapper.map(item, CartItemDTO.class)).collect(Collectors.toList()),
					bill.getPatient().getFname()+" "+bill.getPatient().getLname(),
					bill.getPatient().getGuardian(),
					bill.getDate(), convert(bill.getRefund()) , bill.getTotal());
		}else {
			return new BillDTO(bill.getTid(),bill.getDoctor().getName(), 
					bill.getBillItems().stream().map( item -> 
					{CartItemDTO dto = new CartItemDTO();
					
						dto.setName(item.getProcedure().getProcedure());
						dto.setRate(item.getRate());
						dto.setId(item.getId());
						return dto;
					})
					.collect(Collectors.toList()) ,
					bill.getPatient().getFname()+" "+bill.getPatient().getLname(),
					bill.getPatient().getGuardian(),
					bill.getDate(),  bill.getTotal());
		}
		
		
	}
	
}
