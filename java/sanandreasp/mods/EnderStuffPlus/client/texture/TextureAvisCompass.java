package sanandreasp.mods.EnderStuffPlus.client.texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.mods.EnderStuffPlus.item.ItemAvisCompass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class TextureAvisCompass extends TextureAtlasSprite
{
    public double currentAngle;
    public double angleDelta;
    
	public TextureAvisCompass() {
		super("enderstuffp:compass");
	}

	@Override
    public void updateAnimation() {
        Minecraft mc = Minecraft.getMinecraft();

        if( mc.theWorld != null && mc.thePlayer != null ) {
        	if( ItemAvisCompass.getNearestTile(MathHelper.floor_double(mc.thePlayer.posX), MathHelper.floor_double(mc.thePlayer.posZ)) != null ) {
        		this.updateCompass(mc.theWorld, mc.thePlayer.posX, mc.thePlayer.posZ, (double)mc.thePlayer.rotationYaw, false);
        	} else {
        		super.updateAnimation();
        	}
        } else {
    		super.updateAnimation();
        }
    }
	
    public void updateCompass(World world, double playerX, double playerZ, double playerYaw, boolean notInWorld) {
        double rotation = 0.0D;

        if( world != null && !notInWorld ) {
        	TileEntity avisEgg = ItemAvisCompass.getNearestTile(MathHelper.floor_double(playerX), MathHelper.floor_double(playerZ));
        	double deltaX, deltaZ;
        	if( avisEgg != null ) {
        		deltaX = avisEgg.xCoord + 0.5D - playerX;
        		deltaZ = avisEgg.zCoord + 0.5D - playerZ;
        	} else {
        		deltaX = deltaZ = 0.0D;
        	}
        	
            playerYaw %= 360.0D;
            
            rotation = -((playerYaw - 90.0D) * Math.PI / 180.0D - Math.atan2(deltaZ, deltaX));

            if( !world.provider.isSurfaceWorld() ) {
                rotation = Math.random() * Math.PI * 2.0D;
            }
        }

        double currRotation;

        for( currRotation = rotation - this.currentAngle; currRotation < -Math.PI; currRotation += (Math.PI * 2D) );

        while (currRotation >= Math.PI) {
            currRotation -= (Math.PI * 2D);
        }

        if( currRotation < -1.0D ) {
            currRotation = -1.0D;
        }

        if( currRotation > 1.0D ) {
            currRotation = 1.0D;
        }

        this.angleDelta += currRotation * 0.1D;
        this.angleDelta *= 0.8D;
        this.currentAngle += this.angleDelta;

        int newFrameCnt;

        for( newFrameCnt = (int)((this.currentAngle / (Math.PI * 2D) + 1.0D) * (double)this.framesTextureData.size()) % this.framesTextureData.size(); newFrameCnt < 0; newFrameCnt = (newFrameCnt + this.framesTextureData.size()) % this.framesTextureData.size() );

        if( newFrameCnt != this.frameCounter ) {
            this.frameCounter = newFrameCnt;
            TextureUtil.uploadTextureSub((int[])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
        }
    }
}
