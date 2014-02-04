package sanandreasp.mods.EnderStuffPlus.client.registry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundPool;
import net.minecraftforge.client.event.sound.PlayBackgroundMusicEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

@SideOnly(Side.CLIENT)
public class SoundRegistry
{
	SoundPool end_BG_Music;
	
	@ForgeSubscribe
    public void onSound(SoundLoadEvent event) {
        try {
        	addSound(event, "endermiss/", "idle", 5);
        	addSound(event, "endermiss/", "hit", 4);
        	addSound(event, "endermiss/", "death", 1);
        	addSound(event, "enderignis/", "idle", 5);
        	addSound(event, "enderignis/", "hit", 4);
        	addSound(event, "enderignis/", "death", 1);
        	addSound(event, "endernivis/", "idle", 5);
        	addSound(event, "endernivis/", "scream", 4);
        	addSound(event, "endernivis/", "death", 1);
        	addSound(event, "enderray/", "death", 1);
        	addSound(event, "enderray/", "charge", 1);
        	addSound(event, "enderray/", "scream", 4);
        	addSound(event, "enderray/", "moan", 2);
        	addSound(event, "enderavis/", "idle", 2);
        	addSound(event, "enderavis/", "hit", 1);
        	
        	end_BG_Music = new SoundPool(Minecraft.getMinecraft().getResourceManager(), "music", true);
        	
        	end_BG_Music.addSound("enderstuffp:weird_ambiance1.ogg");
        	end_BG_Music.addSound("enderstuffp:weird_ambiance2.ogg");
        } catch (Exception e) {
            System.err.println("Failed to register one or more sounds:");
            e.printStackTrace();
        }
    }

	@ForgeSubscribe
	public void onMusic(PlayBackgroundMusicEvent event) {
		if( Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.dimension == 1 ) {
			event.result = end_BG_Music.getRandomSound();
		}
	}

	private void addSound(SoundLoadEvent event, String path, String fileName, int qty) throws Exception {
		if( qty < 2 ) {
			event.manager.addSound("enderstuffp:"+path+fileName+".ogg");
		} else {
			for( int i = 1; i <= qty; i++ ) {
				event.manager.addSound("enderstuffp:"+path+fileName+String.valueOf(i)+".ogg");
			}
		}
    }
}
