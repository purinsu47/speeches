package com.legal.sight.speech.controller;

import com.legal.sight.speech.model.Speech;
import com.legal.sight.speech.service.SpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/speeches")
public class SpeechController {
    @Autowired
    private SpeechService speechService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllSpeeches() {
        List<Speech> speeches = speechService.getAllSpeeches();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", speeches);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addSpeech(@RequestBody Speech speech) {
        Speech newSpeech = speechService.addSpeech(speech);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", newSpeech);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateSpeech(@PathVariable Long id, @RequestBody Speech speech) {
        Speech updatedSpeech = speechService.updateSpeech(id, speech);
        Map<String, Object> response = new HashMap<>();
        if (updatedSpeech != null) {
            response.put("status", "success");
            response.put("data", updatedSpeech);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Speech not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteSpeech(@PathVariable Long id) {
        speechService.deleteSpeech(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Speech deleted successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchSpeeches(@RequestParam(required = false) String author,
                                                              @RequestParam(required = false) String keywords,
                                                              @RequestParam(required = false) String text,
                                                              @RequestParam(required = false) LocalDate startDate,
                                                              @RequestParam(required = false) LocalDate endDate) {
        List<Speech> speeches = speechService.searchSpeeches(author, keywords, text, startDate, endDate);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", speeches);
        return ResponseEntity.ok(response);
    }
}