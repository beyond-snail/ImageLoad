package com.example.imageload.load;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * 图片加载类
 * 
 * @author Administrator
 * 
 */
public class ImageLoader {
	// 图片缓存
	ImageCache mImageCache = new MemoryCache();
	// 线程池，线程数量为CPU的数量
	ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime
			.getRuntime().availableProcessors());

	// 注入缓存实现
	public void setImageCache(ImageCache cache) {
		mImageCache = cache;
	}

	// 加载图片
	public void displayImage(final String imageUrl, final ImageView imageView) {
		Bitmap bitmap = mImageCache.get(imageUrl);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			return;
		}
		// 图片没有缓存，提交到线程池下载图片
		submitLoadRequest(imageUrl, imageView);
	}

	private void submitLoadRequest(final String imageUrl,
			final ImageView imageView) {
		imageView.setTag(imageUrl);
		mExecutorService.submit(new Runnable() {
			@Override
			public void run() {
				Bitmap bitmap = downLoadImage(imageUrl);
				if (bitmap == null) {
					return;
				}
				if (imageView.getTag().equals(imageUrl)) {
					imageView.setImageBitmap(bitmap);
				}
				mImageCache.put(imageUrl, bitmap);
			}
		});
	}

	protected Bitmap downLoadImage(String imageUrl) {
		Bitmap bitmap = null;
		try {
			URL url = new URL(imageUrl);
			final HttpURLConnection conn = (HttpURLConnection) url
					.openConnection();
			bitmap = BitmapFactory.decodeStream(conn.getInputStream());
			conn.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
