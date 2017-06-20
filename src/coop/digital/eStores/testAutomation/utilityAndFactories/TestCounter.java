package coop.digital.eStores.testAutomation.utilityAndFactories;

public class TestCounter {

	private static int CurrentTestCount;
	private static int TotalTestCount;
	public TestCounter() 
	{
		CurrentTestCount = 0 ;
		TotalTestCount = 0; 
	}
	
	public static void intcrementCount()
	{
		CurrentTestCount++;
	}
	
	public static void setTotalTestCases(int TotalTests)
	{
		TotalTestCount = TotalTests;
	}
	public static int getCount()
	{
		return CurrentTestCount;
	}

	public static int getTotalTestCases()
	{
		return TotalTestCount;
	}
	//Determines if there is more than one test case. 
	public static boolean multipleTestCases()
	{
		if (TotalTestCount > 1)
		{
			return true;
		} else
		{
			return false;
		}
		
	}
}
