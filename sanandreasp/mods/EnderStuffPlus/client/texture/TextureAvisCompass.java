package sanandreasp.mods.EnderStuffPlus.client.texture;

import sanandreasp.mods.EnderStuffPlus.item.ItemAvisCompass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureClock;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TextureAvisCompass extends TextureAtlasSprite {
    /** Current compass heading in radians */
    public double currentAngle;

    /** Speed and direction of compass rotation */
    public double angleDelta;
    
	public TextureAvisCompass() {
		super("enderstuffp:compass");
	}

    public void updateAnimation()
    {
        Minecraft minecraft = Minecraft.getMinecraft();

        if( minecraft.theWorld != null && minecraft.thePlayer != null )
        {
        	if( ItemAvisCompass.getNearestTile(MathHelper.floor_double(minecraft.thePlayer.posX), MathHelper.floor_double(minecraft.thePlayer.posZ)) != null )
        		this.updateCompass(minecraft.theWorld, minecraft.thePlayer.posX, minecraft.thePlayer.posZ, (double)minecraft.thePlayer.rotationYaw, false, false);
        	else
        		super.updateAnimation();
        }
        else
        {
    		super.updateAnimation();
        }
    }
	
    /**
     * Updates the compass based on the given x,z coords and camera direction
     */
    public void updateCompass(World par1World, double par2, double par4, double par6, boolean par8, boolean par9)
    {
        double d3 = 0.0D;

        if( par1World != null && !par8 )
        {
        	TileEntity avisEgg = ItemAvisCompass.getNearestTile(MathHelper.floor_double(par2), MathHelper.floor_double(par4));
        	double d4, d5;
        	if( avisEgg != null ) {
        		d4 = avisEgg.xCoord + 0.5D - par2;
        		d5 = avisEgg.zCoord + 0.5D - par4;
        	} else {
        		d4 = d5 = 0.0D;
        	}
            par6 %= 360.0D;
            d3 = -((par6 - 90.0D) * Math.PI / 180.0D - Math.atan2(d5, d4));

            if( !par1World.provider.isSurfaceWorld() )
            {
                d3 = Math.random() * Math.PI * 2.0D;
            }
        }

        if( par9 )
        {
            this.currentAngle = d3;
        }
        else
        {
            double d6;

            for( d6 = d3 - this.currentAngle; d6 < -Math.PI; d6 += (Math.PI * 2D) )
            {
                ;
            }

            while (d6 >= Math.PI)
            {
                d6 -= (Math.PI * 2D);
            }

            if( d6 < -1.0D )
            {
                d6 = -1.0D;
            }

            if( d6 > 1.0D )
            {
                d6 = 1.0D;
            }

            this.angleDelta += d6 * 0.1D;
            this.angleDelta *= 0.8D;
            this.currentAngle += this.angleDelta;
        }

        int i;

        for (i = (int)((this.currentAngle / (Math.PI * 2D) + 1.0D) * (double)this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size())
        {
            ;
        }

        if (i != this.frameCounter)
        {
            this.frameCounter = i;
            TextureUtil.uploadTextureSub((int[])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
        }
    }
}
