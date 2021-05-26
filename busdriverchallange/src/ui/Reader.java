package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import model.Config;

/**
 * @author Tobias Stelter This class reads a file to create a matrix.
 */

public class Reader {

	/**
	 * Reads the file
	 * 
	 * @return matrix
	 */

	public static int[][] readFile(String path, int pathOption, String filename) {
		int[][] matrix = new int[Config.drivers * Config.routes][Config.totalDays * Config.shiftsPerDay];

		path = choosePath(path, pathOption, filename);
		Path filePath = Paths.get(path);

		// converting to UTF 8
		Charset charset = StandardCharsets.UTF_8;

		// try with resource
		try (BufferedReader bufferedReader = Files.newBufferedReader(filePath, charset)) {
			for (int i = 0; i < Config.drivers * Config.routes; i++) {
				String[] st = bufferedReader.readLine().trim().split(",");
				for (int j = 0; j < Config.totalDays * Config.shiftsPerDay; j++) {
					matrix[i][j] = Integer.parseInt(st[j].replaceAll("[\\.$|{|\\s|,}|]", ""));
				}
			}
		} catch (IOException ex) {
			Printer.printError();
		}

		return matrix;
	}

	private static String choosePath(String path, int pathOption, String filename) {

		if (pathOption == 1) {
			path = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\" + filename;
		} else {
			path = path.concat("\\");
			path = path.concat(filename);
			path = path.replaceAll("\\\\|/", "\\\\\\\\");
		}

		return path;

	}

}
