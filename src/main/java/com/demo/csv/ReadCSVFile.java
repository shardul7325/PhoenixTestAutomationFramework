package com.demo.csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class ReadCSVFile {

	public static void main(String[] args) throws IOException, CsvException {
		//Code to read the CSV file in JAVA!! [Important Interview Question]
		
		/*
		 * File file = new File("C:\\Users\\shard\\Coding Workspaces\\Eclipse workspaces\\proj1\\PhoenixTestAutomationFramework\\src\\main\\resources\\testData\\LoginCreds.csv");
		 * FileReader fr = new FileReader(file);
		 */
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/LoginCreds.csv");
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr); // CSVReader Constructor requires a Reader!!
		
		
		List<String[]> dataList = csvReader.readAll();
		
		for(String[] dataLine : dataList) {
			
			for(String data : dataLine) {
				
				System.out.print(data + " ");
			}
			
			System.out.println();
		}
	}
}
