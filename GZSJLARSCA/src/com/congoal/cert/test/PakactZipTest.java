package com.congoal.cert.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PakactZipTest {

	public static void main(String[] args) throws Exception {
		
//		String dir = "F:\\pojo_wse14\\CCA\\WebRoot\\certs\\new\\sub";
//		
//		String[] filenames = new String[]{
//				dir+"\\CN20150120112005.cer", 
//				dir+"\\CN20150120115451.cer", 
//				dir+"\\CN20150121094528.cer"};
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		String zipFilename = dir+"\\"+sdf.format(new Date())+".zip";
//		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(zipFilename)));
//		for (int i = 0; i < filenames.length; i++) {
//			String filename = filenames[i];
//			FileInputStream fis = new FileInputStream(filename);
//			String entryName = filename.substring(filename.lastIndexOf("\\")+1, filename.length());
//			System.out.println("entryname: "+entryName);
//			ZipEntry zipEntry = new ZipEntry(entryName);
//			zos.putNextEntry(zipEntry);
//			byte[] buf = new byte[1024];
//			int bufz = 1024;
//			int len = 0;
//			while ((len=fis.read(buf, 0, bufz))>0) {
//				zos.write(buf, 0, len);
//			}
//			zos.closeEntry();
//			fis.close();
//		}
//		
//		zos.close();
//		System.out.println("压缩完成！");
//		System.out.println("1");
//		System.out.println(System.nanoTime()+"");
//		System.out.println("2");
//		System.out.println(System.nanoTime()+"");
//		System.out.println("3");
//		System.out.println(System.nanoTime()+"");
//		System.out.println("4");
//		System.out.println(System.nanoTime()+"");
		System.out.println(System.nanoTime());
	}
}
