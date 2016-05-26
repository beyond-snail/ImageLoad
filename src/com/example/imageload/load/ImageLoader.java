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
 * ͼƬ������
 * 
 * @author Administrator
 * 
 */
public class ImageLoader {
	// ͼƬ����
	ImageCache mImageCache = new MemoryCache();
	// �̳߳أ��߳�����ΪCPU������
	ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime
			.getRuntime().availableProcessors());

	// ע�뻺��ʵ��
	public void setImageCache(ImageCache cache) {
		mImageCache = cache;
	}

	// ����ͼƬ
	public void displayImage(final String imageUrl, final ImageView imageView) {
		Bitmap bitmap = mImageCache.get(imageUrl);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			return;
		}
		// ͼƬû�л��棬�ύ���̳߳�����ͼƬ
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
