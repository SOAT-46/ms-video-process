package com.fiap.videos.service;

import com.fiap.videos.util.VideoUtils;
import com.fiap.videos.util.ZipUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.client.RestTemplate;

import java.io.File;

import static org.mockito.Mockito.*;

class VideoProcessingServiceTest {

    private RestTemplate restTemplate;
    private VideoProcessingService service;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        service = new VideoProcessingService(restTemplate);
    }

    @Test
    void testProcessVideoSuccess() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "video.mp4", "video/mp4", "video-content".getBytes());

        try (MockedStatic<VideoUtils> videoUtils = mockStatic(VideoUtils.class);
             MockedStatic<ZipUtils> zipUtils = mockStatic(ZipUtils.class)) {

            videoUtils.when(() -> VideoUtils.extractFrame(anyString(), anyString(), anyInt())).thenAnswer(inv -> null);
            zipUtils.when(() -> ZipUtils.zipDirectory(any(File.class), any(File.class))).thenAnswer(inv -> null);

            service.processVideo(file, 1L, 1L);

            verify(restTemplate, times(2)).put(contains("saveStatus"), any());
        }
    }
}
