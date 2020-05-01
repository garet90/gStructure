package holiday.garet.GStructure;

import net.querz.nbt.tag.CompoundTag;

public class GCustomPotionEffect {
	private byte id;
	private byte amplifier;
	private int duration;
	private byte ambient;
	private byte showParticles;
	private byte showIcon;
	
	public void read(CompoundTag tag) {
		id = tag.getByte("Id");
		amplifier = tag.getByte("Amplifier");
		duration = tag.getInt("Duration");
		ambient = tag.getByte("Ambient");
		showParticles = tag.getByte("ShowParticles");
		showIcon = tag.getByte("ShowIcon");
	}
	
	public byte getId() {
		return id;
	}
	
	public byte getAmplifier() {
		return amplifier;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public byte getAmbient() {
		return ambient;
	}
	
	public byte getShowParticles() {
		return showParticles;
	}
	
	public byte getShowIcon() {
		return showIcon;
	}
	
	public static GCustomPotionEffect readNewEffect(CompoundTag tag) {
		GCustomPotionEffect e = new GCustomPotionEffect();
		e.read(tag);
		return e;
	}
}
