import java.util.LinkedList;

public class Map {
	
	private Terrain[][] terrMap; // array of Terrain objects
	private RedBlackBST<Integer, Terrain> dirtTree;
    private LinkedList<Terrain> dirt;
    private LinkedList<Well> gravity;
    private int count;
    private Well[] chunks;

	public Map() {

		this.count = 0;
		this.dirt = new LinkedList<Terrain>();
		this.gravity = new LinkedList<Well>();
		this.chunks = new Well[10];
        this.dirtTree = new RedBlackBST<Integer, Terrain>();
		this.terrMap = new Terrain[1000][1000]; // hard coded number of Terrain object in map

		for (int i = 0; i < 1000; i++) {
			for (int j = 0; i < 1000; i++) {
				terrMap[i][j] = null; 
			}
		}
		for (int i = 0; i < 10; i++) {
			chunks[i] = new Well();
		}

		int id = 0;
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 5; j++) {
				place(id, i, j);
				chunks[0].add(returnt(i, j));
				id++;
			}
		}

		for (int i = 30; i < 20; i++) {

		}
	}

	// asks if terrain chunk at x, y exists or is null
	public boolean isTerrain(int x, int y) {
		return terrMap[convert(x)][convert(y)] != null;
	}

	public boolean isTerrainIndex(int row, int col) {
		return (terrMap[row][col] != null);
	}

	public int convert(double x) {
		return (int) Math.ceil(x / 27.0);
	}

	public Terrain returnt(int row, int col) {
		return terrMap[row][col];
	}

	public void destroy(int x, int y) {
		if (isTerrain(x, y)) {
			int id = terrMap[convert(x)][convert(y)].getID();
			dirtTree.delete(id);
	        Iterable<Integer> keys = dirtTree.keys();
	        dirt = new LinkedList<Terrain>();
	        for (int i : keys) {
	            dirt.add(dirtTree.get(i));
	        }
	        terrMap[x][y] = null;
	        //screen.setTerrain(dirt);
	    }
	}

	public void place(int id, int row, int col) {
		if (!isTerrainIndex(row, col));
		Terrain terr = new Terrain(id, 27 * row, 27 * col, "images\\dirt.jpg");
		terrMap[row][col] = terr;
		dirtTree.put(new Integer(count), terr); 
		dirt.push(terr);
		count++;
	}

	public Iterable<Terrain> getMap() {
		return dirt;
	}

	public Iterable<Well> getgrav() {
		return gravity;
	}
}