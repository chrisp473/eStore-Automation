package coop.digital.eStores.testAutomation.helpers;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import coop.digital.eStores.testAutomation.actionsAndAssertions.CoreActions;
import coop.digital.eStores.testAutomation.actionsAndAssertions.CoreAssertions;
import coop.digital.eStores.testAutomation.constants.ElementTypes;
import coop.digital.eStores.testAutomation.utilityAndFactories.TestLogger;

public class ElementProperties {

	//Key compulsary element properties
		private By ByLocatator;
		private String ColumnName;
		private String Description="NOT_SPECIFIED";
		private ElementTypes ElementType;
		
		//Additional element properites. 
		private String Iframe="";
		private By AdditionalLocator;

		
		//******************
		//***Constructors***
		//******************
		
		/**
		 * @param ByLocatar
		 * @param Description
		 * @param EleType
		 */
		public ElementProperties(By ByLocatar, String Description,
				ElementTypes EleType) {
			this.ByLocatator = ByLocatar;
			this.Description = Description;
			this.ElementType = EleType;
		}
		
		/**
		 * @param ByLocatar
		 * @param Description
		 * @param EleType
		 */
		public ElementProperties(By ByLocatar, String Description,
				ElementTypes EleType,String Iframe) {
			this.ByLocatator = ByLocatar;
			this.Description = Description;
			this.ElementType = EleType;
			this.Iframe 	 = Iframe;
		}
		
		/**
		 * @param ByLocatar
		 * @param Description
		 * @param EleType
		 */
		public ElementProperties(By ByLocatar, String Description,
				ElementTypes EleType, String ColumnName,String Iframe ) {
			this.ByLocatator = ByLocatar;
			this.Description = Description;
			this.ElementType = EleType;
			this.ColumnName  = ColumnName;
			this.Iframe 	 = Iframe;
		}

		/**
		 * @param ByLocatar
		 * @param Description
		 * @param ColumnName
		 * @param EleType
		 */
		public ElementProperties(By ByLocatar, String Description,
				String ColumnName, ElementTypes EleType) {
			this.ByLocatator = ByLocatar;
			this.Description = Description;
			this.ColumnName = ColumnName;
			this.ElementType = EleType;
		}

		/**
		 * @param ByLocatar
		 * @param Description
		 * @param ColumnName
		 * @param EleType
		 * @param AdditionalLocator
		 */
		public ElementProperties(By ByLocatar, String Description,
				String ColumnName, ElementTypes EleType, By AdditionalLocator) {
			this.ByLocatator = ByLocatar;
			this.Description = Description;
			this.ColumnName = ColumnName;
			this.ElementType = EleType;
			this.AdditionalLocator = AdditionalLocator;
		}

		//************************
		//***setter and Getters***
		//************************
		
		/**
		 * @param ByLocatator
		 *            the ByLocatator to set
		 */
		public void setByLocatator(By ByLocatator) {
			this.ByLocatator = ByLocatator;
		}
		
		public void setIframeXpath(String IframeXpath) {
			Iframe = IframeXpath;
		}
		
		public String getIframeXpath() {
			return Iframe;
		}

		public By getAdditionalLocator() {
			return AdditionalLocator;
		}

		public By getLocator() {
			return ByLocatator;
		}

		public String getDescription() {
			return Description;
		}

		public ElementTypes getElementType() {
			return ElementType;
		}

		// Check that a column name has been set first
		// Check that the column name exists in the Data table.
		// Before returning the column name.
		public String getColumnName() throws Exception {
			checkColumnNameIsAvailable();
			CheckColumnExistsInDataTable();
			return ColumnName;
		}

		/**
		 * @Description This Method is used to retrieve the column name from excel
		 *              sheet.
		 * @return String
		 * @throws Exception
		 */
		public String getColumnNameExcel() throws Exception {
			String columnName = "";
			
			if (DoesColumnExistInDataTable()) {
				columnName = this.ColumnName;
			}
			
			return columnName;
		}

		/**
		 * @Description This Method is used for check column exists in excel sheet
		 *              or not.
		 * @return String
		 * @throws Exception
		 */
		// TODO - This method need to be corrected, if and else condition return the same value
//		private String CheckColumnExistsInDataTableExcel() throws Exception {
//			String columnNameTemp = "";
//			if (!DoesColumnExistInDataTable()) {
//				columnNameTemp = this.ColumnName;
//			} else {
//				columnNameTemp = this.ColumnName;
//			}
//			return columnNameTemp;
//		}

		/**
		 * @return boolean
		 */
		public boolean isColumnNameAvailable() {
			return !ColumnName.equals("");
		}

		public void checkColumnNameIsAvailable() throws Exception {
			if (!isColumnNameAvailable()) {
				TestHelper
						.handleExceptionNoRetry("Data Table Column Name not specified for Element: "
								+ Description);
			}
		}

		private void CheckColumnExistsInDataTable() throws Exception {
			if (!DoesColumnExistInDataTable()) {
				TestHelper.handleExceptionNoRetry(ColumnName
						+ " Does Not Exist in DataTable");
			}
		}

