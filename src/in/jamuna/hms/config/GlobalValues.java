package in.jamuna.hms.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalValues {
	
	private GlobalValues() {
		super();
	}
	
	private static final List<String> EXCLUDED_URI=new ArrayList<>(Arrays.asList(
											"/","/authenticate",
											"/resources/bootstrap.min.css",
											"/resources/bootstrap.min.js",
											"/resources/jquery.min.js",
											"/resources/popper.min.js"
											));

	private static final boolean DEVELOPMENT_BUILD=false;
	
	private static final int perPage=20;
	
	private static final int searchLimit=50;
	
	private static final String adminHomePage="redirect:common/bills/visit-bills-page";
	private static final String receptionistHomePage="redirect:/receptionist/add-patient-form";

	private static final boolean allowValidityForRefundedPatient=false;
	private static final int minimumRate=50;
	

	//////////////// slip/bill config ////////////////////////
	private static final String heading="Jamuna Memorial Hospital";
	private static final String subHeader="Pilibhit Road, Khatima 262308, Uttarakhand."
			+ " Contact No- 05943-251227, 8057107960";
	
	private static final String footer="<b>Services:</b>Echocardiography,"
			+ "Color Doppler, Electroencephalography, ABG, Nebulizer, "
			+ "ICU, Cesarean, Delivery and Surgery for all obstetric diseases,"
			+ " Colposcopy, Laproscopic surgery, Physiotheray, Private Rooms.<br>\r\n"
			+ "<b>Note</b> : 1- Bring back old prescription slips."
			+ " 2- Do not consume medicines without doctor's advice."
			+ "  3- Holiday on Sunday. 4- Consultation time: 10.00 AM To 2.00 PM."
			+ " 5- This slip is valid for 7 days.";
	
	////////////////////////////////////////////////////////
	
	
	public static String getFooter() {
		return footer;
	}

	

	public static String getSubheader() {
		return subHeader;
	}



	public static int getMinimumrate() {
		return minimumRate;
	}




	public static String getHeading() {
		return heading;
	}



	public static boolean isAllowvalidityforrefundedpatient() {
		return allowValidityForRefundedPatient;
	}



	public static String getAdminhomepage() {
		return adminHomePage;
	}



	public static String getReceptionisthomepage() {
		return receptionistHomePage;
	}



	public static int getSearchlimit() {
		return searchLimit;
	}


	private static final String[] sexes= {"MALE","FEMALE","TRANSGENDER"}; 
	
	
	public static String[] getSexes() {
		return sexes;
	}



	public static int getPerpage() {
		return perPage;
	}



	public static List<String> getExcludedUri() {
		return EXCLUDED_URI;
	}



	public static boolean isDevelopmentBuild() {
		return DEVELOPMENT_BUILD;
	}
	
	
}
