package com.rsogs.nukkit.rsmainprefix;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.event.player.PlayerTeleportEvent;

/**
 * Created by Rlxzmdd on 2016/12/25.
 */
public class Listeners implements Listener {
    private MainPrefix mp;
    public Listeners(MainPrefix mp){
        this.mp = mp;
    }
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e){
        mp.isNew(e.getPlayer());
        mp.updatePrefix(e.getPlayer());
    }
    @EventHandler
    public void playerJoinEvent(PlayerRespawnEvent e){
        mp.updatePrefix(e.getPlayer());
    }
    @EventHandler
    public void playerJoinEvent(PlayerTeleportEvent e){
        mp.updatePrefix(e.getPlayer());
    }
    @EventHandler
    public void playerChatEvent(PlayerChatEvent e){
        e.setFormat(mp.getChatPrefix(e.getPlayer(),e.getMessage()));
    }
}
