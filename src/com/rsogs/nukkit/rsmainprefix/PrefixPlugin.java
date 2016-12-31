package com.rsogs.nukkit.rsmainprefix;

import cn.nukkit.Player;

/**
 * Created by Rlxzmdd on 2016/12/25.
 */
public interface PrefixPlugin {
    String getPrefix();
    String getPlayerPrefix(Player player);
    String getPlayerPrefix(String name);
    //来源插件的名称
    String getComePlugin();
}
