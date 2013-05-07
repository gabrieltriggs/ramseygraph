/**
 * Start.java
 * 
 * Initiates the graph drawing.
 * 
 * @author Jon Johnson
 * @version 2013-5-7
 *
 */
public class Start {
  public static void main(String[] args) {
		new Start().start();
	}
	
	/**
	 * Pass the bitstring for the graph you want to draw
	 * to the constructor for graph.
	 */
	public void start() {
		RamseyGraph graph = new RamseyGraph("");
		graph.start();
	}
}
