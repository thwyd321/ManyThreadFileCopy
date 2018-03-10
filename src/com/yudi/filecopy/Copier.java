package com.yudi.filecopy;

import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JProgressBar;


public class Copier {
	
	private UserInterface ui;
	public Copier(UserInterface ui){
		this.ui = ui;
		try {
			multiThreadCopy(ui.srcFileField.getText(),ui.destFileField.getText(),Integer.parseInt(ui.threadNumField.getText()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void multiThreadCopy(String srcFile, String destFile,int threadNum) throws IOException {
		//启动的线程数目
		int count = threadNum;
		JProgressBar[] progressBars= ui.progressBars;
		RandomAccessFile[] srcRafs = new RandomAccessFile[count];
		RandomAccessFile[] destRafs = new RandomAccessFile[count];
		
		int[] startPoints = new int[count];
		int[] endPoints = new int[count];

		for (int i = 0; i < count; i++) {
			srcRafs[i] = new RandomAccessFile(srcFile, "r");
			int srcSize = (int) srcRafs[i].length();
			int blockSize = srcSize / count;
			destRafs[i] = new RandomAccessFile(destFile,"rw");
			startPoints[i] = i * blockSize;
			if (i == count - 1) {
				endPoints[i] = srcSize-1;
			} else {
				endPoints[i] = (i + 1) * blockSize - 1;
			}
		}
		for (int i = 0; i < count; i++) {
		new CopyThread(srcRafs[i], destRafs[i], startPoints[i], endPoints[i],progressBars[i]).start();
		}
	}
	

}
