package com.example.imageload.load;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;


/**
 * 存放到SD卡中
 * @author Administrator
 *
 */
public class DiskCache {
	static String cacheDir = "sdcard/cache/";
	
	//从缓存中获取图片
	public Bitmap get(String url){
		return BitmapFactory.decodeFile(cacheDir + url);
	}
	
	//将图片缓存到内存
	public void put(String url, Bitmap bmp){
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(cacheDir + url);
			//压缩图片 100 表示压缩率
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
