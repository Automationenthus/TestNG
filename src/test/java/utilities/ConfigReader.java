package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	 private static Properties prop;

	    public static Properties initProperties() {
	        try {
	            FileInputStream file = new FileInputStream("src/test/resources/config/config.properties");
	            prop = new Properties();
	            prop.load(file);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return prop;
	    }

	    public static String getProperty(String key) {
	        if (prop == null) {
	            initProperties();
	        }
	        return prop.getProperty(key);
	    }

}