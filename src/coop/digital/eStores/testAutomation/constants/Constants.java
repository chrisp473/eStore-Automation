package coop.digital.eStores.testAutomation.constants;

import java.io.File;

public class Constants {

	public static final int PAGE_SYNC_DEFAULT_TIMEOUT = 60;

	public static final int OBJECT_SYNC_DEFAULT_TIMEOUT = 20;
	
	public static final int IE_OBJECT_SYNC_DEFAULT_TIMEOUT = 30;
	
	public static final int PAGE_URL_WAIT_TIMEOUT = 10;
	
	public static final String XML_LOG_FILE_LOCATION = File.separator+"log.xml";

	public static final String XML_lOG_TEMPLATE_LOCATION = File.separator+"ProjectResources"+File.separator+"Templates"+File.separator+"log.xml";
	
	public static final String XML_TESTDATA_TEMPLATE_LOCATION = File.separator+"ProjectResources"+File.separator+"Templates"+File.separator+"TestData.xml";
	
	public static final String INITIAL_SET_UP_FILE_PREFIX = "InitialSetup-";
	
	public static final String TESTRESULTS_LOCATION  = File.separator+"Results"+File.separator+"TestResults.xml";
	
	public static final String TESTRESULT_TEMPLATE_LOCATION = File.separator+"ProjectResources"+File.separator+"Templates"+File.separator+"TestResults.xml";

	public static final String BLANK_VALUE = "";
	
	public static final String SUCCESS = "SUCCESS";

	public static final String FAILURE = "FAILURE";
}
