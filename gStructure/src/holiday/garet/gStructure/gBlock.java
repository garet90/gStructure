package holiday.garet.GStructure;

public class GBlock {
	
	// format from https://minecraft.gamepedia.com/Structure_block_file_format
	
	private int x;
	private int y;
	private int z;
	private int state;
	
	public GBlock(int x, int y, int z, int state) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.state = state;
	}
	
	public GBlock() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.state = 0;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public int getState() {
		return state;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
	public void setState(int state) {
		this.state = state;
	}
}
