package org.jandy.JandySimpleTransfer;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class main extends JavaPlugin {

    private HashMap<Player, Player> teleportRequests;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("tpa")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("只有玩家才能执行此命令！");
                return true;
            }

            if (args.length < 1) {
                sender.sendMessage("请提供一个目标玩家的ID！");
                return true;
            }

            Player player1 = (Player) sender;
            Player player2 = getPlayerFromId(args[0]);

            if (player2 == null) {
                player1.sendMessage("找不到ID为 " + args[0] + " 的玩家！");
                return true;
            }

            teleportRequests.put(player2, player1);
            player2.sendMessage(ChatColor.GREEN + "玩家 " + player1.getName() + " 请求传送到你的位置。输入 /tpaccept 接受请求，输入 /tpdeny 拒绝请求。");
            player1.sendMessage(ChatColor.YELLOW + "已向玩家 " + player2.getName() + " 发送传送请求。");
            return true;
        }

        return false;
    }

    @Override
    public void onEnable() {
        getLogger().info("插件已启用");
        teleportRequests = new HashMap<>();
    }

    @Override
    public void onDisable() {
        getLogger().info("插件已禁用");
    }

    private Player getPlayerFromId(String id) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(id)) {
                return player;
            }
        }
        return null;
    }
}