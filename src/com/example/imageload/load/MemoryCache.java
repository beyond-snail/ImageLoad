package com.example.imageload.load;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MemoryCache implements ImageCache {

	private LruCache<String, Bitmap> mMemoryCache;

	public MemoryCache() {
		// ��ʼ��LRU����
		initImageLoader();
	}

	private void initImageLoader() {
		// �����ʹ�õ�����ڴ�
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// ȡ�ķ�֮һ�Ŀ����ڴ���Ϊ����
		final int cacheSize = maxMemory / 4;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight() / 1024;
			}
		};
	}

	@Override
	public Bitmap get(String url) {
		return mMemoryCache.get(url);
	}

	@Override
	public void put(String url, Bitmap bmp) {
		mMemoryCache.put(url, bmp);
	}

}
