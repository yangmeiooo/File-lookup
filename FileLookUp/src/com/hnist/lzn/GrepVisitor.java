package com.hnist.lzn;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class GrepVisitor extends SimpleFileVisitor<Path> {
		//保存Path的集合成员变量
		private List<Path> result = null;
		//查找的内容变量
		private String str = null;
		//List文件大小集合
		private List<String> time = null;
		
		//初始化
		 public GrepVisitor(List<Path> path,String str,List<String> time) {
			this.str = str;
			
			this.result = path;
			
			this.time = time;
		}
	
		 //覆盖父类的visitFile方法，进行拓展
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		// TODO Auto-generated method stub
		if(file.toFile().exists()) {
			
			//得到文件的大小以单位字节表示
			long size = Files.size(file);
			
			System.out.println("size字节:"+size);
			
			if(size>4096) {
				
			return FileVisitResult.CONTINUE;
			
			}
			
			//调用compile方法
			Testgrep.compile(this.str);
			//调用grep方法来判断检索到没
			boolean br = Testgrep.grep(file.toFile());
			
			if(br==true) {
			//加入相关的集合
			time.add(String.valueOf(size));
				
			result.add(file);
		
			
			}
			
		}
		//没找到就继续遍历相关的文件
		return FileVisitResult.CONTINUE;
	}
	
	/*
	 * 访问文件出错，进行的处理
	 * @see java.nio.file.SimpleFileVisitor#visitFileFailed(java.lang.Object, java.io.IOException)
	 */
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		// TODO Auto-generated method stub
		//出错，但是不再访问这个路径下的任何文件了
		return FileVisitResult.SKIP_SUBTREE;
	}

	/*
	 * 访问目录出错后进行的处理
	 * @see java.nio.file.SimpleFileVisitor#postVisitDirectory(java.lang.Object, java.io.IOException)
	 */
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		// TODO Auto-generated method stub
		return FileVisitResult.CONTINUE;
	}

	
	
	
}
