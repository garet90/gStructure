package holiday.garet.gStructure;

import java.util.List;

public class gEntityData {
	
	// format from https://minecraft.gamepedia.com/Chunk_format#Entity_format
	
	private String id;
	
	private double x;
	private double y;
	private double z;
	
	private double dX;
	private double dY;
	private double dZ;
	
	private float yaw;
	private float pitch;
	
	private float fallDistance;
	private short fire;
	private short air;
	private byte onGround;
	
	private int dimension;
	private byte invulnerable;
	private int portalCooldown;
	private long UUIDMost;
	private long UUIDLeast;
	
	private String customName;
	
	private byte customNameVisible;
	private byte silent;
	
	private List<gEntityData> passengers;
	
	private byte glowing;
	
	private List<String> tags;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	public void setDX(double dX) {
		this.dX = dX;
	}
	
	public void setDY(double dY) {
		this.dY = dY;
	}
	
	public void setDZ(double dZ) {
		this.dZ = dZ;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public void setFallDistance(float fallDistance) {
		this.fallDistance = fallDistance;
	}
	
	public void setFire(short fire) {
		this.fire = fire;
	}
	
	public void setAir(short air) {
		this.air = air;
	}
	
	public void setOnGround(byte onGround) {
		this.onGround = onGround;
	}
	
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	public void setInvulnerable(byte invulnerable) {
		this.invulnerable = invulnerable;
	}
	
	public void setPortalCooldown(int portalCooldown) {
		this.portalCooldown = portalCooldown;
	}
	
	public void setUUIDMost(long UUIDMost) {
		this.UUIDMost = UUIDMost;
	}
	
	public void setUUIDLeast(long UUIDLeast) {
		this.UUIDLeast = UUIDLeast;
	}
	
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
	public void setCustomNameVisible(byte customNameVisible) {
		this.customNameVisible = customNameVisible;
	}
	
	public void setSilent(byte silent) {
		this.silent = silent;
	}
	
	public void setPassengers(List<gEntityData> passengers) {
		this.passengers = passengers;
	}
	
	public void setGlowing(byte glowing) {
		this.glowing = glowing;
	}
	
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public String getId() {
		return id;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public double getDX() {
		return dX;
	}
	
	public double getDY() {
		return dY;
	}
	
	public double getDZ() {
		return dZ;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public float getFallDistance() {
		return fallDistance;
	}
	
	public short getFire() {
		return fire;
	}
	
	public short getAir() {
		return air;
	}
	
	public byte getOnGround() {
		return onGround;
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public byte getInvulnerable() {
		return invulnerable;
	}
	
	public boolean getInvulnerableAsBoolean() {
		if (invulnerable == 1) {
			return true;
		}
		return false;
	}
	
	public int getPortalCooldown() {
		return portalCooldown;
	}
	
	public long getUUIDMost() {
		return UUIDMost;
	}
	
	public long getUUIDLeast() {
		return UUIDLeast;
	}
	
	public String getCustomName() {
		return customName;
	}
	
	public byte getCustomNameVisible() {
		return customNameVisible;
	}
	
	public boolean getCustomNameVisibleAsBoolean() {
		if (customNameVisible == 1) {
			return true;
		}
		return false;
	}
	
	public byte getSilent() {
		return silent;
	}
	
	public boolean getSilentAsBoolean() {
		if (silent == 1) {
			return true;
		}
		return false;
	}
	
	public List<gEntityData> getPassengers() {
		return passengers;
	}
	
	public byte getGlowing() {
		return glowing;
	}
	
	public boolean getGlowingAsBoolean() {
		if (glowing == 1) {
			return true;
		}
		return false;
	}
	
	public List<String> getTags() {
		return tags;
	}
}
