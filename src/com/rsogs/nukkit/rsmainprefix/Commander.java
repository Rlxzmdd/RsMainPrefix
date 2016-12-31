package com.rsogs.nukkit.rsmainprefix;

import cn.nukkit.command.Command;
import cn.nukkit.command.PluginIdentifiableCommand;

/**
 * Created by Rlxzmdd on 2016/12/30.
 */
abstract class Commander extends Command implements PluginIdentifiableCommand {
    public Commander(String name) {
        super(name);
    }
}