		private boolean DoesColumnExistInDataTable() {
			return TestHelper.doesTestDataContainKey(ColumnName);
		}

		//************
		//**Actions***
		//************
		
		//Actions, operations, assertions etc. applied to element's 
		//Utilise actions listed in the Actions class. 
		
		public void input() throws Exception{
			CoreActions.input(this,getDataValue() );
		}
		
		public void click() throws Exception{
			CoreActions.click(this);
		}
		
		public void clickViaJavaScript() throws Exception{
			CoreActions.clickViaJavaScript(this.Description, this.Iframe, this.ByLocatator);
		}
		
		public void selectOption() throws Exception{
			CoreActions.selectOption(this,getDataValue());
		}
		
		public void selectOption(String option) throws Exception{
			CoreActions.selectOption(this,option);
		}
		
		public void waitForElementToBeDisplayed() throws Exception
		{
			CoreActions.waitForElementToBeDisplayed(this);
		}
		
		public String getDataValue()throws Exception{
			return TestHelper.getTestDataValue(this.ColumnName);
		}
		
		public void inputText() throws Exception{
			CoreActions.inputText(this.Description, this.Iframe, this.ByLocatator, getDataValue());
		}
		
		public void inputText(String value) throws Exception{
			CoreActions.inputText(this.Description, this.Iframe, this.ByLocatator, value);
		}
		
		public void inputPassword(String value) throws Exception{
			CoreActions.inputPassword(this.Description, this.Iframe, this.ByLocatator, value);
		}
		
		public void inputText(String value, Keys keyValue) throws Exception{
			CoreActions.inputText(this.Description, this.Iframe, this.ByLocatator, value, keyValue);
		}
		
		public void inputEncodedTextBase64(String encodedPassword) throws Exception{
			CoreActions.inputEncodedTextBase64(this,encodedPassword);
		}
		
		public boolean isElementPresentAndAtLeastOne() throws Exception{
			return  CoreActions.isElementPresentAndAtLeastOne(this);
		}
		
		public String getElementText() throws Exception{
			return CoreActions.getElementText(this);
		}
		
		public void assertElementTextEquals(String expectedText) throws Exception{
			CoreAssertions.assertElementTextEquals(this, expectedText);
		}
		
		public void assertElementIsNotDisplayed() throws Exception{
			CoreAssertions.assertElementIsNotDisplayed(this);
		}
		
		public void assertElementIsDisplayed() throws Exception
		{
			CoreAssertions.assertElementIsDisplayed(this);
		}
		
		public void assertElementTextMatchesDataTable() throws Exception {
			CoreAssertions.assertElementTextEquals(this, this.getDataValue());
		}
		public String getAttributeValue(String attributeName, boolean waitUntilDisplayed, boolean waitUntilEnabled, int timeoutSeconds)  {
			return CoreActions.getWebElementAttribute(this,attributeName, waitUntilDisplayed, waitUntilEnabled, timeoutSeconds);
		}
		
		public String getWebElementAttribute(String attributeName, boolean waitUntilDisplayed, boolean waitUntilEnabled, int timeoutSeconds)  {
			return CoreActions.getWebElementAttribute(this,attributeName, waitUntilDisplayed, waitUntilEnabled, timeoutSeconds);
		}
		
		public String getWebElementText(boolean waitUntilDisplayed, boolean waitUntilEnabled) throws Exception {
			return CoreActions.getWebElementText(this, waitUntilDisplayed, waitUntilEnabled);
		}
		
		public WebElement getWebElement(boolean waitUntilDisplayed, boolean waitUntilEnabled) throws Exception {
			return CoreActions.getWebElement(this, waitUntilDisplayed, waitUntilEnabled);
		}
		
		public List<WebElement> getWebElements() throws Exception {
			return CoreActions.getWebElements(this);
		}
		
		public WebElement getWebElement() throws Exception {
			return CoreActions.getWebElement(this);
		}
		
		public void assertSubElementIsDisplayed(WebElement ParentElement) throws Exception{
			CoreAssertions.assertSubElementIsDisplayed(this.Description, this.ByLocatator, ParentElement);
		}
		
		public void assertElementIsEnabled() throws Exception{
			CoreAssertions.assertElementIsEnabled(this);
		}
		
		public void assertElementTextContains(String expectedText) throws Exception{
			CoreAssertions.assertElementTextContains(this,expectedText);
		}
		
		public boolean isElementPresentAndUnique() throws Exception
		{
			return CoreActions.isElementPresentAndUnique(Iframe,ByLocatator);
		}
		
//		public int getRowCount() throws Exception
//		{
//			return Webtable.getRowCount(this);
//		}
//		
//		public int getColumnCount() throws Exception
//		{
//			return Webtable.getColumnCount(this);
//		}
//		
//		public int getColumnsCount() throws Exception
//		{
//			return Webtable.getColumnsCount(this.getLocator());
//		}
//		
//		public String getTextFromCell(int rowNumber, int columnNumber) throws Exception
//		{
//			return Webtable.getTextFromCell(this,rowNumber,columnNumber);
//		}
//		
//		public String getHeaderTextFromCell(int rowNumber, int columnNumber) throws Exception
//		{
//			return Webtable.getHeaderTextFromCell(this,rowNumber,columnNumber);
//		}
//		
//		public void clickLinkInCell(int rowNumber, int columnNumber, String htmlTag, String attribute, String attributeValue) throws Exception
//		{
//			Webtable.clickLinkInCell(this,rowNumber,columnNumber,htmlTag,attribute,attributeValue);
//		}
		
