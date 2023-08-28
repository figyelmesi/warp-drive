package com.starship.warpdrive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Converter {

	private static final String TARGET_FILE = "servers.csv";
	private static final String CSV_HEADER = "Serial,Version,T-pro,PM,Web,Other,Date\n";
	private static final String START_DATE = "01/01/2023 00:00:00";

	private static DateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private static List<Server> servers = new ArrayList<>();
	private static int logEntryCounter, recordCounter;

	public static void main(String[] args) throws ParseException, IOException {

		File inputFile = new File(args[0]);

		importFile(inputFile);
		filterEntries();
		exportCSV(inputFile.getParentFile() + "/" + TARGET_FILE);

		System.out.println(logEntryCounter + " log entries processed");
		System.out.println(recordCounter + " server records exported");

	}

	private static void exportCSV(String file) throws IOException {
		File targetFile = new File(file);
		FileWriter fileWriter = new FileWriter(targetFile);
		fileWriter.write(CSV_HEADER);
		for (Server server : servers) {
			fileWriter.write(server + "\n");
			recordCounter++;
		}
		fileWriter.close();
	}

	private static void filterEntries() throws ParseException {
		Date startDate = sf.parse(START_DATE);
		servers = servers.stream().filter(e -> e.getAccessDate().after(startDate))
				.filter(e -> !e.getStatus().equals("Revoked")).collect(Collectors.toList());
	}

	private static void importFile(File file) throws ParseException {
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(file));
			reader.readLine();
			String line = reader.readLine();
			while (line != null) {
				String values[] = line.replace("\"", "").split(";", 9);
				Server server = new Server();
				server.setSerial(convertSerial(values[1]));
				server.setStatus(values[4]);
				addLicenseInfo(values, server);
				String lastAcces = values[5];
				if (!lastAcces.isEmpty()) {
					server.setAccessDate(sf.parse(values[5]));
					servers.add(server);
				}
				line = reader.readLine();
				logEntryCounter++;
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String convertSerial(String serial) {
		if (serial.length() == 20) {
			serial = serial.substring(0, 5) + "-" + serial.substring(5, 10) + "-" + serial.substring(10, 15) + "-"
					+ serial.substring(15, 20);
		}
		return serial;
	}

	private static void addLicenseInfo(String[] licenseValue, Server server) {
		String licenses[] = licenseValue[7].split(" / ");
		server.setVersion(licenses[0]);
		for (int i = 0; i < licenses.length; i++) {
			String value = licenses[i];
			if (value.contains("x tpro")) {
				server.settProLicenses(Integer.parseInt(value.substring(0, value.indexOf(" x"))));
			} else if (value.contains("x PM")) {
				server.setPmLicenses(Integer.parseInt(value.substring(0, value.indexOf(" x"))));
			} else if (value.contains("x Web")) {
				server.setWebLicenses(Integer.parseInt(value.substring(0, value.indexOf(" x"))));
			} else if (value.contains("x other")) {
				server.setOtherLicenses(Integer.parseInt(value.substring(0, value.indexOf(" x"))));
			}
		}
	}
}
