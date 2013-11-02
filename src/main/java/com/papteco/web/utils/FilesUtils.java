package com.papteco.web.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;

@Component
public class FilesUtils extends BaseUtils{
	
	public static void copyFile(String fromFile, String toFile) throws IOException {
		InputStream fis = new BufferedInputStream(new FileInputStream(fromFile));
		byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();

        BufferedOutputStream buff = null;
		buff = new BufferedOutputStream(new FileOutputStream(new File(toFile)));
		buff.write(buffer);
		buff.flush();
		buff.close();
	}
	
	public void downloadFile(String fromFile, String toFile) throws IOException {
		InputStream fis = new BufferedInputStream(new FileInputStream(fromFile));
		byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();

        BufferedOutputStream buff = null;
		buff = new BufferedOutputStream(new FileOutputStream(new File(toFile)));
		buff.write(buffer);
		buff.flush();
		buff.close();
		
	}
	
	public InputStream getFileInputStream(String fromFile) throws IOException {
		return new BufferedInputStream(new FileInputStream(fromFile));
	}
	
	public void localOpenFile(String file) throws IOException{
		Runtime.getRuntime().exec("cmd /C Start " + file); 
	}
	
	public static void main(String[] args){
		FilesUtils fu = new FilesUtils();
		try {
//			fu.downloadFile("C:/cony/tmp/1000-1309-1/Memo/E1000-130913-1-first memo - Rev 0.1.ppt", "C:/cony/tmpfile/abc.ppt");
//			fu.localOpenFile("C:/cony/tmpfile/abc.ppt");
			Runtime.getRuntime().exec("cmd /C Start C:/cony/tmpfile/abc.ppt"); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
