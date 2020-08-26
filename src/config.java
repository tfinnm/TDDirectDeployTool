
public class config {

	public String host;
	public String username;
	public String password;
	public String dir;
	public boolean sudo;
	public String sudopass;
	public String cmd1;
	public String tmpfile1;
	public String cmd2;
	public String tmpfile2;
	
	public config(String h, String u, String p, String d, boolean s, String sp, String c1, String f1, String c2, String f2) {
		host = h;
		username = u;
		password = p;
		dir = d;
		sudo = s;
		sudopass = sp;
		cmd1 = c1;
		tmpfile1 = f1;
		cmd2 = c2;
		tmpfile2 = f2;
	}
	
}
