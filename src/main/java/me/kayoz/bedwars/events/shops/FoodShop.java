package me.kayoz.bedwars.events.shops;

import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by KaYoz on 8/1/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class FoodShop implements Listener {

    public static void openShop(Player p) {

        Inventory inv = Bukkit.createInventory(null, 36, Chat.format("&6&lFood"));

        String canBuy;
        int iron = ShopEvents.getItem(Material.IRON_INGOT, p);
        int gold = ShopEvents.getItem(Material.GOLD_INGOT, p);

        if (iron >= 5) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Iron to purchase this.");
        }

        ItemStack carrot = ItemBuilder.build(Material.CARROT_ITEM, 1, "&eCarrot", Arrays.asList("&eWill Recieve:", "  &74 Carrots", "", "&7Cost: &65 Iron", "", canBuy));

        if (iron >= 15) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Iron to purchase this.");
        }

        ItemStack steak = ItemBuilder.build(Material.COOKED_BEEF, 1, "&eSteak", Arrays.asList("&eWill Recieve:", "  &74 Steak", "", "&7Cost: &f15 Iron", "", canBuy));

        if (gold >= 5) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Gold to purchase this.");
        }

        ItemStack gapple = ItemBuilder.build(Material.GOLDEN_APPLE, 1, "&eGolden Apple", Arrays.asList("&eWill Recieve:", "  &71 Golden Apple", "", "&7Cost: &65 Gold", "", canBuy));

        ItemStack back = ItemBuilder.build(Material.BARRIER, 1, "&cBack", Arrays.asList("&7Go to the previous menu."));
        inv.setItem(12, carrot);
        inv.setItem(13, steak);
        inv.setItem(14, gapple);
        inv.setItem(22, back);

        p.openInventory(inv);
    }

    @EventHandler
    public void onSelect(InventoryClickEvent e) {

        if (e.getWhoClicked().getType() != EntityType.PLAYER
                || e.getSlotType() == InventoryType.SlotType.OUTSIDE
                || !e.getCurrentItem().hasItemMeta()) {
            return;
        }

        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        ItemStack item = e.getCurrentItem();

        if (inv.getName().equals(Chat.format("&6&lFood"))) {

            int iron = ShopEvents.getItem(Material.IRON_INGOT, p);
            int gold = ShopEvents.getItem(Material.GOLD_INGOT, p);

            if (item.getType() == Material.CARROT_ITEM) {

                e.setCancelled(true);

                if (iron >= 5) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.IRON_INGOT, 5) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.CARROT, 4));

                        Chat.sendColoredMessage(p, "&eYou have purchased &64 Carrots&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {

                    Chat.sendColoredMessage(p, "&cYou do not have enough Iron to purchase this.");

                }

            } else if (item.getType() == Material.COOKED_BEEF) {

                e.setCancelled(true);

                if (iron >= 15) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.IRON_INGOT, 15) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 4));

                        Chat.sendColoredMessage(p, "&eYou have purchased &64 Steak&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {

                    Chat.sendColoredMessage(p, "&cYou do not have enough Iron to purchase this.");

                }
            } else if (item.getType() == Material.ARROW) {

                e.setCancelled(true);

                if (gold >= 5) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.GOLD_INGOT, 5) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));

                        Chat.sendColoredMessage(p, "&eYou have purchased a &6Golden Apple&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {

                    Chat.sendColoredMessage(p, "&cYou do not have enough Gold to purchase this.");

                }
            } else if (item.getType() == Material.BARRIER) {

                e.setCancelled(true);

                p.getOpenInventory().close();

                ShopEvents.openMain(p);
            }
        }
    }
}
