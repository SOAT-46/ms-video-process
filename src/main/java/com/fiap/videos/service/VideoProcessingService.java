package com.fiap.videos.service;

import com.fiap.videos.util.VideoUtils;
import com.fiap.videos.util.ZipUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class VideoProcessingService {

    private final RestTemplate restTemplate;
    private static final String TEMP_DIR = "temp";
    private static final String VIDEO_API_URL = "http://localhost:8081/api/videos/saveStatus/";

    public VideoProcessingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void processVideo(MultipartFile video, Long videoId, Long userId) {
        try {
            updateStatus(videoId, "PROCESSANDO");

            String id = UUID.randomUUID().toString();
            File dir = new File(TEMP_DIR + "/" + id);
            dir.mkdirs();

            File videoFile = new File(dir, video.getOriginalFilename());
            video.transferTo(videoFile);

            String outputImagePath = new File(dir, "imagem_" + video.getOriginalFilename() + ".jpg").getAbsolutePath();
            VideoUtils.extractFrame(videoFile.getAbsolutePath(), outputImagePath, 1);

            File zipFile = new File(TEMP_DIR + "/" + id + ".zip");
            ZipUtils.zipDirectory(dir, zipFile);

            updateStatus(videoId, "FINALIZADO");
            deleteDirectoryRecursively(dir);
        } catch (Exception e) {
            updateStatus(videoId, "ERRO");
        }
    }

    private void updateStatus(Long videoId, String status) {
        restTemplate.put(VIDEO_API_URL + videoId, "\"" + status + "\"");
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
