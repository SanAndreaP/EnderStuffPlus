/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util.manager;

import cpw.mods.fml.common.FMLLog;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import org.apache.logging.log4j.Level;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public final class IslandManager
{
    private static final Map<Integer, EnumBlockType[][][]> ISLANDS = new ConcurrentHashMap<>(72);
    public static volatile boolean isInitialized = false;
    public static volatile boolean errored = false;
    public static Thread islandInitThread;

    public static void initialize() {
        islandInitThread = new Thread(new InitDelegate(), "EspIslandsInit");
        islandInitThread.start();
    }

    public static EnumBlockType[][][] getRandomIslandShape(Random random) {
        if( !isInitialized ) {
            try {
                FMLLog.log(EnderStuffPlus.MOD_LOG, Level.WARN, "Island Initialization not done yet! waiting for other thread to finish!");
                islandInitThread.join();
            } catch( InterruptedException ex ) {
                FMLLog.log(EnderStuffPlus.MOD_LOG, Level.WARN, ex, "Island generation thread interrupted!");
                errored = true;
            }
        }

        if( errored ) {
            FMLLog.log(EnderStuffPlus.MOD_LOG, Level.WARN, "Island cannot be generated, because an error occurred!");
            return null;
        }

        return ISLANDS.get(random.nextInt(ISLANDS.size()));
    }

    public static enum EnumBlockType
    {
        AIR,
        STONE,
        FEATURE
    }

    private static class InitDelegate
           implements Runnable
    {
        @Override
        public void run() {
            ISLANDS.clear();

            FMLLog.log(EnderStuffPlus.MOD_LOG, Level.INFO, "Starting initialization of island generation");
            long start = System.currentTimeMillis();

            int count = 0;
            URL stream = EnderStuffPlus.class.getResource("/assets/enderstuffp/islands");
            try {
                File islandDir = new File(stream.toURI());
                if( islandDir.isDirectory() ) {
                    File[] files = islandDir.listFiles();
                    if( files != null ) {
                        for( final File imgFile : files ) {
                            BufferedImage origImg = ImageIO.read(imgFile);
                            for( int rot = 0; rot <= 270; rot += 90 ) {
                                float scale = 1.0F;
                                BufferedImage rotatedImg = rotateImg(origImg, rot);
                                EnumBlockType[][][] val = new EnumBlockType[origImg.getWidth()][40][origImg.getHeight()];

                                for( int y = 0; y < 40 && scale > 0.0F; y++, scale *= 0.8F ) {
                                    generateLayer(val, rotatedImg, y, scale);
                                }

                                ISLANDS.put(count++, val);

                                scale = 1.0F;
                                val = new EnumBlockType[origImg.getWidth()][40][origImg.getHeight()];
                                for( int y = 0; y < 40 && scale < 100.0F; y++, scale *= 1.5F ) {
                                    generateLayer(val, rotatedImg, y, 1.0F - scale / 100.0F);

                                }

                                ISLANDS.put(count++, val);
                            }
                        }
                    }
                }
            } catch( IOException | URISyntaxException ex ) {
                FMLLog.log(EnderStuffPlus.MOD_LOG, Level.WARN, ex, "Initialization of island generation errored!");
                errored = true;
                return;
            }

            FMLLog.log(EnderStuffPlus.MOD_LOG, Level.INFO, "Initialization of island generation finished in %d ms, creating %s islands",
                       System.currentTimeMillis() - start, count);

            isInitialized = true;
        }

        private static void generateLayer(EnumBlockType[][][] islandCache, BufferedImage img, int layer, float scale) {
            BufferedImage scaledImg = scaleImg(img, scale);
            int width = scaledImg.getWidth();
            int height = scaledImg.getHeight();

            for( int i = 0; i < width; i++ ) {
                for( int j = 0; j < height; j++ ) {
                    int rgb = scaledImg.getRGB(i, j);
                    switch( rgb & 0xFFFFFF ) {
                        case 0x000000:
                            islandCache[i][layer][j] = EnumBlockType.STONE;
                            break;
                        case 0xFF0000:
                            islandCache[i][layer][j] = EnumBlockType.FEATURE;
                            break;
                        default:
                            islandCache[i][layer][j] = EnumBlockType.AIR;
                    }
                }
            }
        }

        private static BufferedImage rotateImg(Image img, int rotation) {
            double rotRadiants = Math.toRadians(rotation);

            BufferedImage bImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            double locationX = bImg.getWidth() / 2.0F;
            double locationY = bImg.getHeight() / 2.0F;

            AffineTransform tx = new AffineTransform();
            tx.translate(locationX, locationY);
            tx.rotate(rotRadiants);
            tx.translate(-locationX, -locationY);

            Graphics2D bGr = bImg.createGraphics();
            bGr.setColor(Color.WHITE);
            bGr.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
            bGr.drawImage(img, tx, null);
            bGr.dispose();

            return bImg;
        }

        private static BufferedImage scaleImg(Image img, float scale) {
            BufferedImage bImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            double locationX = bImg.getWidth() / 2.0F;
            double locationY = bImg.getHeight() / 2.0F;

            AffineTransform tx = new AffineTransform();
            tx.translate(locationX, locationY);
            tx.scale(scale, scale);
            tx.translate(-locationX, -locationY);

            Graphics2D bGr = bImg.createGraphics();
            bGr.setColor(Color.WHITE);
            bGr.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
            bGr.drawImage(img, tx, null);
            bGr.dispose();

            return bImg;
        }
    }
}
