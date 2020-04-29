package holiday.garet.gStructure;

import java.io.File;
import java.io.FileInputStream;
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
import org.jnbt.NBTCompression;
import org.jnbt.NBTReader;

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
				NBTReader reader = new NBTReader(new FileInputStream(file), NBTCompression.GZIP);
				reader.beginObject();
				
				this.dataVersion = reader.nextInt(); // DataVersion
				
				reader.beginArray(); // blocks
				while (reader.hasNext()) {
					gBlock b = new gBlock();
					reader.beginObject(); // blockObject
					reader.beginArray(); // pos
					b.setX(reader.nextInt()); // x
					b.setY(reader.nextInt()); // y
					b.setZ(reader.nextInt()); // z
					reader.endArray();
					b.setState(reader.nextInt()); // state
					this.blocks.add(b);
					reader.endObject();
				}
				reader.endArray();
				
				reader.beginArray(); // entities
				while (reader.hasNext()) {
					gEntity e = new gEntity();
					reader.beginObject();
					
					reader.beginArray();
					e.setX(reader.nextDouble());
					e.setY(reader.nextDouble());
					e.setZ(reader.nextDouble());
					reader.endArray();
					
					reader.beginArray();
					e.setBlockX(reader.nextInt());
					e.setBlockY(reader.nextInt());
					e.setBlockZ(reader.nextInt());
					reader.endArray();
					
					e.setEntityData(readEntity(reader));
					
					this.entities.add(e);
					reader.endObject();
				}
				reader.endArray();
				
				reader.beginArray(); // palette
				while (reader.hasNext()) {
					gPalette p = new gPalette();
					reader.beginObject(); // paletteObject
					p.setName(reader.nextString()); // Name
					if (reader.hasNext()) {
						reader.beginObject();
						while (reader.hasNext()) {
							p.setProperty(reader.nextName(), reader.nextString());
						}
						reader.endObject();
					}
					this.palette.add(p);
					reader.endObject();
				}
				reader.endArray();
				
				reader.beginArray();
				this.width = reader.nextInt();
				this.height = reader.nextInt();
				this.depth = reader.nextInt();
				reader.endArray();
				
				reader.endObject();
				reader.close();
			}
		} catch (Exception e) {
			
		}
	}
	
	private gEntityData readEntity(NBTReader reader) {
		try {
			gEntityData e = new gEntityData();
			reader.beginObject();
			
			if (reader.nextName() == "id") {
				e.setId(reader.nextString());
			}
			
			reader.beginArray();
			e.setX(reader.nextDouble());
			e.setY(reader.nextDouble());
			e.setZ(reader.nextDouble());
			reader.endArray();
			
			reader.beginArray();
			e.setDX(reader.nextDouble());
			e.setDY(reader.nextDouble());
			e.setDZ(reader.nextDouble());
			reader.endArray();
			
			reader.beginArray();
			e.setYaw(reader.nextFloat());
			e.setPitch(reader.nextFloat());
			reader.endArray();
			
			e.setFallDistance(reader.nextFloat());
			
			e.setFire(reader.nextShort());
			
			e.setAir(reader.nextShort());
			
			e.setOnGround(reader.nextByte());
			
			if (reader.nextName() == "Dimension") {
				e.setDimension(reader.nextInt());
			}
			
			e.setInvulnerable(reader.nextByte());
			
			e.setPortalCooldown(reader.nextInt());
			
			e.setUUIDMost(reader.nextLong());
			e.setUUIDLeast(reader.nextLong());
			
			if (reader.nextName() == "CustomName") {
				e.setCustomName(reader.nextString());
			}
			
			if (reader.nextName() == "CustomNameVisible") {
				e.setCustomNameVisible(reader.nextByte());
			}
			
			if (reader.nextName() == "Silent") {
				e.setSilent(reader.nextByte());
			}
			
			reader.beginArray();
			List<gEntityData> passengers = new ArrayList<gEntityData>();
			while (reader.hasNext()) {
				passengers.add(readEntity(reader));
			}
			e.setPassengers(passengers);
			reader.endArray();
			
			e.setGlowing(reader.nextByte());
			
			reader.beginArray();
			List<String> tags = new ArrayList<String>();
			while (reader.hasNext()) {
				tags.add(reader.nextString());
			}
			e.setTags(tags);
			reader.endArray();
			
			reader.endObject();
			return e;
		} catch (Exception e) {
			return null;
		}
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
