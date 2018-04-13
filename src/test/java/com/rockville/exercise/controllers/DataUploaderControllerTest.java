package com.rockville.exercise.controllers;

import com.rockville.exercise.utils.DataProcessor;
import com.rockville.exercise.utils.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataUploaderControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private DataProcessor processor;


    @Test
    public void uploadTest() throws Exception {
        String success = "success";
        MockMultipartFile firstFile =
                new MockMultipartFile("data", "filename.txt", "text/plain", "name=value".getBytes());

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload")
                .file(firstFile))
                .andExpect(status().is(200))
                .andExpect(content().string(success));
    }

    @Test
    public void NotFounTest() throws Exception {
        String result = "";
        MockMultipartFile firstFile =
                new MockMultipartFile("data", "filename.txt", "text/plain", "name=value".getBytes());

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/any-url")
                .file(firstFile))
                .andExpect(status().is(404))
                .andExpect(content().string(result));
    }

    @Test
    public void errorTest() throws Exception {
        String error = "error";
        MockMultipartFile firstFile =
                new MockMultipartFile("data", "filename.txt", "text/plain", "name=value".getBytes());

        when(processor.process(firstFile)).thenThrow(new IOException());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload")
                .file(firstFile))
                .andExpect(status().is(200))
                .andExpect(content().string(error));
    }
}
