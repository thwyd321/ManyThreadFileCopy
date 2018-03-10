package com.yudi.filecopy;

import java.awt.Container;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class UserInterface extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 6856418808332445320L;
	//源文件
	private JLabel srcFile;
	//目标文件
	private JLabel destFile;
	public JTextField srcFileField;
	public JTextField destFileField;
	private JButton chooseSrc;
	private JButton chooseDest;
	private JLabel threadNumLabel;
	public JTextField threadNumField;
	private JButton startButton;
	public JProgressBar[] progressBars;
	private JButton restartButton;
	private Container barContainer;
	
	public void start() {
		init();
	}
	private void init() {
		this.setTitle("多线程文件复制");
		srcFile = new JLabel("源文件");
		destFile = new JLabel("目标文件");
		threadNumLabel = new JLabel("线程数量");
		startButton = new JButton("开始复制");
		srcFileField = new JTextField();
		destFileField = new JTextField();
		threadNumField = new JTextField();
		chooseSrc = new JButton("open");
		chooseDest = new JButton("open");
		restartButton = new JButton("restart");
		barContainer = new Container();
		
		this.setSize(420,600);
		this.setLocation(200, 100);
		this.setLayout(null);
		this.setVisible(true);
		
		
		srcFile.setBounds(20, 20, 80, 20);
		destFile.setBounds(20,60,80,20);
		threadNumLabel.setBounds(20, 100, 80, 20);
		startButton.setBounds(20, 140, 160, 40);
		restartButton.setBounds(200, 140,160, 40);
			
		srcFileField.setBounds(80,20 , 240, 20);
		destFileField.setBounds(80,60 , 240, 20);
		threadNumField.setBounds(80, 100,80, 20);
		
		chooseSrc.setBounds(330, 20, 70, 20);
		chooseDest.setBounds(330, 60, 70, 20);		
		
		barContainer.setBounds(0, 180, 420, 300);
		
		
		this.add(srcFile);
		this.add(destFile);
		this.add(srcFileField);
		this.add(destFileField);
		this.add(chooseSrc);
		this.add(chooseDest);
		this.add(threadNumLabel);
		this.add(threadNumField);
		this.add(startButton);
		this.add(restartButton);
		this.add(barContainer);
		
		
		chooseSrc.addActionListener(this);
		chooseDest.addActionListener(this);
		startButton.addActionListener(this);
		restartButton.addActionListener(this);
		
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(-1);
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==chooseSrc){
			FileDialog d = new FileDialog(this, "选择源文件");
			d.setVisible(true);
			if(d.getFile()!=null&&d.getDirectory()!=null){
				srcFileField.setText(d.getDirectory()+d.getFile());
				
			}
		}
		if(e.getSource()==chooseDest){
			FileDialog d = new FileDialog(this, "选择目标文件");
			d.setVisible(true);
			if(d.getFile()!=null&&d.getDirectory()!=null){
				destFileField.setText(d.getDirectory()+d.getFile());
				
			}
			
		}
		
		if(e.getSource()==startButton){
			int threadNum = Integer.parseInt(threadNumField.getText());
			int unitHeight = 300/(2*threadNum);//每个间隙和进度条的高度
			progressBars = new JProgressBar[threadNum];
			for(int i=0;i<threadNum;i++){
				JLabel threadLabel = new JLabel("线程"+(i+1));
				JProgressBar progressBar = new JProgressBar();
				progressBars[i] = progressBar;
				threadLabel.setBounds(20, (2*(i+1)-1)*unitHeight, 80, unitHeight);
				progressBar.setBounds(80, (2*(i+1)-1)*unitHeight, 240, unitHeight);
				barContainer.add(threadLabel);
				barContainer.add(progressBar);
			}
			this.repaint();
			Copier copier = new Copier(this);
	
		}
		
		if(e.getSource()==restartButton){
			int threadNum = 0;
			srcFileField.setText("");
			destFileField.setText("");
			threadNumField.setText("");
			barContainer.removeAll();
			this.repaint();
		}
		
	}
}
