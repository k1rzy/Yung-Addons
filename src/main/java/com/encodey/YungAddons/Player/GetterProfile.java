package com.encodey.YungAddons.Player;

import lombok.Getter;

@Getter
public class GetterProfile {

    @Getter
    private final String username;

    @Getter String uuid;

    public GetterProfile(String username, String uuid) {
        this.username = username;
        this.uuid = uuid;
    }
}
