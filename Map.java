import java.util.LinkedList;

public class Map {
	
	private Terrain terrMap[][]; // array of Terrain objects
	private RedBlackBST<Integer, Actor> dirtTree;
    private LinkedList<Actor> dirt;
    private int count;
    private GameScreen screen;

	public Map(GameScreen screen) {

		this.count = 0;
		this.screen = screen;
		this.dirt = new LinkedList<Actor>();
        this.dirtTree = new RedBlackBST<Integer, Actor>();
		terrMap = new Terrain[100][100]; // hard coded number of Terrain object in map

		for (int i = 0; i < 100; i++) {
			for (int j = 0; i < 100; i++) {
				terrMap = null; 
			}
		}
	}

	// asks if terrain chunk at x, y exists or is null
	public boolean isTerrain(int x, int y) {
		return terrMap[convert(x)][convert(y)] != null;
	}

	public boolean isTerrainIndex(int row, int col) {
		return terrMap[row][col] != null;
	}

	public int convert(int x) {
		return (int) Math.ceil(x / 27.0);
	}

	public void destroy(int x, int y) {
		if (isTerrain(x, y)) {
			int id = terrMap[convert(x)][convert(y)].getID;
			dirtTree.delete(id);
	        Iterable<Integer> keys = dirtTree.keys();
	        dirt = new LinkedList<Terrain>();
	        for (int i : keys) {
	            dirt.add(dirtTree.get(i));
	        }
	        terrMap[x][y] = null;
	        screen.setTerrain(dirt);
	    }
	}

	public void place(int row, int col) {
		if (!isTerrainIndex(row, col));
		Terrain terr = new Terrain(row, col);
		terrMap[row][col] = terr;
		dirtTree.put(new Integer(count), terr);
		dirt.push(terr);
		count++;
	}

	public Iterable getMap() {
		return dirt;
	}
}