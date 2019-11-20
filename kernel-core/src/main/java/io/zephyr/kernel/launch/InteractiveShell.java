package io.zephyr.kernel.launch;

import io.zephyr.api.Console;
import io.zephyr.api.Invoker;
import io.zephyr.api.Parameters;
import lombok.val;
import picocli.CommandLine;

import java.util.Arrays;

public class InteractiveShell {
  private final Console console;
  private final Invoker invoker;
  private final KernelOptions options;

  public InteractiveShell(Invoker invoker, KernelOptions options, Console console) {
    this.invoker = invoker;
    this.console = console;
    this.options = options;
  }

  public void start() {
    String[] args = null;
    for (; ; ) {
      try {
        args = console.read();
        if (isHelp(args)) {
          printHelp(console);
        }
        if (isExit(args)) {
          console.successln("Goodbye");
          return;
        }
        invoker.invoke(Parameters.of(args));
      } catch (Exception ex) {
        console.errorln("Command {0} not understood", Arrays.toString(args));
      }
    }
  }

  private void printHelp(Console console) throws Exception {
    console.successln("Usage:");
    val registry = invoker.getRegistry();
    for (val command : registry.getCommands()) {
      console.successln("Help for {0}", command.getName());
      val cli = new CommandLine(command);
      cli.usage(console.getWriter());
    }
  }

  private boolean isHelp(String[] c) {
    return doesEqual(c, "help");
  }

  private boolean doesEqual(String[] c, String target) {
    if (c == null) {
      return false;
    }
    for (val cmd : c) {
      if (cmd.trim().equalsIgnoreCase(target)) {
        return true;
      }
    }
    return false;
  }

  private boolean isExit(String[] c) {
    return doesEqual(c, "quit") || doesEqual(c, "exit");
  }
}
