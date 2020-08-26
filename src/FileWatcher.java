import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 
 * Based on Omkar Marathe's file watcher implementation.
 *
 */
public class FileWatcher implements Runnable {

	private WatchService watcher;

	/**
	 * @param directory
	 * @Path directory to watch files into
	 * @param fileHandler
	 * @param watchRecursive
	 *            if directory is to be watched recursively
	 * @param watchedEvents
	 *            Set of file events watched
	 * 
	 * @throws IOException
	 */
	public FileWatcher(Path directory, boolean watchRecursive) throws IOException {
		super();
		this.watcher = FileSystems.getDefault().newWatchService();
		if (watchRecursive) {
			// register all subfolders
			Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
					return FileVisitResult.CONTINUE;
				}
			});
		} else {
			directory.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
		}
	}

	@SuppressWarnings("unchecked")
	public void run() {
		WatchKey key = null;
		while (true) {
			try {
				key = watcher.take();
				if (key != null) {
					for (WatchEvent<?> event : key.pollEvents()) {
						WatchEvent<Path> ev = (WatchEvent<Path>) event;
						//directory in which file event is detected
						Path directory = (Path) key.watchable(); 
						Path fileName = ev.context();
						//handles file here!
						System.out.print("detected change: "+directory.toString()+"/"+fileName.toString());
						if ((!core.configMode.getState()) && (!fileName.toString().endsWith("TDDDTConfig"))) {
							for (config c: configs.getConfig(directory.toString(), fileName.toString())) {
								deployManager.deploy(c, directory, fileName);
							}
						}

					}
					key.reset();
				}
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
}