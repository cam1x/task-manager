package com.epam.model;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;

@Data
public class SubTask {

    @Indexed
    private String name;
    @TextIndexed
    private String description;
}
