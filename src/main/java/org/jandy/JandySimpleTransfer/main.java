package org.jandy.JandySimpleTransfer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class main extends JavaPlugin {

    private HashMap<Player, Player> teleportRequests;

    @Override
    public void onEnable() {
        getLogger().info("插件已启用");
        teleportRequests = new HashMap<>();
    }

    @Override
    public void onDisable() {
        getLogger().info("插件已禁用");
    }

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
            player2.sendMessage("玩家 " + player1.getName() + " 请求传送到你的位置。输入 /tpaccept 接受请求，输入 /tpdeny 拒绝请求。");
            player1.sendMessage("已向玩家 " + player2.getName() + " 发送传送请求。");
            return true;
        } else if (label.equalsIgnoreCase("tpaccept")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("只有玩家才能执行此命令！");
                return true;
            }

            Player player2 = (Player) sender;
            Player player1 = teleportRequests.get(player2);

            if (player1 == null) {
                player2.sendMessage("没有传送请求待处理！");
                return true;
            }

            player1.teleport(player2.getLocation());
            player1.sendMessage("你已接受 " + player2.getName() + " 的传送请求。");
            player2.sendMessage("传送请求已接受。");
            teleportRequests.remove(player2);
            return true;
        } else if (label.equalsIgnoreCase("tpdeny")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("只有玩家才能执行此命令！");
                return true;
            }

            Player player2 = (Player) sender;
            Player player1 = teleportRequests.get(player2);

            if (player1 == null) {
                player2.sendMessage("没有传送请求待处理！");
                return true;
            }

            player1.sendMessage(player2.getName() + " 拒绝了你的传送请求。");
            player2.sendMessage("传送请求已拒绝。");
            teleportRequests.remove(player2);
            return true;
        }

        return false;
    }

    private Player getPlayerFromId(String id) {
        for (Player player : getServer().getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(id)) {
                return player;
            }
        }
        return null;
    }
}

