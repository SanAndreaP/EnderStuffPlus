package sanandreasp.mods.EnderStuffPlus.client.registry;

import java.util.EnumSet;

import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TickHandlerPlayerClt
    implements ITickHandler
{
    @Override
    public String getLabel() {
        return "ESPPlrTicksCLT";
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if( player.ridingEntity != null && player.ridingEntity instanceof IEnderPet ) {
            IEnderPet pet = (IEnderPet) player.ridingEntity;
            pet.processRiding(player); // TODO: within this method, optimize movement - send jump every tick
        }
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.PLAYER);
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {}
}
