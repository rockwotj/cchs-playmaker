package playmaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import playmaker.fieldpieces.DrawingField;

public class HeaderMenu extends JMenuBar
{
	public HeaderMenu(final DrawingField field)
	{
		JMenu file = new JMenu("File");
		file.setMnemonic(70);
		add(file);

		JMenuItem open = new JMenuItem("Open file");
		open.setMnemonic(79);
		open.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				field.load();
			}
		});
		file.add(open);
		JMenuItem save = new JMenuItem("Save");
		save.setMnemonic(83);
		save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				field.save();
			}
		});
		file.add(save);
		JMenuItem saveAs = new JMenuItem("Save as");
		saveAs.setMnemonic(65);
		saveAs.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				field.saveAs();
			}
		});
		file.add(saveAs);
		JMenu export = new JMenu("Export as...");
		export.setMnemonic(69);
		file.add(export);

		JMenuItem jpeg = new JMenuItem("JPEG");
		jpeg.setMnemonic(74);
		export.add(jpeg);
		jpeg.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				field.exportAsJPEG();
			}
		});
		JMenuItem png = new JMenuItem("PNG");
		export.add(png);
		png.setMnemonic(80);
		png.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				field.exportAsPNG();
			}
		});
		JMenuItem gif = new JMenuItem("GIF");
		export.add(gif);
		gif.setMnemonic(71);
		gif.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				field.exportAsGIF();
			}
		});
		file.addSeparator();
		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(88);
		file.add(exit);
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				int confirm = JOptionPane.showOptionDialog(null,
						"Are You Sure to Close the Playmaker?", "Exit Confirmation", 0, 3, null,
						null, null);
				if (confirm == 0)
				{
					System.exit(0);
				}
			}
		});
		JMenu options = new JMenu("Options");
		options.setMnemonic(79);
		add(options);
		JMenuItem fileName = new JMenuItem("Name Play");
		fileName.setMnemonic(78);
		options.add(fileName);
		fileName.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String file = JOptionPane.showInputDialog("Please input the play's name:",
						Main.fileName);
				if (file != null)
				{
					Main.fileName = file;
					Main.setFrameTitle();
				}
			}
		});
		JMenuItem perferences = new JMenuItem("Prefrences");
		perferences.setMnemonic(80);
		options.add(perferences);
		perferences.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				PropertiesFrame.setDefaultsFrame();
				field.repaint();
			}
		});
	}
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 * 
 * Qualified Name: GUI.HeaderMenu
 * 
 * JD-Core Version: 0.7.0.1
 */