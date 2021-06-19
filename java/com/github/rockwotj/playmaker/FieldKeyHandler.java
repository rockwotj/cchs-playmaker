package com.github.rockwotj.playmaker;

import com.github.rockwotj.playmaker.fieldpieces.DrawingField;
import com.github.rockwotj.playmaker.fieldpieces.Player;
import com.github.rockwotj.playmaker.fieldpieces.TextBox;
import com.github.rockwotj.playmaker.fieldpieces.Zone;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FieldKeyHandler extends KeyAdapter {
  private final DrawingField field;

  public FieldKeyHandler(DrawingField field) {
    this.field = field;
  }

  public void keyPressed(KeyEvent event) {
    switch (event.getKeyCode()) {
      case KeyEvent.VK_DELETE:
      case KeyEvent.VK_BACK_SPACE:
        field.removeSelected();
        break;
      case KeyEvent.VK_ENTER:
        {
          if (this.field.selected instanceof Player) {
            PropertiesFrame.playerPropertyFrame((Player) field.selected);
          } else if (this.field.selected instanceof Zone) {
            PropertiesFrame.zonePropertyFrame((Zone) field.selected);
          } else if (field.selected instanceof TextBox) {
            PropertiesFrame.textBoxPropertyFrame((TextBox) field.selected);
          }
          break;
        }
      case KeyEvent.VK_PAGE_UP:
        field.bringToFront();
        break;
      case KeyEvent.VK_PAGE_DOWN:
        field.sendToBack();
        break;
    }
  }
}
