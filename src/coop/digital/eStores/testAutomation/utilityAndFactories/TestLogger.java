package coop.digital.eStores.testAutomation.utilityAndFactories;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import coop.digital.eStores.testAutomation.helpers.TestHelper;
import coop.digital.eStores.testAutomation.utilityAndFactories.XMLTestLogger;

public class TestLogger {

	private static Logger logger;
	private static FileHandler fileHandler;
	private static XMLTestLogger htmlLogger;
	private static boolean enableHTMLLog = false;

	public static void logFail(String message){
		logger.severe(message);
		if(enableHTMLLog){
			htmlLogger.addFailStep(message);
		}
	}
	
	public static void logException(String message)
	{
		logger.warning(message);
		
		//--------------- Create HTMLLogger object
		if(enableHTMLLog && htmlLogger!=null){
			htmlLogger.addWarningStep(message);
		}
		//--------------- Create HTMLLogger object
	}

	public static void logTestEnd()
	{
		logger.info("END OF TEST");
		killHandler();
	}

	public static void logTestStart() throws Exception
	{
		if (logger == null)
		{
			logger = Logger.getLogger("logger");
		}
		
		initialiseHandler();
		logger.info("START OF TEST");
	}

	public static void logTestStep(int stepNumber, String message)
	{
		String logMessage = String.format("STEP %d ~ %s", stepNumber, message);
		logger.info(logMessage);
		
		//--------------- Create HTMLLogger object 	
		if(enableHTMLLog  && htmlLogger!=null){
		htmlLogger.addPassStep(message);
		}
		//--------------- Create HTMLLogger object 	
	}

	public static void initialiseHandler() throws Exception
	{
		String logFilePath = TestHelper.getTestCaseResultsDirectory();
		logFilePath += "/log.txt";
		
		TestLogFormatter formatter = new TestLogFormatter();
		
		fileHandler = new FileHandler(logFilePath, 1024 * 1024, 10, true);
		fileHandler.setFormatter(formatter);
		
		logger.addHandler(fileHandler);
		
		//--------------- Create HTMLLogger object 
		enableHTMLLog = true; //this line is temporary, just to let log.xml file to get created for every user and every test, remove it later on
		if(System.getProperty("EnableHTMLLog")!=null){
			if(System.getProperty("EnableHTMLLog").equalsIgnoreCase("YES") ||System.getProperty("EnableHTMLLog").equalsIgnoreCase("Y") ){
				enableHTMLLog = true;
			}
		}
		
		if(enableHTMLLog){
		htmlLogger = new XMLTestLogger();
		htmlLogger.setResultFilePath(TestHelper.getTestCaseResultsDirectory() + "/log.xml");
		htmlLogger.createLogTemplate();
		}
		//--------------- Create HTMLLogger object
		
	}

	private static void killHandler()
	{
		fileHandler.close();
		logger.removeHandler(fileHandler);
	}

	private static class TestLogFormatter extends Formatter
	{
		public TestLogFormatter()
		{
			super();
		}

		public String format(LogRecord record)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			return String.format("%s %s %s\n", dateFormat.format(new Date()), record.getLevel().getName(), formatMessage(record));
		}
	}
	
	public static void completeHTMLReport(){
		if(enableHTMLLog  && htmlLogger!=null){
			htmlLogger.completeHTMLReport();
		}		
	}
	
	public static void logScreenShotInHTMLReport(String scrnFileName){
		if(enableHTMLLog  && htmlLogger!=null){
			htmlLogger.addScreenShot(scrnFileName);
		}
	}
	
	public static void clearHTMLLogger(){
		htmlLogger = null;
	}
}
