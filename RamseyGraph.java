import java.awt.Color;
import java.util.List;

import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;;

@SuppressWarnings("serial")
public class RamseyGraph extends GraphicsProgram {

  //width and height of the window
	public static final int APPLICATION_WIDTH = 1600;
	public static final int APPLICATION_HEIGHT = 900;

	//example bitstrings
	//these are not actually used
	public final static String chromosome196 = "110100110101101110000010010001011110101100110001000010100111111001100011111000001010010100001011011010101001110101000001011110101111010100001110010101100001111000110110101101101000010100110010110110001110100111111001100100100101000001001111000011101110111011100010010001101010100110000000101011010101010010110011101000001000010011111011100111101001101001001011110010110010011001011011110000110011110001110011011100111001101111000001100110101100111100011010000011001010001101011001011100101000111100010011110011010011000010100110010000111001100110000000001100000001101011000101011000101111011000100110110111000011100100011101111100101100010101010011111000001100001111010010010100111011110000100011001001100101111111110111101110111110111111001101111001000111001110010110010110101100010011100001011100101001001001100101000000011101000100101011110101001101111101010010011010100001111110111001101111000000001";
	public final static String chromosome1183 = "110011011101101001001010000110101011011010100010100001010100100000000110111000100001011001000100111011000010111110001100000111101101000110111000011011111100001000000010111001011110011000010011111101000000001111010100011011001011010001011111101011101000110100100110011010100000111111100111010111111001000000110110110011100001000001000111001010110010001111111010101000110011101101111100000010100010111011101110010110000010100111000101011011011001111011001000010010101110101000001011101100111011011011100010110101001010100100110000000011000111111101010110111100011011101001010111000001010101110011010101000011010001000001111000011101111010100010100100010001001010111110101000111000001101001011011101100101011101110011101111010101001000110101100011111110110001001110100010010100111111101001001101101011111110110011100011000010010111100000110101111110010110101101001010010001000011010000000110110010110100111";	
	
	private int numNodes;
	private String chromosome;
	private List<List<Integer>> cliques;
	
	/**
	 * Constructor. Takes a chromosome to draw.
	 * @param chromosome
	 */
	public RamseyGraph(String chromosome) {
		this.chromosome = chromosome;
		numNodes = (int) Math.sqrt(chromosome.length() * 2) + 1;
	}
	
	/**
	 * Constructor. Takes a chromosome to draw and an array of cliques
	 * that will be drawn as black.
	 * 
	 * @param chromosome
	 */
	public RamseyGraph(String chromosome, List<List<Integer>> cliques) {
		this(chromosome);
		this.cliques = cliques;
	}
	
	/**
	 * Draws a graph.
	 */
	public void run() {
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		GOval[] vertices = drawVertices(); //draw vertices on screen
		char[] chromosomeArray = chromosome.toCharArray(); //convert bitstring to char array
		boolean[] chromosomeBooleanArray = charToBoolean(chromosomeArray); //convert char array to bool array
		boolean[][] adjacencyMatrix = getAdjMatrixFromBoolArray(chromosomeBooleanArray); //convert bool array to adj mat
		drawEdges(vertices, adjacencyMatrix); //draw edges on screen
	}

	/**
	 * Draws the vertices in a circle based on screen size.
	 * @return  array of drawn vertices
	 */
	public GOval[] drawVertices() {
		GOval[] vertices = new GOval[numNodes];
		double r = Math.min(APPLICATION_WIDTH, APPLICATION_HEIGHT) / 2 - 10;
		double width = 10;
		double height = 10;
		double x = 0;
		double y = 0;
		for (int i = 0; i < numNodes; i++) {
			x = r * Math.cos(2 * Math.PI / numNodes * i);
			y = r * Math.sin(2 * Math.PI / numNodes * i);
			vertices[i] = new GOval(x + APPLICATION_WIDTH / 2, y
					+ APPLICATION_HEIGHT / 2, width, height);
			vertices[i].setFilled(true);
			vertices[i].setColor(Color.BLACK);
			add(vertices[i]);
		}
		return vertices;
	}

	/**
	 * Draws edges with the appropriate colors.
	 * 1 -> Red
	 * 0 -> Blue
	 * 
	 * @param vertices  matrix of vertices that have been drawn
	 * @param adjacencyMatrix  matrix version of graph
	 */
	public void drawEdges(GOval[] vertices, boolean[][] adjacencyMatrix) {
		for (int i = 0; i < numNodes; i++) {
			GOval source = vertices[i];
			for (int j = 0; j < numNodes; j++) {
				if (i != j) {
					boolean gray = false;
					
					if (cliques != null) {
						for (List<Integer> l : cliques) {
							if (l.contains(i) && l.contains(j)) {
								gray = true;
								break;
							}
						}
					}
					
					GOval target = vertices[j];
					GLine edge = new GLine(source.getX() + source.getWidth()/2, source.getY() + source.getHeight()/2,
					target.getX() + source.getWidth()/2, target.getY() + source.getHeight()/2);
					edge.setColor(gray ? Color.GRAY : adjacencyMatrix[i][j] ? Color.RED : Color.BLUE);
					add(edge);
					
					if (gray) {
						edge.sendToBack();
					}
				}
			}
		}
	}
	
	/**
	 * Takes in a 1-dimensional boolean array and returns an adjacency matrix.
	 * 
	 * @param arr  1D bool array made from bitstring
	 * @return  adjacency matrix for use by drawEdges
	 */
	private boolean[][] getAdjMatrixFromBoolArray(boolean[] arr) {
		int idx = 0;
		boolean[][] adj = new boolean[numNodes][numNodes];
		
		for (int i = 0; i < numNodes; i++) {
			for (int j = i + 1; j < numNodes; j++) {
				adj[i][j] = arr[idx];
				adj[j][i] = arr[idx];
				idx++;
			}
		}
		
		return adj;
	}
	
	/**
	 * Maps 1 to true, 0 to false to turn a char array into a bool array
	 */
	private boolean[] charToBoolean(char[] bits) {
		boolean[] result = new boolean[bits.length];
		
		for (int i = 0; i < bits.length; i++) {
			result[i] = bits[i] == '1';
		}
		
		return result;
	}
}
