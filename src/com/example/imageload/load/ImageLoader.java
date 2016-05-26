package com.example.imageload.load;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.widget.ImageView;


/**
 * 图片加载类
 * @author Administrator
 *
 */

public class ImageLoader {
	//图片缓存
	LruCache<String, Bitmap> mImageCache;
	//线程池，线程数量为CPU的数量
	ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	public ImageLoader(){
		initImageLoader();
	}

	private void initImageLoader() {
		//计算可使用的最大内存
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() /1024);
		//取四分之一的可用内存作为缓存
		final int cacheSize = maxMemory/4;
		mImageCache = new LruCache<String, Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight() / 1024;
			}
		};
	}
	
	public void displayImage(final String url, final ImageView imageView){
		imageView.setTag(url);
		mExecutorService.submit(new Runnable() {
			
			@Override
			public void run() {
				Bitmap bitmap = downLoadImage(url);
				if (bitmap == null){
					return;
				}
				if (imageView.getTag().equals(url)){
					imageView.setImageBitmap(bitmap);
				}
				mImageCache.put(url, bitmap);
			}
		});
	}

	protected Bitmap downLoadImage(String imageUrl) {
		
		Bitmap bitmap = null;
		
		
		try {
			URL url = new URL(imageUrl);
			final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			bitmap = BitmapFactory.decodeStream(conn.getInputStream());
			conn.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return bitmap;
	}
}
