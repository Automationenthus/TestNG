package utilities;



import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import hooks.Hooks;

public class ExtentwithListeners implements ITestListener {
	public ExtentSparkReporter sparkReport; //UI of the report
	public ExtentReports extent; //generate common info of the report
	public ExtentTest test;  //creating test entry and update the status of the test method
	
	String reportName;
	
	
	public void onStart(ITestContext context) {

		SimpleDateFormat df= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentDate=df.format(dt);  //timestamp
		reportName="Test-Report-" +currentDate+ ".html";
		sparkReport=new ExtentSparkReporter(".\\extentReport\\"+reportName);  //location of the report
		sparkReport.config().setDocumentTitle("dsalgo");
		sparkReport.config().setReportName("dsAlgoTestNG");
		sparkReport.config().setTheme(Theme.DARK);
		extent=new ExtentReports();
		extent.attachReporter(sparkReport);

	}

			
	public void onTestSuccess(ITestResult result) {
		test=extent.createTest(result.getTestClass().getName());
		test.log(Status.PASS,result.getName()+"test got passed");
		
		
	}

		
	@Override
	public void onTestFailure(ITestResult result) {
	    test = extent.createTest(result.getMethod().getMethodName());
	    test.log(Status.FAIL, result.getName() + " test failed");
	    test.log(Status.INFO, result.getThrowable());

	    String imagePath = new Hooks().captureScreenShot(result.getName());

	    if (imagePath != null) {
	        test.addScreenCaptureFromPath(imagePath);  
	    } else {
	        test.log(Status.WARNING, "Screenshot path is null.");
	    }
	}


	
	public void onTestSkipped(ITestResult result) {
		test=extent.createTest(result.getTestClass().getName());
		test.log(Status.SKIP,result.getName()+"test got skipped");
		test.log(Status.INFO,result.getThrowable().getMessage());
	}

	
	public void onFinish(ITestContext context) {
		extent.flush();
	}
	
	

}
