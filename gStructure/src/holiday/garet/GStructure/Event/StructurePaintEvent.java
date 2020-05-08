package holiday.garet.GStructure.Event;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import holiday.garet.GStructure.GPalette;
import holiday.garet.GStructure.BlockEntityTag.BlockEntityTag;

public class StructurePaintEvent extends Event {
	
	boolean cancelled = false;
	Location location;
	GPalette palette;
	BlockEntityTag blockEntityTag;
	
	public StructurePaintEvent(Location location, GPalette palette, BlockEntityTag blockEntityTag) {
		this.location = location;
		this.palette = palette;
		this.blockEntityTag = blockEntityTag;
	}
	
    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

	public boolean getCancelled() {
		return cancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public GPalette getPalette() {
		return palette;
	}
	
	public BlockEntityTag getBlockEntityTag() {
		return blockEntityTag;
	}

}