package sanandreasp.mods.EnderStuffPlus.client.registry;

import java.util.EnumSet;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TickHandlerClient implements ITickHandler
{
//    public static long ticksPlayed = 0;

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
//        TickHandlerClient.ticksPlayed++;
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) { }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }

    @Override
    public String getLabel() {
        return "ESPCltTicks";
    }
}
