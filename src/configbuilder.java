import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class configbuilder {

	public static void build() {
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showSaveDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				String file = chooser.getSelectedFile().getAbsolutePath();
				StringBuffer inputBuffer = new StringBuffer();

				inputBuffer.append(JOptionPane.showInputDialog("Enter regex"));
				inputBuffer.append('\n');
				String[] hostuser = JOptionPane.showInputDialog("Enter username@hostname", System.getProperty("user.name")+"@localhost").split("@"); 
				inputBuffer.append(hostuser[0]);
				inputBuffer.append('\n');
				inputBuffer.append(hostuser[1]);
				inputBuffer.append('\n');
				inputBuffer.append(JOptionPane.showInputDialog("Enter password"));
				inputBuffer.append('\n');
				inputBuffer.append(JOptionPane.showInputDialog("Enter remote directory"));
				inputBuffer.append('\n');
				boolean su = JOptionPane.showConfirmDialog(null, "Use sudo?","Tfinnm Development Direct Deploy Tool", JOptionPane.YES_NO_OPTION)==1;
				inputBuffer.append(su);
				inputBuffer.append('\n');
				if (su) inputBuffer.append(JOptionPane.showInputDialog("Enter super user password"));
				inputBuffer.append('\n');
				inputBuffer.append(JOptionPane.showInputDialog("Enter predeploy command"));
				inputBuffer.append('\n');
				chooser.showOpenDialog(null);
				inputBuffer.append(chooser.getSelectedFile().getAbsolutePath());
				inputBuffer.append('\n');
				inputBuffer.append(JOptionPane.showInputDialog("Enter predeploy command"));
				inputBuffer.append('\n');
				chooser.showOpenDialog(null);
				inputBuffer.append(chooser.getSelectedFile().getAbsolutePath());

				// write the new string with the replaced line OVER the same file
				FileOutputStream fileOut = new FileOutputStream(file);
				fileOut.write(inputBuffer.toString().getBytes());
				fileOut.close();
			} catch (IOException e) {

			}  
	    }
	}
	
}
