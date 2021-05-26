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

	String path;
	int pathOption;
	String filename;

	public Reader() {

	}

	public Reader(String path, int pathOption, String filename) {
		this.path = path;
		this.pathOption = pathOption;
		this.filename = filename;
	}

	public int[][] readFile() {
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

	private String choosePath(String path, int pathOption, String filename) {

		if (pathOption == 1) {
			path = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\" + filename;
		} else {
			path = path.concat("\\");
			path = path.concat(filename);
			path = path.replaceAll("\\\\|/", "\\\\\\\\");
		}

		return path;

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getPathOption() {
		return pathOption;
	}

	public void setPathOption(int pathOption) {
		this.pathOption = pathOption;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
