package com.github.rockwotj.playmaker;

import com.github.rockwotj.playmaker.fieldpieces.DrawingField;
import com.google.common.collect.ImmutableList;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class ToolBar extends JPanel {
  public final JButton player;
  public final JButton arrowhead;
  public final JButton misc;
  public final JButton blockhead;
  public final JButton selection;
  public final JButton zone;
  public final List<JButton> allButtons;

  public final JTextField text;
  public final JTextArea tooltip;
  public final JComboBox<String> lines;
  public final JButton add;
  private final DrawingField field;
  private TitledBorder name;

  public ToolBar(final DrawingField field) {
    this.name = BorderFactory.createTitledBorder(Main.fileName);
    setBorder(this.name);
    this.field = field;
    JPanel buttons = new JPanel();
    GridLayout lay = new GridLayout(0, 1);
    buttons.setLayout(lay);
    this.selection = new JButton("Selection Tool");
    this.player = new JButton("Add Player");
    String[] typesOfLines = {"Solid Line", "Dotted Line", "Motion Line", "Curved Line"};
    this.lines = new JComboBox<>(typesOfLines);
    JPanel lineBox = new JPanel();
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    c.fill = 1;
    layout.setConstraints(this.lines, c);
    lineBox.setLayout(layout);
    this.add = new JButton("Add");
    lineBox.add(this.add);
    lineBox.add(this.lines);
    this.arrowhead = new JButton("Add Arrowhead");
    this.blockhead = new JButton("Add Blockhead");
    this.misc = new JButton("Add Text Box");
    this.zone = new JButton("Add Zone");
    this.text = new JTextField();
    this.text.setColumns(10);
    this.text.setMaximumSize(this.text.getPreferredSize());
    this.tooltip = new JTextArea();
    this.tooltip.setEditable(false);
    this.tooltip.setLineWrap(true);
    this.tooltip.setWrapStyleWord(true);
    this.tooltip.setFont(new Font("Times New Roman", Font.PLAIN, 14));
    this.tooltip.setText("Click a button to start");
    buttons.add(this.selection);
    buttons.add(this.player);
    buttons.add(lineBox);
    buttons.add(this.arrowhead);
    buttons.add(this.blockhead);
    buttons.add(this.zone);
    buttons.add(this.misc);
    JPanel textPanel = new JPanel();
    textPanel.setLayout(new BorderLayout());
    textPanel.add(this.text, "North");
    textPanel.add(this.tooltip);
    setLayout(new BorderLayout());
    add(buttons, "North");
    add(textPanel);

    allButtons = ImmutableList.of(selection, player, arrowhead, misc, blockhead, zone, add);

    setButtonAction(
        selection,
        () -> {
          tooltip.setText(
              "Click & drag the mouse to move an object. "
                  + "Right or double click to edit its properties. "
                  + "Use page_up and page_down keys to move the piece up and down. "
                  + "Delete key will remove pieces.");
          field.requestFocusInWindow();
        });

    setButtonAction(
        add,
        () -> tooltip.setText(
            "Click & drag the mouse to define a line. "
                + "For a curved line after the straight line is defined "
                + "your next click will define the curveness of the line"));

    setButtonAction(
        player,
        () -> tooltip.setText("Click to add a player"));
    setButtonAction(
        arrowhead,
        () -> tooltip.setText("Click & drag the mouse to make a line with an arrowhead"));
    setButtonAction(
        blockhead,
        () -> tooltip.setText("Click & drag the mouse to make a line that ends in a pancake"));
    setButtonAction(
        misc,
        () -> tooltip.setText("Click to create a text-box enter your text in the field above"));
    setButtonAction(
        zone,
        () -> tooltip.setText("Click & drag to define a \ndefensive zone"));
  }

  public void setName() {
    this.name = BorderFactory.createTitledBorder(Main.fileName);
    setBorder(this.name);
  }

  private void setButtonAction(JButton button, Runnable action) {
    button.addActionListener(
        arg -> {
          for (JButton otherButton : allButtons) {
            otherButton.setEnabled(button != otherButton);
          }
          field.unhighlightAll();
          action.run();
        });
  }
}
