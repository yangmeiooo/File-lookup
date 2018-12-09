package com.hnist.lzn;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/*
 * @author 李振宁
 * @datetime 2018/12/9
 * 
 */
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//启动界面的线程
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				FileFrame frame = new FileFrame();
				//设置标题
				frame.setTitle("文件查找小工具");
				//设置退出	
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//设置窗口显示
				frame.setVisible(true);	
				
				
			}
		});
				
				
	}
					
			
		
	

}
