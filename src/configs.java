import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class configs {

	public static config[] getConfig(String directory, String file) throws FileNotFoundException {
		ArrayList<config> confs = new ArrayList<config>();
		File[] files = new File(directory).listFiles((d, name) -> name.endsWith(".TDDDTConfig"));
		for (int i = 0; i < files.length; i++) {
			Scanner filesc = new Scanner(files[i]);
			if (file.matches(filesc.nextLine())) {
				String host = filesc.nextLine();
				String username = filesc.nextLine();
				String password = filesc.nextLine();
				String dir = filesc.nextLine();
				Boolean sudo = Boolean.getBoolean(filesc.nextLine());
				String supass = filesc.nextLine();
				String prcmd = filesc.nextLine();
				String prtmpfile = filesc.nextLine();
				String pocmd = filesc.nextLine();
				String potmpfile = filesc.nextLine();
				confs.add(new config(host,username,password,dir,sudo,supass,prcmd,prtmpfile,pocmd,potmpfile));
			}
			filesc.close();
		}
		return confs.toArray(new config[0]);
	}

}
