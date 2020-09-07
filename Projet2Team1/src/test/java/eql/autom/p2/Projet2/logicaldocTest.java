package eql.autom.p2.Projet2;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.support.SoapUIException;

public class logicaldocTest {
	
	@RunWith(Parameterized.class)
	public class LogicalDocTest {
		private String testCaseName;
	

		public LogicalDocTest(String testCaseName) {
			this.testCaseName = testCaseName;
		}

		@Parameters(name = "{0}")
		public Collection<String[]> getTestCases() throws XmlException, IOException, SoapUIException {
			final ArrayList<String[]> testCases = new ArrayList<String[]>();
			WsdlProject project = new WsdlProject("src/test/resources/ProjectLogicalDoc-soapui-project.xml");
			TestSuite TS = project.getTestSuiteByName("TestSuite - LogicalDoc - PropertiesTestCase");
			//List<TestSuite> testSuites = project.getTestSuiteList();
			//for (TestSuite suite : testSuites) {
				//List<TestCase> lTestCases = suite.getTestCaseList();
				//
			List<TestCase> TCListe = TS.getTestCaseList();
			for (TestCase testCase : TCListe) 
				{
				if (!testCase.isDisabled()) 
					{
						testCases.add(new String[] { testCase.getName() });
					}
				}
			//}
			return testCases;
			
		}

		@Test
		public void testSoapUITestCase() throws XmlException, IOException, SoapUIException {
			System.out.println("\n[START] Nom du cas de test SoapUI : " + testCaseName.toUpperCase());
			assertTrue(true);
			assertTrue(runSoapUITestCase(this.testCaseName));
		}

		public boolean runSoapUITestCase(String testCase) throws XmlException, IOException, SoapUIException {
			TestRunner.Status exitValue = TestRunner.Status.INITIALIZED;
			WsdlProject soapuiProject = new WsdlProject("src/test/resources/ProjectLogicalDoc-soapui-project.xml");
			TestSuite TS = soapuiProject.getTestSuiteByName("TestSuite - LogicalDoc - PropertiesTestCase");
				System.out.println("[SEARCH] recherche du TC ["+testCase+"] dans la suite "+ TS.getName().toUpperCase());
				TestCase soapuiTestCase = TS.getTestCaseByName(testCase);
				if (soapuiTestCase == null)
				{
					System.out.println("[NOT FOUND YET] runner soapUI, le cas de test [" + testCase+ "] n'est pas dans cette suite de test");
				} 
				else 
				{
					System.out.println("[RUN] Running SoapUI test [" + testCase + "]");
					TestCaseRunner runner = soapuiTestCase.run(new PropertiesMap(), false);
					exitValue = runner.getStatus();
					if(exitValue == TestRunner.Status.FINISHED) 
					{
						System.out.println("[END] : Cas de test soapUI terminé ('" + TS.getName().toUpperCase() + "':[" + testCase + "]) : " + exitValue);
						return true;
					}
				}

		
				System.out.println("[END] : Cas de test soapUI terminé ('" + TS.getName().toUpperCase() + "':[" + testCase + "]) Status : " + exitValue);
				return false;
			}
		
		
		
		}

}
