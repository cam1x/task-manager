package com.epam.repository;

import com.epam.model.Task;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;

public interface TaskRepository extends MongoRepository<Task, String> {

    Task findByName(String name);

    Collection<Task> findByDueDateBefore(LocalDateTime dateTime);

    Collection<Task> findTaskByCategory(String category);

    @Query(value = "{ 'category': ?0 }", fields = "{'subTasks': 1}")
    Collection<Task> findSubTaskByCategory(String category);

    Collection<Task> findAllBy(TextCriteria textCriteria);
}
