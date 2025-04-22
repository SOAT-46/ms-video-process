package com.fiap.videos.controller;

import com.fiap.videos.service.VideoProcessingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(VideoController.class)
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoProcessingService service;

    @Test
    void testUploadVideo() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "video.mp4", "video/mp4", "test content".getBytes());

        mockMvc.perform(multipart("/videos/upload")
                        .file(file)
                        .param("videoId", "1")
                        .param("userId", "1")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("Vídeo enviado e sendo processado."));

        Mockito.verify(service).processVideo(file, 1L, 1L);
    }
}