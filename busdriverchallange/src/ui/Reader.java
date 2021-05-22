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

	public static int[][] readFile() {
		int[][] matrix = new int[Config.drivers * Config.routes][Config.totalDays * Config.shiftsPerDay];

		String path = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\matrix.txt";
		Path filePath = Paths.get(path);

		// converting to UTF 8
		Charset charset = StandardCharsets.UTF_8;

		// try with resource
		try (BufferedReader bufferedReader = Files.newBufferedReader(filePath, charset)) {
			for (int i = 0; i < Config.drivers * Config.routes; i++) {
				String[] st = bufferedReader.readLine().trim().split(",");
				for (int j = 0; j < Config.totalDays * Config.shiftsPerDay; j++) {
					matrix[i][j] = Integer.parseInt(st[j].replaceAll("[\\.$|{|,}|]", ""));
				}
			}
		} catch (IOException ex) {
			Printer.printError();
		}

		return matrix;
	}

}
