package in.jamuna.hms.dto.reports;

public class VisitReportDTO {
	
	private long opds;
	private long emergencies;
	private long followUps;
	public long getOpds() {
		return opds;
	}
	public void setOpds(long opds) {
		this.opds = opds;
	}
	public long getEmergencies() {
		return emergencies;
	}
	public void setEmergencies(long emergencies) {
		this.emergencies = emergencies;
	}
	public long getFollowUps() {
		return followUps;
	}
	public void setFollowUps(long followUps) {
		this.followUps = followUps;
	}
		
}
