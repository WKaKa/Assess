package aohuan.com.assess.utils.multi_image_selector.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.List;

public class BitmapUtils {
	public static Bitmap convertToBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// 缩放
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int)scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}
	/**
	 * 把我图片转化为字节流
	 *
	 * */
	public static String sendPhoto(Bitmap bitmap){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0 - 100)压缩文件
		byte[] bt = stream.toByteArray();
		String photoStr = byte2hex(bt);
		return photoStr;
	}

	private static String byte2hex(byte[] b){
		StringBuffer sb = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}
		}
		return sb.toString();
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = null;
		if(bitmap.getWidth()>bitmap.getHeight()){
			output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Config.ARGB_8888);
		}else if(bitmap.getWidth()<bitmap.getHeight()){
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Config.ARGB_8888);
		}else if(bitmap.getWidth() == bitmap.getHeight()){
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		}
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, output.getWidth(), output.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
	/** 质量压缩 */
	public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
	/** 比例压缩 */
	public static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }
	public static String AppendBitmapString(List<Bitmap> list){

		StringBuilder sBuffer=new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("---"+i);
			Bitmap bitmap=list.get(i);
			sBuffer.append(sendPhoto(bitmap)).append(",");
		}

		String s=sBuffer.toString();
//		System.out.println("----path-"+s);
		return s.substring(0, s.length()-1);
	}

//	public static String AppendBitmapStringByFenTyep(List<String> list, String type){
//
//		String str =
//		return s.substring(0, s.length()-1);
//	}


	public static String AppendBitmapStringByFen(List<String> list){

		StringBuilder sBuffer=new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("---"+i);
			String bitmap = list.get(i);
			sBuffer.append(bitmap).append(";");
		}

		String s=sBuffer.toString();
//		System.out.println("----path-"+s);
		return s.substring(0, s.length()-1);
	}
	/**
	 * 获取一个指定大小的bitmap
	 *
	 * @param reqWidth  目标宽度
	 * @param reqHeight 目标高度
	 */
	public static Bitmap getBitmapFromFile(String pathName, int reqWidth,
										   int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		options = calculateInSampleSize(options, reqWidth, reqHeight);
		return BitmapFactory.decodeFile(pathName, options);
	}
	public static BitmapFactory.Options calculateInSampleSize(
			final BitmapFactory.Options options, final int reqWidth,
			final int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > 400 || width > 450) {
			if (height > reqHeight || width > reqWidth) {
				// 计算出实际宽高和目标宽高的比率
				final int heightRatio = Math.round((float) height
						/ (float) reqHeight);
				final int widthRatio = Math.round((float) width
						/ (float) reqWidth);
				// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
				// 一定都会大于等于目标的宽和高。
				inSampleSize = heightRatio < widthRatio ? heightRatio
						: widthRatio;
			}
		}
		// 设置压缩比例
		options.inSampleSize = inSampleSize;
		options.inJustDecodeBounds = false;
		return options;
	}

}
