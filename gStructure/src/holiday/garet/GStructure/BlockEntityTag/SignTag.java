package holiday.garet.GStructure.BlockEntityTag;

import net.querz.nbt.tag.CompoundTag;

public class SignTag extends BlockEntityTag {
	private String color;
	private String text1;
	private String text2;
	private String text3;
	private String text4;
	
	public String getColor() {
		return color;
	}
	
	public String getText1() {
		return text1;
	}
	
	public String getText2() {
		return text2;
	}
	
	public String getText3() {
		return text3;
	}
	
	public String getText4() {
		return text4;
	}
	
	public void read(CompoundTag tag) {
		super.read(tag);
		color = tag.getString("Color");
		text1 = tag.getString("Text1");
		text2 = tag.getString("Text2");
		text3 = tag.getString("Text3");
		text4 = tag.getString("Text4");
	}
}
