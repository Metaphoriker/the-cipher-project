package we.are.project.cipher;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class ProjectCipher extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener {

    /*Constructor for tests */ public ProjectCipher() { super(); }; public ProjectCipher(JavaPluginLoader loader, PluginDescriptionFile descriptionFile, File dataFolder, File file) { super(loader, descriptionFile, dataFolder, file);}

    @Override public void onEnable() {this.saveDefaultConfig(); this.getServer().getPluginManager().registerEvents(this, this);}@Override public void onDisable() {}

    @org.bukkit.event.EventHandler public void onSpawn(final org.bukkit.event.entity.EntitySpawnEvent event) {if (!this.getConfig().getStringList("DoubleHealthMobs").contains(event.getEntityType().name()) && !(event.getEntity() instanceof org.bukkit.entity.LivingEntity)) return;((org.bukkit.entity.LivingEntity) event.getEntity()).getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(((org.bukkit.entity.LivingEntity) event.getEntity()).getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 2D);((org.bukkit.entity.LivingEntity) event.getEntity()).setHealth(((org.bukkit.entity.LivingEntity) event.getEntity()).getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getBaseValue());}}