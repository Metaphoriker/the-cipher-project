package we.are.project.cipher;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.BlockFace;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;

import java.io.File;
import java.util.Optional;

public class ProjectCipher extends JavaPlugin implements Listener { @Override public void onEnable() {this.saveDefaultConfig(); this.getServer().getPluginManager().registerEvents(this, this); Bukkit.getPluginManager().registerEvents(new AngelChestListener(), this);}@Override public void onDisable() {}
    /*Constructor for tests */ public ProjectCipher() { super(); }; public ProjectCipher(JavaPluginLoader loader, PluginDescriptionFile descriptionFile, File dataFolder, File file) { super(loader, descriptionFile, dataFolder, file);}
    private static ProjectCipher instance; { instance = this; } public static ProjectCipher getInstance() { return instance; }
    @EventHandler public void on(final EntitySpawnEvent event) {if (!this.getConfig().getStringList("DoubleHealthMobs").contains(event.getEntityType().name()) && !(event.getEntity() instanceof LivingEntity)) return;((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 2D);((LivingEntity) event.getEntity()).setHealth(((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());}
    @EventHandler public void on(PlayerDeathEvent event) throws Exception {InfiniteCode.run(arg -> event.getEntity(), player -> ((Player)player).getAttribute(Attribute.GENERIC_MAX_HEALTH), attrib -> InfiniteCode.runVoid(() -> ((AttributeInstance)attrib).setBaseValue(((AttributeInstance) attrib).getBaseValue() - 2)), empty -> event.getEntity(), player -> ((Player)player).getKiller(), killer -> Optional.ofNullable(killer instanceof Player player ? player.getAttribute(Attribute.GENERIC_MAX_HEALTH) : null), attribOpt -> InfiniteCode.runVoid(() -> ((Optional<AttributeInstance>)attribOpt).ifPresent(attrib -> attrib.setBaseValue(attrib.getBaseValue() + 2))), empty -> event.getEntity(), player -> ((Player)player).getAttribute(Attribute.GENERIC_MAX_HEALTH), attrib -> attrib instanceof AttributeInstance attribute && attribute.getBaseValue() <= 0, ban -> Boolean.parseBoolean(ban + "") ? InfiniteCode.runVoid(() -> Bukkit.getBanList(BanList.Type.NAME).addBan(event.getEntity().getName(), "You have been eliminated", null, null)) : 1, val -> val == null ? InfiniteCode.runVoid(() -> event.getEntity().kickPlayer("You have been eliminated")) : 1);} @EventHandler(priority = EventPriority.MONITOR) public void onEggLand(final ProjectileHitEvent event) {if (event.getEntity().getType() == org.bukkit.entity.EntityType.EGG && event.getHitEntity() != null) {event.getHitEntity().getWorld().createExplosion(event.getEntity().getLocation(), 3f);}}
    @EventHandler public void on(PlayerMoveEvent event) throws Exception {InfiniteCode.run(e -> event.getPlayer(), p -> p instanceof Player player && player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.WATER) && event.getPlayer().getVelocity().getY() < .1 ? 1 : null, b -> b != null ? InfiniteCode.runVoid(() -> event.setCancelled(true)) : 1, e -> event.getPlayer(), p -> p instanceof Player player && player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.LAVA) && event.getPlayer().getVelocity().getY() < .1 ? 1 : null, b -> b != null ? InfiniteCode.runVoid(() -> event.setCancelled(true)) : 1);} @EventHandler public void on(EntityDamageByEntityEvent event) throws Exception {InfiniteCode.run(e -> event.getDamager(), p -> p instanceof LivingEntity attacker ? InfiniteCode.run(e -> event.getEntity(), e2 -> e2 instanceof Entity attacked ? InfiniteCode.run(e -> new BlockIterator(attacker, (int)Math.ceil(attacker.getLocation().distance(attacked.getLocation()))), iter -> iter instanceof BlockIterator iterator ? InfiniteCode.whilst(condition -> iterator.hasNext() ? 1 : null, exec -> iterator.next().getLocation().getBlock().getType().isSolid() ? null : 1, fail -> InfiniteCode.runVoid(() -> event.setCancelled(true))) : null) : null) : null);} @EventHandler public void on(PlayerInteractEvent event) throws Exception {InfiniteCode.run(e -> event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK ? event.getClickedBlock() : null, block -> block != null ? InfiniteCode.run(e -> event.getClickedBlock().getLocation().distance(event.getPlayer().getLocation()), dist -> new BlockIterator(event.getClickedBlock().getLocation(), (int)dist), iter -> iter instanceof BlockIterator iterator ? InfiniteCode.whilst(condition -> iterator.hasNext() ? 1 : null, exec -> iterator.next().getLocation().getBlock().getType().isSolid() ? null : 1, fail -> InfiniteCode.runVoid(() -> event.setCancelled(true))) : null) : null);}

    public ProjectCipher run(Runnable... runnables) { for (Runnable runnable : runnables) {(runnable == null ? new Runnable() {@Override public void run() {}} : runnable).run();} return this; } public ProjectCipher runIfElse(boolean bool, Runnable whenTrue, Runnable whenFalse) { return run(bool ? whenTrue : whenFalse); }

}