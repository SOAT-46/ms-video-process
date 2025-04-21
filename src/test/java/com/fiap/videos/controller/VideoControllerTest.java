package com.fiap.videos.controller;

import com.fiap.videos.service.VideoProcessingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VideoControllerTest {

    @InjectMocks
    private VideoController controller;

    @Mock
    private VideoProcessingService service;

    @Test
    void testUploadVideo() {

    }

    @Test
    void testDownloadVideo() {

    }
}
