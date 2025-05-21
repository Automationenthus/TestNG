package tests;

import org.testng.annotations.DataProvider;

public class DataSupply {
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
