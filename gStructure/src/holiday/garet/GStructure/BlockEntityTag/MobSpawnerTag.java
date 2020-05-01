package holiday.garet.GStructure.BlockEntityTag;

import java.util.List;

import holiday.garet.GStructure.GEntity;
import holiday.garet.GStructure.GSpawnPotential;

public class MobSpawnerTag extends BlockEntityTag {
	private List<GSpawnPotential> spawnPotentials;
	private GEntity spawnData;
	private short spawnCount;
	private short spawnRange;
	private short delay;
	private short minSpawnDelay;
	private short maxSpawnDelay;
	private short maxNearbyEntities;
	private short requiredPlayerRange;
}
