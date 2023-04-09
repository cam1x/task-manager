package com.epam.service;

import com.epam.model.SubTask;
import com.epam.model.Task;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final MongoTemplate mongoTemplate;

    public Collection<Task> searchByCategory(@NonNull String category) {
        var query = new Query();
        query.addCriteria(Criteria.where("category").is(category));
        return mongoTemplate.find(query, Task.class);
    }

    public Collection<Task> searchByDescription(@NonNull String description) {
        var query = new Query();
        query.addCriteria(Criteria.where("description").regex(description));
        return mongoTemplate.find(query, Task.class);
    }

    public Collection<Task> searchBySubTaskName(@NonNull String subtaskName) {
        var query = new Query();
        query.addCriteria(Criteria.where("subTasks.name").regex(subtaskName));
        return mongoTemplate.find(query, Task.class);
    }

    public Collection<SubTask> searchSubTasksByTaskCategory(@NonNull String category) {
        var query = new Query();
        query.addCriteria(Criteria.where("category").is(category));
        var tasks = mongoTemplate.find(query, Task.class);
        return tasks.stream()
                .flatMap(task -> task.getSubTasks().stream())
                .collect(Collectors.toList());
    }

    public Collection<Task> findOverdueTasks() {
        var query = new Query();
        query.addCriteria(Criteria.where("dueDate").lt(new java.util.Date()));
        return mongoTemplate.find(query, Task.class);
    }

}
