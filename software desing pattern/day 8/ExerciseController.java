package com.example.demo.controller;

import com.example.demo.model.Exercise;
import com.example.demo.service.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public List<Exercise> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        Optional<Exercise> exercise = exerciseService.getExerciseById(id);
        return exercise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Exercise> getExercisesByUserId(@PathVariable Long userId) {
        return exerciseService.getExercisesByUserId(userId);
    }

    @PostMapping
    public Exercise createExercise(@RequestBody Exercise exercise) {
        return exerciseService.saveExercise(exercise);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @RequestBody Exercise exerciseDetails) {
        Optional<Exercise> optionalExercise = exerciseService.getExerciseById(id);
        if (optionalExercise.isPresent()) {
            Exercise exercise = optionalExercise.get();
            exercise.setDate(exerciseDetails.getDate());
            exercise.setExerciseType(exerciseDetails.getExerciseType());
            exercise.setDuration(exerciseDetails.getDuration());
            exercise.setCaloriesBurned(exerciseDetails.getCaloriesBurned());
            exercise.setIntensity(exerciseDetails.getIntensity());
            exercise.setNotes(exerciseDetails.getNotes());
            final Exercise updatedExercise = exerciseService.saveExercise(exercise);
            return ResponseEntity.ok(updatedExercise);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        Optional<Exercise> optionalExercise = exerciseService.getExerciseById(id);
        if (optionalExercise.isPresent()) {
            exerciseService.deleteExercise(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
