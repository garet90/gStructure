package holiday.garet.GStructure.BlockEntityTag;

import java.util.List;

import holiday.garet.GStructure.GItem;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;

public class ChestTag extends BlockEntityTag {
	private String customName;
	private String lock;
	private List<GItem> items;
	private String lootTable;
	private long lootTableSeed;
	
	public String getCustomName() {
		return customName;
	}
	
	public String getLock() {
		return lock;
	}
	
	public List<GItem> getItems() {
		return items;
	}
	
	public String getLootTable() {
		return lootTable;
	}
	
	public long getLootTableSeed() {
		return lootTableSeed;
	}
	
	public void read(CompoundTag tag) {
		super.read(tag);
		this.customName = tag.getString("CustomName");
		this.lock = tag.getString("Lock");
		
		ListTag<CompoundTag> items = tag.getListTag("Items").asCompoundTagList();
		items.forEach((item) -> {
			GItem i = GItem.readNewItem(item);
			this.items.add(i);
		});
		
		this.lootTable = tag.getString("LootTable");
		this.lootTableSeed = tag.getLong("LootTableSeed");
	}
}
