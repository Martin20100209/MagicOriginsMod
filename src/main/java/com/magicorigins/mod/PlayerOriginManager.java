package com.magicorigins.mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages player-to-origin mappings based on usernames
 * This class stores the predefined assignments from UserAbilities.txt
 * 
 * @author MagicOrigins Mod Team
 * @version 1.0.0
 */
public class PlayerOriginManager {
    
    /**
     * Map storing username to origin type mappings
     * Key: Minecraft username (case-sensitive)
     * Value: Origin type identifier (lowercase)
     */
    private static final Map<String, String> PLAYER_ORIGIN_MAP = new HashMap<>();
    
    /**
     * Map storing player UUID to origin type for runtime lookup
     * Key: Player UUID
     * Value: Origin type identifier (lowercase)
     */
    private static final Map<UUID, String> UUID_ORIGIN_CACHE = new HashMap<>();
    
    /**
     * Initialize the player origin mappings from the configuration
     * This method populates the username-to-origin mapping based on UserAbilities.txt
     */
    public static void initialize() {
        MagicOriginsMod.LOGGER.info("Initializing player origin mappings...");
        
        // Player mappings based on UserAbilities.txt
        PLAYER_ORIGIN_MAP.put("Fishy01003", "water");
        PLAYER_ORIGIN_MAP.put("52bm", "nature");
        PLAYER_ORIGIN_MAP.put("Green_boii", "swamp");
        PLAYER_ORIGIN_MAP.put("Balo01003", "stone");
        PLAYER_ORIGIN_MAP.put("Temsync", "echo");
        PLAYER_ORIGIN_MAP.put("Polocol", "fire");
        PLAYER_ORIGIN_MAP.put("Snowfester", "frost");
        PLAYER_ORIGIN_MAP.put("Fkoe", "amphibian");
        PLAYER_ORIGIN_MAP.put("_Kitax", "shadow");
        
        MagicOriginsMod.LOGGER.info("Loaded {} player origin mappings", PLAYER_ORIGIN_MAP.size());
        
        // Log all mappings for debugging
        PLAYER_ORIGIN_MAP.forEach((username, origin) -> 
            MagicOriginsMod.LOGGER.debug("Player mapping: {} -> {}", username, origin)
        );
    }
    
    /**
     * Gets the origin type for a player based on their username
     * 
     * @param username The player's Minecraft username
     * @return The origin type identifier, or null if no mapping exists
     */
    public static String getOriginForPlayer(String username) {
        String origin = PLAYER_ORIGIN_MAP.get(username);
        MagicOriginsMod.LOGGER.debug("Origin lookup for {}: {}", username, origin);
        return origin;
    }
    
    /**
     * Caches a player's origin by their UUID for faster runtime lookup
     * 
     * @param playerUUID The player's UUID
     * @param origin The origin type to cache
     */
    public static void cachePlayerOrigin(UUID playerUUID, String origin) {
        UUID_ORIGIN_CACHE.put(playerUUID, origin);
        MagicOriginsMod.LOGGER.debug("Cached origin for UUID {}: {}", playerUUID, origin);
    }
    
    /**
     * Gets a cached origin for a player by UUID
     * 
     * @param playerUUID The player's UUID
     * @return The cached origin type, or null if not cached
     */
    public static String getCachedOrigin(UUID playerUUID) {
        return UUID_ORIGIN_CACHE.get(playerUUID);
    }
    
    /**
     * Checks if a player has a predefined origin mapping
     * 
     * @param username The player's username
     * @return true if the player has a mapping, false otherwise
     */
    public static boolean hasOriginMapping(String username) {
        return PLAYER_ORIGIN_MAP.containsKey(username);
    }
    
    /**
     * Gets the total number of configured player mappings
     * 
     * @return The number of player-origin mappings
     */
    public static int getMappingCount() {
        return PLAYER_ORIGIN_MAP.size();
    }
    
    /**
     * Clears the UUID cache (useful for server restarts)
     */
    public static void clearCache() {
        UUID_ORIGIN_CACHE.clear();
        MagicOriginsMod.LOGGER.debug("Cleared UUID origin cache");
    }
    
    /**
     * Gets all configured usernames
     * 
     * @return Set of all configured usernames
     */
    public static java.util.Set<String> getConfiguredUsernames() {
        return PLAYER_ORIGIN_MAP.keySet();
    }
}