package holiday.garet.gStructure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.DoubleTag;
import net.querz.nbt.tag.FloatTag;
import net.querz.nbt.tag.IntTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.StringTag;

public class gStructure {
	
	// format from https://minecraft.gamepedia.com/Structure_block_file_format
	
	private int dataVersion;
	
	private List<gBlock> blocks;
	
	private List<gEntity> entities;
	
	private List<gPalette> palette;
	
	private int width;
	private int height;
	private int depth;
	
	Plugin plugin;
	
	public gStructure(Plugin plugin) {
		blocks = new ArrayList<gBlock>();
		entities = new ArrayList<gEntity>();
		palette = new ArrayList<gPalette>();
		this.plugin = plugin;
	}
	
	public int getDataVersion() {
		return dataVersion;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void read(File file) {
		try {
			if (file.exists()) {
				NamedTag nbt = NBTUtil.read(file);
				CompoundTag nbt_t = (CompoundTag) nbt.getTag(); // TODO make sure works
				
				this.dataVersion = nbt_t.getInt("DataVersion"); // DataVersion
				
				ListTag<CompoundTag> blocks = nbt_t.getListTag("blocks").asCompoundTagList(); // blocks
				blocks.forEach((block) -> {
					gBlock b = new gBlock();
					ListTag<IntTag> pos = block.getListTag("pos").asIntTagList();
					b.setX(pos.get(0).asInt()); // x
					b.setY(pos.get(1).asInt()); // y
					b.setZ(pos.get(2).asInt()); // z
					b.setState(block.getInt("state")); // state
					this.blocks.add(b);
					// TODO add nbt
				});
				
				ListTag<CompoundTag> entities = nbt_t.getListTag("entities").asCompoundTagList();
				entities.forEach((entity) -> {
					gEntity e = new gEntity();
					ListTag<DoubleTag> pos = entity.getListTag("pos").asDoubleTagList();
					e.setX(pos.get(0).asDouble());
					e.setY(pos.get(1).asDouble());
					e.setZ(pos.get(2).asDouble());
					ListTag<IntTag> blockPos = entity.getListTag("blockPos").asIntTagList();
					e.setBlockX(blockPos.get(0).asInt());
					e.setBlockY(blockPos.get(1).asInt());
					e.setBlockZ(blockPos.get(2).asInt());
					
					e.setEntityData(readEntity(entity.getCompoundTag("nbt")));
					this.entities.add(e);
				});
				
				ListTag<CompoundTag> palette = nbt_t.getListTag("palette").asCompoundTagList();
				for (int i = 0; i < palette.size(); i++) {
					CompoundTag pal = palette.get(i);
					gPalette p = new gPalette();
					
					p.setName(pal.getString("Name"));
					
					CompoundTag Properties = pal.getCompoundTag("Properties");
					
					Properties.forEach((Property) -> {
						p.setProperty(Property.getKey(), Property.getValue().toString());
					});
					this.palette.add(p);
				}
				
				ListTag<IntTag> size = nbt_t.getListTag("size").asIntTagList();
				this.width = size.get(0).asInt();
				this.height = size.get(1).asInt();
				this.depth = size.get(2).asInt();
			}
		} catch (Exception e) {
			
		}
	}
	
	private gEntityData readEntity(CompoundTag reader) {
		gEntityData e = new gEntityData();
		
		e.setId(reader.getString("id"));
		
		ListTag<DoubleTag> Pos = reader.getListTag("Pos").asDoubleTagList();
		e.setX(Pos.get(0).asDouble());
		e.setY(Pos.get(1).asDouble());
		e.setZ(Pos.get(2).asDouble());

		ListTag<DoubleTag> Motion = reader.getListTag("Motion").asDoubleTagList();
		e.setDX(Motion.get(0).asDouble());
		e.setDY(Motion.get(1).asDouble());
		e.setDZ(Motion.get(2).asDouble());
		
		ListTag<FloatTag> Rotation = reader.getListTag("Rotation").asFloatTagList();
		e.setYaw(Rotation.get(0).asFloat());
		e.setPitch(Rotation.get(1).asFloat());
		
		e.setFallDistance(reader.getFloat("FallDistance"));
		
		e.setFire(reader.getShort("Fire"));
		
		e.setAir(reader.getShort("Air"));
		
		e.setOnGround(reader.getByte("OnGround"));
		
		e.setDimension(reader.getInt("Dimension"));
		
		e.setInvulnerable(reader.getByte("Invulnerable"));
		
		e.setPortalCooldown(reader.getInt("PortalCooldown"));
		
		e.setUUIDMost(reader.getLong("UUIDMost"));
		e.setUUIDLeast(reader.getLong("UUIDLeast"));
		
		e.setCustomName(reader.getString("CustomName"));
		
		e.setCustomNameVisible(reader.getByte("CustomNameVisible"));
		
		e.setSilent(reader.getByte("Silent"));
		
		List<gEntityData> passengers = new ArrayList<gEntityData>();
		ListTag<CompoundTag> Passengers = reader.getListTag("Passengers").asCompoundTagList();
		Passengers.forEach((Passenger) -> {
			passengers.add(readEntity(Passenger));
		});
		e.setPassengers(passengers);
		
		e.setGlowing(reader.getByte("Glowing"));

		List<String> tags = new ArrayList<String>();
		ListTag<StringTag> Tags = reader.getListTag("Tags").asStringTagList();
		Tags.forEach((TagE) -> {
			tags.add(TagE.getValue());
		});
		e.setTags(tags);
		
		return e;
	}
	
	public void generate(Location location) {
		// generate blocks
		for (int i = 0; i < blocks.size(); i++) {
			gBlock block = blocks.get(i);
			Location blockLocation = location.clone().add(new Vector(block.getX(), block.getY(), block.getZ()));
			paint(blockLocation, palette.get(block.getState()));
		}
		// add entities
		for (int i = 0; i < entities.size(); i++) {
			gEntity entity = entities.get(i);
			Location entityLocation = location.clone().add(new Vector(entity.getX(), entity.getY(), entity.getZ()));
			summonEntity(entityLocation, entity.getEntityData());
		}
	}
	
	private void paint(Location location, gPalette palette) {
		World world = location.getWorld();
		Block block = world.getBlockAt(location);
		String material = palette.getName().split(":")[1];
		block.setType(Material.matchMaterial(material));
		palette.getProperties().forEach((name, value) -> {
			block.setMetadata(name, new gMetaDataValue(value, plugin));
		});
	}
	
	private Entity summonEntity(Location location, gEntityData entityData) {
		EntityType entityType = EntityType.valueOf(entityData.getId());
		Entity entity = location.getWorld().spawnEntity(location, entityType);
		entity.setVelocity(new Vector(entityData.getDX(), entityData.getDY(), entityData.getDZ()));
		entity.setRotation(entityData.getYaw(), entityData.getPitch());
		entity.setFallDistance(entityData.getFallDistance());
		entity.setFireTicks(entityData.getFire());
		entity.setInvulnerable(entityData.getInvulnerableAsBoolean());
		entity.setPortalCooldown(entityData.getPortalCooldown());
		entity.setCustomName(entityData.getCustomName());
		entity.setCustomNameVisible(entityData.getCustomNameVisibleAsBoolean());
		entity.setSilent(entityData.getSilentAsBoolean());
		entity.setGlowing(entityData.getGlowingAsBoolean());
		List<gEntityData> passengers = entityData.getPassengers();
		for (int i = 0; i < passengers.size(); i++) {
			entity.addPassenger(summonEntity(location, passengers.get(i)));
		}
		List<String> tags = entityData.getTags();
		for (int i = 0; i < tags.size(); i++) {
			entity.addScoreboardTag(tags.get(i));
		}
		return entity;
	}
}
