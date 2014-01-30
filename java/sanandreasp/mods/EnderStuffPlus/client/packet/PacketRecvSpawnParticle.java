package sanandreasp.mods.EnderStuffPlus.client.packet;

import java.io.DataInputStream;
import java.io.IOException;
import sanandreasp.mods.EnderStuffPlus.client.particle.ParticleFX_EnderMob;
import sanandreasp.mods.EnderStuffPlus.client.particle.ParticleFX_Rayball;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderIgnis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderNivis;
import sanandreasp.mods.EnderStuffPlus.packet.PacketBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PacketRecvSpawnParticle extends PacketBase {
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
		int eID = iStream.readInt();
		World worldObj = ((EntityPlayer)player).worldObj;
		Entity entity = eID >= 0 ? worldObj.getEntityByID(eID) : null;
		
		byte subID = iStream.readByte();
		
		if( entity != null ) {
			double posX = iStream.readDouble(),
				   posY = iStream.readDouble(),
				   posZ = iStream.readDouble(),
				   dataI = iStream.readDouble(),
				   dataII = iStream.readDouble(),
				   dataIII = iStream.readDouble();
			
			switch(subID) {
				case 0:
					for( int k = 0; k < 2 && worldObj.isRemote; k++ ) {
						
						EntityFX part = new ParticleFX_EnderMob(worldObj,
								posX + (rand.nextDouble() - 0.5D) * entity.width,
								(posY + rand.nextDouble() * entity.height) - 0.25D,
								posZ + (rand.nextDouble() - 0.5D) * entity.width,
								(rand.nextDouble() - 0.5D) * 2D, -rand.nextDouble(),
								(rand.nextDouble() - 0.5D) * 2D,
								(float)dataI, (float)dataII, (float)dataIII);
						Minecraft.getMinecraft().effectRenderer.addEffect(part);
					}
					break;
				case 1:
					for( int var3 = 0; var3 < 7; ++var3 ) {
						double var4 = this.rand.nextGaussian() * 0.02D;
						double var6 = this.rand.nextGaussian() * 0.02D;
						double var8 = this.rand.nextGaussian() * 0.02D;
						worldObj.spawnParticle("smoke",
								posX + this.rand.nextFloat() * entity.width * 2.0F - entity.width,
								posY + 0.5D + this.rand.nextFloat() * entity.height,
								posZ + this.rand.nextFloat() * entity.width * 2.0F - entity.width,
								var4, var6, var8);
					}
					break;
				case 2:
					for( int var3 = 0; var3 < 7; ++var3 ) {
						double var4 = this.rand.nextGaussian() * 0.02D;
						double var6 = this.rand.nextGaussian() * 0.02D;
						double var8 = this.rand.nextGaussian() * 0.02D;
						worldObj.spawnParticle("heart",
								posX + this.rand.nextFloat() * entity.width * 2.0F - entity.width,
								posY + 0.5D + this.rand.nextFloat() * entity.height,
								posZ + this.rand.nextFloat() * entity.width * 2.0F - entity.width,
								var4, var6, var8);
					}
					break;
				case 3: {
					float[] clr = {0.0F, 0.0F, 0.0F};
					if( entity instanceof EntityEnderMiss )
						clr = new float[] {1.0F, 0.5F, 0.7F};
					else if( entity instanceof EntityEnderNivis )
						clr = new float[] {0F, 0.2F, 1F};
					else if( entity instanceof EntityEnderIgnis )
						clr = new float[] {1F, 0.75F, 0F};

					EntityFX part = new ParticleFX_EnderMob(worldObj,
							posX, posY, posZ, dataI, dataII,
							dataIII, clr[0], clr[1], clr[2]);
							Minecraft.getMinecraft().effectRenderer.addEffect(part);
				}	break;
				case 4:
					Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleFX_Rayball(worldObj,
							posX, posY + 0.25D, posZ, 1.0F, 0.0F, 1.0F));
					break;
				case 5:
					for( int k = 0; k < 25 && worldObj.isRemote; k++ ) {
						float[] color = new float[] {0.5F, 0F, 1F};
						
						EntityFX part = new ParticleFX_EnderMob(worldObj,
								posX + (rand.nextDouble() - 0.5D) * entity.width,
								(posY + rand.nextDouble() * entity.height) - 0.25D,
								posZ + (rand.nextDouble() - 0.5D) * entity.width,
								(rand.nextDouble() - 0.5D) * 2D, -rand.nextDouble(),
								(rand.nextDouble() - 0.5D) * 2D,
								color[0], color[1], color[2]);
						Minecraft.getMinecraft().effectRenderer.addEffect(part);
					}
					break;
			}
		} else if( eID == -10 ) {
			int x = iStream.readInt();
			int y = iStream.readInt();
			int z = iStream.readInt();
			switch(iStream.readByte()) {
				case 0:
					for( int k = 0; k < 25 && worldObj.isRemote; k++ ) {
						float[] color = new float[] {0.5F, 0F, 1F};
						
						EntityFX part = new ParticleFX_EnderMob(worldObj,
								x + (rand.nextDouble() - 0.5D)+0.5D,
								y + rand.nextDouble() - 0.25D,
								z + (rand.nextDouble() - 0.5D)+0.5D,
								(rand.nextDouble() - 0.5D) * 2D, -rand.nextDouble(),
								(rand.nextDouble() - 0.5D) * 2D,
								color[0], color[1], color[2]);
						Minecraft.getMinecraft().effectRenderer.addEffect(part);
					}
					break;
			}
		}
	}
}
