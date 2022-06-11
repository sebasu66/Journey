package com.sebasu.journey.Interfaces;

import com.sebasu.journey.components.Player;

public interface PlayerAttachable {

    public void attachPlayer(Player player);

    public void detachPlayer();

    public boolean isPlayerAttached();

}
