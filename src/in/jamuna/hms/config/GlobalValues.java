package in.jamuna.hms.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalValues {
	
	private GlobalValues() {
		super();
	}

	
	private static final List<String> EXCLUDED_URI=new ArrayList<>(Arrays.asList(
											"/","/login"
											));

	private static final boolean DEVELOPMENT_BUILD=false;
	
	private static final int perPage=20;
	
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
