package de.sanandrew.mods.enderstuffplus.registry.event;

import java.util.ArrayList;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraftforge.event.entity.living.LivingDropsEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.enderstuffplus.entity.living.IEnderCreature;
import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;
import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;

public class EntityDropEventInst
{
    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event) {
        EntityLivingBase entity = event.entityLiving;

        if( (entity instanceof EntityEnderman || entity instanceof IEnderCreature)
                && SAPUtils.RANDOM.nextInt(Math.max(1, 30 - (2 << event.lootingLevel))) == 0 )
        {
            event.drops.add(new EntityItem(entity.worldObj, entity.posX, entity.posY + 0.5F, entity.posZ,
                                           new ItemStack(ModItemRegistry.enderFlesh,
                                                         SAPUtils.RANDOM.nextInt(2 + event.lootingLevel) + 1, 0)));
        }

        EntityPlayer player = event.source.getEntity() instanceof EntityPlayer ? ((EntityPlayer) event.source.getEntity()) : null;
        ItemStack itemstack = player != null ? player.getHeldItem() : null;
        if( itemstack != null && itemstack.getItem() == ModItemRegistry.niobSword ) {
            boolean spawnParticles = false;

            ArrayList<EntityItem> dropsCopy = new ArrayList<EntityItem>(event.drops);
            for( EntityItem item : dropsCopy ) {
                ItemStack newStack =
                        EnchantmentHelper.getEnchantmentLevel(ESPModRegistry.enderChestTel.effectId, itemstack) > 0
                            ? SAPUtils.addItemStackToInventory(item.getEntityItem().copy(), player.getInventoryEnderChest())
                            : item.getEntityItem().copy();

                if( newStack != null ) {
                    newStack = SAPUtils.addItemStackToInventory(newStack.copy(), player.inventory);

                    if( newStack == null ) {
                        event.drops.remove(item);
                        spawnParticles = true;
                    } else {
                        item.setEntityItemStack(newStack.copy());
                    }
                } else {
                    event.drops.remove(item);
                    spawnParticles = true;
                }
            }

            if( spawnParticles ) {
//                ESPModRegistry.sendPacketAllRng("fxPortal", event.entityLiving.posX, event.entityLiving.posY,
//                                                event.entityLiving.posZ, 128.0D, player.dimension,
//                                                event.entityLiving.posX, event.entityLiving.posY,
//                                                event.entityLiving.posZ, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F, 20);
            }
        }
    }
}
