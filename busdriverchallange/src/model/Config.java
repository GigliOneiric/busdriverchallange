package model;

/**
 * @author Tobias Stelter
 * This class implement the general config.
 */

public class Config {
		
	public static int routes = 3; // 3 routes
	public static int drivers = 11; // 11 drivers
	
	public static String[] shiftNames = {" D1"," N1"," D2"," N2"," D3"," N3"," D4"," N4"," D5"," N5"," D6"," N6"," D7"," N7"," D8"," N8"," D9"," N9","D10","N10","D11","N11","D12","N12","D13","N13","D14","N14  "}; // Schichtnamen für die Ausgabe
	public static String[] routeNames = {"A1","A2","A3","B1","B2","B3","C1","C2","C3","D1","D2","D3","E1","E2","E3","F1","F2","F3","G1","G2","G3","H1","H2","H3","I1","I2","I3","J1","J2","J3","K1","K2","K3"}; // Routennamen nach Fahrer
	
	public static int shiftsPerDay = 2; // 2 shifts per day
	public static int totalDays = 14; // 14 days
	
	public static int nightShifstAreBad = 3; // Busdriver dont like to work more than 3 days
	public static int equalNightShifts = 4;
	public static int longBreak = 6; // 3 Days
	
	public static int pointsPreferredShift = 3; // Respect a single shift preference
	public static int pointsPrefferedHoliday = 4; // Respect a single driver day-off preference
	public static int pointsLongBreak = 5; // For each allocated long rest (3 or more consecutive days off)
	public static int pointsUnassignedShift = -20; // For each unassigned shift
	public static int pointsNightshiftFollowedByDayshift = -30; // For each late shift followed immediately by an early shift in a single driver’s schedule
	public static int pointsMoreThanThreeNightShifts = -10; // For every after the third consecutive late shift assigned to a single driver
	public static int pointsUnbalancedNightShifts = -8; // For every late shift assigned that is not equal to 4
	
	public static int pointsInvalidSolution = -50; // For every late shift assigned that is not equal to 4
	
	public static String filenameMatrix = "matrix.txt";
	public static String filenameRestrictions = "restrictions.txt";

}
