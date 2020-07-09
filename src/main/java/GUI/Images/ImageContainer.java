package GUI.Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class ImageContainer {

    private final static Map<String, BufferedImage> imageMap = loadImages();
    private static long loadingTime;

    public static Map<String, BufferedImage> loadImages() {

        loadingTime = System.currentTimeMillis();
        ConcurrentHashMap<String, BufferedImage> imageMap = new ConcurrentHashMap<>();
        ExecutorService es = Executors.newFixedThreadPool(2);
        File imageDirectory = new File("Images/");
        if (!imageDirectory.isDirectory()) {
            System.out.println("Image directory error");
        }
        File[] files = imageDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    es.submit(new Runnable(){
                        @Override
                        public void run() {
                            try{
                                if(file.getAbsolutePath().endsWith(".jpg")) {
                                    imageMap.put(file.getName().replace(".jpg",""),ImageIO.read(file));
                                }
                                else if (file.getAbsolutePath().endsWith(".png")) {
                                    imageMap.put(file.getName().replace(".png",""),ImageIO.read(file));
                                }
                            }
                            catch (IOException e)
                            {
                                System.out.println("Cannot load image");
                            }
                        }
                    });
                }
            }
        }
        else {
            System.out.println("Image folder empty!");
        }
        es.shutdown();
        try {
            if(!es.awaitTermination(10L, TimeUnit.SECONDS)) {
                System.out.println("Images did not load successfully!");
                es.shutdownNow();
            }
            loadingTime = System.currentTimeMillis() - loadingTime;
        }
        catch(InterruptedException e) {
            System.out.println("Loading images interrupted!");
        }
        System.out.println(imageMap.size());
        System.out.println(loadingTime);
        return imageMap;
    }

    public static BufferedImage getImage(String imageName) {
        return imageMap.get(imageName);
    }

    public static BufferedImage getResizedImage(String imageName, Dimension dimensions) {

        BufferedImage resizedImage = new BufferedImage(dimensions.width, dimensions.height, imageMap.get(imageName).getType());
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(imageMap.get(imageName), 0, 0, dimensions.width, dimensions.height, null);
        graphics2D.dispose();
        return resizedImage;
    }
    public static BufferedImage getRescaledImage(String imageName, double scaleRatio)
    {
        int newHeight = (int) (scaleRatio*imageMap.get(imageName).getHeight());
        int newWidth = (int) (scaleRatio*imageMap.get(imageName).getWidth());
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, imageMap.get(imageName).getType());
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(imageMap.get(imageName), 0, 0, newWidth, newHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    public static long getLoadingTime() {
        return loadingTime;
    }
}
