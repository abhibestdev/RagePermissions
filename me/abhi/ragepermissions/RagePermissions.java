package me.abhi.ragepermissions;

import me.abhi.ragepermissions.group.Group;
import me.abhi.ragepermissions.group.GroupCommand;
import me.abhi.ragepermissions.group.GroupManager;
import me.abhi.ragepermissions.listener.PlayerListener;
import me.abhi.ragepermissions.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class RagePermissions extends JavaPlugin {

    private static RagePermissions instance;
    private GroupManager groupManager;
    private HashMap<UUID, Group> groupHashMap;
    private HashMap<UUID, PermissionAttachment> permissionAttachmentHashMap;

    @Override
    public void onEnable() {
        instance = this;
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        groupManager = new GroupManager(this);
        groupHashMap = new HashMap<>();
        permissionAttachmentHashMap = new HashMap<>();
        getCommand("group").setExecutor(new GroupCommand(this));
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        if (this.getConfig().getConfigurationSection("group") != null) {
            for (String string : getConfig().getConfigurationSection("group").getKeys(false)) {
                UUID uuid = UUID.fromString(string);
                Player player = this.getServer().getPlayer(uuid);
                if (player != null) {
                    if (getConfig().get(player.getUniqueId().toString()) != null) {
                        String groupName = getConfig().getString(player.getUniqueId().toString());
                        if (groupManager.groupExists(groupName)) {
                            Group group = groupManager.getGroup(groupName);
                            groupHashMap.put(player.getUniqueId(), group);
                        } else if (groupManager.groupExists("default")) {
                            Group group = groupManager.getGroup("default");
                            groupHashMap.put(player.getUniqueId(), group);
                        }
                        Util.setPermissions(player);
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        groupManager.saveGroups();
    }

    public static RagePermissions getInstance() {
        return instance;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public HashMap<UUID, Group> getGroupHashMap() {
        return groupHashMap;
    }

    public HashMap<UUID, PermissionAttachment> getPermissionAttachmentHashMap() {
        return permissionAttachmentHashMap;
    }
}