		public void assertDropDownSelectedItemEquals(String expectedValue) throws Exception{
			CoreAssertions.assertDropDownSelectedItemEquals(Description,Iframe, ByLocatator,expectedValue);
		}
		
		public void assertElementAttributeEquals(String attributeName, String expectedAttributeValue) throws Exception{
			CoreAssertions.assertElementAttributeEquals(Description,Iframe, ByLocatator,attributeName,expectedAttributeValue);
		}
		
//		public boolean isColumnPresentInWebTable(String columnName) throws Exception{
//			return Webtable.isColumnPresentInWebTable(this, columnName);
//		}
//		
//		public int getColumnNumber(String columnName) throws Exception{
//			return Webtable.getColumnNumber(this, columnName);
//		}
		
		public boolean isElementDisplayed() throws Exception{
			return CoreActions.isElementDisplayed(Iframe, ByLocatator);
		}
		
		public String getTextDropDownSelectedItem() throws Exception{
			return CoreActions.getTextDropDownSelectedItem(this,true,true);
		}
		
		public void assertElementIsSelected() throws Exception{
			CoreAssertions.assertElementIsSelected(this);
		}
		
		public void assertElementIsNotSelected() throws Exception{
			CoreAssertions.assertElementIsNotSelected(this);
		}
		
		/**
		 * Creates Log entry and Executes specified against this element. 
		 * no Retries are execrcised. 
		 * @param JavaScript - the java script to be executed
		 * @throws Exception
		 */
		public void ExecuteJavaScript(String JavaScript) throws Exception {
			
			
			try {
				
				String logMessage = String.format("Executing JavaScript '%s' on element %s", JavaScript, Description);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement object = this.getWebElement();
				object.clear();
				BrowserHelper.executeJavascriptOnObject(JavaScript, object);
				TestHelper.incrementStepCount();
			}
			catch(AssertionError | Exception e)
			{
				CoreActions.RecordException(e.getMessage());
			}
		}
		
		/**
		 * Uses the ExecuteJavaScript routine to force teh innerHTML of the element to match teh input Text
		 * @param Text - Text to be inputted. 
		 * @throws Exception
		 */
		public void InputTextWithJS(String Text) throws Exception {
			String logMessage = String.format("Inputing %s into the %s Object", Text, this.Description);
			TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);

			String JavaScript = String.format("arguments[0].innerHTML = '%s'", Text);
			ExecuteJavaScript(JavaScript);
			
			//Don't increment step count as ExecuteJavaScript method will do this. 
		}
		
		
		/**
		 * Uses the Actions class to enter the value in textBox/WebElement
		 * @param Text - Text to be inputted. 
		 * @throws Exception
		 */
		public void inputTextWithAction(String value) throws Exception {
			CoreActions.inputTextWithAction(this.Description, this.Iframe, this.ByLocatator, value);
		}
		
		/**
		 * Gets data from Datatable for this element and then input Text using Javascript. 
		 * @throws Exception
		 */
		public void InputTextWithJS() throws Exception {
			InputTextWithJS(getDataValue());
		}
		
		
		
		/**
		 * Logs and inputs a special key (E.g. enter) to this object.
		 * @param KeyToInput - the key to type
		 * @throws Exception
		 */
		public void InputKeys(Keys KeyToInput) throws Exception {
			
			try
			{
				String logMessage = String.format("Inputting key  '%s' in element '%s'", KeyToInput.toString(), this.Description);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement element = this.getWebElement();
				element.sendKeys(KeyToInput);

				TestHelper.incrementStepCount();
			}
			catch(AssertionError | Exception e)
			{
				CoreActions.RecordException(e.getMessage());
			}
			
		}
		
		
		
		/**
		 * Logs and inputs a special key (E.g. enter) to this object.
		 * @param KeyToInput - the key to type
		 * @throws Exception
		 */
		public void inputKeysUsingAction(Keys KeyToInput) throws Exception {
			
			try
			{
				String logMessage = String.format("Inputting key  '%s' in element '%s'", KeyToInput.toString(), this.Description);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				Actions action = new Actions(BrowserHelper.getDriver());
				WebElement element = this.getWebElement();
				action.sendKeys(element,KeyToInput);

				TestHelper.incrementStepCount();
			}
			catch(AssertionError | Exception e)
			{
				CoreActions.RecordException(e.getMessage());
			}
			
		}
		
		public void HoverOver() throws Exception {
			CoreActions.HoverMouseOver(this.Description, this.Iframe, this.ByLocatator);
		}
		
		public void clickUsingActions() throws Exception{
			CoreActions.clickUsingActions(this);
		}
	}


