package wtf.choco.arrows.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.registry.ArrowRegistry;
import wtf.choco.arrows.util.AAConstants;
import wtf.choco.arrows.util.NumberUtils;
import wtf.choco.commons.util.MathUtil;
import wtf.choco.commons.util.NamespacedKeyUtil;

import static wtf.choco.arrows.AlchemicalArrows.CHAT_PREFIX;

public class GiveArrowCommand implements TabExecutor {

    private final AlchemicalArrows plugin;
    private final ArrowRegistry arrowRegistry;

    public GiveArrowCommand(@NotNull AlchemicalArrows plugin) {
        this.plugin = plugin;
        this.arrowRegistry = plugin.getArrowRegistry();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // TODO: This is legacy. The whole block can be removed after legacy support is removed. Permission is handled by the command in the plugin.yml
        if (!AAConstants.PERMISSION_COMMAND_GIVEARROW_PREDICATE.test(sender)) {
            sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "You have insufficient permissions to execute this command");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.GRAY + "Missing parameter. " + ChatColor.YELLOW + "/" + label + " <arrow> [count] [player]");
            return true;
        }

        int giveCount = (args.length >= 2) ? MathUtil.clamp(NumberUtils.toInt(args[1], 1), 1, 64) : 1;
        List<Player> targets = (sender instanceof Player player) ? Arrays.asList(player) : Collections.EMPTY_LIST;

        if (args.length >= 3) {
            try {
                List<Player> potentialTargets = Bukkit.selectEntities(sender, args[2]).stream()
                        .filter(e -> e.getType() == EntityType.PLAYER)
                        .map(e -> (Player) e).collect(Collectors.toList());

                if (potentialTargets.size() == 0) {
                    sender.sendMessage(CHAT_PREFIX + "Malformed player selector. You must specify either an online player name, UUID or selector (i.e. @p, @a, @s or @r)");
                    return true;
                }

                targets = potentialTargets;
            } catch (IllegalArgumentException e) {
                sender.sendMessage(CHAT_PREFIX + "Malformed player selector. You must specify either a player name, UUID or selector (i.e. @p, @a, @s or @r)");
                return true;
            }
        }

        if (targets.isEmpty()) {
            sender.sendMessage(CHAT_PREFIX + "A player name must be specified in order to execute this command from the console");
            return true;
        }

        NamespacedKey arrowKey = NamespacedKeyUtil.fromString(args[0], plugin);
        if (arrowKey == null) {
            sender.sendMessage(CHAT_PREFIX + "Invalid namespace. Arrow IDs should be formatted as " + ChatColor.YELLOW + "namespace:id"
                    + ChatColor.GRAY + " (for example, " + ChatColor.YELLOW + "alchemicalarrows:air" + ChatColor.GRAY + ")");
            return true;
        }

        AlchemicalArrow arrow = arrowRegistry.get(arrowKey);
        if (arrow == null) {
            sender.sendMessage(CHAT_PREFIX + "Could not find an arrow with the ID " + ChatColor.YELLOW + arrowKey);
            return true;
        }

        ItemStack itemToGive = arrow.createItemStack(giveCount);
        targets.forEach(p -> p.getInventory().addItem(itemToGive));

        if (targets.size() == 1) {
            Player target = targets.get(0);
            sender.sendMessage(CHAT_PREFIX + "Successfully given " + ChatColor.GREEN + giveCount + ChatColor.GRAY + " of " + arrow.getDisplayName() + ChatColor.GRAY + (target == sender ? "" : " to " + target.getName()));
        } else {
            sender.sendMessage(CHAT_PREFIX + "Successfully given " + ChatColor.GREEN + giveCount + ChatColor.GRAY + " of " + arrow.getDisplayName() + ChatColor.GRAY + " to " + targets.size() + " players");
        }

        return true;
    }

    private static final List<String> NUMBER_ARGS = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();

            for (AlchemicalArrow arrow : plugin.getArrowRegistry().getRegisteredArrows()) {
                NamespacedKey key = arrow.getKey();
                if (key.toString().startsWith(args[0]) || key.getKey().startsWith(args[0])) {
                    arguments.add(arrow.getKey().toString());
                }
            }

            return arguments;
        }

        else if (args.length == 2) {
            return args[1].isEmpty() ? NUMBER_ARGS : Collections.emptyList();
        }

        else if (args.length == 3) {
            return null; // Player names
        }

        return Collections.emptyList();
    }

}
