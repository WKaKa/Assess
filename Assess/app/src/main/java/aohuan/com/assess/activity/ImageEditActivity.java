package aohuan.com.assess.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import aohuan.com.assess.R;
import aohuan.com.assess.baseactivity.AhView;
import aohuan.com.assess.baseactivity.BaseActivity;
import aohuan.com.assess.utils.UserInfoUtils;
import aohuan.com.imagecut.CropImageView;
import butterknife.InjectView;
@AhView(R.layout.activity_image_edit)
public class ImageEditActivity extends BaseActivity {
    public static final int INT = 10;
    public static final int INT1 = 15;
    @InjectView(R.id.m_image)
    CropImageView mImage;
    @InjectView(R.id.m_ok)
    Button mOk;
    private String mPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String fileName = getSharedPreferences("temp", Context.MODE_WORLD_WRITEABLE).getString("tempName", "");
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName));
        Log.e("haha:::::", uri.getPath());
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();//屏幕宽度
        int height = wm.getDefaultDisplay().getHeight();//屏幕高度
        Bitmap bitmap = convertToBitmap(uri.getPath(), width, height- 300);
        mImage.setImageBitmap(bitmap);
        mImage.setFixedAspectRatio(true);
        mImage.setGuidelines(2);
        mImage.setAspectRatio(1, 1);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bit = mImage.getCroppedImage();
                saveNativePath(bit, fileName);
            }
        });
    }

    /**
     * 将裁剪后的照片保存到本地
     */
    private void saveNativePath(Bitmap bit, String imageName) {
        File filePath = new File("/sdcard/zhongjian/");
        filePath.mkdirs();
        String imagePath= "/sdcard/zhongjian/"+imageName;
        FileOutputStream fileOutputStream= null;
        try {
            fileOutputStream= new FileOutputStream(imagePath);
            bit.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        UserInfoUtils.setTemporaryUserIcon(ImageEditActivity.this, imagePath);
        Log.e("haha:::::::::", "imagePath::" + UserInfoUtils.getTemporaryUserIcon(ImageEditActivity.this));
        Intent intent = new Intent();
        intent.putExtra("imagePath","success");
        setResult(MainActivity.RESULT_OK, intent);
        Log.e("haha", "setResult");
        finish();
    }

    public static Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
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
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }
}
