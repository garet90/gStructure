package holiday.garet.GStructure.BlockEntityTag;

import net.querz.nbt.tag.CompoundTag;

public class BlockEntityTag {
	String id;
	int x;
	int y;
	int z;
	byte keepPacked;
	
	public void read(CompoundTag tag) {
		this.id = tag.getString("id");
		this.x = tag.getInt("x");
		this.y = tag.getInt("y");
		this.z = tag.getInt("z");
		this.keepPacked = tag.getByte("keepPacked");
	}
	
	public String getId() {
		return id;
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
	
	public byte getKeepPacked() {
		return keepPacked;
	}
	
	public static BlockEntityTag getBlockEntityType(CompoundTag tag) {
		return null;
		// TODO
	}
}
