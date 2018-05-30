package helper;

import entity.ActionUtility;
import helper.Variables;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

public class FileWriteIO {
	public static void fileWrite(List<ActionUtility[][]> listActionUtility, String fileName) {
		StringBuilder display = new StringBuilder();
//		initialize the utility value to be written to file
		String value = "0.000";
		DecimalFormat decimalFormat = new DecimalFormat(value);
		
//		for each square and its utility value, write to file
		for (int col = 0; col<Variables.NUM_COLS; col++) {
			for (int row = 0; row<Variables.NUM_ROWS; row++) {
				Iterator<ActionUtility[][]> iter = listActionUtility.iterator();
				while(iter.hasNext()) {
					ActionUtility[][] utilityVal = iter.next();
					display.append(decimalFormat.format(utilityVal[col][row].utility.getUtility()).substring(0, 5));
					
					// append "," for csv format file
					if (iter.hasNext()) {
						display.append(",");
					}
				}
				display.append("\n");
			}
		}
		
		try {
			// write the data into a .csv file
			FileWriter fw = new FileWriter(new File(fileName + ".csv"), false);
			fw.write(display.toString().trim());
			fw.close();
		} catch(IOException e) {
			// if there is error writing to file, print error
			e.printStackTrace();
		}
	}
}
