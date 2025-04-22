package com.fiap.videos.controller;

import com.fiap.videos.service.VideoProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoProcessingService processingService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("videoId") Long videoId,
            @RequestParam("userId") Long userId) {

        processingService.processVideo(file, videoId, userId);
        return ResponseEntity.ok("Vídeo enviado e sendo processado.");
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<FileSystemResource> downloadZip(@PathVariable String fileName) {
        String filePath = "temp/" + fileName;
        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            return ResponseEntity.notFound().build();
        }

        FileSystemResource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
