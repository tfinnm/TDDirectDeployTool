import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class deployManager {

	public static void deploy(config c, Path directory, Path fileName) {
		Session session = null;
		Channel channel = null;
		try {
			JSch ssh = new JSch();
			session = connectauthenticate(ssh, session, c);
			upload(session, channel, c.tmpfile1, c.dir+"/TDDDTtemp/");
			execute(session, channel, c.cmd1, c.sudo, c.sudopass);
			remove(session, channel, c.dir+"/TDDDTtemp/"+new File(c.tmpfile1).toPath().getFileName().toString());
			upload(session, channel, directory.toString()+"/"+fileName.toString(), c.dir);
			upload(session, channel, c.tmpfile2, c.dir+"/TDDDTtemp/");
			execute(session, channel, c.cmd2, c.sudo, c.sudopass);
			remove(session, channel, c.dir+"/TDDDTtemp/"+new File(c.tmpfile2).toPath().getFileName().toString());
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}
	}

	private static Session connectauthenticate(JSch ssh, Session session, config c) throws JSchException {
		session = ssh.getSession(c.username, c.host, 22);
		session.setPassword(c.password);
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();
		return session;
	}

	private static void upload(Session session, Channel channel, String file, String destination) throws JSchException, SftpException {
		channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftp = (ChannelSftp) channel;
		try {
			sftp.stat(destination);
		}catch (Exception e) {
			sftp.mkdir(destination);
		}
		sftp.put(file, destination);
		channel.disconnect();
	}

	private static void remove(Session session, Channel channel, String file) throws JSchException, SftpException {
		channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftp = (ChannelSftp) channel;
		sftp.rm(file);
		channel.disconnect();
	}

	private static void execute(Session session, Channel channel, String command, boolean sudo, String sudopass) throws JSchException, IOException {
		channel=session.openChannel("exec");
		if (sudo) {
			((ChannelExec)channel).setCommand("sudo -S -p '' "+command);
			OutputStream out=channel.getOutputStream();
			channel.connect();
			out.write((sudopass+"\n").getBytes());
			out.flush();

		} else {
			((ChannelExec)channel).setCommand(command);
			channel.connect();
		}
		channel.disconnect();
	}

}
