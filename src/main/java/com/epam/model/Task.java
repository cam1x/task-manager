package com.epam.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Document("tasks")
public class Task {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    @TextIndexed
    private String description;
    private String category;
    private LocalDateTime creationDate;
    private LocalDateTime dueDate;
    private Collection<SubTask> subTasks;
}
