package holiday.garet.GStructure;

import java.util.ArrayList;
import java.util.List;

import net.querz.nbt.tag.CompoundTag;

public class GExplosion {
	private byte flicker;
	private byte trail;
	private byte type;
	private List<Integer> colors;
	private List<Integer> fadeColors;
	
	public GExplosion() {
		colors = new ArrayList<Integer>();
		fadeColors = new ArrayList<Integer>();
	}
	
	public byte getFlicker() {
		return flicker;
	}
	
	public byte getTrail() {
		return trail;
	}
	
	public byte getType() {
		return type;
	}
	
	public List<Integer> getColors() {
		return colors;
	}
	
	public List<Integer> getFadeColors() {
		return fadeColors;
	}
	
	public void read(CompoundTag tag) {
		flicker = tag.getByte("Flicker");
		trail = tag.getByte("Trail");
		type = tag.getByte("Type");
		int[] colors = tag.getIntArray("Colors");
		for (int i = 0; i < colors.length; i++) {
			this.colors.add(colors[i]);
		}
		int[] fadeColors = tag.getIntArray("FadeColors");
		for (int i = 0; i < fadeColors.length; i++) {
			this.fadeColors.add(fadeColors[i]);
		}
	}
	
	public static GExplosion readNewExplosion(CompoundTag tag) {
		GExplosion e = new GExplosion();
		e.read(tag);
		return e;
	}
}
