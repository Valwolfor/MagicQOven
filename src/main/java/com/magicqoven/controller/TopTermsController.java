package com.magicqoven.controller;

import com.magicqoven.entity.TopTerms;
import com.magicqoven.service.inter.TopTermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/top-terms")
public class TopTermsController {

    private final TopTermsService topTermsService;

    @Autowired
    public TopTermsController(TopTermsService topTermsService) {
        this.topTermsService = topTermsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TopTerms>> getAllTopTerms() {
        List<TopTerms> topTerms = topTermsService.getAllData();
        return new ResponseEntity<>(topTerms, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addTopTerm(@RequestBody TopTerms topTerm) {
        topTermsService.saveData(topTerm);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/addAll")
    public ResponseEntity<Void> addAllTopTerms(@RequestBody List<TopTerms> topTerms) {
        topTermsService.saveAllData(topTerms);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/between")
    public ResponseEntity<List<TopTerms>> getBetweenWeekAndRefreshWeek(@RequestParam("week") LocalDate week, @RequestParam("refresh") LocalDate refresh) {
        List<TopTerms> topTerms = topTermsService.getBetweenWeekAndRefreshWeeK(week, refresh);
        return new ResponseEntity<>(topTerms, HttpStatus.OK);
    }

    // Otros endpoints para buscar por otros criterios como ID, nombre, término, puntaje, rango, etc.

    // Manejo de excepciones global
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
