package holiday.garet.GStructure.ItemTag;

import java.util.ArrayList;
import java.util.List;

import holiday.garet.GStructure.GCustomPotionEffect;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;

public class PotionEffectsTag extends ItemDataTag {
	private List<GCustomPotionEffect> customPotionEffects;
	private String potion;
	private int customPotionColor;
	
	public PotionEffectsTag() {
		type = 13;
		customPotionEffects = new ArrayList<GCustomPotionEffect>();
	}
	
	public List<GCustomPotionEffect> getCustomPotionEffects() {
		return customPotionEffects;
	}
	
	public String getPotion() {
		return potion;
	}
	
	public int getCustomPotionColor() {
		return customPotionColor;
	}
	
	public void read(CompoundTag tag) {
		ListTag<CompoundTag> customPotionEffects = tag.getListTag("CustomPotionEffects").asCompoundTagList();
		customPotionEffects.forEach((customPotionEffect) -> {
			this.customPotionEffects.add(GCustomPotionEffect.readNewEffect(customPotionEffect));
		});
	}
}
