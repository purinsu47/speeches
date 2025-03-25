package com.legal.sight.speech;

import com.legal.sight.speech.model.Speech;
import com.legal.sight.speech.repository.SpeechRepository;
import com.legal.sight.speech.service.SpeechService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SpeechServiceTest {

    @Mock
    private SpeechRepository speechRepository;

    @InjectMocks
    private SpeechService speechService;

    private Speech speech1;
    private Speech speech2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        speech1 = new Speech("Speech Content 1", "Prince Carlo", "Politics, Economy", LocalDate.of(2025, 3, 25));
        speech1.setId(1L);

        speech2 = new Speech("Speech Content 2", "Legal Sight", "Technology, Innovation", LocalDate.of(2025, 3, 26));
        speech2.setId(2L);
    }

    @Test
    void testGetAllSpeeches() {
        when(speechRepository.findAll()).thenReturn(Arrays.asList(speech1, speech2));
        List<Speech> speeches = speechService.getAllSpeeches();
        assertEquals(2, speeches.size());
        verify(speechRepository, times(1)).findAll();
    }

    @Test
    void testAddSpeech() {
        when(speechRepository.save(any(Speech.class))).thenReturn(speech1);
        Speech savedSpeech = speechService.addSpeech(speech1);
        assertNotNull(savedSpeech);
        assertEquals("Prince Carlo", savedSpeech.getAuthor());
    }

    @Test
    void testUpdateSpeechSuccess() {
        when(speechRepository.existsById(1L)).thenReturn(true);
        when(speechRepository.save(any(Speech.class))).thenReturn(speech1);
        Speech updatedSpeech = speechService.updateSpeech(1L, speech1);
        assertNotNull(updatedSpeech);
        assertEquals("Prince Carlo", updatedSpeech.getAuthor());
    }

    @Test
    void testUpdateSpeechNotFound() {
        when(speechRepository.existsById(3L)).thenReturn(false);
        Speech updatedSpeech = speechService.updateSpeech(3L, speech1);
        assertNull(updatedSpeech);
    }

    @Test
    void testDeleteSpeech() {
        doNothing().when(speechRepository).deleteById(1L);
        speechService.deleteSpeech(1L);
        verify(speechRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSearchByAuthorContaining() {
        when(speechRepository.findByAuthorContaining("Prince"))
                .thenReturn(Arrays.asList(speech1));
        List<Speech> result = speechService.searchSpeeches("Prince", null, null, null, null);
        assertEquals(1, result.size());
        assertEquals("Prince Carlo", result.get(0).getAuthor());
    }
}
