package in.jamuna.hms.dto.reports;

import in.jamuna.hms.entities.hospital.ProcedureRatesEntity;

public class BillGroupReportItemDTO {
	private ProcedureRatesEntity procedure;
	private long count;
	private int total;
	
	public ProcedureRatesEntity getProcedure() {
		return procedure;
	}
	public void setProcedure(ProcedureRatesEntity procedure) {
		this.procedure = procedure;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}


