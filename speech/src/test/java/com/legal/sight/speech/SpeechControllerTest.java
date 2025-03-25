package com.legal.sight.speech;

import com.legal.sight.speech.controller.SpeechController;
import com.legal.sight.speech.model.Speech;
import com.legal.sight.speech.service.SpeechService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



public class SpeechControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SpeechService speechService;

    @InjectMocks
    private SpeechController speechController;

    private Speech speech1;
    private Speech speech2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(speechController).build();
        speech1 = new Speech("I want to be part of your team.", "Prince", "LinkedIn, LegalSight", LocalDate.of(2025, 3, 25));
        speech1.setId(1L);
        speech2 = new Speech("Please accept my application as a Senior Java Developer", "Carlo", "Technology, Innovation", LocalDate.of(2025, 3, 26));
        speech2.setId(2L);
    }

    @Test
    void testGetAllSpeeches() throws Exception {
        List<Speech> speeches = Arrays.asList(speech1, speech2);
        when(speechService.getAllSpeeches()).thenReturn(speeches);

        mockMvc.perform(get("/speeches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2));
    }

    @Test
    void testAddSpeech() throws Exception {
        when(speechService.addSpeech(any(Speech.class))).thenReturn(speech1);

        mockMvc.perform(post("/speeches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"I want to be part of your team.\", \"author\":\"Prince\", \"keywords\":\"LinkedIn, LegalSight\", \"date\":\"2025-03-25\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    void testUpdateSpeech() throws Exception {
        when(speechService.updateSpeech(eq(1L), any(Speech.class))).thenReturn(speech1);
        mockMvc.perform(put("/speeches/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"Senior Java Developer\", \"author\":\"Prince\", \"keywords\":\"LegalSight\", \"date\":\"2025-03-26\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    void testUpdateSpeech_NotFound() throws Exception {
        when(speechService.updateSpeech(eq(1L), any(Speech.class))).thenReturn(null);

        mockMvc.perform(put("/speeches/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"Senior Java Developer\", \"author\":\"Prince\", \"keywords\":\"LegalSight\", \"date\":\"2025-03-26\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Speech not found"));
    }


    @Test
    void testDeleteSpeech() throws Exception {
        doNothing().when(speechService).deleteSpeech(1L);

        mockMvc.perform(delete("/speeches/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    void testSearchSpeeches() throws Exception {
        when(speechService.searchSpeeches("Prince", null, null, null, null)).thenReturn(Arrays.asList(speech1));

        mockMvc.perform(get("/speeches/search?author=Prince"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1));
    }
}
