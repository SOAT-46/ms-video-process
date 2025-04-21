package com.fiap.videos.service;

import com.fiap.videos.util.VideoUtils;
import com.fiap.videos.util.ZipUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class VideoProcessingService {

    private static final String TEMP_DIR = "temp";

    public String processVideos(List<MultipartFile> videos) throws IOException {
        String id = UUID.randomUUID().toString();
        String dirPath = TEMP_DIR + "/" + id;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        for (MultipartFile video : videos) {
            String originalFilename = video.getOriginalFilename();
            if (originalFilename == null) {
                originalFilename = "video_" + UUID.randomUUID() + ".mp4";
            }
            File videoFile = new File(dir, originalFilename);
            video.transferTo(videoFile);

            String outputImagePath = new File(dir, "imagem_" + originalFilename + ".jpg").getAbsolutePath();
            VideoUtils.extractFrame(videoFile.getAbsolutePath(), outputImagePath, 1);
        }

        String zipPath = TEMP_DIR + "/" + id + ".zip";
        File zipFile = new File(zipPath);
        ZipUtils.zipDirectory(dir, zipFile);

        deleteDirectoryRecursively(dir);

        return zipPath;
    }

    private void deleteDirectoryRecursively(File directory) {
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                deleteDirectoryRecursively(file);
            }
        }
        directory.delete();
    }
}