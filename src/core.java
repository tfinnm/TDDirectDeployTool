import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class core {
	
	public static CheckboxMenuItem configMode = new CheckboxMenuItem("Config Mode");
	public static BufferedImage img = new BufferedImage(15, 15, BufferedImage.TYPE_INT_RGB);
	
	public static void main(String[] args) {
		
		try {
			new Thread(new FileWatcher(new File("push").toPath(), true)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}




	private static void createAndShowGUI() {
		//Check the SystemTray support
		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray is not supported");
			return;
		}
		final PopupMenu popup = new PopupMenu();
		final TrayIcon trayIcon = new TrayIcon(img);
		trayIcon.setToolTip("Tfinnm Development Direct Deploy Tool");
		final SystemTray tray = SystemTray.getSystemTray();

		// Create a popup menu components
		MenuItem aboutItem = new MenuItem("About");
		MenuItem buildItem = new MenuItem("Build Config");
		MenuItem exitItem = new MenuItem("Exit");

		buildItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				configbuilder.build();
			}
		});
		
		//Add components to popup menu
		popup.add(aboutItem);
		popup.addSeparator();
		popup.add(configMode);
		popup.add(buildItem);
		popup.addSeparator();
		popup.add(exitItem);

		trayIcon.setPopupMenu(popup);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
			return;
		}      
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tray.remove(trayIcon);
				System.exit(0);
			}
		});
		aboutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Copyright 2020 Toby Mcdonald <toby.mcdonald@tfinnm.tk>", "About Tfinnm Development Direct Deploy Tool", JOptionPane.QUESTION_MESSAGE);
			}
			
		});
	}

}
