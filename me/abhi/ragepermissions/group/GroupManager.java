package me.abhi.ragepermissions.group;

import me.abhi.ragepermissions.RagePermissions;

import java.util.ArrayList;
import java.util.List;

public class GroupManager {

    private RagePermissions plugin;
    public List<Group> groupList;

    public GroupManager(RagePermissions plugin) {
        this.plugin = plugin;
        groupList = new ArrayList<>();
        loadGroups();
    }

    private void loadGroups() {
        if (this.plugin.getConfig().getConfigurationSection("groups") != null) {
            for (String name : this.plugin.getConfig().getConfigurationSection("groups").getKeys(false)) {
                String prefix = this.plugin.getConfig().getString("groups." + name + ".prefix");
                String tabColor = this.plugin.getConfig().getString("groups." + name + ".tabColor");
                List<String> permissions = this.plugin.getConfig().getStringList("groups." + name + ".permissions");
                Group group = new Group(name, prefix, tabColor, permissions);
                groupList.add(group);
            }
        }
    }

    public boolean groupExists(String name) {
        for (Group group : groupList) {
            if (group.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public Group getGroup(String name) {
        for (Group group : groupList) {
            if (group.getName().equalsIgnoreCase(name)) {
                return group;
            }
        }
        return null;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void saveGroups() {
        this.plugin.getConfig().set("groups", null);
        for (Group group : groupList) {
            this.plugin.getConfig().set("groups." + group.getName() + ".prefix", group.getPrefix());
            this.plugin.getConfig().set("groups." + group.getName() + ".tabColor", group.getTabColor());
            this.plugin.getConfig().set("groups." + group.getName() + ".permissions", group.getPermissions());
        }
        this.plugin.saveConfig();
    }
}
