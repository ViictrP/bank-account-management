package com.victorprado.donus.core.entity;

import java.time.LocalDateTime;

public abstract class Upgradable extends Creatable {

    LocalDateTime lastModifiedDate;

    abstract void updateLastMofiedDate();
}
