public class Map {
	private Terrain terrMap[][]; // array of Terrain objects
	// this part of the map

	public Map() {
		terrMap = new Terrain[10][10]; // hard coded number of Terrain object in map

		for (int i = 0; i < 10; i++) {
			for (int j = 0; i < 10; i++) {
				terrMap = null; 
			}
		}
	}

	// asks if terrain chunk at x, y exists or is null
	public boolean isTerrain(int x, int y) {
		return terrMap[x][y] != null;
	}

	public static destroy(int x, int y) {
		terrMap[x][y] = null;
	}

	public void place(Terrain terr, int row, int col)
	{
		terrMap[row][col] = terr;
	}

	// public Terrain[][] setMap(int xID, int yID, Terrain terrPatch) {
	// 	terrMap[xID][yID] = terrPatch; 
	// 	terrFull = true; 
	// }

	// public boolean(int x, int y) {

	// }

	public Terrain[][] getMap() {
		return terrMap;
	}
}