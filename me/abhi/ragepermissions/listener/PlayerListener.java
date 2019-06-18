package me.abhi.ragepermissions.listener;

import me.abhi.ragepermissions.RagePermissions;
import me.abhi.ragepermissions.group.Group;
import me.abhi.ragepermissions.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private RagePermissions plugin;

    public PlayerListener(RagePermissions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (this.plugin.getConfig().get("group." + player.getUniqueId().toString()) != null) {
            String groupName = this.plugin.getConfig().getString(player.getUniqueId().toString());
            if (this.plugin.getGroupManager().groupExists(groupName)) {
                Group group = this.plugin.getGroupManager().getGroup(groupName);
                this.plugin.getGroupHashMap().put(player.getUniqueId(), group);
            } else if (this.plugin.getGroupManager().groupExists("default")) {
                Group group = this.plugin.getGroupManager().getGroup("default");
                this.plugin.getGroupHashMap().put(player.getUniqueId(), group);
            }
        }
        Util.setPermissions(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Util.unsetPermissions(player);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String format = ChatColor.GREEN + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + event.getMessage();
        if (this.plugin.getGroupHashMap().containsKey(player.getUniqueId())) {
            Group group = this.plugin.getGroupHashMap().get(player.getUniqueId());
            if (group.getPrefix() != null) {
                format = group.getPrefix() + " " + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + event.getMessage();
            }
        }
        event.setFormat(format);
    }
}
