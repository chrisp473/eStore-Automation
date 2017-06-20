package coop.digital.eStores.testAutomation.utilityAndFactories;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XMLUtil {

	//Need to replace hte code in the setSystemPropertyFromConfigurationFile, and updateResultSummaryXML methods in the testhelper class. 
		public static Document getXMLDoc(String DocumentPath) throws Exception{
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance(); 
			domFactory.setIgnoringComments(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder(); 
			Document doc = builder.parse(new File(DocumentPath));
			int a;
			a=1;
					
			return doc;
			
		}
		
		@SuppressWarnings("deprecation")
		public static void UpdateXMLFile(Document doc, String DocumentPath) throws Exception{
			//-------- Write to XMl fILE
			XMLSerializer serializer = new XMLSerializer();
			serializer.setOutputCharStream(new java.io.FileWriter(DocumentPath));
			OutputFormat format = new OutputFormat();
			format.setStandalone(true);
			serializer.setOutputFormat(format);
			serializer.serialize(doc);
		}
		public static NodeList GetNodesThatMatchXPath (String Xpath, Document doc) throws XPathExpressionException {
			//locate existing node for this test if it exists 
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile(Xpath);		
			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			return nl;
		}
}
