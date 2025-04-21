package com.fiap.videos.util;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VideoUtils {
    public static void extractFrame(String videoPath, String outputImagePath, double seconds) throws IOException {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath)) {
            grabber.start();

            int totalFrames = grabber.getLengthInFrames();
            double fps = grabber.getFrameRate();
            int targetFrameNumber = (int) (seconds * fps);

            Frame frame = null;
            for (int i = 0; i <= targetFrameNumber && i < totalFrames; i++) {
                frame = grabber.grabImage();
            }

            if (frame != null && frame.image != null) {
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage bi = converter.convert(frame);
                ImageIO.write(bi, "jpg", new File(outputImagePath));
            }

            grabber.stop();
        }
    }
}
