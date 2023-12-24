package we.are.project.cipher;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.*;

/**Made by IkeVoodoo, This has limited uses but is really useful to save on lines!*/ public class InfiniteCode { /**Runnables in, Output out*/ public static Object run(ArgRunnable... args) throws Exception {Object result = null;for (ArgRunnable arg : args) result = arg.run(result);return result;}
/**Interface to run code, takes output from the last, and outputs a new value (first input is null)*/ public interface ArgRunnable { public Object run(Object arg) throws Exception; }public static Object runVoid(Runnable runnable) {runnable.run();return null;}
    private static CommandMap map = null;
    public static Object whilst(ArgRunnable condition, ArgRunnable exec, ArgRunnable fail) throws Exception {Object lastExec = null; while(run(condition) != null && (lastExec = run(exec)) != null); return lastExec == null ? run(fail) : lastExec;}
    public static boolean registerCommand(String name, String desc, String usage, List<String> aliases, ArgRunnable runnable) throws Exception {return run(e -> map == null ? map = (CommandMap) run(empty -> Bukkit.getServer().getClass().getDeclaredField("commandMap"), field -> run(empty -> InfiniteCode.runVoid(() -> ((Field)field).setAccessible(true)), empty -> ((Field)field).get(Bukkit.getServer()))) : null, e -> map.register(name, new Command(name, desc, usage, Optional.ofNullable(aliases).orElse(Arrays.asList())) {@Override public boolean execute(CommandSender commandSender, String s, String[] strings) {try {return run(e -> runnable.run(new Object[]{commandSender, strings})) != null;} catch (Exception ignored) {} return false;}})) != null;}
    /*Public Map for variables. Allows setting variables without semicolons*/public static final Map<String,Object> VARIABLES = new HashMap<>();
}
