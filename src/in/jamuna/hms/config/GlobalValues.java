package in.jamuna.hms.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalValues {
	
	private GlobalValues() {
		super();
	}

	
	private static final List<String> EXCLUDED_URI=new ArrayList<>(Arrays.asList(
											"/","/authenticate"
											));

	private static final boolean DEVELOPMENT_BUILD=false;
	
	private static final int perPage=20;
	
	private static final int searchLimit=15;
	
	private static final String adminHomePage="redirect:/manager/dashboard";
	private static final String receptionistHomePage="redirect:/receptionist/add-patient-form";

	private static final boolean allowValidityForRefundedPatient=false;
	private static final int minimumRate=50;
	
	
	//////////////// slip/bill config ////////////////////////
	private static final String heading="Jamuna Memorial Hospital";
	private static final String subHeader="xxxxxxxxxxxxxxxxxxxxxxxxx";
	private static final String footer="";
	
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
