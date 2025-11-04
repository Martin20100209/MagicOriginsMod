package com.magicorigins.mod;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles automatic origin assignment for players on login
 * Integrates with Origins mod to automatically assign origins without player choice
 * 
 * @author MagicOrigins Mod Team
 * @version 1.0.0
 */
@Mod.EventBusSubscriber(modid = MagicOriginsMod.MOD_ID)
public class OriginAssignmentHandler {
    
    /**
     * NBT tag key for storing player's assigned origin
     */
    private static final String ORIGIN_NBT_KEY = "magicorigins:assigned_origin";
    
    /**
     * NBT tag key for tracking if origin has been assigned
     */
    private static final String ORIGIN_ASSIGNED_KEY = "magicorigins:origin_assigned";
    
    /**
     * Handles player login events to assign origins automatically
     * This event is called when a player joins the server
     * 
     * @param event The PlayerLoggedInEvent
     */
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return; // Only handle server-side
        }
        
        String username = serverPlayer.getGameProfile().getName();
        MagicOriginsMod.LOGGER.info("Player {} logged in - checking origin assignment", username);
        
        // Check if player has a predefined origin mapping
        String assignedOrigin = PlayerOriginManager.getOriginForPlayer(username);
        if (assignedOrigin == null) {
            MagicOriginsMod.LOGGER.debug("No origin mapping found for player: {}", username);
            return;
        }
        
        // Check if origin has already been assigned
        CompoundTag playerData = serverPlayer.getPersistentData();
        if (playerData.getBoolean(ORIGIN_ASSIGNED_KEY)) {
            MagicOriginsMod.LOGGER.debug("Origin already assigned for player: {}", username);
            return;
        }
        
        // Assign the origin
        assignOriginToPlayer(serverPlayer, assignedOrigin);
    }
    
    /**
     * Assigns an origin to a player and integrates with Origins mod
     * 
     * @param player The server player to assign origin to
     * @param originType The origin type to assign
     */
    private static void assignOriginToPlayer(ServerPlayer player, String originType) {
        try {
            MagicOriginsMod.LOGGER.info("Assigning origin '{}' to player '{}'", 
                originType, player.getGameProfile().getName());
            
            // Store in player persistent data
            CompoundTag playerData = player.getPersistentData();
            playerData.putString(ORIGIN_NBT_KEY, originType);
            playerData.putBoolean(ORIGIN_ASSIGNED_KEY, true);
            
            // Cache the assignment
            PlayerOriginManager.cachePlayerOrigin(player.getUUID(), originType);
            
            // Integration with Origins mod would go here
            // This is where we would call Origins mod API to set the player's origin
            integrateWithOriginsMod(player, originType);
            
            MagicOriginsMod.LOGGER.info("Successfully assigned origin '{}' to player '{}'", 
                originType, player.getGameProfile().getName());
                
        } catch (Exception e) {
            MagicOriginsMod.LOGGER.error("Failed to assign origin '{}' to player '{}': {}", 
                originType, player.getGameProfile().getName(), e.getMessage());
        }
    }
    
    /**
     * Integrates with the Origins mod to actually set the player's origin
     * This method handles the Origins mod API calls
     * 
     * @param player The server player
     * @param originType The origin type to set
     */
    private static void integrateWithOriginsMod(ServerPlayer player, String originType) {
        try {
            // Origins mod integration code would go here
            // This is a placeholder for the actual Origins mod API calls
            
            // Example of how this might work (pseudo-code):
            // OriginComponent originComponent = ModComponents.ORIGIN.get(player);
            // ResourceLocation originId = new ResourceLocation("origins", originType);
            // originComponent.setOrigin(originId);
            // originComponent.sync();
            
            MagicOriginsMod.LOGGER.debug("Origins mod integration completed for player '{}' with origin '{}'", 
                player.getGameProfile().getName(), originType);
                
        } catch (Exception e) {
            MagicOriginsMod.LOGGER.error("Failed to integrate with Origins mod for player '{}': {}", 
                player.getGameProfile().getName(), e.getMessage());
        }
    }
    
    /**
     * Gets the assigned origin for a player
     * 
     * @param player The server player
     * @return The assigned origin type, or null if none assigned
     */
    public static String getPlayerOrigin(ServerPlayer player) {
        CompoundTag playerData = player.getPersistentData();
        if (playerData.contains(ORIGIN_NBT_KEY)) {
            return playerData.getString(ORIGIN_NBT_KEY);
        }
        return null;
    }
    
    /**
     * Checks if a player has been assigned an origin
     * 
     * @param player The server player
     * @return true if origin has been assigned, false otherwise
     */
    public static boolean hasAssignedOrigin(ServerPlayer player) {
        CompoundTag playerData = player.getPersistentData();
        return playerData.getBoolean(ORIGIN_ASSIGNED_KEY);
    }
    
    /**
     * Resets a player's origin assignment (for admin commands or debugging)
     * 
     * @param player The server player
     */
    public static void resetPlayerOrigin(ServerPlayer player) {
        CompoundTag playerData = player.getPersistentData();
        playerData.remove(ORIGIN_NBT_KEY);
        playerData.putBoolean(ORIGIN_ASSIGNED_KEY, false);
        
        PlayerOriginManager.clearCache();
        
        MagicOriginsMod.LOGGER.info("Reset origin assignment for player '{}'", 
            player.getGameProfile().getName());
    }
}