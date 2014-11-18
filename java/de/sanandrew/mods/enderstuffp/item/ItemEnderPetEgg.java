package de.sanandrew.mods.enderstuffp.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.entity.living.IEnderPet;
import de.sanandrew.mods.enderstuffp.util.CreativeTabsEnderStuff;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumEnderPetEggInfo;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.List;
import java.util.UUID;

public class ItemEnderPetEgg
    extends Item
{
    public ItemEnderPetEgg() {
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":enderPetEgg");
        this.setCreativeTab(CreativeTabsEnderStuff.ESP_TAB);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    public static boolean spawnEnderPet(World world, ItemStack stack, double posX, double posY, double posZ, UUID playerId) {
        if( !SAPUtils.isIndexInRange(EnumEnderPetEggInfo.VALUES, stack.getItemDamage()) ) {
            return false;
        } else {
            NBTTagCompound nbt = stack.getTagCompound();
            if( nbt != null && nbt.hasKey(EnumEnderPetEggInfo.NBT_ID) ) {
                Class<? extends EntityCreature> clazz = EnumEnderPetEggInfo.getEntityClass(EnumEnderPetEggInfo.getInfo(nbt.getByte(EnumEnderPetEggInfo.NBT_ID)));
                Entity entity = EntityList.createEntityByName((String) EntityList.classToStringMapping.get(clazz), world);

                if( entity instanceof IEnderPet ) {
                    IEnderPet pet = IEnderPet.class.cast(entity);

                    entity.setLocationAndAngles(posX, posY, posZ, world.rand.nextFloat() * 360.0F, 0.0F);
                    pet.readPetFromNBT(nbt);
                    pet.setTamed(true);
                    pet.setOwner(playerId);
                    world.spawnEntityInWorld(entity);
                    pet.getEntity().playLivingSound();
                }

                return entity != null;
            }

            return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List infos, boolean isAdvancedInfo) {
        EnumEnderPetEggInfo.addInformation(EnumEnderPetEggInfo.getInfo(stack.getItemDamage()), stack, infos, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        EnumEnderPetEggInfo info = EnumEnderPetEggInfo.getInfo(stack.getItemDamage());
        return pass == 0 ? info.backColorw : info.foreColor;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int dmg, int pass) {
        return Items.spawn_egg.getIconFromDamageForRenderPass(dmg, pass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tab, List stacks) {
        ItemStack pet = new ItemStack(this, 1);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setByte(EnumEnderPetEggInfo.NBT_ID, (byte) EnumEnderPetEggInfo.ENDERMISS_INFO.ordinal());
        nbt.setFloat(EnumEnderPetEggInfo.NBT_HEALTH, 40.0F);
        nbt.setFloat(EnumEnderPetEggInfo.NBT_MAX_HEALTH, 40.0F);
        nbt.setBoolean(EnumEnderPetEggInfo.NBT_MISS_AVISFEATHER, false);
        nbt.setBoolean(EnumEnderPetEggInfo.NBT_MISS_SPECIAL, false);
        pet.setTagCompound(nbt);
        stacks.add(pet.copy());

        nbt.setBoolean(EnumEnderPetEggInfo.NBT_MISS_SPECIAL, true);
        stacks.add(pet.copy());

        nbt = new NBTTagCompound();
        nbt.setByte(EnumEnderPetEggInfo.NBT_ID, (byte) 1);
        nbt.setFloat(EnumEnderPetEggInfo.NBT_HEALTH, 40.0F);
        nbt.setFloat(EnumEnderPetEggInfo.NBT_MAX_HEALTH, 40.0F);
        nbt.setFloat(EnumEnderPetEggInfo.NBT_AVIS_STAMINA, 10.0F);
        nbt.setBoolean(EnumEnderPetEggInfo.NBT_AVIS_SADDLED, false);
        pet.setTagCompound(nbt);
        stacks.add(pet.copy());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass) {
        return stack.hasTagCompound();
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float offX, float offY, float offZ) {
        if( world.isRemote ) {
            return true;
        } else {
            Block blockId = world.getBlock(x, y, z);
            x += Facing.offsetsXForSide[side];
            y += Facing.offsetsYForSide[side];
            z += Facing.offsetsZForSide[side];
            double yOffset = 0.0D;

            if( side == 1 && blockId == Blocks.fence || blockId == Blocks.nether_brick_fence ) {
                yOffset = 0.5D;
            }

            if( spawnEnderPet(world, stack, x + 0.5D, y + yOffset, z + 0.5D, player.getGameProfile().getId()) ) {
                --stack.stackSize;
            }

            return true;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) { }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
