import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Tester3D {
	public static void main (String[] args) {
		//Change window dimensions if your monitor resolution is too low
		int width = 1000;
		int height = 1000;
		String input = " ";
		Scanner s = new Scanner(System.in);
		
		double[][] vertexTable = {};
		int[][] lineTable = {};
		//Format of file must be:
		// **
		// (number of lines
		// (x1 coords separated by a space)
		// (x2 coords separated by a space)
		// (y1 coords separated by a space)
		// (y2 coords separated by a space)
		// **
		File file = new File("src/coords");   
		Scanner scanner;
		int vertexes;
		int lines;
		try {
			scanner = new Scanner(file);
			vertexes = scanner.nextInt();
			vertexTable = new double[vertexes][3];
			lines = scanner.nextInt();
			lineTable = new int[lines][2];
			for (int i = 0; i < vertexes; i++) {
				vertexTable[i][0] = scanner.nextInt();
				vertexTable[i][1] = scanner.nextInt();
				vertexTable[i][2] = scanner.nextInt();
			}
			for (int i = 0; i < lines; i++) {
				lineTable[i][0] = scanner.nextInt();
				lineTable[i][1] = scanner.nextInt();
			}
			
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Frame frame = new Frame(width, height, vertexTable, lineTable);				
		frame.setUpGUI();		
		Graphics g = frame.getGraphics();
		
		while (!(input.equals("q"))) {
			System.out.println("Would you like to perform a transformation on the printed line(s)? \nType \"T\" for Translation, \"S\" for Scale, or \"R\" for Rotate. Type \"q\" to exit.");
			input = s.nextLine();
			if (input.equalsIgnoreCase("T")) {
				System.out.println("You have chosen Translation");
				System.out.println("Enter your X displacement: ");
				int xDis = s.nextInt();
				System.out.println("Enter your Y displacement: ");
				int yDis = s.nextInt();
				System.out.println("Enter your z displacement: ");
				int zDis = s.nextInt();
				frame.trans3D(xDis, yDis, zDis);
				g.clearRect(0, 0, height, width);
				frame.repaint();
			} else if (input.equalsIgnoreCase("S")) {
				System.out.println("You have chosen Scale");
				System.out.println("Enter your X Scale Factor: ");
				double xFac = s.nextDouble();
				System.out.println("Enter your Y Scale Factor: ");
				double yFac = s.nextDouble();
				System.out.println("Enter your Z Scale Factor: ");
				double zFac = s.nextDouble();
				frame.scale3D(xFac, yFac, zFac);
				g.clearRect(0, 0, height, width);
				frame.repaint();
			} else if (input.equalsIgnoreCase("R")) {
				System.out.println("You have chosen Rotate");
				System.out.println("Enter your Angle of Rotation: ");
				double angle = s.nextDouble();
				System.out.println("Enter your desired axis of rotation (x, y, or z): ");
				String axis = s.next();
				frame.rotate3D(angle, axis);
				g.clearRect(0, 0, height, width);
				frame.repaint();
			} else if (input.equalsIgnoreCase("q")) {
				System.out.println("Exiting");
			} else {
				//Failsafe case if invalid input is detected
				System.out.println("Invalid input, please enter one of the options listed.");
			}
		}
		
		}
}
