package com.yudi.filecopy;

import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JProgressBar;

public class CopyThread extends Thread {
	
	RandomAccessFile srcRaf ;
	RandomAccessFile destRaf;
	long startPoint;
	long endPoint;
	JProgressBar progressBar;
	
	public CopyThread(RandomAccessFile srcRaf,RandomAccessFile destRaf,long startPoint,long endPoint, JProgressBar progressBar) {
		this.srcRaf = srcRaf;
		this.destRaf = destRaf;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.progressBar = progressBar;
	}
	
	
	@Override
	public void run() {
		try {
			srcRaf.seek(startPoint);
			destRaf.seek(startPoint);
			int len = -1;
			int length = (int) (endPoint-startPoint+1);
			progressBar.setMaximum(length);
			byte[] b = new byte[1024*1024];
			int times =0;
			if(length%b.length == 0){
				times = length/b.length;
			}else{
				times = length/b.length+1;
			}
			int total = 0;
			for (int i = 0; i < times; i++) {
				if(i==times-1){
					if(length%b.length==0){
						srcRaf.read(b);
						destRaf.write(b);
						total +=b.length;
					}else{
						byte[] temp = new byte[length%b.length];
						srcRaf.read(temp);
						destRaf.write(temp);
						total+=length%b.length;
					}
				}else{
					srcRaf.read(b);
					destRaf.write(b);
					total +=b.length;
					
				}
				progressBar.setValue(total);
				progressBar.repaint();
			}
			
			
			srcRaf.close();
			destRaf.close();
			System.out.println(this.getName()+"-->finised copying");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

}
