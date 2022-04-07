/**Made by Ik#2932, This has limited uses but is really useful to save on lines!*/ public class InfiniteCode {
/**Interface to run code, takes output from the last, and outputs a new value (first input is null)*/ public interface ArgRunnable { public Object run(Object arg); }
/**Runnable in, Output out*/ public static Object run(ArgRunnable... args) {Object result = null;for (ArgRunnable arg : args) result = arg.run(result);return result;}
}
