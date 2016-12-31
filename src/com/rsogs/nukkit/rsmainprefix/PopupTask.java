package com.rsogs.nukkit.rsmainprefix;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;

/**
 * Created by Rlxzmdd on 2016/12/31.
 */
public class PopupTask extends PluginTask {
    PopupTask(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        for (Player p : getOwner().getServer().getOnlinePlayers().values()) {
            p.sendPopup(((MainPrefix)getOwner()).updatePopupPrefix(p));
        }
       // getOwner().getLogger().info(i+"");
    }
}
