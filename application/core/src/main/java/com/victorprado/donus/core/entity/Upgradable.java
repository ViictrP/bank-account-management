package com.victorprado.donus.core.entity;

import java.time.LocalDateTime;

public abstract class Upgradable extends Creatable {

    LocalDateTime lastModifiedDate;

    public LocalDateTime getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    abstract void updateLastMofiedDate();
}
