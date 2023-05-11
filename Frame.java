import java.awt.Graphics;

import javax.swing.JFrame;

public class Frame extends JFrame {

	private int width;
	private int height;
	private double xe = 6;
	private double ye = 7;
	private double ze = -1;
	int distance;
	int s = 15;
	int x1Coords[];
	int x2Coords[];
	int y1Coords[];
	int y2Coords[];
	double vertexes[][];
	int lines[][];
	int translationCase = 0;
	double t[][];
	double VN[][];
	
	public Frame(int w, int h, double[][] vertexes, int[][] lines) {
		width = w;
		height = h;
		this.vertexes = vertexes;
		this.lines = lines;
		t = new double[4][4];
		distance = 60;
		t[0][0] = 1;
		t[1][1] = 1;
		t[2][2] = 1;
		t[3][3] = 1;
		calculateVN();
	}
	
	//Resets the transformation Matrix
	public void resetT(){
		t[0][0] = 1;
		t[0][1] = 0;
		t[0][2] = 0;
		t[0][3] = 0;
		t[1][0] = 0;
		t[1][1] = 1;
		t[1][2] = 0;
		t[1][3] = 0;
		t[2][0] = 0;
		t[2][1] = 0;
		t[2][2] = 1;
		t[2][3] = 0;
		t[3][0] = 0;
		t[3][1] = 0;
		t[3][2] = 0;
		t[3][3] = 1;
	}
	public void printT() {
		System.out.println(t[0][0] + " " + t[0][1] + " " + t[0][2] + " " +  t[0][3]);
		System.out.println(t[1][0] + " " + t[1][1] + " " + t[1][2] + " " +  t[1][3]);
		System.out.println(t[2][0] + " " + t[2][1] + " " + t[2][2] + " " +  t[2][3]);
		System.out.println(t[3][0] + " " + t[3][1] + " " + t[3][2] + " " +  t[3][3]);
	}
	
	
	//Matrix Multiplication algorithm for the case 1x3 * 3x3
	public double[] multiplyMatrices(double[] a, double[][] b) {
		double[] result = new double[a.length];
            for (int j = 0; j < b.length; j++) {
                for (int k = 0; k < b[0].length; k++)
                    result[j] += (a[k]) * (b[k][j]);
            }
		return result;
	}
	
