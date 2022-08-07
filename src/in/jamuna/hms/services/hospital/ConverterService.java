package in.jamuna.hms.services.hospital;

import in.jamuna.hms.dto.cart.BillDTO;
import in.jamuna.hms.dto.reports.MiniTestStockDTO;
import in.jamuna.hms.dto.reports.TestDTO;
import in.jamuna.hms.dto.cart.CartItemDTO;
import in.jamuna.hms.dto.common.CommonIdAndNameDto;
import in.jamuna.hms.dto.patient.PatientDTO;
import in.jamuna.hms.entities.hospital.billing.*;
import in.jamuna.hms.entities.hospital.stock.ProcedureProductMappingEntity;
import in.jamuna.hms.entities.hospital.stock.TestStockEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConverterService {
	private static final Logger LOGGER=Logger.getLogger(ConverterService.class.getName());
	final
	ModelMapper mapper;
	final
	PatientService patientService;

	public ConverterService(ModelMapper mapper, PatientService patientService) {
		this.mapper = mapper;
		this.patientService = patientService;
	}

	public CommonIdAndNameDto convert(VisitTypeEntity visit) {
		return new CommonIdAndNameDto(visit.getId(),visit.getVisit());
	}
	
	public BillDTO convert(VisitBillEntity bill) {
		// int tid, String doctor, String patient, String guardian, Date billingDate, BillDTO refund,
	//	int fees
		BillDTO dto;
		LOGGER.info("22");
		if(bill.getRefundBill() != null) {
			dto = new BillDTO(bill.getTid(),bill.getDoctor().getName(),bill.getPatient().getFname()+" "+bill.getPatient().getLname()
					,bill.getPatient().getGuardian(),bill.getBillingDate(),convert(bill.getRefundBill()),bill.getFees());
			
			
		}
		else {
			dto = new BillDTO(bill.getTid(),bill.getDoctor().getName(),bill.getPatient().getFname()+" "+bill.getPatient().getLname()
					,bill.getPatient().getGuardian(),bill.getBillingDate(),bill.getFees());
		
		}
		PatientDTO p = mapper.map( bill.getPatient() , PatientDTO.class);
		p.setAge( p.getAge() + patientService.addYearsToAge(bill.getPatient()) );
		dto.setPatientDTO(
				p
				);
		
			return dto;
	}

	public BillDTO convert(ProcedureBillEntity bill) {
		
		PatientDTO p = mapper.map( bill.getPatient() , PatientDTO.class);
		p.setAge( p.getAge() + patientService.addYearsToAge(bill.getPatient()) );
		
		if(bill.getRefundBill() != null) {
			return new BillDTO(bill.getTid(),p,bill.getDoctor().getName(), 
					bill.getBillItems().stream().map( item -> 
					{
						CartItemDTO dto = new CartItemDTO();
						
						dto.setName(item.getProcedure().getProcedure());
						dto.setRate(item.getRate());
						dto.setId(item.getId());
						return dto;
					
					}
							).collect(Collectors.toList()),
					bill.getPatient().getFname()+" "+bill.getPatient().getLname(),
					bill.getPatient().getGuardian(),
					bill.getDate(), convert(bill.getRefundBill()) , bill.getTotal());
		}else {
			return new BillDTO(bill.getTid(),p,bill.getDoctor().getName(), 
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
					bill.getDate(), null ,bill.getTotal());
		}
		
		
	}

	public CommonIdAndNameDto convert(BillGroupsEntity group) {
		// TODO Auto-generated method stub
		return new CommonIdAndNameDto(group.getId(), group.getName(),group.isEnabled());
	}

	public TestDTO convert(ProcedureRatesEntity item) {
		TestDTO dto = new TestDTO();
		dto.setId(item.getId());
		dto.setName(item.getProcedure());
		if(item.getCategory()!=null)
			dto.setCategory(new CommonIdAndNameDto(item.getCategory().getId(),item.getCategory().getName()));
		return dto;
	}

    public CartItemDTO convert(ProcedureProductMappingEntity m) {
		CartItemDTO dto = new CartItemDTO();
		dto.setName(m.getProduct().getName());
		dto.setId(m.getId());
		dto.setRate(m.getRatio());

		return dto;
    }

	public Date convert(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//surround below line with try catch block as below code throws checked exception
		return sdf.parse(date);
	}

    public MiniTestStockDTO convert(TestStockEntity m) {
		MiniTestStockDTO dto = new MiniTestStockDTO();
		dto.setBatch(m.getBatch());
		dto.setExpiry(m.getExpiry());
		dto.setFree(m.getFree());
		dto.setQty(m.getQty());
		dto.setQtyLeft(m.getQtyLeft());dto.setId(m.getId());
		return dto;
    }
}
