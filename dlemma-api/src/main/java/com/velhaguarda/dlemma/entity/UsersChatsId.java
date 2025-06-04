package com.velhaguarda.dlemma.entity;

import java.io.Serializable;
import java.util.UUID;
import java.util.Objects;

public class UsersChatsId implements Serializable {
    private UUID user;
    private int dilemma; // ← aqui é o nome correto, pois na entidade é 'dilemma'

    public UsersChatsId() {}

    public UsersChatsId(UUID user, int dilemma) {
        this.user = user;
        this.dilemma = dilemma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsersChatsId)) return false;
        UsersChatsId that = (UsersChatsId) o;
        return Objects.equals(user, that.user) && dilemma == that.dilemma;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, dilemma);
    }
}
