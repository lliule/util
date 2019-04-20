package com.lly.test.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *缓冲区中的4个属性：
 * capacity: 表示缓冲区中最大存储的数据的容量，一旦声明，不能改变
 * limit: 界限，表示缓冲区中可以操作数据的大小（limit 后的数据不能读写)
 * position: 位置， 表示缓冲区中正在操作数据的位置
 * mark : 标记，表示记录当前Position的位置，可以通过reset() 恢复到Mark的位置
 *
 * 0<= mark <= position <= limit <= capacity
 *
 *直接缓冲区与非直接缓冲区
 * 非直接缓冲区： 通过 allocate() 方法分配缓冲区，将缓冲区建立在jvm的内存中
 * 直接缓冲区： allocateDirect()分配直接缓冲区，建立在物理内存中，提高效率
 *
 * @author leliu
 */
public class BufferTest {

	@Test
	public void test1() {
		// 分配一个指定的大小的缓冲区
		// 此时 capacity = 1024, limit= 1024, position = 0
		ByteBuffer buf = ByteBuffer.allocate(1024);

		// 存储数据到缓冲区
		// 此时 capacity 和limit 不变， position 变大
		buf.put("abcd".getBytes());


		// 切换到读数据模式
		// position = 0， limit = 4
		buf.flip();

		// 读取数据
		byte[] dst = new byte[buf.limit()];
		// 此时position = 4,其他不变
		buf.get(dst);
		System.out.println(new String(dst));

		// 切换到重复刚才的数据位置
		buf.rewind();

		// 清空缓冲区
		// 回到最初状态,但是缓冲区的数据依旧存在，处于被“被遗忘状态”
		buf.clear();

		// 得到a
		System.out.println((char)buf.get(1));

		// 判断缓冲区中是否还有剩余的数据
		if(buf.hasRemaining()) {
			System.out.println(buf.remaining());
		}

	}

	/**
	 * 通道数据传输
	 * @throws IOException
	 */
	@Test
	public void test2() throws IOException {
		FileChannel inChannel = FileChannel.open(Paths.get("d:/1.jpg"), StandardOpenOption.READ);

		FileChannel outChannel = FileChannel.open(Paths.get("d:/2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);

		inChannel.transferTo(0, inChannel.size(), outChannel);
		inChannel.close();
		outChannel.close();
	}
}
