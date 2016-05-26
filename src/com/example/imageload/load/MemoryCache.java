package com.example.imageload.load;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MemoryCache implements ImageCache {

	private LruCache<String, Bitmap> mMemoryCache;

	public MemoryCache() {
		// 初始化LRU缓存
		initImageLoader();
	}

	private void initImageLoader() {
		// 计算可使用的最大内存
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// 取四分之一的可用内存作为缓存
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
