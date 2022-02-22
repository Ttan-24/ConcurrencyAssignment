package uclan.com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFileManager {
	private static File logFile;

	// create a log file
	public static void openLog(String logPath) {
		// Opens the log file, or creates one if one doesn't exist already.
		logFile = new File(logPath + "/Concurrency Log.txt");
	}

	// write to the log
	public static void writeToLog(String logText) {
		System.out.println(logText);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		try {
			FileWriter fr = new FileWriter(logFile, true);
			BufferedWriter br = new BufferedWriter(fr);
			br.write("[" + dtf.format(now) + "] " + logText + "\r\n");
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// write to the log without the end line
	public static void writeToLogWithoutEndLine(String logText) {
		System.out.print(logText);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		try {
			FileWriter fr = new FileWriter(logFile, true);
			BufferedWriter br = new BufferedWriter(fr);
			br.write("[" + dtf.format(now) + "] " + logText + "\r\n");
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// log warnings
	public static void logWarning(String logTxt) {
		writeToLog("WARNING: " + logTxt);
	}

	// log errors
	public static void logError(String logTxt) {
		writeToLog("ERROR: " + logTxt);
	}

	// log debugs
	public static void logDebug(String logTxt) {
		writeToLog("DEBUG: " + logTxt);
	}
}
