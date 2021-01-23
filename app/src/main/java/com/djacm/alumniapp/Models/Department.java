package com.djacm.alumniapp.Models;

public class Department {
    String name;
    int iconPath;

    public int getIconPath() {
        return iconPath;
    }

    public String getName() {
        return name;
    }

    public Department(String name, int iconPath) {
        this.iconPath = iconPath;
        this.name = name;
    }
}
