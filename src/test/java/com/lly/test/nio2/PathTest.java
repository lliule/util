package com.lly.test.nio2;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Iterator;

/**
 * PathTest Create on 2019/4/8
 *
 * @author leliu
 */
public class PathTest {

	@Test
	public void test1() {
		Path path = Paths.get("C:\\Users\\Admin\\Desktop\\人才档案.xlsx");
		System.out.println("path: " + path);

		Path path1 = Paths.get("C:", "Users", "Admin", "Desktop", "人才档案.xlsx");
		System.out.println("path2: " + path1);

		Path path2 = Paths.get("C:", "Users", "Admin", "Desktop", "人才档案");
		System.out.println("path2: " + path2);

		// 相对路径
		Path path3 = Paths.get("/java/nio/text.txt");
		System.out.println(path3);

	}

	@Test
	public void test2() throws IOException {
		// 遍历改目录下的字目录
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("D:\\BaiduNetdiskDownload\\高级进阶书籍"));
		Iterator<Path> iterator = directoryStream.iterator();
		while (iterator.hasNext()) {
			Path next = iterator.next();
			System.out.println(next);
		}

		// 递归创建文件目录
		createDir();

		// 创建文件test.txt
		Path file = createFile();

		// 使用缓冲字符流读取文件内容
		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(file, Charset.forName("UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
	}

	private Path createFile() throws IOException {
		Path file = Files.createFile(Paths.get("D:\\temp\\test\\test\\text.txt"));
		Charset charset = Charset.forName("UTF-8");
		String text = "hello, 你好, nio2";
		BufferedWriter writer = Files.newBufferedWriter(file, charset, StandardOpenOption.APPEND);
		writer.write(text);
		writer.close();
		return file;
	}

	private void createDir() throws IOException {
		Path file = Files.createDirectories(Paths.get("D:\\temp\\test\\test"));
		System.out.println(file.getFileName());
	}

	/**
	 * 监听文件的变化
	 */
	@Test
	public void test3() throws IOException, InterruptedException {
		Path path = Paths.get("D:\\temp\\");
		watchDor(path);
	}

	private void watchDor(Path path) throws IOException, InterruptedException {
		// 创建实例
		WatchService watchService = FileSystems.getDefault().newWatchService();
		// 注册watchService 所监听的时间(目录创建，修改和删除)
		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

		while(true) {
			WatchKey key = watchService.take();
			for (WatchEvent<?> watchEvent : key.pollEvents()) {
				WatchEvent.Kind<?> kind = watchEvent.kind();
				// 忽略Overflow事件
				if(kind == StandardWatchEventKinds.OVERFLOW) {
					continue;
				}
				WatchEvent<Path> pathWatchEvent = (WatchEvent<Path>) watchEvent;
				Path fileName = pathWatchEvent.context();
				System.out.println(kind + " : " + fileName);

			}

			// 重置key
			boolean valid = key.reset();
			// 如果key 无效（比如监听的文件被删除),则退出
			if(!valid) {
				break;
			}

		}

	}

}
