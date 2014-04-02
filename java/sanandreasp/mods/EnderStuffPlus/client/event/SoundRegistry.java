package sanandreasp.mods.EnderStuffPlus.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundPool;

import net.minecraftforge.client.event.sound.PlayBackgroundMusicEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SoundRegistry
{
    SoundPool endBgMusic;

    private void addSound(SoundLoadEvent event, String path, String fileName, int qty) throws Exception {
        if( qty < 2 ) {
            event.manager.addSound("enderstuffp:" + path + fileName + ".ogg");
        } else {
            for( int i = 1; i <= qty; i++ ) {
                event.manager.addSound("enderstuffp:" + path + fileName + String.valueOf(i) + ".ogg");
            }
        }
    }

    @ForgeSubscribe
    public void onMusic(PlayBackgroundMusicEvent event) {
        if( Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.dimension == 1 ) {
            event.result = this.endBgMusic.getRandomSound();
        }
    }

    @ForgeSubscribe
    public void onSound(SoundLoadEvent event) {
        try {
            this.addSound(event, "endermiss/", "idle", 5);
            this.addSound(event, "endermiss/", "hit", 4);
            this.addSound(event, "endermiss/", "death", 1);
            this.addSound(event, "enderignis/", "idle", 5);
            this.addSound(event, "enderignis/", "hit", 4);
            this.addSound(event, "enderignis/", "death", 1);
            this.addSound(event, "endernivis/", "idle", 5);
            this.addSound(event, "endernivis/", "scream", 4);
            this.addSound(event, "endernivis/", "death", 1);
            this.addSound(event, "enderray/", "death", 1);
            this.addSound(event, "enderray/", "charge", 1);
            this.addSound(event, "enderray/", "scream", 4);
            this.addSound(event, "enderray/", "moan", 2);
            this.addSound(event, "enderavis/", "idle", 2);
            this.addSound(event, "enderavis/", "hit", 1);

            this.endBgMusic = new SoundPool(Minecraft.getMinecraft().getResourceManager(), "music", true);

            this.endBgMusic.addSound("enderstuffp:weird_ambiance1.ogg");
            this.endBgMusic.addSound("enderstuffp:weird_ambiance2.ogg");
        } catch( Exception e ) {
            System.err.println("Failed to register one or more sounds:");
            e.printStackTrace();
        }
    }
}
