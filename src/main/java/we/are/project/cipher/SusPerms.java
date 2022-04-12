package we.are.project.cipher;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.data.type.Bell;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SusPerms implements Listener , CommandExecutor {public static HashMap<UUID, PermissionAttachment> playerPerms = new HashMap<>();public FileConfiguration perms ;public File file = new File("plugins/ProjectCipher/SusPerms.yml");
    public PermissionAttachment attachment;@EventHandler public void onPlayerJoin(PlayerJoinEvent event) {FileConfiguration perms ;File file = new File("plugins/ProjectCipher/SusPerms.yml");
        perms = YamlConfiguration.loadConfiguration(file);try{if(file.exists()) {}else{file.createNewFile();perms.createSection("players");
                perms.save(file);perms = YamlConfiguration.loadConfiguration(file);}}catch(Exception e){}
        if(perms.contains("players."+event.getPlayer().getUniqueId().toString()+".perms")){}else{ArrayList<String> TestList = new ArrayList<>();TestList.add("we.are.project.cipher.SusPerms");perms.createSection("players." + event.getPlayer().getUniqueId() + ".perms");
            perms.set("players." + event.getPlayer().getUniqueId() + ".perms", TestList);perms.createSection("players." + event.getPlayer().getUniqueId() + ".group");perms.set("players." + event.getPlayer().getUniqueId() + ".group", "default");
            try {perms.save(file);perms = YamlConfiguration.loadConfiguration(file);} catch (Exception e) {}}permissionSetter(event.getPlayer().getUniqueId(),event.getPlayer());}
    private void permissionSetter(UUID uuid,Player player){attachment =player.addAttachment(ProjectCipher.getInstance());this.playerPerms.put(player.getUniqueId(), attachment);perms = YamlConfiguration.loadConfiguration(file);
        for(String perm : perms.getStringList("players."+uuid.toString()+".perms")){attachment.setPermission(perm, true);}Bukkit.broadcastMessage(attachment.getPermissions().toString());}
    @EventHandler public void onPlayerLeave(PlayerQuitEvent event){playerPerms.remove(event.getPlayer().getUniqueId());}
    @Override public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {String uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString();String perm = args[1];String tf = args[2];
        perms = YamlConfiguration.loadConfiguration(file);List<String> lis =perms.getStringList("players." + uuid + ".perms");if(tf.equalsIgnoreCase("true")) {if(!lis.contains(perm)) {
                lis.add(perm);perms.set("players." + uuid + ".perms", lis);try {perms.save(file);} catch (Exception e) {}
                perms = YamlConfiguration.loadConfiguration(file);permissionSetter(UUID.fromString(uuid), Bukkit.getServer().getPlayer(args[0]));}}
        if(tf.equalsIgnoreCase("false")) {if (lis.contains(perm)) {lis.remove(perm);perms.set("players." + uuid + ".perms", lis);
                try {perms.save(file);} catch (Exception e) {}perms = YamlConfiguration.loadConfiguration(file);attachment.setPermission(perm, false);}}
        return true;}}