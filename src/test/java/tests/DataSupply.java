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

}
