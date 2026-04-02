package com.bxb.hamrahi_app.util;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;

public class ImageUtil {

    public static String compressAndEncode(byte[] imageBytes) throws IOException {

        // Read original image
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage originalImage = ImageIO.read(bis);

        if (originalImage == null) {
            return Base64.getEncoder().encodeToString(imageBytes);
        }

        //Resize (maintain aspect ratio)
        int targetWidth = 512;
        int targetHeight = (originalImage.getHeight() * targetWidth) / originalImage.getWidth();

        Image scaledImage = originalImage.getScaledInstance(
                targetWidth, targetHeight, Image.SCALE_SMOOTH
        );

        BufferedImage resizedImage = new BufferedImage(
                targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        // Compress to JPEG
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.75f);

        writer.write(null, new IIOImage(resizedImage, null, null), param);

        ios.close();
        writer.dispose();

        // Convert to Base64
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}