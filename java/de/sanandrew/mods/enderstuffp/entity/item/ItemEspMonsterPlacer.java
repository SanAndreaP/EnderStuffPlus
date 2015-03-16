/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.entity.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EspCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ItemEspMonsterPlacer
        extends ItemMonsterPlacer
{
    private static final List<Class<? extends Entity>> ESP_ENTITIES = new ArrayList<>(5);
    private static final List<int[]> ESP_ENTITIES_CLR = new ArrayList<>(5);

    public static void registerEntityForEgg(Class<? extends Entity> entityCls, int foreClr, int backClr) {
        ESP_ENTITIES.add(entityCls);
        ESP_ENTITIES_CLR.add(ESP_ENTITIES.indexOf(entityCls), new int[] {foreClr, backClr});
    }

    private static Class<? extends Entity> getEntityClass(int index) {
        if( index < 0 || index > ESP_ENTITIES.size() ) {
            return null;
        }

        return ESP_ENTITIES.get(index);
    }

    private static int[] getEntityColor(int index) {
        if( index < 0 || index > ESP_ENTITIES_CLR.size() ) {
            return null;
        }

        return ESP_ENTITIES_CLR.get(index);
    }

    public static Entity spawnEspCreature(World world, int index, double x, double y, double z) {
        Entity entity = createEntity(index, world);

        if (entity != null && entity instanceof EntityLiving) {
            EntityLiving entityliving = (EntityLiving)entity;
            entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
            entityliving.rotationYawHead = entityliving.rotationYaw;
            entityliving.renderYawOffset = entityliving.rotationYaw;
            entityliving.onSpawnWithEgg(null);
            world.spawnEntityInWorld(entity);
            entityliving.playLivingSound();
        }

        return entity;
    }

    private static Entity createEntity(int index, World world) {
        Entity entity = null;

        try {
            Class<? extends Entity> entityClass = getEntityClass(index);

            if( entityClass != null ) {
                entity = entityClass.getConstructor(World.class).newInstance(world);
            }
        } catch( InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e ) {
            e.printStackTrace();
        }

        if( entity == null ) {
            EnderStuffPlus.MOD_LOG.printf(Level.WARN, "Skipping EnderStuff+ Entity with id %d", index);
        }

        return entity;
    }

    public ItemEspMonsterPlacer() {
        super();
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
        this.setUnlocalizedName("monsterPlacer");
        this.setTextureName("spawn_egg");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String itemName = SAPUtils.translate(this.getUnlocalizedName() + ".name").trim();
        Class<? extends Entity> cls = getEntityClass(stack.getItemDamage());
        String entityName = cls != null ? (String)EntityList.classToStringMapping.get(cls) : "UNKNOWN";

        return itemName + ' ' + SAPUtils.translate("entity." + entityName + ".name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        int[] clr = getEntityColor(stack.getItemDamage());
        return clr != null ? clr[pass > 0 ? 0 : 1] : 0xFFFFFF;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float offX, float offY, float offZ) {
        if( !world.isRemote ) {
            Block block = world.getBlock(x, y, z);
            x += Facing.offsetsXForSide[side];
            y += Facing.offsetsYForSide[side];
            z += Facing.offsetsZForSide[side];
            double shiftY = 0.0D;

            if( side == 1 && block.getRenderType() == 11 ) {
                shiftY = 0.5D;
            }

            Entity entity = spawnEspCreature(world, stack.getItemDamage(), x + 0.5D, y + shiftY, z + 0.5D);

            if( entity != null ) {
                if( entity instanceof EntityLiving && stack.hasDisplayName() ) {
                    ((EntityLiving)entity).setCustomNameTag(stack.getDisplayName());
                }

                if( !player.capabilities.isCreativeMode ) {
                    stack.stackSize--;
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if( !world.isRemote ) {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

            if( movingobjectposition == null ) {
                return stack;
            } else {
                if( movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK ) {
                    int x = movingobjectposition.blockX;
                    int y = movingobjectposition.blockY;
                    int z = movingobjectposition.blockZ;

                    if( !world.canMineBlock(player, x, y, z) ) {
                        return stack;
                    }

                    if( !player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, stack) ) {
                        return stack;
                    }

                    if( world.getBlock(x, y, z) instanceof BlockLiquid ) {
                        Entity entity = spawnEspCreature(world, stack.getItemDamage(), x, y, z);

                        if( entity != null ) {
                            if( entity instanceof EntityLiving && stack.hasDisplayName() ) {
                                ((EntityLiving)entity).setCustomNameTag(stack.getDisplayName());
                            }

                            if( !player.capabilities.isCreativeMode ) {
                                stack.stackSize--;
                            }
                        }
                    }
                }
            }
        }

        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tab, List items) {
        int count = ESP_ENTITIES.size();
        for( int i = 0; i < count; i++ ) {
            items.add(new ItemStack(this, 1, i));
        }
    }
}
