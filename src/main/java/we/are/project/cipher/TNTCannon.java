package we.are.project.cipher;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class TNTCannon implements Listener { @EventHandler public void on(PlayerInteractEvent event) {if ((event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) && (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasDisplayName() && event.getItem().getItemMeta().getDisplayName().equals("Throwing TNT"))) {if (event.getItem().getAmount() > 1) {event.getItem().setAmount(event.getItem().getAmount() - 1);} else {event.getPlayer().getInventory().remove(event.getItem());} launch(event.getPlayer());}}
    public TNTCannon() {try {InfiniteCode.registerCommand("throwingtnt", "Spawns throwing TNT for a player", "/throwingtnt", Arrays.asList("tnt"), (object) -> {if (((Object[]) object)[0] instanceof Player) {((Player) ((Object[]) object)[0]).getInventory().addItem(makeTNT());} return true;});} catch (Exception ignored) {}}
    public void launch(Player player) {if (ProjectCipher.getInstance().getConfig().getBoolean("ThrowingTNT")) {player.updateInventory(); vel(player.getWorld().spawn(player.getEyeLocation().add(player.getLocation().getDirection()), TNTPrimed.class), player.getLocation().getDirection(), 1, 0.1, 10);}}
    public void vel(Entity entity, Vector vector, double multiplier, double yBoost, double yMax) {vector.normalize(); vector.setY(vector.getY() + yBoost); vector.multiply(multiplier);
        if (vector.getY() > yMax) vector.setY(yMax); entity.setVelocity(vector);}
    public ItemStack makeTNT() {ItemStack stack = new ItemStack(Material.DEAD_BUSH, 64); ItemMeta meta = stack.getItemMeta(); if (meta != null) {meta.setDisplayName("Throwing TNT");}
        stack.setItemMeta(meta); return stack;}
}
