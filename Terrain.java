public class Terrain extends Actor {
	private double radius, width, height;
	private int xID, yID; 

	public Terrain (double x, double y, String imgName, int xID, int yID) {
		// takes x and y value that signify the left, bottom corner of a Terrain block
		super(x, y, imgName);
		xID = this.xID;
		yID = this.yID;

		radius = 5.0; // hard coded radius
		width = this.x + 10;
		height = this.y + 10;
	}

	public static markMap() {
		Map.setMap(xID, yID, this.Terrain);
	}

	// checks to see if a point is in the bounds of this Terrain 
	public boolean contains(int pointX, pointY) {
		if (pointX >= this.x && pointX <= width) {
			if (pointY >= this.y && pointX <= height)
				return true;
		}
		else return false;
	}
}