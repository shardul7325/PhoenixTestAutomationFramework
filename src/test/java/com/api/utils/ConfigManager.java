package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	// Special class in Java for reading Properties
	// Create the object of Properties Class
	private static Properties prop = new Properties();
	private static String configFilePath;
	private static String env;

	private ConfigManager() {
		// Private Constructor to prevent the creation of Objects!!!
	}

	static {
		// Operation of loading the properties file in the memory!!
		// static block -> it will executed!! ONCE During CLASS LOADING TIME!!!

//		env = System.getProperty("env").toLowerCase().trim();
		env = System.getProperty("env", "qa").toLowerCase().trim();
		// The above method helps in setting the default method if by chance no
		// parameter was entered.

		switch (env) {

		case "dev" -> configFilePath = "config/config.dev.properties";

		case "qa" -> configFilePath = "config/config.qa.properties";

		case "uat" -> configFilePath = "config/config.uat.properties";

		default -> configFilePath = "config/config.qa.properties";

		}
		System.out.println("Pulling Setup resources from " + configFilePath);

		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFilePath);

		// Checking if the given path for the config file is correct!!
		if (inputStream == null) {
			throw new RuntimeException("Cannot find the config file at the given path: " + configFilePath);
		}

		try {
			prop.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Write a program to read the .properties file from
	// src/test/resources/config/config.properties
	public static String getProperty(String key) {
		return prop.getProperty(key);
	}
}
