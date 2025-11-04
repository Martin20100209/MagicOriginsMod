package com.magicorigins.mod;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javadoc.Deprecated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Magic Origins Mod - A Minecraft Forge mod that automatically assigns origins to players
 * Compatible with Origins mod and adds Warden loot drops
 * 
 * @author MagicOrigins Mod Team
 * @version 1.0.0
 * @since 1.21.x
 */
@Mod(MagicOriginsMod.MOD_ID)
public class MagicOriginsMod {
    
    /**
     * The unique identifier for this mod
     */
    public static final String MOD_ID = "magicorigins";
    
    /**
     * Logger instance for this mod
     */
    public static final Logger LOGGER = LogManager.getLogger();
    
    /**
     * Main constructor for the mod
     * Initializes event handlers and registers components
     */
    public MagicOriginsMod() {
        LOGGER.info("Magic Origins Mod initializing...");
        
        // Register event handlers
        MinecraftForge.EVENT_BUS.register(OriginAssignmentHandler.class);
        MinecraftForge.EVENT_BUS.register(WardenLootHandler.class);
        MinecraftForge.EVENT_BUS.register(PlayerDataHandler.class);
        
        // Initialize player origin mappings
        PlayerOriginManager.initialize();
        
        LOGGER.info("Magic Origins Mod initialized successfully!");
    }
    
    /**
     * Creates a ResourceLocation with this mod's namespace
     * 
     * @param path The path for the resource
     * @return A ResourceLocation with the mod's namespace
     */
    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}