package com.example.transactions.controller;


import com.example.transactions.dto.SavingGoalDTO;
import com.example.transactions.service.contract.SavingGoalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savinggoals")
@AllArgsConstructor
@Slf4j
public class SavingGoalController {

    private final SavingGoalService savingGoalService;

    @GetMapping
    public ResponseEntity<List<SavingGoalDTO>> getAllSavingGoals() {
        List<SavingGoalDTO> savingGoals = savingGoalService.getAll();

        log.info("Returning all saving goals for logged user: {}", savingGoals.toString());

        return ResponseEntity
                .ok(savingGoals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SavingGoalDTO> getSavingGoalById(@PathVariable Long id) {
        SavingGoalDTO savingGoal = savingGoalService.getById(id);

        log.info("Returning saving goal for logged user: {}", savingGoal.toString());

        return ResponseEntity
                .ok(savingGoal);
    }

    @PostMapping
    public ResponseEntity<SavingGoalDTO> createSavingGoal(@RequestBody SavingGoalDTO savingGoal) {
        SavingGoalDTO createdSavingGoal = savingGoalService.create(savingGoal);

        log.info("Created saving goal: {}", createdSavingGoal.toString());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdSavingGoal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavingGoal(@PathVariable Long id) {
        savingGoalService.delete(id);

        log.info("Deleted saving goal with id: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/{id}/update-amount")
    public ResponseEntity<SavingGoalDTO> updateAmount(@PathVariable Long id, @RequestParam double amountAdded) {
        SavingGoalDTO savingGoal = savingGoalService.updateCurrentAmount(id, amountAdded);

        log.info("Updated saving goal {} by amount: {}", savingGoal.toString(), amountAdded);

        return ResponseEntity
                .ok(savingGoal);
    }


}
