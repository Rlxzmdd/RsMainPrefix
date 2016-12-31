package com.rsogs.nukkit.rsmainprefix;

import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;

/**
 * Created by Rlxzmdd on 2016/12/30.
 */
public class CommandSet extends Commander
    {
        public MainPrefix plugin;

    public CommandSet(String cmd, MainPrefix plugin)
        {
            super(cmd);
            this.plugin = plugin;
            setUsage("/set");
            setAliases(new String[]{"set"});
            setDescription("设置玩家的称号");
            setPermission("con.rsogs.nukkit.rsmainprefix.set");
        }

    public boolean execute(final CommandSender sender, String s, String[] args) {
        if(!sender.isOp()){
            sender.sendMessage("你没有权限使用这个");
        }
        if(args.length < 2){
            sender.sendMessage("请输入/prefix [玩家名称] [称号] 为玩家设定称号");
        }
        plugin.setPrefix((args[0]),args[1]);
        sender.sendMessage("设置完成，请查验");
        return true;
    }

        @Override
        public Plugin getPlugin() {
            return plugin;
        }
    }
