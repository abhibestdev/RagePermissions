package me.abhi.ragepermissions.group;

import me.abhi.ragepermissions.RagePermissions;
import me.abhi.ragepermissions.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GroupCommand implements CommandExecutor {

    private RagePermissions plugin;

    public GroupCommand(RagePermissions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("ragepermissions.command.group")) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /" + commandLabel + " <create:delete:permission:assign:list:set> <possible argument>");
            return true;
        }
        if (args[0].equalsIgnoreCase("create")) {
            if (args.length != 2) {
                sender.sendMessage(ChatColor.RED + "Usage: /" + commandLabel + " create <name>");
                return true;
            }
            String name = args[1];
            if (this.plugin.getGroupManager().groupExists(name)) {
                sender.sendMessage(ChatColor.RED + "That group already exists!");
                return true;
            }
            Group group = new Group(name, "&a", "&a");
            this.plugin.getGroupManager().groupList.add(group);
            sender.sendMessage(ChatColor.GREEN + "Group '" + name + "' has been created!");
            return true;
        }
        if (args[0].equalsIgnoreCase("delete")) {
            if (args.length != 2) {
                sender.sendMessage(ChatColor.RED + "Usage: /" + commandLabel + " delete <name>");
                return true;
            }
            String name = args[1];
            if (!this.plugin.getGroupManager().groupExists(name)) {
                sender.sendMessage(ChatColor.RED + "That group doesn't exist!");
                return true;
            }
            Group group = this.plugin.getGroupManager().getGroup(name);
            for (UUID uuid : this.plugin.getGroupHashMap().keySet()) {
                if (this.plugin.getGroupHashMap().get(uuid) == group) {
                    Player player = this.plugin.getServer().getPlayer(uuid);
                    Util.resetGroup(player);
                }
            }
            this.plugin.getGroupManager().getGroupList().remove(group);
            sender.sendMessage(ChatColor.GREEN + "Group '" + name + "' has been deleted!");
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            sender.sendMessage(ChatColor.GREEN + "The current groups are:");
            for (Group group : this.plugin.getGroupManager().getGroupList()) {
                sender.sendMessage(ChatColor.GREEN + " - " + group.getName());
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("permission")) {
            if (args.length < 3) {
                sender.sendMessage(ChatColor.RED + "Usage: /" + commandLabel + " permission <add:delete:list> <group> <permission>");
                return true;
            }
            if (args[1].equalsIgnoreCase("add")) {

                String name = args[2];
                if (!this.plugin.getGroupManager().groupExists(name)) {
                    sender.sendMessage(ChatColor.RED + "That group doesn't exist!");
                    return true;
                }
                if (args.length != 4) {
                    sender.sendMessage(ChatColor.RED + "Usage: /" + commandLabel + " permission <add:delete> <group> <permission>");
                    return true;
                }
                Group group = this.plugin.getGroupManager().getGroup(name);
                group.getPermissions().add(args[3]);
                for (UUID uuid : this.plugin.getGroupHashMap().keySet()) {
                    if (this.plugin.getGroupHashMap().get(uuid) == group) {
                        Player player = this.plugin.getServer().getPlayer(uuid);
                        Util.unsetPermissions(player);
                        Util.setPermissions(player);
                    }
                }
                sender.sendMessage(ChatColor.GREEN + "The group '" + name + "' has been given the permission '" + args[3] + "'!");
                return true;
            }
            if (args[1].equalsIgnoreCase("delete")) {
                String name = args[2];
                if (!this.plugin.getGroupManager().groupExists(name)) {
                    sender.sendMessage(ChatColor.RED + "That group doesn't exist!");
                    return true;
                }
                if (args.length != 4) {
                    sender.sendMessage(ChatColor.RED + "Usage: /" + commandLabel + " permission <add:delete> <group> <permission>");
                    return true;
                }
                Group group = this.plugin.getGroupManager().getGroup(name);
                for (UUID uuid : this.plugin.getGroupHashMap().keySet()) {
                    if (this.plugin.getGroupHashMap().get(uuid) == group) {
                        Player player = this.plugin.getServer().getPlayer(uuid);
                        Util.unsetPermissions(player);
                        Util.setPermissions(player);
                    }
                }
                sender.sendMessage(ChatColor.GREEN + "The group '" + name + "' has been revoked of the permission '" + args[3] + "'!");
                return true;
            }
            if (args[1].equalsIgnoreCase("list")) {
                String name = args[2];
                if (!this.plugin.getGroupManager().groupExists(name)) {
                    sender.sendMessage(ChatColor.RED + "That group doesn't exist!");
                    return true;
                }
                Group group = this.plugin.getGroupManager().getGroup(name);
                sender.sendMessage(ChatColor.GREEN + "The current permissions for '" + name + "' are:");
                for (String permission : group.getPermissions()) {
                    sender.sendMessage(ChatColor.GREEN + " - " + permission);
                }
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("set")) {
            if (args.length != 4) {
                sender.sendMessage(ChatColor.RED + "Usage: /" + commandLabel + " set <prefix:tabcolor> <group> <permission>");
                return true;
            }
            if (args[1].equalsIgnoreCase("prefix")) {
                String name = args[2];
                if (!this.plugin.getGroupManager().groupExists(name)) {
                    sender.sendMessage(ChatColor.RED + "That group doesn't exist!");
                    return true;
                }
                Group group = this.plugin.getGroupManager().getGroup(name);
                group.setPrefix(ChatColor.translateAlternateColorCodes('&', args[3]));
                sender.sendMessage(ChatColor.GREEN + "The group '" + name + "' has been given the prefix '" + ChatColor.translateAlternateColorCodes('&', args[3]) + ChatColor.GREEN + "'!");
                return true;
            }
            if (args[1].equalsIgnoreCase("tabcolor")) {
                String name = args[2];
                if (!this.plugin.getGroupManager().groupExists(name)) {
                    sender.sendMessage(ChatColor.RED + "That group doesn't exist!");
                    return true;
                }
                Group group = this.plugin.getGroupManager().getGroup(name);
                group.setTabColor(ChatColor.translateAlternateColorCodes('&', args[3]));
                for (UUID uuid : this.plugin.getGroupHashMap().keySet()) {
                    if (this.plugin.getGroupHashMap().get(uuid) == group) {
                        Player player = this.plugin.getServer().getPlayer(uuid);
                        Util.unsetPermissions(player);
                        Util.setPermissions(player);
                    }
                }
                sender.sendMessage(ChatColor.GREEN + "The group '" + name + "' has been given the tab color '" + args[3] + "'!");
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("assign")) {
            if (args.length != 3) {
                sender.sendMessage(ChatColor.RED + "/" + commandLabel + " assign <player> <group>");
                return true;
            }
            OfflinePlayer offlinePlayer = this.plugin.getServer().getOfflinePlayer(args[1]);
            if (offlinePlayer == null) {
                sender.sendMessage(ChatColor.RED + "Could not find player!");
                return true;
            }
            String name = args[2];
            if (!this.plugin.getGroupManager().groupExists(name)) {
                sender.sendMessage(ChatColor.RED + "That group doesn't exist!");
                return true;
            }
            Group group = this.plugin.getGroupManager().getGroup(name);
            this.plugin.getConfig().set("group." +  offlinePlayer.getUniqueId().toString(), name);
            this.plugin.saveConfig();
            sender.sendMessage(ChatColor.GREEN + "The group for " + offlinePlayer.getName() + " has been set to '" + group.getName() + "'!");
            if (offlinePlayer.isOnline()) {
                Player player = this.plugin.getServer().getPlayer(args[1]);
                this.plugin.getGroupHashMap().put(player.getUniqueId(), group);
                player.sendMessage(ChatColor.GREEN + "Your rank has been updated to '" + group.getName() + "'!");
                Util.unsetPermissions(player);
                Util.setPermissions(player);
            }
            return true;
        }
        return true;
    }
}
