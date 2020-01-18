package io.zephyr.kernel.core;

import io.zephyr.kernel.events.EventType;
import lombok.Getter;

public enum PluginEvents implements EventType {

  /** lifecycle for plugin set */
  PLUGIN_SET_INSTALLATION_INITIATED,
  PLUGIN_SET_INSTALLATION_COMPLETE,

  /** lifecycle for individual plugins */
  PLUGIN_INSTALLATION_INITIATED,
  PLUGIN_INSTALLATION_COMPLETE,
  PLUGIN_INSTALLATION_FAILED;

  @Getter private final int id;

  PluginEvents() {
    id = EventType.newId();
  }
}
