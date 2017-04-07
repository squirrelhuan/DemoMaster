package com.huan.demomaster.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.huan.demomaster.MyApp;
import com.huan.demomaster.constances.Constants;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.net.Uri;

public class ImageUtils {

	/**
	 * Save image to the SD card
	 * 
	 * @param photoBitmap
	 * @param photoName
	 * @param path
	 */
	public static String savePhoto(Bitmap photoBitmap, String path, String photoName) {
		String localPath = null;
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File photoFile = new File(path, photoName + ".png");
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) { // ת�����
						localPath = photoFile.getPath();
						fileOutputStream.flush();
					}
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				localPath = null;
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				localPath = null;
				e.printStackTrace();
			} finally {
				try {
					if (fileOutputStream != null) {
						fileOutputStream.close();
						fileOutputStream = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return localPath;
	}

	/**
	 * getRoundBiemap
	 * 
	 * @param bitmap
	 *            ����Bitmap����
	 * @param tempUri
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap, Uri tempUri) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			left = 0;
			top = 0;
			right = width;
			bottom = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);// ���û����޾��

		canvas.drawARGB(0, 0, 0, 0); // �������Canvas
		paint.setColor(color);

		// ���������ַ�����Բ,drawRounRect��drawCircle
		// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);//
		// ��Բ�Ǿ��Σ���һ������Ϊͼ����ʾ���򣬵ڶ��������͵����������ֱ���ˮƽԲ�ǰ뾶�ʹ�ֱԲ�ǰ뾶��
		canvas.drawCircle(roundPx, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// ��������ͼƬ�ཻʱ��ģʽ,�ο�http://trylovecatch.iteye.com/blog/1189452
		canvas.drawBitmap(bitmap, src, dst, paint); // ��Mode.SRC_INģʽ�ϲ�bitmap���Ѿ�draw�˵�Circle
		return output;
	}

	public static class PhotoTask extends Thread {
		private String file;
		private Drawable drawable = null;

		private boolean isCut = false;

		/**
		 * 照片水印处理
		 * 
		 * @param file
		 * @param drawable
		 * @param iscut
		 *            是否剪切
		 */
		public PhotoTask(String file,/* String newfile, */Drawable drawable, boolean isCut) {
			this.file = file;
			this.drawable = drawable;
			this.isCut = isCut;
		}
		public PhotoTask(String file,/* String newfile, */Drawable drawable) {
			this.file = file;
			this.drawable = drawable;
		}

		@Override
		public void run() {
			BufferedOutputStream bos = null;
			Bitmap icon = null;
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(file, options); //此时返回bm为空  
				float percent = options.outHeight > options.outWidth ? options.outHeight / 960f : options.outWidth / 960f;

				if (percent < 1) {
					percent = 1;
				}
				int width = (int) (options.outWidth / percent);
				int height = (int) (options.outHeight / percent);
				icon = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

				//初始化画布 绘制的图像到icon上    
				Canvas canvas = new Canvas(icon);
				//建立画笔    
				Paint photoPaint = new Paint();
				//获取跟清晰的图像采样    
				photoPaint.setDither(true);
				//过滤一些    
				//                    photoPaint.setFilterBitmap(true);  
				options.inJustDecodeBounds = false;

				Bitmap prePhoto = BitmapFactory.decodeFile(file);
				if (percent > 1) {
					prePhoto = Bitmap.createScaledBitmap(prePhoto, width, height, true);
				}

				canvas.drawBitmap(prePhoto, 0, 0, photoPaint);

				if (prePhoto != null && !prePhoto.isRecycled()) {
					prePhoto.recycle();
					prePhoto = null;
					System.gc();
				}

				//设置画笔    
				Paint textPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
				//字体大小    
				textPaint1.setTextSize(30.0f);
				//采用默认的宽度    
				textPaint1.setTypeface(Typeface.DEFAULT);
				//采用的颜色    
				textPaint1.setColor(Color.WHITE);
				//阴影设置    
				textPaint1.setShadowLayer(3f, 1, 1, Color.DKGRAY);

				// 地点水印    
				String mark = "DemoMaster";/*MyApp.getAddress() == null ? "" : MyApp.getAddress();*/
				float textWidth = textPaint1.measureText(mark);
				//textPaint.setShadowLayer(3f, 1, 1,getResources().getColor(R.color.gray2));//的设置  
				canvas.drawText(mark, width - textWidth - 10, height - 60, textPaint1);

				//设置画笔    
				Paint textPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
				//字体大小    
				textPaint2.setTextSize(30.0f);
				//采用默认的宽度    
				textPaint2.setTypeface(Typeface.DEFAULT);
				//采用的颜色    
				textPaint2.setColor(Color.WHITE);
				//阴影设置    
				textPaint2.setShadowLayer(3f, 1, 1, Color.DKGRAY);

				// 时间水印    
				String mark2 = getCurrTime("yyyy-MM-dd HH:mm:ss");
				float textWidth2 = textPaint2.measureText(mark2);
				//textPaint.setShadowLayer(3f, 1, 1,getResources().getColor(R.color.gray2));//的设置  
				canvas.drawText(mark2, width - textWidth2 - 10, height - 26, textPaint2);

				if (drawable != null) {
					//图片水印 
					//构建Paint时直接加上去锯齿属性  
					Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
					paint.setXfermode(new Xfermode());
					Matrix max = new Matrix();
					BitmapDrawable bDrawable = (BitmapDrawable) drawable;
					//canvas.drawBitmap(bDrawable.getBitmap(),max,paint);
					canvas.drawBitmap(bDrawable.getBitmap(), width - textWidth - bDrawable.getBitmap().getWidth() - 10, height
							- bDrawable.getBitmap().getHeight() - 26, paint);
				}
				bos = new BufferedOutputStream(new FileOutputStream(file));
				//压缩
				int quaility = (int) (100 / percent > 80 ? 80 : 100 / percent);
				icon.compress(CompressFormat.JPEG, quaility, bos);
				bos.flush();
				if (isCut) {
					String filename = file.split("/")[file.split("/").length - 1];
					FileUtils.fileCopyAndDeleteOld(file, Constants.APP_PATH_PICTURE + "/" + filename);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (icon != null && !icon.isRecycled()) {
					icon.recycle();
					icon = null;
					System.gc();
				}
			}
		}
	}

	private static String getCurrTime(String pattern) {
		if (pattern == null) {
			pattern = "yyyyMMddHHmmss";
		}
		return (new SimpleDateFormat(pattern)).format(new Date());
	}
}
