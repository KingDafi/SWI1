package cz.osu.swi1_sm.model.dto;

import java.util.UUID;

public class BorrowRequest {
    private UUID userId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}