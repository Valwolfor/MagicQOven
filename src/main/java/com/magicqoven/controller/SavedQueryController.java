package com.magicqoven.controller;

import com.magicqoven.entity.SavedQueryBuilt;
import com.magicqoven.entity.User;
import com.magicqoven.service.inter.SavedQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-queries")
@Validated
public class SavedQueryController {

    private final SavedQueryService savedQueryService;

    @Autowired
    public SavedQueryController(SavedQueryService savedQueryService) {
        this.savedQueryService = savedQueryService;
    }

    @PostMapping("/save")
    public ResponseEntity<SavedQueryBuilt> saveQuery(@RequestBody SavedQueryBuilt query) {
        SavedQueryBuilt savedQuery = savedQueryService.saveQuery(query);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuery);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<SavedQueryBuilt>> getQueriesByUser(
            @RequestParam("userId")  Long userId) {
        User user = new User();
        user.setId(userId);
        List<SavedQueryBuilt> queries = savedQueryService.getQueriesByUser(user);
        return ResponseEntity.ok(queries);
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<SavedQueryBuilt>> getQueriesByName(
            @RequestParam("name") String name) {
        List<SavedQueryBuilt> queries = savedQueryService.getQueriesByName(name);
        return ResponseEntity.ok(queries);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SavedQueryBuilt>> getAllQueries() {
        List<SavedQueryBuilt> queries = savedQueryService.getAllQueries();
        return ResponseEntity.ok(queries);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteQuery(
            @PathVariable("id")  Long id) {
        SavedQueryBuilt query = new SavedQueryBuilt();
        query.setId(id);
        savedQueryService.deleteQuery(query);
        return ResponseEntity.noContent().build();
    }
}
