package me.abhi.ragepermissions.group;

import java.util.List;

public class Group {

    public String name;
    public String prefix;
    public String tabColor;
    public List<String> permissions;

    public Group(String name, String prefix, String tabColor) {
        this.name = name;
        this.prefix = prefix;
        this.tabColor = tabColor;
    }

    public Group(String name, String prefix, String tabColor, List<String> permissions) {
        this.name = name;
        this.prefix = prefix;
        this.tabColor = tabColor;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getTabColor() {
        return tabColor;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setTabColor(String tabColor) {
        this.tabColor = tabColor;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
