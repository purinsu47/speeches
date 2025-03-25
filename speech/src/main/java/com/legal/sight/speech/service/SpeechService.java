package com.legal.sight.speech.service;

import com.legal.sight.speech.model.Speech;
import com.legal.sight.speech.repository.SpeechRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SpeechService {
    @Autowired
    private SpeechRepository speechRepository;

    public List<Speech> getAllSpeeches() {
        return speechRepository.findAll();
    }

    public Speech addSpeech(Speech speech) {
        return speechRepository.save(speech);
    }

    public Speech updateSpeech(Long id, Speech speech) {
        if (speechRepository.existsById(id)) {
            speech.setId(id);
            return speechRepository.save(speech);
        }
        return null;
    }

    public void deleteSpeech(Long id) {
        speechRepository.deleteById(id);
    }

    public List<Speech> searchSpeeches(String author, String keywords, String text, LocalDate startDate, LocalDate endDate) {
        if (author != null) return speechRepository.findByAuthorContaining(author);
        if (keywords != null) return speechRepository.findByKeywordsContaining(keywords);
        if (text != null) return speechRepository.findByTextContaining(text);
        if (startDate != null && endDate != null) return speechRepository.findByDateBetween(startDate, endDate);
        return speechRepository.findAll();
    }
}
