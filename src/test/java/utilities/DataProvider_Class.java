package utilities;

import org.testng.annotations.DataProvider;
import utilities.ExcelReader;
import utilities.ConfigReader;
import java.util.List;
import java.util.Map;

public class DataProvider_Class {

	@DataProvider(name = "ValidLoginData")
	public Object[][] provideValidLoginData() {
		String sheetName = "Login";
		String scenarioType = "valid";
		Map<String, String> dataMap = ExcelReader.getDataByScenario(sheetName, scenarioType);

		String Username = dataMap.get("Username");
		String Password = dataMap.get("Password");
		return new Object[][] { { Username, Password } };
	}
	
	@DataProvider(name = "inValidLoginData")
	public Object[][] provideinValidLoginData() {
		String sheetName = "Login";
		String scenarioType = "invalid";
		Map<String, String> dataMap = ExcelReader.getDataByScenario(sheetName, scenarioType);

		String Username = dataMap.get("Username");
		String Password = dataMap.get("Password");
		return new Object[][] { { Username, Password } };
	}

}
