package com.example.imageload.load;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageCache {
	LruCache<String, Bitmap> mImageCache;
	
	public ImageCache() {
		initImageLoader();
	}
	
	private void initImageLoader() {
		//�����ʹ�õ�����ڴ�
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() /1024);
		//ȡ�ķ�֮һ�Ŀ����ڴ���Ϊ����
		final int cacheSize = maxMemory/4;
		mImageCache = new LruCache<String, Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight() / 1024;
			}
		};
	}
	
	public void put(String url, Bitmap bitmap){
		mImageCache.put(url, bitmap);
	}
	
	public Bitmap get(String url){
		return mImageCache.get(url);
	}
	
}
