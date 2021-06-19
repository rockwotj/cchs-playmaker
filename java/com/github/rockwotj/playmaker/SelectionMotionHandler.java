package com.github.rockwotj.playmaker;

import com.github.rockwotj.playmaker.fieldpieces.CurvedLine;
import com.github.rockwotj.playmaker.fieldpieces.DrawingField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;

public class SelectionMotionHandler implements MouseMotionListener {
  private ToolBar toolBar;
  private DrawingField field;
  private ToolClickHandler clicker;

  public SelectionMotionHandler(
      DrawingField field, ToolBar optionToolBar, ToolClickHandler clickHandler) {
    this.toolBar = optionToolBar;
    this.field = field;
    this.clicker = clickHandler;
  }

  public void mouseDragged(MouseEvent arg0) {
    if (SwingUtilities.isLeftMouseButton(arg0)) {
      if (!this.toolBar.selection.isEnabled()) {
        if (this.field.selected != null) {
          this.field.moveSelectedTo(arg0.getX(), arg0.getY());
        }
      } else if (((!this.toolBar.add.isEnabled()) && (this.toolBar.lines.getSelectedIndex() == 0))
          || ((!this.toolBar.add.isEnabled()) && (this.toolBar.lines.getSelectedIndex() == 1))
          || (((!this.toolBar.arrowhead.isEnabled())
                  || (!this.toolBar.blockhead.isEnabled())
                  || ((!this.toolBar.add.isEnabled())
                      && (this.toolBar.lines.getSelectedIndex() == 2)))
              && (this.field.selected != null))) {
        this.field.moveTail(arg0.getX(), arg0.getY());
      } else if ((!this.toolBar.add.isEnabled())
          && (this.toolBar.lines.getSelectedIndex() == 3)
          && (this.field.selected != null)) {
        if ((this.field.selected instanceof CurvedLine)) {
          if (this.clicker.timeToAddCurvedControlPoint == 0) {
            this.field.moveTail(arg0.getX(), arg0.getY());
          }
        } else {
          this.field.moveTail(arg0.getX(), arg0.getY());
        }
      } else if ((!this.toolBar.zone.isEnabled()) && (this.field.selected != null)) {
        this.field.setEdge(arg0.getX(), arg0.getY());
      }
    }
  }

  public void mouseMoved(MouseEvent e) {
    if ((!this.toolBar.add.isEnabled())
        && (this.toolBar.lines.getSelectedIndex() == 3)
        && (this.field.selected != null)
        && ((this.field.selected instanceof CurvedLine))) {
      this.field.setCurvedControl(e.getX(), e.getY());
    }
  }
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 *
 * Qualified Name: GUI.SelectionMotionHandler
 *
 * JD-Core Version: 0.7.0.1
 */
