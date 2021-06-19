package com.github.rockwotj.playmaker;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.github.rockwotj.playmaker.fieldpieces.DrawingField;
import com.github.rockwotj.playmaker.fieldpieces.Player;
import com.github.rockwotj.playmaker.fieldpieces.TextBox;
import com.github.rockwotj.playmaker.fieldpieces.Zone;

public class FieldKeyHandler implements KeyListener {
  private DrawingField field;
  private ToolBar toolBar;

  public FieldKeyHandler(DrawingField field, ToolBar optionToolBar) {
    this.field = field;
    this.toolBar = optionToolBar;
  }

  public void keyPressed(KeyEvent arg0) {
    int keyCode = arg0.getKeyCode();
    if ((keyCode == 127) && (!this.toolBar.selection.isEnabled())) {
      this.field.removeSelected();
    }
    if ((keyCode == 10) && (!this.toolBar.selection.isEnabled())) {
      if ((this.field.selected instanceof Player)) {
        PropertiesFrame.playerPropertyFrame((Player) this.field.selected);
      } else if ((this.field.selected instanceof Zone)) {
        PropertiesFrame.zonePropertyFrame((Zone) this.field.selected);
      } else if ((this.field.selected instanceof TextBox)) {
        PropertiesFrame.textBoxPropertyFrame((TextBox) this.field.selected);
      }
    }
    if ((keyCode == 33) && (!this.toolBar.selection.isEnabled())) {
      this.field.bringToFront();
    }
    if ((keyCode == 34) && (!this.toolBar.selection.isEnabled())) {
      this.field.sendToBack();
    }
  }

  public void keyReleased(KeyEvent arg0) {}

  public void keyTyped(KeyEvent arg0) {}
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 *
 * Qualified Name: GUI.FieldKeyHandler
 *
 * JD-Core Version: 0.7.0.1
 */