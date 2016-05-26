package com.example.imageload.load;

import android.graphics.Bitmap;


/**
 * ˫���档��ȡͼƬʱ�ȴ��ڴ滺���л�ȡ������ڴ���û�л����ͼƬ���ٴ�SD���л�ȡ.
 * ����ͼƬҲ�����ڴ��SD���ж�����һ��
 * @author Administrator
 *
 */
public class DoubleCache {
	ImageCache mMemoryCache = new ImageCache();
	DiskCache mDiskCache = new DiskCache();
	
	//�ȴ��ڴ滺���л�ȡͼƬ�����û���ٴ�SD���л���
	public Bitmap get(String url){
		Bitmap bitmap = mMemoryCache.get(url);
		if (bitmap == null){
			bitmap = mDiskCache.get(url);
		}
		return bitmap;
	}
	
	//��ͼƬ���浽�ڴ��SD����
	public void put(String url, Bitmap bmp){
		mMemoryCache.put(url, bmp);
		mDiskCache.put(url, bmp);
	}
}
