package holiday.garet.gStructure;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

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
	
	public gStructure() {
		blocks = new ArrayList<gBlock>();
		entities = new ArrayList<gEntity>();
		palette = new ArrayList<gPalette>();
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
}
