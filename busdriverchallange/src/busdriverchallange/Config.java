package busdriverchallange;

/**
 * @author Tobias Stelter
 * This class implement the general config.
 */

public class Config {
		
	public static int routes = 3; // Wir haben 3 Routen
	public static int drivers = 11; // Wir haben 11 Fahrer
	
	public static String[] shiftNames = {" D1"," N1"," D2"," N2"," D3"," N3"," D4"," N4"," D5"," N5"," D6"," N6"," D7"," N7"," D8"," N8"," D9"," N9","D10","N10","D11","N11","D12","N12","D13","N13","D14","N14  "}; // Schichtnamen für die Ausgabe
	public static String[] routeNames = {"A1","A2","A3","B1","B2","B3","C1","C2","C3","D1","D2","D3","E1","E2","E3","F1","F2","F3","G1","G2","G3","H1","H2","H3","I1","I2","I3","J1","J2","J3","K1","K2","K3"}; // Routennamen nach Fahrer
	
	public static int shiftsPerDay = 2; //A day has two shifts
	public static int totalDays = 14;
	
	public static int nightShifstAreBad = 3; // Busdriver dont like to work more than 3 days
	public static int equalNightShifts = 4;
	public static int longBreak = 6; // 3 Days
	
	public static int pointsPreferredShift = 3; // Berücksichtigung Schicht-Wunschtermin
	public static int pointsPrefferedHoliday = 4; // Berücksichtigung Urlaubswunsch
	public static int pointsLongBreak = 5; // Lange Pause (3 oder mehr Tage)
	public static int pointsUnassignedShift = -20; // Nicht zugewiesene Schicht
	public static int pointsNightshiftFollowedByDayshift = -30; // Frühschicht für Fahrer folgt auf (gerade erst beendete Spätschicht)
	public static int pointsMoreThanThreeNightShifts = -10; // Jede weitere Spätschicht (3 oder mehr in Folge)
	public static int pointsUnbalancedNightShifts = -8; // Anzahl der Spätschichten ist ungleich verteilt (Soll: 4 pro Fahrer)

}
