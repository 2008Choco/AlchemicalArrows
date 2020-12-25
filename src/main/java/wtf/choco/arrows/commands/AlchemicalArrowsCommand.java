package wtf.choco.arrows.commands;

import static wtf.choco.arrows.AlchemicalArrows.CHAT_PREFIX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;

import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.registry.ArrowStateManager;
import wtf.choco.arrows.utils.UpdateChecker;
import wtf.choco.arrows.utils.UpdateChecker.UpdateResult;

public class AlchemicalArrowsCommand implements TabExecutor {

    private final AlchemicalArrows plugin;
    private final ArrowStateManager stateManager;

    public AlchemicalArrowsCommand(@NotNull AlchemicalArrows plugin) {
        this.plugin = plugin;
        this.stateManager = plugin.getArrowStateManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.GRAY + "Missing parameter. " + ChatColor.YELLOW + "/" + label + " <reload|version|killallarrows>");
            return true;
        }

        if (args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("killallarrows") /* legacy */) {
            if (!sender.hasPermission("arrows.command.clear") || !sender.hasPermission("arrows.command.killallarrows") /* legacy */) {
                sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "You have insufficient permissions to execute this command");
                return true;
            }

            Collection<AlchemicalArrowEntity> arrows = stateManager.getArrows();
            int arrowCount = arrows.size();

            if (arrowCount == 0) {
                sender.sendMessage(CHAT_PREFIX + "No alchemical arrows were found in the world");
                return true;
            }

            arrows.forEach(a -> {
                this.stateManager.remove(a);
                a.getArrow().remove();
            });

            sender.sendMessage(CHAT_PREFIX + "Successfully removed " + ChatColor.YELLOW + arrowCount + ChatColor.GRAY + " alchemical arrows from the world");
        }

        else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("info")) {
            sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------");
            sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Version: " + ChatColor.GRAY  + plugin.getDescription().getVersion());
            sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Developer / Maintainer: " + ChatColor.GRAY + "2008Choco" + ChatColor.YELLOW + "( https://choco.gg/ )");
            sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Development Page: " + ChatColor.GRAY + "https://www.spigotmc.org/resources/alchemicalarrows.11693/");
            sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Report Bugs To: " + ChatColor.GRAY + "https://github.com/2008Choco/AlchemicalArrows/issues/");

            if (UpdateChecker.isInitialized()) {
                UpdateResult result = UpdateChecker.get().getLastResult();
                sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "New Version Available: " + (result == null ? ChatColor.YELLOW + "N/A (Unchecked)" : (result.requiresUpdate() ? ChatColor.GREEN + "Yes" : ChatColor.RED + "No")));
            }

            sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------");
        }

        else if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("arrows.command.reload")) {
                sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "You have insufficient permissions to execute this command");
                return true;
            }

            this.plugin.reloadConfig();
            sender.sendMessage(CHAT_PREFIX + ChatColor.GREEN + "AlchemicalArrows configuration successfully reloaded");
        }

        else {
            sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.GRAY + "Unknown parameter " + ChatColor.AQUA + args[0] + ChatColor.GRAY + ". " + ChatColor.YELLOW + "/" + label + " <reload|version|killallarrows>");
        }

        return true;
    }

    private static final List<String> BASE_ARGS = Arrays.asList("version", "reload", "clear");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return args.length == 1 ? StringUtil.copyPartialMatches(args[0], BASE_ARGS, new ArrayList<>()) : Collections.emptyList();
    }

}