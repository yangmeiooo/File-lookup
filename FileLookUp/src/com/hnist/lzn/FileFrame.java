package com.hnist.lzn;



import java.awt.Component;
import java.awt.Event;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;

import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;



import com.hnist.lzn.FindJavaVisitor;
import com.hnist.lzn.GrepVisitor;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class FileFrame extends JFrame {
	
	//小窗口
	private JFrame aFrame;
	
	//监视器，监视某个变量，改变就取消面板
	private Timer timer;
	
	//监听器监听的变量
	private boolean a;
	
	//按钮
	private JButton search;
	
	//更换目录按钮
	private JButton director;
	
	//标签1
	private JLabel filename;
	
	//标签2,可能用的到
	//private JLabel text;
	
	//文件名输入框
	private JTextField filenametextfield;
	
	//文件路径输入框
	private JTextField directortextfield;
	
	//一个组合框选择不同的模式
	private JComboBox<String> jComboBox;
	
	//显示的表格数据
	private JTable table;
	//默认的表格数据对象行，必须用它使用Vector的Vectors来存储单元格值对象。 
	private DefaultTableModel dt;
	
	//表格上显示的信息
	private Vector<String> title;
	
	//来一个面板绘图
	private JPanel jPanel;
	
	//来一个保存的
	private List<Path> list = new ArrayList<>();
	
	//保存文件的大小集合，java at sun.nio.fs.WindowsException.translateToIOException
	private List<String> listtime = new ArrayList<>();
	
	
	
	
	public FileFrame() {
		
		//定时器每隔500监听这个变量，true就让这个窗口为空
		timer = new Timer(500, event2 ->
			{
				if(this.a) {
					
					//
					System.out.println("lalalalal");
					aFrame.dispose();
					System.out.println("0000");
				}
					
					
				});
		//启动
		timer.start();
		
		//先给表格第一行补充默认行信息
		title = new Vector<String>();
		title.add("名称");
		title.add("路径");
		title.add("修改时间");
		title.add("文件大小");
		
		//初始化一些组件
		search = new JButton("查找");
		director = new JButton("更换目录");
		
		//设置更改目录的按钮事件监听
		director.addActionListener(event1 ->{
		/*
		 * 创建文件选择器选择查找路径
		 */
		
				try {
					//new一个文件选择器
					JFileChooser chooser = new JFileChooser();
					//设置文件选择器，以读取只读取目录模式
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					//点击同意，会返回一个Int值
					int n = chooser.showOpenDialog(this);
					//判断
					if(n==JFileChooser.APPROVE_OPTION) {
						
						//获取选择的文件目录路径
						String directoryname = chooser.getSelectedFile().getAbsolutePath();
						//设置显示在窗口
						directortextfield.setText(directoryname);
						
					}
					
				}catch(Exception e) {
					
					e.printStackTrace();
					
				}
			
				
				
		});
		
		//new了标签和输入框
		filename = new JLabel("文件名或内容");
		filenametextfield = new JTextField();
		directortextfield = new JTextField();
		//新建组合框
		jComboBox = new JComboBox<>();
		//新建了一个表格数据模型
		dt = new DefaultTableModel(title, 0);
		//模型添加进JTable
		table = new JTable(dt);
		table.setAutoCreateRowSorter(true);
		//新建的面板
		jPanel = new JPanel();
		JScrollPane jScrollPane = new JScrollPane(table);
		jScrollPane.setBounds(20, 75, 880, 400);
		
		
		
		
		
		
		//新建一个面板，往里加东西
		//设置面板布局为null
		jPanel.setLayout(null);
		//绝对定位设置组件在界面的位置
		filename.setBounds(20, 20, 75, 20);
		filenametextfield.setBounds(100, 20, 250, 20);
		director.setBounds(400, 20, 75, 20);
		directortextfield.setBounds(480, 20, 250, 20);
		search.setBounds(800, 20, 100, 20);
		jComboBox.addItem("按文件名查找");
		jComboBox.addItem("按内容查找");
		jComboBox.setBounds(800, 45, 100, 20);
		
	
		
		/*
		 * 创建查找文件按钮的事件监听
		 */
		search.addActionListener(event ->{
			
		//按下按钮的时候，置false
		this.a = false;
			
		search.setEnabled(false);	
		
		 aFrame = new JFrame();
		
		JFXPanel jxJfxPanel = new JFXPanel();
		
		jxJfxPanel.setLayout(null);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				intiFX(jxJfxPanel);
				
				
			}

			private void intiFX(JFXPanel jxJfxPanel) {
				// TODO Auto-generated method stub
				Scene scene = creatScene();
				
				jxJfxPanel.setScene(scene);
			}
			
			
			private Scene creatScene() {
				
				Group group =  new javafx.scene.Group();
	
				Scene scene = new Scene(group, Color.ALICEBLUE);
				
				ProgressIndicator progressIndicator = new ProgressIndicator(-1.0f);
				
				progressIndicator.setLayoutX(120);
				
				progressIndicator.setLayoutY(20);
				
				Text text = new Text("正在查询，请稍后！");
				
				text.setLayoutX(100);
				
				text.setLayoutY(90);
			
				group.getChildren().add(progressIndicator);
				
				group.getChildren().add(text);
				
				return (scene); 
			}
			
		});
		
		aFrame.add(jxJfxPanel);
		
		aFrame.setSize(300, 200);
		
		//setLocationByPlatform(true);
		aFrame.setLocation(300, 160);
		
		
		aFrame.setVisible(true);
		
		//aFrame.setDefaultCloseOperation(aFrame.EXIT_ON_CLOSE);
		
		/*
		 * 这里线程处理耗时任务
		 */
		
		Runnable r = () -> {
			
			try {
				
				
				String SelectedName = jComboBox.getSelectedItem().toString();
					
				System.out.println(SelectedName);
				
				//每点击一次按钮清空ArrayList里的东西
				list.clear();
				listtime.clear();
				//清空一下行内的东西,可能需要或者不需要，先写在这
				
				
				//判断是怎样查询
				if(SelectedName=="按文件名查找") {
					
				//先得到查找的文件名
				String name = filenametextfield.getText();
				
				//再得到查找路径
				String pathname = directortextfield.getText();
				System.out.println("这是目录名"+pathname);
				if(pathname==null) {
					
					//Linux下,从根目录查找
					Path path = Paths.get("/");
					
					ClickName(path,this.list,name,this.listtime);
					
				}else {
				/*
				 *  这是按文件查找的部分	
				 */
				//可能出错
				Path path = Paths.get(pathname);
				
				System.out.println("文件路径："+pathname);
				
				//调用查找函数进行查找
				ClickName(path,this.list,name,this.listtime);
				}
				
				System.out.println("查找到的文件数目:"+this.list.size());
					
				}else {
				//先获得查找的内容
				String content = filenametextfield.getText();
				
				String pathname = directortextfield.getText();
				
				System.out.println("这是内容的路径------->"+pathname);
				
			
				//这里应该是判断一下为空就，给一个默认值
				if(pathname==null) {
					
					Path path = Paths.get("/");
					
					ClickGrep(path,this.list,content,this.listtime);
					
				}else {
					Path path = Paths.get(pathname);
					
					
					
					ClickGrep(path,this.list,content,this.listtime);
					
				}
					
					
					System.out.println("查找到的文件数目:"+this.list.size());
					
					
				}
				
				
			}catch(Exception e) {
				
				
			}
			
			
		};
		
		//启动多线程
		Thread a = new Thread(r);
		
		a.start();
		
		
			
			
		});
		
		
		
		
		
		
		//把组件添加到面板中
		jPanel.add(search);
		jPanel.add(director);
		jPanel.add(filename);
		jPanel.add(filenametextfield);
		jPanel.add(directortextfield);
		jPanel.add(jComboBox);
		jPanel.add(jScrollPane);
		
		//设置界面的大小
		setSize(930, 550);
		
		//设置随机在界面的位置产生
		setLocationByPlatform(true);
		
		//添加面板到JFrame
		add(jPanel);
	}
	/*
	 * 按文件名查找的函数
	 * @path 文件路径Path
	 * @list 文件Path集合
	 * @name 需要查找的文件名
	 * @list2 存储文件大小的集合
	 */
	
	private void ClickName(Path path,List<Path> list,String name,List<String> list2) {
		
		try {
			Files.walkFileTree(path, new FindJavaVisitor(list,name,listtime));
			
			xunhuan(this.list,this.listtime);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	 *    按关键字来检索
	 * @path 文件路径Path
	 * @list 文件Path集合
	 * @content 检索的一些关键字
	 * @list2 存储文件大小的集合
	 */
	
	private void ClickGrep(Path path,List<Path> list,String content,List<String> list2) {
		
		try {
			//调用文件查找函数
			Files.walkFileTree(path, new GrepVisitor(list, content,listtime));
			//遍历函数
			xunhuan(this.list,this.listtime);
			
		}catch(IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	/*
	 * 遍历的方法
	 * @list 文件Path集合
	 * @listtime 
	 */
	private void xunhuan(List<Path> list,List<String> listtime) throws IOException {
		
		
		
		for(int i=0;i<list.size();i++) {
			
			//文件大小
			String filesize = listtime.get(i);
			//从path得到file
			File file = list.get(i).toFile();
			//得到路径
			String filepath = file.getAbsolutePath();
			//得到文件名
			String name = file.getName();
			//得到文件大小,出现了一个NoSuchFile错误，所有才用另外的一个List存储文件的大小
			//long size = Files.size(rePath);
			//转String
			//String filesize = String.valueOf(size);
			
			//得到文件最后的修改时间
			long time = file.lastModified();
			//转类型
			String modifytime = String.valueOf(time);
			//放入String类型的一维数组
			String[] st = {name,filepath,modifytime,filesize};
			//填充数据
			dt.addRow(st);
			
			//设置让Timer定时器为true
			this.a = true;
			//按钮设置为true
			this.search.setEnabled(true);
			
		
			
		}
		
	}
	
	class SimSwingWork extends SwingWorker<Void,Void>{

		@Override
		protected Void doInBackground() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		
		
	}
	
	
		
		
		
	
	
	
	
	
	
}
