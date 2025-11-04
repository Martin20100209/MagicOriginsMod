package com.magicorigins.mod;

import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles Warden loot drops modification
 * Ensures Wardens drop Totem of Undying with 100% chance
 * 
 * @author MagicOrigins Mod Team
 * @version 1.0.0
 */
@Mod.EventBusSubscriber(modid = MagicOriginsMod.MOD_ID)
public class WardenLootHandler {
    
    /**
     * Handles mob death events to modify Warden loot drops
     * Adds Totem of Undying to Warden drops with 100% chance
     * 
     * @param event The LivingDropsEvent containing drop information
     */
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        // Check if the entity that died is a Warden
        if (!(event.getEntity() instanceof Warden warden)) {
            return; // Only handle Warden deaths
        }
        
        Level level = warden.level();
        if (level.isClientSide()) {
            return; // Only handle server-side
        }
        
        MagicOriginsMod.LOGGER.info("Warden died - adding guaranteed Totem of Undying drop");
        
        try {
            // Create Totem of Undying item stack
            ItemStack totemStack = new ItemStack(Items.TOTEM_OF_UNDYING, 1);
            
            // Create item entity at Warden's position
            net.minecraft.world.entity.item.ItemEntity totemEntity = 
                new net.minecraft.world.entity.item.ItemEntity(
                    level, 
                    warden.getX(), 
                    warden.getY(), 
                    warden.getZ(), 
                    totemStack
                );
            
            // Add the item entity to the drops list
            event.getDrops().add(totemEntity);
            
            MagicOriginsMod.LOGGER.info("Successfully added Totem of Undying to Warden drops");
            
        } catch (Exception e) {
            MagicOriginsMod.LOGGER.error("Failed to add Totem of Undying to Warden drops: {}", e.getMessage());
        }
    }
    
    /**
     * Alternative method to handle Warden loot using loot table modification
     * This method demonstrates how to modify loot tables programmatically
     * 
     * Note: This is an alternative approach - use either this OR the LivingDropsEvent method
     */
    public static void modifyWardenLootTable() {
        // This method would be called during mod initialization
        // to modify the Warden's loot table directly
        
        MagicOriginsMod.LOGGER.debug("Warden loot table modification method called");
        
        // Loot table modification code would go here
        // This is more complex and requires working with Minecraft's loot system
        
        /*
        Example approach:
        1. Get the Warden's loot table
        2. Add a new loot pool with 100% chance for Totem of Undying
        3. Register the modified loot table
        
        LootTable wardenTable = level.getServer().getLootData()
            .getLootTable(EntityType.WARDEN.getDefaultLootTable());
        // Modify table...
        */
    }
    
    /**
     * Utility method to create enhanced Totem of Undying
     * This could be extended to create custom totems with special properties
     * 
     * @return ItemStack containing the totem
     */
    private static ItemStack createEnhancedTotem() {
        ItemStack totem = new ItemStack(Items.TOTEM_OF_UNDYING, 1);
        
        // Could add custom NBT data here for enhanced properties
        // Example: totem.getOrCreateTag().putString("magicorigins:source", "warden");
        
        return totem;
    }
    
    /**
     * Checks if a Warden should drop enhanced loot
     * Could be extended to check for specific conditions
     * 
     * @param warden The Warden entity
     * @return true if enhanced loot should drop
     */
    private static boolean shouldDropEnhancedLoot(Warden warden) {
        // Always return true for 100% drop chance
        // Could be modified to check for specific conditions like:
        // - Player who killed it
        // - Location where killed
        // - Time of day
        // - etc.
        
        return true;
    }
}