package com.hnist.lzn;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class FindJavaVisitor extends SimpleFileVisitor<Path> {
	//保存Path的集合成员变量
	private List<Path> result = null;
	//保存查找的文件名
	String string = null;
	//保存文件大小的集合
	private List<String> time = null;
	
	
	
	public FindJavaVisitor(List<Path> path,String attrs,List<String> time) {
	
		this.result = path;
		
		String[] ad = attrs.split("\\.");
		
		this.string = ad[0];
		
		this.time = time;
	}
	
	/*
	 * 访问文件出错，进行的处理
	 * @see java.nio.file.SimpleFileVisitor#visitFileFailed(java.lang.Object, java.io.IOException)
	 */
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		// TODO Auto-generated method stub
		return FileVisitResult.SKIP_SUBTREE;
	}
	 //覆盖父类的visitFile方法，进行拓展
	@Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
		
		System.out.println(file.getFileName());
		System.out.println(file.toFile().getName()+"--->");
		if(file.toFile().exists()) {
			
			String a = file.toFile().getName();
			
			String[] dStrings = a.split("\\.");
			
			String ms = dStrings[0];
		
        if(ms.equals(this.string)){
        	long size = Files.size(file);
        	System.out.println("满足条件------------------>");
        	time.add(String.valueOf(size));
        	//可能是这里
            result.add(file);
        }
		}
        return FileVisitResult.CONTINUE;
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
