package tests;

import java.util.Map;

import org.testng.annotations.DataProvider;

import utilities.ExcelReader;

public class DataProviders {
	
	
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
	
	@DataProvider()
	public String[] practiceQuestTitle() {
		String[] data= new String[] {
				"Practice Questions"
						
		};
		return data;
	}
	
	@DataProvider()
	public String[] dataStructureTitle() {
		String[] data= new String[] {
				"Data Structures-Introduction"
			};
		return data;
	}
	
	@DataProvider()
	public String[] tryEditorTitle() {
		String[] data= new String[] {
				"Assessment"
			};
		return data;
	}
	
	@DataProvider()
	public String[][] registerPageData(){
		
		String[][] data =new String[][] {
			
			{"Register","pwdBlank","Please fill out this field."},
		    {"Register","usernameBlank","Please fill out this field."},
			{"Register","confirmPwdBlank","Please fill out this field."}
			};
				
		return data;
		
		
	}
	
	@DataProvider(name = "invalidpythonCode")
	public Object[][] incorrectPythonCode() {
		Map<String, String> dataMap = ExcelReader.getDataByScenario("Pythoncode", "incorrectCode");

		String code = dataMap.get("Pcode");
		
		return new Object[][]  { {code} };
	}
	
	@DataProvider(name = "validpythonCode")
	public Object[][] validPythonCode() {
		Map<String, String> dataMap = ExcelReader.getDataByScenario("Pythoncode", "valid");

		String code = dataMap.get("Pcode");
		String result = dataMap.get("Result");
		return new Object[][] { { code, result} };
	}
	
	@DataProvider()
	public Object[][] registerPageDataValid(){
		
		String[][] data =new String[][] {
			
			{"Register","valid","New Account Created. You are logged in as"}
		
			};
				
		return data;
		
		
	}
	
	@DataProvider()
	public Object[][] treePageLinks(){

		Object[][] data =new Object[][] {

			{"Overview of Trees", "Overview of Trees"},              
			{"Terminologies","Terminologies"},                  
			{"Types of Trees","Types of Trees"},                
			{"Tree Traversals","Tree Traversals"},                
			{"Traversals-Illustration","Traversals-Illustration"},        
			{"Binary Trees","Binary Trees"},                   
			{"Types of Binary Trees","Types of Binary Trees"},          
			{"Implementation in Python","Implementation in Python"},       
			{"Binary Tree Traversals", "Binary Tree Traversals"},         
			{"Implementation of Binary Trees", "Implementation of Binary Trees"}, 
			{"Applications of Binary trees", "Applications of Binary trees"},   
			{"Binary Search Trees","Binary Search Trees"},            
			{"Implementation Of BST","Implementation Of BST"}

		};

		return data;


	}
	
	@DataProvider()
	public Object[][] treeLinks(){

		Object[][] data =new Object[][] {

			{"Overview of Trees","Assessment"},              
			{"Terminologies","Assessment"},                  
			{"Types of Trees","Assessment"},                
			{"Tree Traversals","Assessment"},                
			{"Traversals-Illustration","Assessment"},        
			{"Binary Trees","Assessment"},                   
			{"Types of Binary Trees","Assessment"},          
			{"Implementation in Python","Assessment"},       
			{"Binary Tree Traversals","Assessment"},         
			{"Implementation of Binary Trees","Assessment"}, 
			{"Applications of Binary trees","Assessment"},   
			{"Binary Search Trees","Assessment"},            
			{"Implementation Of BST","Assessment"}

		};

		return data;


	}
	

	 @DataProvider(name = "ArraysLinks")
	    public Object[][] arrayLinksData() {
	        return new Object[][] {
	            {"Arrays in Python"},
	            {"Arrays Using List"},
	            {"Basic Operations in Lists"},
	            {"Applications of Array"}
	        };
	    }
	 
	 @DataProvider(name = "questionLinks")
	    public Object[][] questionLinksData() {
	        return new Object[][] {
	            {"Search the array"},
	            {"Max Consecutive Ones"},
	            {"Find Numbers with Even Number of Digits"},
	            {"Squares of a Sorted Array"}
	        };
	    }
	 
	 @DataProvider(name = "StackLinks")
	    public Object[][] StackLinksData() {
	        return new Object[][] {
	            {"Operations in Stack"},
	            {"Implementation"},
	            {"Applications"},
	        };
	    }
}
