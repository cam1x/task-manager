package com.epam.service;

import com.epam.model.SubTask;
import com.epam.model.Task;
import com.epam.repository.TaskRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Collection<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(@NonNull String taskId) {
        return taskRepository.findById(taskId);
    }

    public Task save(@NonNull Task task) {
        return taskRepository.save(task);
    }

    public Task update(@NonNull Task task) {
        if (!taskRepository.existsById(task.getId())) {
            throw new IllegalArgumentException(String.format("Task with id [%s] not found!", task.getId()));
        }
        return taskRepository.save(task);
    }

    public void delete(@NonNull String taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task addSubTask(@NonNull String taskId, @NonNull SubTask subTask) {
        var task = getTask(taskId);
        task.getSubTasks().add(subTask);
        return taskRepository.save(task);
    }

    public Task updateSubTasks(@NonNull String taskId, @NonNull Collection<SubTask> subTasks) {
        var task = getTask(taskId);
        task.setSubTasks(subTasks);
        return taskRepository.save(task);
    }

    public Task deleteSubTasks(@NonNull String taskId) {
        var task = getTask(taskId);
        task.getSubTasks().clear();
        return taskRepository.save(task);
    }

    private Task getTask(String taskId) {
        var foundTask = taskRepository.findById(taskId);

        if (foundTask.isEmpty()) {
            throw new IllegalArgumentException(String.format("Task with id [%s] not found!", taskId));
        }

        return foundTask.get();
    }
}
