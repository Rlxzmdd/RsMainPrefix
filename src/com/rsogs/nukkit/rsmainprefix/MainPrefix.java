package com.rsogs.nukkit.rsmainprefix;

import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainPrefix extends PluginBase implements Listener {

    //其余插件注册称号
    private Set<PrefixPlugin> prefixs = new HashSet<PrefixPlugin>();
    private LinkedHashMap<String, Object> playerprefixs = new LinkedHashMap<String, Object>();

    private Config config;
    private Config playerinfo;

    public void onEnable() {

        this.getServer().getPluginManager().registerEvents(new Listeners(this), this);

        this.saveResource("config.json");
        playerinfo = new Config(new File("plugins/RsMainPrefix/prefix.json"));
        playerprefixs.putAll(playerinfo.getAll());
        config = new Config(new File("plugins/RsMainPrefix/config.json"));
        //  System.out.println(config.getAll());

        this.registerPrefix(new PrefixPlugin() {
            @Override
            public String getPrefix() {
                return "{玩家名称}";
            }

            @Override
            public String getPlayerPrefix(Player player) {
                return player.getName();
            }

            @Override
            public String getPlayerPrefix(String name) {
                return name;
            }

            @Override
            public String getComePlugin() {
                return "RsMainPrefix";
            }
        });
        //血量 公会 模式 地图 权限 货币 手持物品 时间 qq群 结婚
        this.registerPrefix(new PrefixPlugin() {
            @Override
            public String getPrefix() {
                return "{模式}";
            }

            @Override
            public String getPlayerPrefix(Player player) {
                return player.getGamemode() == 1 ? "创造" : "生存";
            }

            @Override
            public String getPlayerPrefix(String name) {
                return getPlayerPrefix(getServer().getPlayer(name));
            }

            @Override
            public String getComePlugin() {
                return "RsMainPrefix";
            }
        });
        this.registerPrefix(new PrefixPlugin() {
            @Override
            public String getPrefix() {
                return "{血量}";
            }

            @Override
            public String getPlayerPrefix(Player player) {
                return String.valueOf(player.getHealth());
            }

            @Override
            public String getPlayerPrefix(String name) {
                return getPlayerPrefix(getServer().getPlayer(name));
            }

            @Override
            public String getComePlugin() {
                return "RsMainPrefix";
            }
        });
        this.registerPrefix(new PrefixPlugin() {
            @Override
            public String getPrefix() {
                return "{地图}";
            }

            @Override
            public String getPlayerPrefix(Player player) {
                return player.getLevel().getName();
            }

            @Override
            public String getPlayerPrefix(String name) {
                return getPlayerPrefix(getServer().getPlayer(name));
            }

            @Override
            public String getComePlugin() {
                return "RsMainPrefix";
            }
        });
        this.registerPrefix(new PrefixPlugin() {
            @Override
            public String getPrefix() {
                return "{权限}";
            }

            @Override
            public String getPlayerPrefix(Player player) {
                return player.isOp() ? "管理员" : "玩家";
            }

            @Override
            public String getPlayerPrefix(String name) {
                return getPlayerPrefix(getServer().getPlayer(name));
            }

            @Override
            public String getComePlugin() {
                return "RsMainPrefix";
            }
        });
        this.registerPrefix(new PrefixPlugin() {
            @Override
            public String getPrefix() {
                return "{时间}";
            }

            @Override
            public String getPlayerPrefix(Player player) {
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                return dateFormat.format(now);
            }

            @Override
            public String getPlayerPrefix(String name) {
                return getPlayerPrefix(getServer().getPlayer(name));
            }

            @Override
            public String getComePlugin() {
                return "RsMainPrefix";
            }
        });
        this.registerPrefix(new PrefixPlugin() {
            @Override
            public String getPrefix() {
                return "{前缀}";
            }

            @Override
            public String getPlayerPrefix(Player player) {
                return this.getPlayerPrefix(player.getName());
            }

            @Override
            public String getPlayerPrefix(String name) {
                return getPrefixByPlayer(name);
            }

            @Override
            public String getComePlugin() {
                return "RsMainPrefix";
            }
        });
        this.registerPrefix(new PrefixPlugin() {
            @Override
            public String getPrefix() {
                return "{QQ群}";
            }

            @Override
            public String getPlayerPrefix(Player player) {
                return config.get("QQ群").toString();
            }

            @Override
            public String getPlayerPrefix(String name) {
                return getPlayerPrefix(getServer().getPlayer(name));
            }

            @Override
            public String getComePlugin() {
                return "RsMainPrefix";
            }
        });
        this.registerPrefix(new PrefixPlugin() {
            @Override
            public String getPrefix() {
                return "{手持物品}";
            }

            @Override
            public String getPlayerPrefix(Player player) {
                return player.getInventory().getItemInHand().getId() + " : " + player.getInventory().getItemInHand().getDamage();
            }

            @Override
            public String getPlayerPrefix(String name) {
                return getPlayerPrefix(getServer().getPlayer(name));
            }

            @Override
            public String getComePlugin() {
                return "RsMainPrefix";
            }
        });
        this.getServer().getCommandMap().register("", new CommandSet("set", this));
        this.getServer().getScheduler().scheduleRepeatingTask(new PopupTask(this), 21);
        this.getServer().getLogger().notice("皇务称号加载完毕...");
    }

    public void onDisable() {
        this.getServer().getLogger().notice("皇务称号卸载完毕...");
        config.save();
        playerinfo.setAll(playerprefixs);
        playerinfo.save();
    }



    public boolean registerPrefix(PrefixPlugin prefix) {
        if (prefixs.contains(prefix)) {
            return false;
        }
        if (prefixs.isEmpty()) {
            getLogger().alert("插件[" + prefix.getComePlugin() + "]注册了 " + prefix.getPrefix());
            prefixs.add(prefix);
            return true;
        }
        for (PrefixPlugin pp : prefixs) {
            if (!prefix.getPrefix().equals(pp.getPrefix())) {
                getLogger().alert("插件[" + prefix.getComePlugin() + "]注册了 " + prefix.getPrefix());
                prefixs.add(prefix);
                return true;
            } else {
                return false;
            }
        }
        prefixs.add(prefix);
        return true;
    }

    public void updatePrefix(Player player) {
        updateMainPrefix(player);
    }

    public void updateMainPrefix(Player player) {
        String str = config.get("头顶称号").toString();
        for (PrefixPlugin pp : prefixs) {
            str = str.replace(pp.getPrefix(), pp.getPlayerPrefix(player));
        }
        player.setNameTag(str);
    }

    public String getChatPrefix(Player player, String msg) {
        String str = config.get("聊天称号").toString();
        for (PrefixPlugin pp : prefixs) {
            str = str.replace(pp.getPrefix(), pp.getPlayerPrefix(player));
        }
        str = str.replace("{聊天信息}", msg);
        return (str);
    }

    public String updatePopupPrefix(Player player) {
        String str = config.get("底部称号").toString();
        for (PrefixPlugin pp : prefixs) {
            str = str.replace(pp.getPrefix(), pp.getPlayerPrefix(player));
        }
        return (str);
    }

    //称号插件功能实现
    /*
    playerinfo
    {
    "zmdd": {
    当前: "0",
    拥有: {
    }
    }
     */
    public String getPrefixByPlayer(String p) {
        isNew(getServer().getPlayer(p));
        return ((Map) playerprefixs.get(p)).get("当前").toString();
    }

    public boolean isNew(Player p) {
        //System.out.println(playerprefixs);
        if (playerprefixs.containsKey(p.getName())) {
            return false;
        } else {
            Map<String, String> a = new HashMap<String, String>();
            a.put("当前", config.get("默认称号").toString());
            playerprefixs.put(p.getName(), a);
            return true;
        }
    }

    public boolean isNew(String p) {
        //System.out.println(playerprefixs);
        if (playerprefixs.containsKey(p)) {
            return false;
        } else {
            Map<String, String> a = new HashMap<String, String>();
            a.put("当前", config.get("默认称号").toString());
            playerprefixs.put(p, a);
            return true;
        }
    }

    public boolean setPrefix(Player p, String pre) {
        isNew(p);
        Map<String, String> a = new HashMap<String, String>();
        a.put("当前", pre);
        playerprefixs.put(p.getName(), a);
        return true;
    }

    public boolean setPrefix(String p, String pre) {
        isNew(p);
        Map<String, String> a = new HashMap<String, String>();
        a.put("当前", pre);
        playerprefixs.put(p, a);
        return true;
    }
}