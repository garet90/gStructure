package holiday.garet.GStructure;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

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
import net.querz.nbt.tag.Tag;

public class GStructure {
	
	// format from https://minecraft.gamepedia.com/Structure_block_file_format
	
	private int dataVersion;
	
	private List<GBlock> blocks;
	
	private List<GEntity> entities;
	
	private List<GPalette> palette;
	
	private int width;
	private int height;
	private int depth;
	
	Plugin plugin;
	
	public GStructure(Plugin plugin) {
		blocks = new ArrayList<GBlock>();
		entities = new ArrayList<GEntity>();
		palette = new ArrayList<GPalette>();
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
		if (file.exists()) {
			NamedTag nbt = new NamedTag(null, null);
			try {
				nbt = NBTUtil.read(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			CompoundTag nbt_t = (CompoundTag) nbt.getTag();
			
			this.dataVersion = nbt_t.getInt("DataVersion"); // DataVersion
			
			ListTag<CompoundTag> blocks = nbt_t.getListTag("blocks").asCompoundTagList(); // blocks
			blocks.forEach((block) -> {
				GBlock b = new GBlock();
				ListTag<IntTag> pos = block.getListTag("pos").asIntTagList();
				b.setX(pos.get(0).asInt()); // x
				b.setY(pos.get(1).asInt()); // y
				b.setZ(pos.get(2).asInt()); // z
				b.setState(block.getInt("state")); // state
				this.blocks.add(b);
				// TODO add nbt
			});
			plugin.getLogger().info("Loaded " + this.blocks.size() + " blocks");
			
			ListTag<CompoundTag> entities = nbt_t.getListTag("entities").asCompoundTagList();
			entities.forEach((entity) -> {
				GEntity e = new GEntity();
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
				// TODO specific entity data
			});
			plugin.getLogger().info("Loaded " + this.entities.size() + " entities");
			
			ListTag<CompoundTag> palette = nbt_t.getListTag("palette").asCompoundTagList();
			palette.forEach((pal) -> {
				GPalette p = new GPalette();
				
				p.setName(pal.getString("Name"));
				
				if (pal.containsKey("Properties")) {
					CompoundTag Properties = pal.getCompoundTag("Properties");
					
					Set<Entry<String, Tag<?>>> props = Properties.entrySet();
					props.forEach((prop) -> {
						p.setProperty(prop.getKey(), ((StringTag)prop.getValue()).getValue());
					});
				}
				this.palette.add(p);
			});
			plugin.getLogger().info("Loaded " + this.palette.size() + " palettes");
			
			ListTag<IntTag> size = nbt_t.getListTag("size").asIntTagList();
			this.width = size.get(0).asInt();
			this.height = size.get(1).asInt();
			this.depth = size.get(2).asInt();
		} else {
			plugin.getLogger().warning("File " + file.getName() + " does not exist!");
		}
	}
	
	private GEntityData readEntity(CompoundTag reader) {
		GEntityData e = new GEntityData();
		
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
		
		List<GEntityData> passengers = new ArrayList<GEntityData>();
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
			GBlock block = blocks.get(i);
			Location blockLocation = location.clone().add(new Vector(block.getX(), block.getY(), block.getZ()));
			paint(blockLocation, palette.get(block.getState()));
		}
		// add entities
		for (int i = 0; i < entities.size(); i++) {
			GEntity entity = entities.get(i);
			Location entityLocation = location.clone().add(new Vector(entity.getX(), entity.getY(), entity.getZ()));
			summonEntity(entityLocation, entity.getEntityData());
		}
	}
	
	private void paint(Location location, GPalette palette) {
		World world = location.getWorld();
		Block block = world.getBlockAt(location);
		String material = palette.getName().split(":")[1];
		block.setType(Material.matchMaterial(material));
		palette.getProperties().forEach((name, value) -> {
			block.setMetadata(name, new GMetaDataValue(value, plugin));
		});
	}
	
	private Entity summonEntity(Location location, GEntityData entityData) {
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
		List<GEntityData> passengers = entityData.getPassengers();
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
