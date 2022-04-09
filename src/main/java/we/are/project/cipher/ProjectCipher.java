package we.are.project.cipher;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.util.BlockIterator;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class ProjectCipher extends JavaPlugin implements Listener { @Override public void onEnable() {this.saveDefaultConfig(); try {InfiniteCode.run(e -> InfiniteCode.runVoid(() -> this.getServer().getPluginManager().registerEvents(this, this)), e -> InfiniteCode.runVoid(() -> Bukkit.getPluginManager().registerEvents(new AngelChestListener(), this))); InfiniteCode.run(e -> InfiniteCode.registerCommand("fly", "toggle flight", null, List.of(), in -> InfiniteCode.run(e2 -> ((Object[]) in)[0], commandSender -> commandSender instanceof CommandSender sender ? InfiniteCode.run(e3 -> ((Object[]) in)[1], a -> a instanceof String[] args ? InfiniteCode.run(e4 -> args.length == 0 ? sender : args.length == 1 ? Bukkit.getPlayer(args[0]) : null, p -> p == null ? !(sender instanceof Player player) ? InfiniteCode.runVoid(() -> sender.sendMessage("§cPlayer not found.")) : InfiniteCode.run(e5 -> InfiniteCode.runVoid(() -> player.setAllowFlight(!player.getAllowFlight()))) : p instanceof Player player ? InfiniteCode.runVoid(() -> player.setAllowFlight(!player.getAllowFlight())) : null) : null) : null)), e -> InfiniteCode.registerCommand("unban", "unban a player", null, List.of(), in -> InfiniteCode.run(e2 -> ((Object[]) in)[0], commandSender -> commandSender instanceof CommandSender sender ? InfiniteCode.run(e2 -> ((Object[]) in)[1], a -> a instanceof String[] args ? sender.hasPermission("projectcipher.unban") ? InfiniteCode.run(e3 -> Bukkit.dispatchCommand(sender, "pardon " + String.join(" ", args))) : null : null) : null)), e -> InfiniteCode.registerCommand("unban-ip", "unban a player ip", null, List.of("ubip"), in -> InfiniteCode.run(e2 -> ((Object[]) in)[0], commandSender -> commandSender instanceof CommandSender sender ? InfiniteCode.run(e2 -> ((Object[]) in)[1], a -> a instanceof String[] args ? sender.hasPermission("projectcipher.unban-ip") ? InfiniteCode.run(e3 -> Bukkit.dispatchCommand(sender, "pardon-ip " + String.join(" ", args))) : null : null) : null))); } catch (Exception ignored) {}}@Override public void onDisable() {}
    /*Constructor for tests */ public ProjectCipher() { super(); }; public ProjectCipher(JavaPluginLoader loader, PluginDescriptionFile descriptionFile, File dataFolder, File file) { super(loader, descriptionFile, dataFolder, file);}
    private static ProjectCipher instance; { instance = this; } public static ProjectCipher getInstance() { return instance; }
    @EventHandler public void on(final EntitySpawnEvent event) {if (!this.getConfig().getStringList("DoubleHealthMobs").contains(event.getEntityType().name()) && !(event.getEntity() instanceof LivingEntity)) return;((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 2D);((LivingEntity) event.getEntity()).setHealth(((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());}
    @EventHandler public void on(PlayerDeathEvent event) throws Exception {InfiniteCode.run(arg -> event.getEntity(), player -> ((Player)player).getAttribute(Attribute.GENERIC_MAX_HEALTH), attrib -> InfiniteCode.runVoid(() -> ((AttributeInstance)attrib).setBaseValue(((AttributeInstance) attrib).getBaseValue() - 2)), empty -> event.getEntity(), player -> ((Player)player).getKiller(), killer -> Optional.ofNullable(killer instanceof Player player ? player.getAttribute(Attribute.GENERIC_MAX_HEALTH) : null), attribOpt -> InfiniteCode.runVoid(() -> ((Optional<AttributeInstance>)attribOpt).ifPresent(attrib -> attrib.setBaseValue(attrib.getBaseValue() + 2))), empty -> event.getEntity(), player -> ((Player)player).getAttribute(Attribute.GENERIC_MAX_HEALTH), attrib -> attrib instanceof AttributeInstance attribute && attribute.getBaseValue() <= 0, ban -> Boolean.parseBoolean(ban + "") ? InfiniteCode.runVoid(() -> Bukkit.getBanList(BanList.Type.NAME).addBan(event.getEntity().getName(), "You have been eliminated", null, null)) : 1, val -> val == null ? InfiniteCode.runVoid(() -> event.getEntity().kickPlayer("You have been eliminated")) : 1);} @EventHandler(priority = EventPriority.MONITOR) public void onEggLand(final ProjectileHitEvent event) {if (event.getEntity().getType() == org.bukkit.entity.EntityType.EGG && event.getHitEntity() != null) {event.getHitEntity().getWorld().createExplosion(event.getEntity().getLocation(), 3f);}}
    /*Join/Leave Messages + Custom chat, Ike#2932 */ @EventHandler public void on(PlayerJoinEvent event) throws Exception {InfiniteCode.run(e -> event.getPlayer(), p -> p instanceof Player player ? InfiniteCode.run(e -> getConfig().getString("join-message", "&7[&a+&7] &r%player%"), s -> InfiniteCode.runVoid(() -> event.setJoinMessage(s.toString().replace("%player%", player.getName())))) : null);} @EventHandler public void on(PlayerQuitEvent event) throws Exception {InfiniteCode.run(e -> event.getPlayer(), p -> p instanceof Player player ? InfiniteCode.run(e -> getConfig().getString("quit-message", "&7[&4-&7] &r%player%"), s -> InfiniteCode.runVoid(() -> event.setQuitMessage(s.toString().replace("%player%", player.getName())))) : null);} @EventHandler public void on(AsyncPlayerChatEvent event) throws Exception {InfiniteCode.run(e -> event.getPlayer(), p -> p instanceof Player player ? InfiniteCode.run(e -> ChatColor.translateAlternateColorCodes('&', getConfig().getString("chat-format", "<%player%> %message%")), s -> InfiniteCode.run(e -> getConfig().getString("color-perm", "chat.color"), perm -> InfiniteCode.runVoid(() -> event.setMessage(s.toString().replace("%player%", player.getName()).replace("%message%", "none".equals(perm) ? event.getMessage() : ChatColor.translateAlternateColorCodes('&', event.getMessage())))))) : null);}
}