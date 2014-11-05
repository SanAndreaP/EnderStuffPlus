package de.sanandrew.mods.enderstuffp.entity.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityItemTantal
        extends EntityItem
{
    public EntityItemTantal(World world) {
        super(world);
    }

    public EntityItemTantal(World world, EntityItem oldEntity, ItemStack stack) {
        super(world, oldEntity.posX, oldEntity.posY, oldEntity.posZ, stack);
        this.motionX = oldEntity.motionX;
        this.motionY = oldEntity.motionY;
        this.motionZ = oldEntity.motionZ;
        this.age = oldEntity.age;
        this.delayBeforeCanPickup = oldEntity.delayBeforeCanPickup;
        this.hoverStart = oldEntity.hoverStart;
        this.lifespan = oldEntity.lifespan;
    }

    @Override
    public boolean attackEntityFrom(DamageSource dmgSource, float attackPts) {
        return !dmgSource.isFireDamage() && super.attackEntityFrom(dmgSource, attackPts);
    }
}
