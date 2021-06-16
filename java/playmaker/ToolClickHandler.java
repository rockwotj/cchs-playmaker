package playmaker;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;
import playmaker.fieldpieces.Arrowhead;
import playmaker.fieldpieces.Blockhead;
import playmaker.fieldpieces.CurvedLine;
import playmaker.fieldpieces.DashedLine;
import playmaker.fieldpieces.DrawingField;
import playmaker.fieldpieces.MotionLine;
import playmaker.fieldpieces.Player;
import playmaker.fieldpieces.SolidLine;
import playmaker.fieldpieces.TextBox;
import playmaker.fieldpieces.Zone;

public class ToolClickHandler implements MouseListener {
  private ToolBar toolBar;
  private DrawingField field;
  int timeToAddCurvedControlPoint;

  public ToolClickHandler(DrawingField field, ToolBar optionToolBar) {
    this.toolBar = optionToolBar;
    this.field = field;
    this.timeToAddCurvedControlPoint = 0;
  }

  public void mouseClicked(MouseEvent arg0) {
    if ((!this.toolBar.selection.isEnabled())
        && (arg0.getClickCount() == 2)
        && (SwingUtilities.isLeftMouseButton(arg0))) {
      this.field.findPieceAt(arg0.getX(), arg0.getY());
      if (this.field.selected != null) {
        if ((this.field.selected instanceof Player)) {
          PropertiesFrame.playerPropertyFrame((Player) this.field.selected);
        } else if ((this.field.selected instanceof TextBox)) {
          PropertiesFrame.textBoxPropertyFrame((TextBox) this.field.selected);
        } else if ((this.field.selected instanceof Zone)) {
          PropertiesFrame.zonePropertyFrame((Zone) this.field.selected);
        }
      }
    }
  }

  public void mouseEntered(MouseEvent arg0) {
    if (!this.toolBar.selection.isEnabled()) {
      this.field.requestFocusInWindow();
    }
  }

  public void mouseExited(MouseEvent arg0) {}

  public void mousePressed(MouseEvent arg0) {
    if ((SwingUtilities.isRightMouseButton(arg0))
        && ((!this.toolBar.selection.isEnabled())
            || (!this.toolBar.player.isEnabled())
            || (!this.toolBar.misc.isEnabled())
            || (!this.toolBar.zone.isEnabled()))) {
      this.field.findPieceAt(arg0.getX(), arg0.getY());
      if (this.field.selected != null) {
        if ((this.field.selected instanceof Player)) {
          PopupMenu.playerPopup((Player) this.field.selected, arg0.getX(), arg0.getY());
        } else if ((this.field.selected instanceof TextBox)) {
          PopupMenu.textBoxPopup((TextBox) this.field.selected, arg0.getX(), arg0.getY());
        } else if ((this.field.selected instanceof Zone)) {
          PopupMenu.zonePopup((Zone) this.field.selected, arg0.getX(), arg0.getY());
        }
      }
    } else if (SwingUtilities.isLeftMouseButton(arg0)) {
      if ((this.timeToAddCurvedControlPoint > 1)
          && (!this.toolBar.add.isEnabled())
          && (this.toolBar.lines.getSelectedIndex() == 3)) {
        this.timeToAddCurvedControlPoint = 0;
      } else if (this.timeToAddCurvedControlPoint == 1) {
        this.timeToAddCurvedControlPoint += 1;
      } else if (!this.toolBar.selection.isEnabled()) {
        this.field.findPieceAt(arg0.getX(), arg0.getY());
      } else if ((!this.toolBar.add.isEnabled()) && (this.toolBar.lines.getSelectedIndex() == 0)) {
        SolidLine line = this.field.addSolid(arg0.getX(), arg0.getY());
        this.field.selected = line;
      } else if ((!this.toolBar.add.isEnabled()) && (this.toolBar.lines.getSelectedIndex() == 1)) {
        DashedLine line = this.field.addDashed(arg0.getX(), arg0.getY());
        this.field.selected = line;
      } else if (!this.toolBar.arrowhead.isEnabled()) {
        Arrowhead line = this.field.addArrow(arg0.getX(), arg0.getY());
        this.field.selected = line;
      } else if (!this.toolBar.blockhead.isEnabled()) {
        Blockhead line = this.field.addBlock(arg0.getX(), arg0.getY());
        this.field.selected = line;
      } else if ((!this.toolBar.add.isEnabled()) && (this.toolBar.lines.getSelectedIndex() == 2)) {
        MotionLine line = this.field.addMotion(arg0.getX(), arg0.getY());
        this.field.selected = line;
      } else if (!this.toolBar.zone.isEnabled()) {
        Zone zone = this.field.addZone(arg0.getX(), arg0.getY());
        this.field.selected = zone;
      } else if ((!this.toolBar.add.isEnabled()) && (this.toolBar.lines.getSelectedIndex() == 3)) {
        CurvedLine line = this.field.addCurve(arg0.getX(), arg0.getY());
        this.field.selected = line;
      }
    }
  }

  public void mouseReleased(MouseEvent arg0) {
    if (SwingUtilities.isLeftMouseButton(arg0)) {
      if (this.field.selected != null) {
        if (this.timeToAddCurvedControlPoint > 0) {
          if (this.timeToAddCurvedControlPoint > 1) {
            this.timeToAddCurvedControlPoint = 0;
            this.field.setCurvedControl(arg0.getX(), arg0.getY());
            this.field.selected = null;
          }
        } else if ((this.timeToAddCurvedControlPoint == 0)
            && ((this.field.selected instanceof CurvedLine))
            && (!this.toolBar.add.isEnabled())
            && (this.toolBar.lines.getSelectedIndex() == 3)) {
          this.timeToAddCurvedControlPoint += 1;
        } else if (this.toolBar.selection.isEnabled()) {
          this.field.selected = null;
        }
      } else if (!this.toolBar.player.isEnabled()) {
        this.field.addPlayer(arg0.getX(), arg0.getY());
      } else if (!this.toolBar.misc.isEnabled()) {
        this.field.addTextBox(arg0.getX(), arg0.getY(), this.toolBar.text.getText());
      }
    }
  }

  public void timeToAddCurvedControlPointPlusPlus() {
    this.timeToAddCurvedControlPoint += 1;
  }
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 *
 * Qualified Name: GUI.ToolClickHandler
 *
 * JD-Core Version: 0.7.0.1
 */
