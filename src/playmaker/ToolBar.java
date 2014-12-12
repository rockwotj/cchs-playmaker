package playmaker;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import playmaker.fieldpieces.DrawingField;

public class ToolBar extends JPanel
{
	public final JButton player;
	public final JButton arrowhead;
	public final JButton misc;
	public final JButton blockhead;
	public final JButton selection;
	public final JButton zone;
	public final JTextField text;
	public final JTextArea tooltip;
	public final JComboBox<String> lines;
	public final JButton add;
	public static final int SolidLine = 0;
	public static final int DottedLine = 1;
	public static final int MotionLine = 2;
	public static final int CurvedLine = 3;
	private final DrawingField field;
	private TitledBorder name;

	public ToolBar(final DrawingField field)
	{
		this.name = BorderFactory.createTitledBorder(Main.fileName);
		setBorder(this.name);
		this.field = field;
		JPanel buttons = new JPanel();
		GridLayout lay = new GridLayout(0, 1);
		buttons.setLayout(lay);
		this.selection = new JButton("Selection Tool");
		this.player = new JButton("Add Player");
		String[] typesOfLines = { "Solid Line", "Dotted Line", "Motion Line", "Curved Line" };
		this.lines = new JComboBox(typesOfLines);
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
		this.tooltip.setFont(new Font("Times New Roman", 0, 14));
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

		this.selection.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ToolBar.this.selection.setEnabled(false);
				ToolBar.this.player.setEnabled(true);
				ToolBar.this.arrowhead.setEnabled(true);
				ToolBar.this.misc.setEnabled(true);
				ToolBar.this.blockhead.setEnabled(true);
				ToolBar.this.zone.setEnabled(true);
				ToolBar.this.add.setEnabled(true);
				ToolBar.this.tooltip
						.setText("Click & drag the mouse tomove an object. Right or  double click to edit its    \nproperties. Use page_up \nand page_down keys to \nmove the piece up and \ndown. Delete key will \nremove pieces.");
				field.requestFocusInWindow();
			}
		});
		this.add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ToolBar.this.selection.setEnabled(true);
				ToolBar.this.player.setEnabled(true);
				ToolBar.this.arrowhead.setEnabled(true);
				ToolBar.this.misc.setEnabled(true);
				ToolBar.this.blockhead.setEnabled(true);
				ToolBar.this.zone.setEnabled(true);
				ToolBar.this.add.setEnabled(false);
				ToolBar.this.tooltip
						.setText("Click & drag the mouse todefine a line. For a curved\nline after the straight line \nis defined your next click will define the curveness \nof the line");
				field.unhighlightAll();
			}
		});
		this.player.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ToolBar.this.selection.setEnabled(true);
				ToolBar.this.player.setEnabled(false);
				ToolBar.this.arrowhead.setEnabled(true);
				ToolBar.this.misc.setEnabled(true);
				ToolBar.this.add.setEnabled(true);
				ToolBar.this.blockhead.setEnabled(true);
				ToolBar.this.zone.setEnabled(true);
				ToolBar.this.tooltip.setText("Click to add a player");
				field.unhighlightAll();
			}
		});
		this.arrowhead.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ToolBar.this.selection.setEnabled(true);
				ToolBar.this.player.setEnabled(true);
				ToolBar.this.arrowhead.setEnabled(false);
				ToolBar.this.misc.setEnabled(true);
				ToolBar.this.blockhead.setEnabled(true);
				ToolBar.this.add.setEnabled(true);
				ToolBar.this.zone.setEnabled(true);
				ToolBar.this.tooltip
						.setText("Click & drag the mouse tomake a line with an arrowhead");
				field.unhighlightAll();
			}
		});
		this.blockhead.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ToolBar.this.selection.setEnabled(true);
				ToolBar.this.player.setEnabled(true);
				ToolBar.this.arrowhead.setEnabled(true);
				ToolBar.this.misc.setEnabled(true);
				ToolBar.this.blockhead.setEnabled(false);
				ToolBar.this.zone.setEnabled(true);
				ToolBar.this.add.setEnabled(true);
				ToolBar.this.tooltip
						.setText("Click & drag the mouse tomake a line that ends in a pancake");
				field.unhighlightAll();
			}
		});
		this.misc.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ToolBar.this.selection.setEnabled(true);
				ToolBar.this.player.setEnabled(true);
				ToolBar.this.arrowhead.setEnabled(true);
				ToolBar.this.misc.setEnabled(false);
				ToolBar.this.blockhead.setEnabled(true);
				ToolBar.this.zone.setEnabled(true);
				ToolBar.this.add.setEnabled(true);
				ToolBar.this.tooltip
						.setText("Click to create a text-box enter your text in the field above");
				field.unhighlightAll();
			}
		});
		this.zone.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ToolBar.this.selection.setEnabled(true);
				ToolBar.this.player.setEnabled(true);
				ToolBar.this.arrowhead.setEnabled(true);
				ToolBar.this.misc.setEnabled(true);
				ToolBar.this.add.setEnabled(true);
				ToolBar.this.blockhead.setEnabled(true);
				ToolBar.this.zone.setEnabled(false);
				ToolBar.this.tooltip.setText("Click & drag to define a \ndefensive zone");
				field.unhighlightAll();
			}
		});
	}

	public void setName()
	{
		this.name = BorderFactory.createTitledBorder(Main.fileName);
		setBorder(this.name);
	}
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 * 
 * Qualified Name: GUI.ToolBar
 * 
 * JD-Core Version: 0.7.0.1
 */