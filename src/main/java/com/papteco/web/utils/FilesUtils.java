package com.papteco.web.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class FilesUtils extends BaseUtils{
	
	public static String genFileId(){
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}
	
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
	
	public static List<String> scanFilesFromFolder(String folder){
		File fld = new File(folder);
		List<String> result = new ArrayList<String>();
		if (fld.isDirectory()) {
			List<File> files = Arrays.asList(fld.listFiles());
			Collections.sort(files, new Comparator<File>() {
				public int compare(File f1, File f2) {
					long diff = f1.lastModified() - f2.lastModified();
					if (diff < 0)
						return 1;
					else if (diff == 0)
						return 0;
					else
						return -1;
				}

				public boolean equals(Object obj) {
					return true;
				}
			});

			for (File file : files) {
				result.add(file.getName());
			}
		}
		return result;
	}
	
	public InputStream getFileInputStream(String fromFile) throws IOException {
		return new BufferedInputStream(new FileInputStream(fromFile));
	}
	
	public void localOpenFile(String file) throws IOException{
		Runtime.getRuntime().exec("cmd /C Start " + file); 
	}
	
	public static void main(String[] args){
//		FilesUtils fu = new FilesUtils();
//		try {
////			fu.downloadFile("C:/cony/tmp/1000-1309-1/Memo/E1000-130913-1-first memo - Rev 0.1.ppt", "C:/cony/tmpfile/abc.ppt");
////			fu.localOpenFile("C:/cony/tmpfile/abc.ppt");
//			Runtime.getRuntime().exec("cmd /C Start C:/cony/tmpfile/abc.ppt"); 
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		List<String> list = FilesUtils.scanFilesFromFolder("C:\\Papteco\\server\\MAILS_BACKUP\\sysadmin");
		for(String str : list){
			System.out.println(str);
		}
		
	}
	
}
