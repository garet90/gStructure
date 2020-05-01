package holiday.garet.GStructure;

import java.util.List;

import holiday.garet.GStructure.ItemTag.AttributeModifiersTag;
import holiday.garet.GStructure.ItemTag.BlockTag;
import holiday.garet.GStructure.ItemTag.BookAndQuillTag;
import holiday.garet.GStructure.ItemTag.BucketOfFishTag;
import holiday.garet.GStructure.ItemTag.CrossbowsTag;
import holiday.garet.GStructure.ItemTag.DebugStickTag;
import holiday.garet.GStructure.ItemTag.DisplayPropertiesTag;
import holiday.garet.GStructure.ItemTag.EnchantmentTag;
import holiday.garet.GStructure.ItemTag.EntityTag;
import holiday.garet.GStructure.ItemTag.FireworkStarTag;
import holiday.garet.GStructure.ItemTag.FireworkTag;
import holiday.garet.GStructure.ItemTag.GeneralTag;
import holiday.garet.GStructure.ItemTag.ItemDataTag;
import holiday.garet.GStructure.ItemTag.MapTag;
import holiday.garet.GStructure.ItemTag.PlayerHeadTag;
import holiday.garet.GStructure.ItemTag.PotionEffectsTag;
import holiday.garet.GStructure.ItemTag.SuspiciousStewTag;
import holiday.garet.GStructure.ItemTag.WrittenBookTag;
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
		if (dataTag.containsKey("display")) {
			DisplayPropertiesTag t = new DisplayPropertiesTag();
			t.read(dataTag);
			tags.add(t);
		}
		if (dataTag.containsKey("pages")) {
			if (dataTag.containsKey("title")) {
				WrittenBookTag t = new WrittenBookTag();
				t.read(dataTag);
				tags.add(t);
			} else {
				BookAndQuillTag t = new BookAndQuillTag();
				t.read(dataTag);
				tags.add(t);
			}
		}
		if (dataTag.containsKey("SkullOwner")) {
			PlayerHeadTag t = new PlayerHeadTag();
			t.read(dataTag);
			tags.add(t);
		}
		if (dataTag.containsKey("Fireworks")) {
			FireworkTag t = new FireworkTag();
			t.read(dataTag);
			tags.add(t);
		}
		if (dataTag.containsKey("Explosion")) {
			FireworkStarTag t = new FireworkStarTag();
			t.read(dataTag);
			tags.add(t);
		}
		if (dataTag.containsKey("EntityTag")) {
			if (dataTag.containsKey("BucketVariantTag")) {
				BucketOfFishTag t = new BucketOfFishTag();
				t.read(dataTag);
				tags.add(t);
			} else {
				EntityTag t = new EntityTag();
				t.read(dataTag);
				tags.add(t);
			}
		}
		if (dataTag.containsKey("map")) {
			MapTag t = new MapTag();
			t.read(dataTag);
			tags.add(t);
		}
		if (dataTag.containsKey("Effects")) {
			SuspiciousStewTag t = new SuspiciousStewTag();
			t.read(dataTag);
			tags.add(t);
		}
		if (dataTag.containsKey("DebugProperty")) {
			DebugStickTag t = new DebugStickTag();
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
