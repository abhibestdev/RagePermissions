package me.abhi.ragepermissions.util;

import me.abhi.ragepermissions.RagePermissions;
import me.abhi.ragepermissions.group.Group;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.Map;

public class Util {


    public static void resetGroup(Player player) {
        if (RagePermissions.getInstance().getGroupManager().groupExists("default")) {
            Group group = RagePermissions.getInstance().getGroupManager().getGroup("default");
            RagePermissions.getInstance().getGroupHashMap().put(player.getUniqueId(), group);
        } else {
            RagePermissions.getInstance().getGroupHashMap().remove(player.getUniqueId());
        }
        Util.unsetPermissions(player);
    }

    public static void setPermissions(Player player) {
        PermissionAttachment permissionAttachment = RagePermissions.getInstance().getPermissionAttachmentHashMap().getOrDefault(player.getUniqueId(), player.addAttachment(RagePermissions.getInstance()));
        if (RagePermissions.getInstance().getGroupHashMap().containsKey(player.getUniqueId())) {
            Group group = RagePermissions.getInstance().getGroupHashMap().get(player.getUniqueId());
            if (group != null && !group.getPermissions().isEmpty()) {
                for (String permission : group.getPermissions()) {
                    permissionAttachment.setPermission(permission, true);
                }
            }
            if (group.getTabColor() != null) {
                player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', group.getTabColor()) + player.getName());
            }
        }
        RagePermissions.getInstance().getPermissionAttachmentHashMap().put(player.getUniqueId(), permissionAttachment);
    }

    public static void unsetPermissions(Player player) {
        PermissionAttachment permissionAttachment = RagePermissions.getInstance().getPermissionAttachmentHashMap().getOrDefault(player.getUniqueId(), player.addAttachment(RagePermissions.getInstance()));
        Map<String, Boolean> flags = RagePermissions.getInstance().getPermissionAttachmentHashMap().getOrDefault(player.getUniqueId(), player.addAttachment(RagePermissions.getInstance())).getPermissions();
        if (!player.isOp()) {
            if (!flags.isEmpty()) {
                for (String permission : flags.keySet()) {
                    permissionAttachment.setPermission(permission, false);
                }
            }
            permissionAttachment.setPermission("minecraft.command.me", false);
            permissionAttachment.setPermission("minecraft.command.say", false);
        }
        RagePermissions.getInstance().getPermissionAttachmentHashMap().remove(player.getUniqueId());
        player.setPlayerListName(ChatColor.RESET + player.getName());
    }
}
