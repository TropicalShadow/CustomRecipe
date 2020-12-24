package me.tropicalshadow.customrecipe.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logging {

    private static String PREFIX = ChatColor.BLUE.toString() + "[CR] ";

    public static void info(Object str){
        Bukkit.getLogger().info(PREFIX+ChatColor.BLUE.toString()+str);
    }
    public static void warning(Object str){
        Bukkit.getLogger().warning(PREFIX+ChatColor.YELLOW.toString()+str);
    }
    public static void danger(Object str){
        Bukkit.getLogger().severe(PREFIX+ChatColor.RED.toString()+str);
    }

}
