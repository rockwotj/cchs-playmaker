package com.github.rockwotj.playmaker;

import com.github.rockwotj.playmaker.fieldpieces.DrawingField;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class HeaderMenu extends JMenuBar {
  public HeaderMenu(final DrawingField field) {
    JMenu file = new JMenu("File");
    file.setMnemonic(70);
    add(file);

    JMenuItem open = new JMenuItem("Open file");
    open.setMnemonic(79);
    open.addActionListener(arg -> field.load());
    file.add(open);
    JMenuItem save = new JMenuItem("Save");
    save.setMnemonic(83);
    save.addActionListener(arg -> field.save());
    file.add(save);
    JMenuItem saveAs = new JMenuItem("Save as");
    saveAs.setMnemonic(65);
    saveAs.addActionListener(arg -> field.saveAs());
    file.add(saveAs);
    JMenu export = new JMenu("Export as...");
    export.setMnemonic(69);
    file.add(export);

    JMenuItem jpeg = new JMenuItem("JPEG");
    jpeg.setMnemonic(74);
    export.add(jpeg);
    jpeg.addActionListener(arg -> field.exportAsJPEG());
    JMenuItem png = new JMenuItem("PNG");
    export.add(png);
    png.setMnemonic(80);
    png.addActionListener(arg -> field.exportAsPNG());
    JMenuItem gif = new JMenuItem("GIF");
    export.add(gif);
    gif.setMnemonic(71);
    gif.addActionListener(arg -> field.exportAsGIF());
    file.addSeparator();
    JMenuItem exit = new JMenuItem("Exit");
    exit.setMnemonic(88);
    file.add(exit);
    exit.addActionListener(
        arg -> {
          int confirm =
              JOptionPane.showOptionDialog(
                  null,
                  "Are You Sure to Close the Playmaker?",
                  "Exit Confirmation",
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.QUESTION_MESSAGE,
                  null,
                  null,
                  null);
          if (confirm == 0) {
            System.exit(0);
          }
        });
    JMenu options = new JMenu("Options");
    options.setMnemonic(79);
    add(options);
    JMenuItem fileName = new JMenuItem("Name Play");
    fileName.setMnemonic(78);
    options.add(fileName);
    fileName.addActionListener(
        arg -> {
          String file1 =
              JOptionPane.showInputDialog("Please input the play's name:", Main.fileName);
          if (file1 != null) {
            Main.fileName = file1;
            Main.setFrameTitle();
          }
        });
    JMenuItem perferences = new JMenuItem("Preferences");
    perferences.setMnemonic(80);
    options.add(perferences);
    perferences.addActionListener(
        arg -> {
          PropertiesFrame.setDefaultsFrame();
          field.repaint();
        });
  }
}