	//Matrix Multiplication algorithm for the case 3x3 * 3x3
	public double[][] multiplyMatrices3x3(double[][] a, double[][] b) {
		double[][] result = new double[3][3];
		 for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                for (int k = 0; k < 3; k++)
	                    result[i][j] += a[i][k] * b[k][j];
	            }
	        }
		return result;
	}
	
	public double[][] multiplyMatrices4x4(double[][] a, double[][] b) {
		double[][] result = new double[4][4];
		 for (int i = 0; i < 4; i++) {
	            for (int j = 0; j < 4; j++) {
	                for (int k = 0; k < 4; k++)
	                    result[i][j] += a[i][k] * b[k][j];
	            }
	        }
		return result;
	}
	
	public void calculateVN() {
		double xr = (xe/Math.sqrt(xe*xe + ye*ye));
		double yr = (ye/Math.sqrt(xe*xe + ye*ye));
		double temp1 = (Math.sqrt(xe*xe + ye*ye))/Math.sqrt(ze*ze + xe*xe + ye*ye);
		double temp2 = ze/Math.sqrt(ze*ze + xe*xe + ye*ye);
		double[][] t1 = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0},{-xe, -ye, -ze, 1}};	
		double[][] t2 = {{1, 0, 0, 0}, {0, 0, -1, 0}, {0, 1, 0, 0},{0, 0, 0, 1}};		
		double[][] t3 = {{-yr, 0, xr, 0}, {0, 1, 0, 0}, {-xr, 0, -yr, 0},{0, 0, 0, 1}};		
		double[][] t4 = {{1, 0, 0, 0}, {0, temp1, temp2, 0}, {0, -temp2, temp1, 0},{0, 0, 0, 1}};
		double[][] t5 = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, -1, 0},{0, 0, 0, 1}};
		double[][] n = {{distance/s, 0, 0, 0}, {0, distance/s, 0, 0}, {0, 0, 1, 0},{0, 0, 0, 1}};
		
		this.VN = t1;
		this.VN = multiplyMatrices4x4(VN, t2);
		this.VN = multiplyMatrices4x4(VN, t3);
		this.VN = multiplyMatrices4x4(VN, t4);
		this.VN = multiplyMatrices4x4(VN, t5);
		this.VN = multiplyMatrices4x4(VN, n);
	}
		
	public void trans3D(int x, int y, int z) {
		double temp[][] = new double[4][4];
		temp[0][0] = 1;
		temp[1][1] = 1;
		temp[2][2] = 1;
		temp[3][3] = 1;
		temp[3][0] = x;
		temp[3][1] = y;
		temp[3][2] = z;
		this.t = multiplyMatrices4x4(this.t, temp);
	}
	
	public void scale3D(double x, double y, double z) {
		double temp[][] = new double[4][4];
		temp[0][0] = x;
		temp[1][1] = y;
		temp[2][2] = z;
		temp[3][3] = 1;
		this.t = multiplyMatrices4x4(this.t, temp);
	}
	
	public void rotate3D(double angle, String axis) {
		double temp[][] = new double[4][4];
		if (axis.equalsIgnoreCase("z")) {
			temp[0][0] = Math.cos(Math.toRadians(angle));
			temp[1][1] = Math.cos(Math.toRadians(angle));
			temp[1][0] = -(Math.sin(Math.toRadians(angle)));
			temp[0][1] = Math.sin(Math.toRadians(angle));
			temp[2][2] = 1;
			temp[3][3] = 1;
		} else if (axis.equalsIgnoreCase("y")) {
			temp[0][0] = Math.cos(Math.toRadians(-angle));
			temp[2][2] = Math.cos(Math.toRadians(-angle));
			temp[0][2] = -(Math.sin(Math.toRadians(-angle)));
			temp[2][0] = Math.sin(Math.toRadians(-angle));
			temp[1][1] = 1;
			temp[3][3] = 1;
		} else {
			temp[1][1] = Math.cos(Math.toRadians(-angle));
			temp[2][2] = Math.cos(Math.toRadians(-angle));
			temp[2][1] = -(Math.sin(Math.toRadians(-angle)));
			temp[1][2] = Math.sin(Math.toRadians(-angle));
			temp[0][0] = 1;
			temp[3][3] = 1;
		}
		this.t = multiplyMatrices4x4(this.t, temp);
	}
	
	
	/** Checks whether the user selected to the simple algorithm
	 * or Bresenham's then updates the frame with the corresponding 
	 * algorithm. 
	 * */
	public void paint(Graphics g) {
		bres3D(g);
	}
	
	
	/**
	 * Sets up the JFrame to display the generated lines.
	 */
	public void setUpGUI() {
		setSize(width, height);
		setTitle("Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
		

	public double perspectiveProject(double xy, double z) {
		return (xy/z)*(width/2) + (width/2);
	}
	
	/**
	 * Implementation of the Bresenham Line Drawing Algorithm that uses the provided
	 * randomly generated coordinates from when the frame was constructed to draw the 
	 * number of lines specified by the user. 
	 */
	public void bres3D(Graphics g)
	{
	// pk is initial decision making parameter
	// Note:x1&y1,x2&y2, dx&dy values are interchanged
	// and passed in plotPixel function so
	// it can handle both cases when m>1 & m<1
		int decide;
		int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;
		
		
		for (int i = 0; i < lines.length; i++) {
			
			int ind1 = lines[i][0];
			int ind2 = lines[i][1];
			
			
			double[] point1 = {vertexes[ind1][0], vertexes[ind1][1], vertexes[ind1][2], 1};
			double[] point2 = {vertexes[ind2][0], vertexes[ind2][1], vertexes[ind2][2], 1};
			//System.out.println(point1[0] + " " + point1[1] + " " + point1[2]);
			point1 = multiplyMatrices(point1, this.t);
			point2 = multiplyMatrices(point2, this.t);
			//System.out.println(point1[0] + " " + point1[1] + " " + point1[2]);
			//System.out.println();
			point1 = multiplyMatrices(point1, this.VN);
			point2 = multiplyMatrices(point2, this.VN);
			
			x1 = (int)(perspectiveProject(point1[0], point1[2]) + 0.5);
			x2 = (int)(perspectiveProject(point2[0], point2[2]) + 0.5);
			y1 = (int)(perspectiveProject(point1[1], point1[2]) + 0.5);
			y2 = (int)(perspectiveProject(point2[1], point2[2]) + 0.5);
			
		
		int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        if (dx > dy) {
        	decide = 0;
        } else {
        	decide = 1;
        	int temp = x1;
        	x1 = y1;
        	y1 = temp;
        	temp = x2;
        	x2 = y2;
        	y2 = temp;
        	temp = dx;
        	dx = dy;
        	dy = temp;
        }
		int pk = 2 * dy - dx;
		
		//Main loop
		for (int j = 0; j <= dx-1; j++) {
	        // checking either to decrement or increment the
	        // value if we have to plot from (0,100) to (100,0)
	        if (x1 < x2) {
	        	x1++;
	        } else {
	        	x1--;
	        }
	        if (pk < 0) {
	            // decision value will decide to plot
	            // either  x1 or y1 in x's position
	            if (decide == 0) {
	                g.drawRect(x1, y1,1,1);
	                pk = pk + 2 * dy;
	            }
	            else {
	                //(y1,x1) is passed in xt
	                g.drawRect(y1,x1,1,1);
	                pk = pk + 2 * dy;
	            }
	        }
	        else {
	        	if (y1 < y2) {
		        	y1++;
		        } else {
		        	y1--;
		        }
	            if (decide == 0) {
	 
	            	g.drawRect(x1, y1,1,1);
	            }
	            else {
	            	g.drawRect(y1,x1,1,1);
	            }
	            pk = pk + 2 * dy - 2 * dx;
	        }
	    }
		}


}
}
