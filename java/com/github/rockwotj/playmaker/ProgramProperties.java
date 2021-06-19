package com.github.rockwotj.playmaker;

import java.awt.Color;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class ProgramProperties {
  private final Preferences preferences;

  private ProgramProperties(Preferences prefs) {
    preferences = prefs;
  }

  public static ProgramProperties load() {
    Preferences prefs = Preferences.userNodeForPackage(Main.class);
    return new ProgramProperties(prefs);
  }

  public void save() throws IOException {
    try {
      preferences.sync();
    } catch (BackingStoreException e) {
      throw new IOException(e);
    }
  }

  public String fontStyle() {
    return preferences.get("fontStyle", "Times New Roman");
  }

  public void setFontStyle(String style) {
    preferences.put("fontStyle", style);
  }

  public int setFontSize() {
    return preferences.getInt("fontSize", 15);
  }

  public void setFontSize(int size) {
    preferences.putInt("fontSize", size);
  }

  public Color textColor() {
    return new Color(preferences.getInt("textColor", Color.BLACK.getRGB()));
  }

  public void setTextColor(Color color) {
    preferences.putInt("textColor", color.getRGB());
  }

  public PlayerShape playerShape() {
    String name = preferences.get("playerShape", PlayerShape.CIRCLE.name());
    for (PlayerShape shape : PlayerShape.values()) {
      if (shape.name().equals(name)) return shape;
    }
    return PlayerShape.CIRCLE;
  }

  public void setPlayerShape(PlayerShape shape) {
    preferences.put("playerShape", shape.name());
  }

  public Color playerColor() {
    return new Color(preferences.getInt("playerColor", Color.WHITE.getRGB()));
  }

  public void setPlayerColor(Color color) {
    preferences.putInt("playerColor", color.getRGB());
  }

  public Color zoneColor() {
    return new Color(preferences.getInt("zoneColor", Color.BLUE.getRGB()));
  }

  public void setZoneColor(Color color) {
    preferences.putInt("zoneColor", color.getRGB());
  }
}
