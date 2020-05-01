package holiday.garet.GStructure;

import java.util.List;

import holiday.garet.GStructure.ItemTag.AttributeModifiersTag;
import holiday.garet.GStructure.ItemTag.BlockTag;
import holiday.garet.GStructure.ItemTag.CrossbowsTag;
import holiday.garet.GStructure.ItemTag.EnchantmentTag;
import holiday.garet.GStructure.ItemTag.GeneralTag;
import holiday.garet.GStructure.ItemTag.ItemDataTag;
import holiday.garet.GStructure.ItemTag.PotionEffectsTag;
import net.querz.nbt.tag.CompoundTag;

public class GItem {
	private byte count;
	private byte slot;
	private String id;
	private List<ItemDataTag> tags;
	
	public void read(CompoundTag tag) {
		this.count = tag.getByte("Count");
		this.slot = tag.getByte("Slot");
		this.id = tag.getString("id");
		CompoundTag dataTag = tag.getCompoundTag("tag");
		if (dataTag.containsKey("Damage")) { // general tag
			GeneralTag t = new GeneralTag();
			t.read(dataTag);
			tags.add(t);
		}
		if (dataTag.containsKey("CanPlaceOn") || tag.containsKey("BlockEntityTag") || tag.containsKey("BlockStateTag")) {
			BlockTag t = new BlockTag();
			t.read(dataTag);
			tags.add(t);
		}
		if (dataTag.containsKey("Enchantments")) {
			EnchantmentTag t = new EnchantmentTag();
			t.read(dataTag);
			tags.add(t);
		}
		if (dataTag.containsKey("AttributeModifiers")) {
			AttributeModifiersTag t = new AttributeModifiersTag();
			t.read(dataTag);
			tags.add(t);
		}
		if (dataTag.containsKey("CustomPotionEffects") || dataTag.containsKey("Potion")) {
			PotionEffectsTag t = new PotionEffectsTag();
			t.read(dataTag);
			tags.add(t);
		}
		if (dataTag.containsKey("Charged")) {
			CrossbowsTag t = new CrossbowsTag();
			t.read(dataTag);
			tags.add(t);
		}
	}
	
	public byte getCount() {
		return count;
	}
	
	public byte getSlot() {
		return slot;
	}
	
	public String getId() {
		return id;
	}
	
	public List<ItemDataTag> getTags() {
		return tags;
	}
	
	public static GItem readNewItem(CompoundTag tag) {
		GItem i = new GItem();
		i.read(tag);
		return i;
	}
}
