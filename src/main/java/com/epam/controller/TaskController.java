package com.epam.controller;

import com.epam.model.SubTask;
import com.epam.model.Task;
import com.epam.service.SearchService;
import com.epam.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<Collection<Task>> findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getById(@PathVariable("taskId") String taskId) {
        var task = taskService.findById(taskId);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Task> save(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.save(task));
    }

    @PutMapping
    public ResponseEntity<Task> update(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.update(task));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> delete(@PathVariable("taskId") String taskId) {
        taskService.delete(taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{taskId}/subtasks")
    public ResponseEntity<Task> addSubtask(@PathVariable("taskId") String taskId, @RequestBody SubTask subTask) {
        return ResponseEntity.ok(taskService.addSubTask(taskId, subTask));
    }

    @PutMapping("/{taskId}/subtasks")
    public ResponseEntity<Task> updateSubtasks(@PathVariable("taskId") String taskId,
                                               @RequestBody Collection<SubTask> subTasks) {
        return ResponseEntity.ok(taskService.updateSubTasks(taskId, subTasks));
    }

    @DeleteMapping("/{taskId}/subtasks")
    public ResponseEntity<Void> deleteSubtasks(@PathVariable("taskId") String taskId) {
        taskService.deleteSubTasks(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/overdue")
    public ResponseEntity<Collection<Task>> findOverdueTasks() {
        return ResponseEntity.ok(searchService.findOverdueTasks());
    }

    @GetMapping("/search/category/{category}")
    public ResponseEntity<Collection<Task>> searchByCategory(@PathVariable("category") String category) {
        return ResponseEntity.ok(searchService.searchByCategory(category));
    }

    @GetMapping("/search/description/{description}")
    public ResponseEntity<Collection<Task>> searchByDescription(@PathVariable("description") String description) {
        return ResponseEntity.ok(searchService.searchByDescription(description));
    }

    @GetMapping("/search/subtasks/name/{subTaskName}")
    public ResponseEntity<Collection<Task>> searchBySubtaskName(@PathVariable("subTaskName") String subTaskName) {
        return ResponseEntity.ok(searchService.searchBySubTaskName(subTaskName));
    }

    @GetMapping("/search/subtasks/category/{category}")
    public ResponseEntity<Collection<SubTask>> searchSubtasksByCategory(@PathVariable("category") String category) {
        return ResponseEntity.ok(searchService.searchSubTasksByTaskCategory(category));
    }
}
