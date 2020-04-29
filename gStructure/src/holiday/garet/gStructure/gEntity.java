package holiday.garet.gStructure;

public class gEntity {
	
	// format from https://minecraft.gamepedia.com/Structure_block_file_format
	
	private double x;
	private double y;
	private double z;
	
	private int blockX;
	private int blockY;
	private int blockZ;

	private gEntityData entityData;
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	public void setBlockX(int blockX) {
		this.blockX = blockX;
	}
	
	public void setBlockY(int blockY) {
		this.blockY = blockY;
	}
	
	public void setBlockZ(int blockZ) {
		this.blockZ = blockZ;
	}
	
	public void setEntityData(gEntityData entityData) {
		this.entityData = entityData;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public int getBlockX() {
		return blockX;
	}
	
	public int getBlockY() {
		return blockY;
	}
	
	public int getBlockZ() {
		return blockZ;
	}
	
	public gEntityData getEntityData() {
		return entityData;
	}
}
