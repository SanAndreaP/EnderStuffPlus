package sanandreasp.mods.EnderStuffPlus.item;

import java.util.List;

import sanandreasp.mods.EnderStuffPlus.entity.item.EntityBait;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlIgnis;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlMiss;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlNivis;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomEnderPearl
    extends Item
{
    @SideOnly(Side.CLIENT)
    private Icon[] icons = new Icon[3];

    public ItemCustomEnderPearl(int id) {
        super(id);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(16);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int damage) {
        return this.icons[damage];
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubItems(int itemId, CreativeTabs tab, List stacks) {
        stacks.add(new ItemStack(this.itemID, 1, 0));
        stacks.add(new ItemStack(this.itemID, 1, 1));
        stacks.add(new ItemStack(this.itemID, 1, 2));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + stack.getItemDamage();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if( !player.capabilities.isCreativeMode ) {
            --stack.stackSize;
        }

        if( !world.isRemote ) {
            switch( stack.getItemDamage() ){
                case 0 :
                    world.spawnEntityInWorld(new EntityPearlNivis(world, player));
                    break;
                case 1 :
                    world.spawnEntityInWorld(new EntityPearlIgnis(world, player));
                    break;
                case 2 :
                    if( player.isSneaking() ) {
                        AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(player.posX - 32, player.posY - 32, player.posZ - 32,
                                                                          player.posX + 32, player.posY + 32, player.posZ + 32);
                        List<EntityBait> baits = world.getEntitiesWithinAABB(EntityBait.class, aabb);
                        for( EntityBait bait : baits ) {
                            if( bait != null && bait.isEntityAlive() ) {
                                bait.setDead();
                            }
                        }
                        world.playSoundAtEntity(player, "fire.ignite", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                        return stack;
                    } else {
                        world.spawnEntityInWorld(new EntityPearlMiss(world, player));
                    }
                    break;
                default :
                    world.spawnEntityInWorld(new EntityEnderPearl(world, player));
            }
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("enderstuffp:pearlNivis");
        this.icons[0] = this.itemIcon;
        this.icons[1] = iconRegister.registerIcon("enderstuffp:pearlIgnis");
        this.icons[2] = iconRegister.registerIcon("enderstuffp:pearlEndermiss");
    }
}
