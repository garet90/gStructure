package holiday.garet.GStructure.BlockEntityTag;

import java.util.List;

import holiday.garet.GStructure.GItem;

public class HopperTag extends BlockEntityTag {
	private String customName;
	private String lock;
	private List<GItem> items;
	private int transferCooldown;
	private String lootTable;
	private long lootTableSeed;
}
