package com.example.imageload.load;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;


/**
 * ��ŵ�SD����
 * @author Administrator
 *
 */
public class DiskCache {
	static String cacheDir = "sdcard/cache/";
	
	//�ӻ����л�ȡͼƬ
	public Bitmap get(String url){
		return BitmapFactory.decodeFile(cacheDir + url);
	}
	
	//��ͼƬ���浽�ڴ�
	public void put(String url, Bitmap bmp){
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(cacheDir + url);
			//ѹ��ͼƬ 100 ��ʾѹ����
			bmp.compress(CompressFormat.PNG, 100, fileOutputStream);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if (fileOutputStream != null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
