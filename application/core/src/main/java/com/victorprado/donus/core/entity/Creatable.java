package com.victorprado.donus.core.entity;

import java.time.LocalDateTime;

public abstract class Creatable {

    LocalDateTime createdDate;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    abstract void generateCreatedDate();
}
