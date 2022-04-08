package we.are.project.cipher;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Optional;

public class ProjectCipher extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener { @Override public void onEnable() {this.saveDefaultConfig(); this.getServer().getPluginManager().registerEvents(this, this);}@Override public void onDisable() {}
    @EventHandler public void on(final EntitySpawnEvent event) {if (!this.getConfig().getStringList("DoubleHealthMobs").contains(event.getEntityType().name()) && !(event.getEntity() instanceof LivingEntity)) return;((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 2D);((LivingEntity) event.getEntity()).setHealth(((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());}
    @EventHandler public void on(PlayerDeathEvent event) throws Exception {InfiniteCode.run(arg -> event.getEntity(), player -> ((Player)player).getAttribute(Attribute.GENERIC_MAX_HEALTH), attrib -> InfiniteCode.runVoid(() -> ((AttributeInstance)attrib).setBaseValue(((AttributeInstance) attrib).getBaseValue() - 2)), empty -> event.getEntity(), player -> ((Player)player).getKiller(), killer -> Optional.ofNullable(killer instanceof Player player ? player.getAttribute(Attribute.GENERIC_MAX_HEALTH) : null), attribOpt -> InfiniteCode.runVoid(() -> ((Optional<AttributeInstance>)attribOpt).ifPresent(attrib -> attrib.setBaseValue(attrib.getBaseValue() + 2))), empty -> event.getEntity(), player -> ((Player)player).getAttribute(Attribute.GENERIC_MAX_HEALTH), attrib -> attrib instanceof AttributeInstance attribute && attribute.getBaseValue() <= 0, ban -> Boolean.parseBoolean(ban + "") ? InfiniteCode.runVoid(() -> Bukkit.getBanList(BanList.Type.NAME).addBan(event.getEntity().getName(), "You have been eliminated", null, null)) : 1, val -> val == null ? InfiniteCode.runVoid(() -> event.getEntity().kickPlayer("You have been eliminated")) : 1);}}