public class Terrain extends Actor {

	public Terrain (double x, double y, String imgName) {
		// takes x and y value that signify the left, bottom corner of a Terrain block
		super(x, y, imgName);
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