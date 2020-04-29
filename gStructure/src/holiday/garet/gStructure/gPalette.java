package holiday.garet.gStructure;

import java.util.HashMap;

public class gPalette {
	
	// format from https://minecraft.gamepedia.com/Structure_block_file_format
	
	String name;
	
	HashMap<String, String> properties;
	
	public gPalette() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setProperty(String key, String value) {
		properties.put(key, value);
	}
}
