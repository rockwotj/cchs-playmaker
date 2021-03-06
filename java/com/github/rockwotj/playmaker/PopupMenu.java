package com.github.rockwotj.playmaker;

import com.github.rockwotj.playmaker.fieldpieces.DrawingField;
import com.github.rockwotj.playmaker.fieldpieces.Player;
import com.github.rockwotj.playmaker.fieldpieces.TextBox;
import com.github.rockwotj.playmaker.fieldpieces.Zone;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class PopupMenu {
  private static DrawingField field;
  private static boolean firstAction;
  private static JPopupMenu playermenu;
  private static JMenuItem playeraddA;
  private static JMenuItem playeraddB;
  private static JMenuItem playeraddC;
  private static JMenuItem playeradd1;
  private static JMenuItem playeradd2;
  private static JMenuItem playeradd3;
  private static JMenuItem playeraddX;
  private static JMenuItem playeraddY;
  private static JMenuItem playeraddZ;
  private static JMenuItem playercircle;
  private static JMenuItem playersquare;
  private static JMenuItem playerwhite;
  private static JMenuItem playerblack;
  private static JMenuItem playerproperties;
  private static JPopupMenu zonemenu;
  private static JMenuItem zoneProperties;
  private static JMenuItem zoneblue;
  private static JMenuItem zoneyellow;
  private static JMenuItem zonecyan;
  private static JMenuItem zonegold;
  private static JMenuItem zonered;
  private static JMenuItem zoneblack;
  private static JPopupMenu textMenu;
  private static JMenuItem textProperties;
  private static JMenuItem textblue;
  private static JMenuItem textblack;
  private static JMenuItem textwhite;
  private static JMenuItem textred;
  private static JMenuItem texttimes;
  private static JMenuItem textluc;
  private static JMenuItem textarial;
  private static JMenuItem textgeo;

  public PopupMenu(DrawingField field) {
    PopupMenu.field = field;
    PopupMenu.firstAction = false;

    constructPlayerMenu();

    createZoneMenu();

    createTextMenu();
  }

  private void createTextMenu() {
    textMenu = new JPopupMenu();
    JMenu fontType = new JMenu(" Change font ");
    texttimes = new JMenuItem(" Times New Roman ");
    fontType.add(texttimes);
    textluc = new JMenuItem(" Lucida Console ");
    fontType.add(textluc);
    textarial = new JMenuItem(" Arial Black ");
    fontType.add(textarial);
    textgeo = new JMenuItem(" Georgia ");
    fontType.add(textgeo);
    JMenu fontColor = new JMenu(" Change font color ");
    textblack = new JMenuItem(" Black ");
    fontColor.add(textblack);
    textwhite = new JMenuItem(" White ");
    fontColor.add(textwhite);
    textblue = new JMenuItem(" Blue ");
    fontColor.add(textblue);
    textred = new JMenuItem(" Red ");
    fontColor.add(textred);
    textProperties = new JMenuItem(" Open Properties ");
    textMenu.add(fontType);
    textMenu.add(fontColor);
    textMenu.addSeparator();
    textMenu.add(textProperties);
  }

  private void createZoneMenu() {
    zonemenu = new JPopupMenu();
    JMenu zonechangeColor = new JMenu(" Change color  ");
    zonemenu.add(zonechangeColor);
    zoneblue = new JMenuItem(" Blue ");
    zonechangeColor.add(zoneblue);
    zoneyellow = new JMenuItem(" Yellow ");
    zonechangeColor.add(zoneyellow);
    zonecyan = new JMenuItem(" Turquoise ");
    zonechangeColor.add(zonecyan);
    zonegold = new JMenuItem(" Gold ");
    zonechangeColor.add(zonegold);
    zonered = new JMenuItem(" Red ");
    zonechangeColor.add(zonered);
    zoneblack = new JMenuItem(" Black ");
    zonechangeColor.add(zoneblack);
    zonemenu.addSeparator();
    zoneProperties = new JMenuItem(" Open Properties ");
    zonemenu.add(zoneProperties);
  }

  private void constructPlayerMenu() {
    playermenu = new JPopupMenu();
    JMenu playeraddLetter = new JMenu(" Add letter... ");
    playermenu.add(playeraddLetter);
    playeraddA = new JMenuItem(" A    ");
    playeraddLetter.add(playeraddA);
    playeraddB = new JMenuItem(" B    ");
    playeraddLetter.add(playeraddB);
    playeraddC = new JMenuItem(" C    ");
    playeraddLetter.add(playeraddC);
    playeraddLetter.addSeparator();
    playeradd1 = new JMenuItem(" 1    ");
    playeraddLetter.add(playeradd1);
    playeradd2 = new JMenuItem(" 2    ");
    playeraddLetter.add(playeradd2);
    playeradd3 = new JMenuItem(" 3    ");
    playeraddLetter.add(playeradd3);
    playeraddLetter.addSeparator();
    playeraddX = new JMenuItem(" X    ");
    playeraddLetter.add(playeraddX);
    playeraddY = new JMenuItem(" Y    ");
    playeraddLetter.add(playeraddY);
    playeraddZ = new JMenuItem(" Z    ");
    playeraddLetter.add(playeraddZ);
    JMenu playersetShape = new JMenu(" Set Shape   ");
    playermenu.add(playersetShape);
    playercircle = new JMenuItem(" Circle  ");
    playersetShape.add(playercircle);
    playersquare = new JMenuItem(" Square  ");
    playersetShape.add(playersquare);
    JMenu playersetColor = new JMenu(" Set Color   ");
    playermenu.add(playersetColor);
    playerwhite = new JMenuItem(" White  ");
    playersetColor.add(playerwhite);
    playerblack = new JMenuItem(" Black  ");
    playersetColor.add(playerblack);
    playermenu.addSeparator();
    playerproperties = new JMenuItem(" Open Properties ");
    playermenu.add(playerproperties);
  }

  public static void zonePopup(final Zone selected, int x, int y) {
    zonemenu.show(field, x, y);
    firstAction = true;
    zoneblue.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setColorFromString("Blue");
              PopupMenu.field.repaint();
            }
          }
        });
    zoneyellow.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setColorFromString("Yellow");
              PopupMenu.field.repaint();
            }
          }
        });
    zonecyan.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setColorFromString("Turquoise");
              PopupMenu.field.repaint();
            }
          }
        });
    zonegold.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setColorFromString("Gold");
              PopupMenu.field.repaint();
            }
          }
        });
    zonered.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setColorFromString("Red");
              PopupMenu.field.repaint();
            }
          }
        });
    zoneblack.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setColorFromString("Black");
              PopupMenu.field.repaint();
            }
          }
        });
    zoneProperties.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              PropertiesFrame.zonePropertyFrame(selected);
            }
          }
        });
  }

  public static void playerPopup(final Player selected, int x, int y) {
    playermenu.show(field, x, y);
    firstAction = true;
    playeraddA.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setTextEnabled(true);
              selected.setLetter("A");
              PopupMenu.field.repaint();
            }
          }
        });
    playeraddB.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setTextEnabled(true);
              selected.setLetter("B");
              PopupMenu.field.repaint();
            }
          }
        });
    playeraddC.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setTextEnabled(true);
              selected.setLetter("C");
              PopupMenu.field.repaint();
            }
          }
        });
    playeradd1.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setTextEnabled(true);
              selected.setLetter("1");
              PopupMenu.field.repaint();
            }
          }
        });
    playeradd2.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setTextEnabled(true);
              selected.setLetter("2");
              PopupMenu.field.repaint();
            }
          }
        });
    playeradd3.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setTextEnabled(true);
              selected.setLetter("3");
              PopupMenu.field.repaint();
            }
          }
        });
    playeraddX.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setTextEnabled(true);
              selected.setLetter("X");
              PopupMenu.field.repaint();
            }
          }
        });
    playeraddY.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setTextEnabled(true);
              selected.setLetter("Y");
              PopupMenu.field.repaint();
            }
          }
        });
    playeraddZ.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setTextEnabled(true);
              selected.setLetter("Z");
              PopupMenu.field.repaint();
            }
          }
        });
    playercircle.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.SetSquare(false);
              PopupMenu.field.repaint();
            }
          }
        });
    playersquare.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.SetSquare(true);
              PopupMenu.field.repaint();
            }
          }
        });
    playerwhite.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setWhite(true);
              PopupMenu.field.repaint();
            }
          }
        });
    playerblack.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setWhite(false);
              PopupMenu.field.repaint();
            }
          }
        });
    playerproperties.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              PropertiesFrame.playerPropertyFrame(selected);
            }
          }
        });
  }

  public static void textBoxPopup(final TextBox selected, int x, int y) {
    textMenu.show(field, x, y);
    firstAction = true;
    textProperties.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              PropertiesFrame.textBoxPropertyFrame(selected);
            }
          }
        });
    textblue.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setFontColor("Blue");
              PopupMenu.field.repaint();
            }
          }
        });
    textblack.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setFontColor("Black");
              PopupMenu.field.repaint();
            }
          }
        });
    textwhite.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setFontColor("White");
              PopupMenu.field.repaint();
            }
          }
        });
    textred.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setFontColor("Red");
              PopupMenu.field.repaint();
            }
          }
        });
    texttimes.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setFontStyle("Times New Roman", (Graphics2D) PopupMenu.field.getGraphics());
              PopupMenu.field.repaint();
            }
          }
        });
    textluc.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setFontStyle("Lucida Console", (Graphics2D) PopupMenu.field.getGraphics());
              PopupMenu.field.repaint();
            }
          }
        });
    textarial.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setFontStyle("Arial Black", (Graphics2D) PopupMenu.field.getGraphics());
              PopupMenu.field.repaint();
            }
          }
        });
    textgeo.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            if (PopupMenu.firstAction) {
              PopupMenu.firstAction = false;
              selected.setFontStyle("Georgia", (Graphics2D) PopupMenu.field.getGraphics());
              PopupMenu.field.repaint();
            }
          }
        });
  }
}

/* Location:           D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\FootballPlayMaker.jar

* Qualified Name:     GUI.PopupMenu

* JD-Core Version:    0.7.0.1

*/
