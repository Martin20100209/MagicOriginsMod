package com.magicorigins.mod;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles player data persistence and management
 * Manages saving and loading player-specific mod data
 * 
 * @author MagicOrigins Mod Team
 * @version 1.0.0
 */
@Mod.EventBusSubscriber(modid = MagicOriginsMod.MOD_ID)
public class PlayerDataHandler {
    
    /**
     * Root NBT key for all mod data
     */
    private static final String MOD_DATA_KEY = "magicorigins_data";
    
    /**
     * NBT key for playtime tracking
     */
    private static final String PLAYTIME_KEY = "total_playtime";
    
    /**
     * NBT key for ability unlock progress
     */
    private static final String ABILITY_PROGRESS_KEY = "ability_progress";
    
    /**
     * NBT key for unlocked abilities
     */
    private static final String UNLOCKED_ABILITIES_KEY = "unlocked_abilities";
    
    /**
     * Handles player data loading when player joins
     * 
     * @param event The PlayerLoggedInEvent
     */
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }
        
        String username = serverPlayer.getGameProfile().getName();
        MagicOriginsMod.LOGGER.debug("Loading player data for: {}", username);
        
        // Initialize player data if it doesn't exist
        initializePlayerData(serverPlayer);
        
        // Load and validate player data
        loadPlayerData(serverPlayer);
    }
    
    /**
     * Handles player data saving when player leaves
     * 
     * @param event The PlayerLoggedOutEvent
     */
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }
        
        String username = serverPlayer.getGameProfile().getName();
        MagicOriginsMod.LOGGER.debug("Saving player data for: {}", username);
        
        // Save player data
        savePlayerData(serverPlayer);
    }
    
    /**
     * Initializes default data structure for a new player
     * 
     * @param player The server player to initialize data for
     */
    private static void initializePlayerData(ServerPlayer player) {
        CompoundTag playerData = player.getPersistentData();
        
        if (!playerData.contains(MOD_DATA_KEY)) {
            CompoundTag modData = new CompoundTag();
            
            // Initialize default values
            modData.putLong(PLAYTIME_KEY, 0L);
            modData.putFloat(ABILITY_PROGRESS_KEY, 0.0f);
            modData.putInt(UNLOCKED_ABILITIES_KEY, 0);
            
            playerData.put(MOD_DATA_KEY, modData);
            
            MagicOriginsMod.LOGGER.debug("Initialized player data for: {}", 
                player.getGameProfile().getName());
        }
    }
    
    /**
     * Loads and validates player data from NBT
     * 
     * @param player The server player to load data for
     */
    private static void loadPlayerData(ServerPlayer player) {
        CompoundTag playerData = player.getPersistentData();
        
        if (playerData.contains(MOD_DATA_KEY)) {
            CompoundTag modData = playerData.getCompound(MOD_DATA_KEY);
            
            long playtime = modData.getLong(PLAYTIME_KEY);
            float abilityProgress = modData.getFloat(ABILITY_PROGRESS_KEY);
            int unlockedAbilities = modData.getInt(UNLOCKED_ABILITIES_KEY);
            
            MagicOriginsMod.LOGGER.debug("Loaded data for {}: playtime={}, progress={}, abilities={}", 
                player.getGameProfile().getName(), playtime, abilityProgress, unlockedAbilities);
        }
    }
    
    /**
     * Saves player data to NBT
     * 
     * @param player The server player to save data for
     */
    private static void savePlayerData(ServerPlayer player) {
        // Data is automatically saved to persistent data
        // This method can be extended for additional save operations
        
        MagicOriginsMod.LOGGER.debug("Player data saved for: {}", 
            player.getGameProfile().getName());
    }
    
    /**
     * Gets the mod-specific data compound for a player
     * 
     * @param player The server player
     * @return The mod data compound tag
     */
    public static CompoundTag getModData(ServerPlayer player) {
        CompoundTag playerData = player.getPersistentData();
        
        if (!playerData.contains(MOD_DATA_KEY)) {
            initializePlayerData(player);
        }
        
        return playerData.getCompound(MOD_DATA_KEY);
    }
    
    /**
     * Gets a player's total playtime in minutes
     * 
     * @param player The server player
     * @return Playtime in minutes
     */
    public static long getPlayerPlaytime(ServerPlayer player) {
        CompoundTag modData = getModData(player);
        return modData.getLong(PLAYTIME_KEY);
    }
    
    /**
     * Sets a player's total playtime
     * 
     * @param player The server player
     * @param playtimeMinutes Playtime in minutes
     */
    public static void setPlayerPlaytime(ServerPlayer player, long playtimeMinutes) {
        CompoundTag modData = getModData(player);
        modData.putLong(PLAYTIME_KEY, playtimeMinutes);
    }
    
    /**
     * Gets a player's ability unlock progress percentage
     * 
     * @param player The server player
     * @return Progress as a percentage (0.0 to 100.0)
     */
    public static float getAbilityProgress(ServerPlayer player) {
        CompoundTag modData = getModData(player);
        return modData.getFloat(ABILITY_PROGRESS_KEY);
    }
    
    /**
     * Sets a player's ability unlock progress
     * 
     * @param player The server player
     * @param progress Progress percentage (0.0 to 100.0)
     */
    public static void setAbilityProgress(ServerPlayer player, float progress) {
        CompoundTag modData = getModData(player);
        modData.putFloat(ABILITY_PROGRESS_KEY, progress);
    }
    
    /**
     * Gets the number of abilities a player has unlocked
     * 
     * @param player The server player
     * @return Number of unlocked abilities (0-3)
     */
    public static int getUnlockedAbilities(ServerPlayer player) {
        CompoundTag modData = getModData(player);
        return modData.getInt(UNLOCKED_ABILITIES_KEY);
    }
    
    /**
     * Sets the number of abilities a player has unlocked
     * 
     * @param player The server player
     * @param count Number of unlocked abilities (0-3)
     */
    public static void setUnlockedAbilities(ServerPlayer player, int count) {
        CompoundTag modData = getModData(player);
        modData.putInt(UNLOCKED_ABILITIES_KEY, Math.max(0, Math.min(3, count)));
    }
    
    /**
     * Resets all mod data for a player
     * 
     * @param player The server player
     */
    public static void resetPlayerData(ServerPlayer player) {
        CompoundTag playerData = player.getPersistentData();
        playerData.remove(MOD_DATA_KEY);
        initializePlayerData(player);
        
        MagicOriginsMod.LOGGER.info("Reset all mod data for player: {}", 
            player.getGameProfile().getName());
    }
}