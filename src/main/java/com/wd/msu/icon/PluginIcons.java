package com.wd.msu.icon;

import com.intellij.openapi.util.IconLoader;
import javax.swing.Icon;

public class PluginIcons {

    public static final Icon success = load("/icons/success.svg");
    public static final Icon success_dark = load("/icons/success_dark.svg");

    public static final Icon testFailed = load("/icons/testFailed.svg");
    public static final Icon testFailed_dark = load("/icons/testFailed_dark.svg");

    public static final Icon projectStructure = load("/icons/projectStructure.svg");
    public static final Icon projectStructure_dark = load("/icons/projectStructure_dark.svg");

    public static final Icon settings = load("/icons/settings.svg");
    public static final Icon settings_dark = load("/icons/settings_dark.svg");

    public static final Icon editFolder = load("/icons/editFolder.svg");
    public static final Icon editFolder_dark = load("/icons/editFolder_dark.svg");

    public static final Icon folder = load("/icons/folder.svg");
    public static final Icon folder_dark = load("/icons/folder_dark.svg");

    public static final Icon config = load("/icons/config.svg");
    public static final Icon config_dark = load("/icons/config_dark.svg");

    public static final Icon locate = load("/icons/locate.svg");
    public static final Icon locate_dark = load("/icons/locate_dark.svg");
    public static Icon load(String iconPath) {
        return IconLoader.getIcon(iconPath, PluginIcons.class);
    }

}
