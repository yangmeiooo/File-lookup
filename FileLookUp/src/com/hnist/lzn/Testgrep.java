package com.hnist.lzn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.MalformedInputException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Testgrep {
	
	//设置Charset字符集对象为utf-8
	private static Charset charset = Charset.forName("utf-8");
	//得到utf-8的解码器
	private static CharsetDecoder decoder = charset.newDecoder();
	//设置模式Pattern的变量
	private static Pattern pattern;
 
	
	/*
	 * 读取文件
	 * @f 一个File对象
	 */
	public static boolean grep(File f) throws IOException {
		
		//从文件输入流读入文件
		FileInputStream fis = new FileInputStream(f);
		//从输入流得到一个文件管道
		FileChannel fc = fis.getChannel();
		//得到文件大小
		int size = (int) fc.size();
		//从文件管道读入一个MAPPed的字节流缓冲区
		MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size);
		
		System.out.println(bb);
		
		try {
		//1.把缓冲区的数据用解码器解码到Char缓冲区
		CharBuffer cb = decoder.decode(bb);
		//关闭流
		fis.close();
		
		fc.close();
		//传递f文件对象和Char缓冲区
		return grep(f, cb);
		
		}catch (MalformedInputException e) {
			//在1.如果用解码器解码到CharBuff出错就会捕获这个错误
			
			return false;
		}finally {
			
			//清空MappedByteBuff这缓冲区
			bb.clear();
			
		}
		
		
		
		
	}
	/*
	 * 搜索文件中是否包含关键字
	 * @f 文件对象
	 * @cb CharBuffer对象
	 */
	public static boolean grep(File f, CharBuffer cb) {
		
		Matcher pm = null;
		//匹配
		pm = pattern.matcher(cb);
			//按照不完全匹配
			if(pm.find()) {
			
			System.out.println("zhaodao");
			//清空Buffer
			cb.clear();
			//变量置空，提示GC收集
			pm = null;
			
			return true;
		}
		
			System.out.println("meizhaodao");
			
			cb.clear();
			
			pm = null;
		
		return false;
	}
	/*
	 * 将关键字编译成模式Pattern
	 * @pat 需要编译的字符串
	 */
	public static void compile(String pat) {
		try {
			
			pattern = Pattern.compile(pat);
			
		} catch (PatternSyntaxException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}


}
