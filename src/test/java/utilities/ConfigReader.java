package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	static ConfigReader confilereader = new ConfigReader();
	private static Properties prop = new Properties();
	private final static String propertyFilePath = "src/test/resources/Config/config.properties";

	public ConfigReader() {
		FileInputStream file;
		try {
			file = new FileInputStream(propertyFilePath);
			prop = new Properties();
			prop.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperties(String key) {
		String property = prop.getProperty(key);
		if (property != null) {
			return property;
		} else {
			throw new RuntimeException(key + " not specified in the Config.properties file");
		}
	}
}
