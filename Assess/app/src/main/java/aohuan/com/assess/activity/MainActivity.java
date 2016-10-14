package aohuan.com.assess.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aohuan.com.assess.R;
import aohuan.com.assess.adapter.CommonAdapter;
import aohuan.com.assess.adapter.ViewHolder;
import aohuan.com.assess.baseactivity.AhView;
import aohuan.com.assess.baseactivity.BaseActivity;
import aohuan.com.assess.bean.GoodsAssessBean;
import aohuan.com.assess.bean.TokenBean;
import aohuan.com.assess.utils.AssessBeanUtils;
import aohuan.com.assess.utils.AssessImgHelp;
import aohuan.com.assess.utils.ImageLoad;
import aohuan.com.assess.utils.MyListView;
import aohuan.com.assess.utils.PermissionsUtils;
import aohuan.com.assess.utils.UserInfoUtils;
import aohuan.com.assess.utils.multi_image_selector.MultiImageSelectorActivity;
import aohuan.com.assess.utils.multi_image_selector.utils.BitmapUtils;
import aohuan.com.asyhttp.AsyHttpClicenUtils;
import aohuan.com.asyhttp.ExceptionType;
import aohuan.com.asyhttp.IUpdateUI;
import aohuan.com.asyhttp.empty.LoadingAndRetryManager;
import butterknife.InjectView;
import butterknife.OnClick;

@AhView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @InjectView(R.id.m_assess_list)
    MyListView mAssessList;
    @InjectView(R.id.m_ratingBar_miaoshu)
    RatingBar mRatingBarMiaoshu;
    @InjectView(R.id.m_ratingBar_fuwu)
    RatingBar mRatingBarFuwu;
    @InjectView(R.id.m_ratingBar_wuliu)
    RatingBar mRatingBarWuliu;
    @InjectView(R.id.m_lie)
    ScrollView mLie;
    @InjectView(R.id.m_commit)
    Button mCommit;
    @InjectView(R.id.m_title_return)
    ImageView mTitleReturn;
    @InjectView(R.id.m_title)
    TextView mTitle;
    @InjectView(R.id.m_right)
    TextView mRight;
    private List<String> img = new ArrayList<>();//商品图片
    private List<String> ids = new ArrayList<>();//商品id
    String imgs = "goodsimg/1474887978114.jpg?imageMogr2/thumbnail/400x400,goodsimg/1474858987297.jpg?imageMogr2/thumbnail/400x400,goodsimg/1474859308992.jpg?imageMogr2/thumbnail/400x400,";
    String id = "1,2,3";
    /**
     * 获取裁剪的图片
     */
    public static final int CROP_PICTURE = 999;
    /*系统版本号*/
    int num;
    Uri fileNameTwo;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int REQUEST_IMAGE = 2;
    private ArrayList<String> mSelectPath;
    private AssessImgHelp assessImgHelp;
    private AssessBeanUtils mAssessBean = new AssessBeanUtils();
    private String miaoshu = "", fuwu = "", wuliu = "";
    private CommonAdapter<GoodsAssessBean> mAdapter;
    Bitmap bitmap;
    private String mToken = "";
    private PermissionsUtils mPermissionsUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitleReturn.setVisibility(View.GONE);
        getToken();
        initView();
        mPermissionsUtils = new PermissionsUtils(MainActivity.this);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showData();
    }

    private void initView() {
        //将字符串截取成，字符数组，在；的位置进行截取
        String[] ss = imgs.split(",");
        //将字符数组转化成List集合
        img = Arrays.asList(ss);
        String[] ss1 = id.split(",");
        //将字符数组转化成List集合
        ids = Arrays.asList(ss1);
        assessImgHelp = new AssessImgHelp(ids, img);
        getStarts();
        showData();
    }

    /*
    * 服务，物流，描述星级评定获取相应的值
    * */
    private void getStarts() {
        mRatingBarMiaoshu.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                miaoshu = rating + "";
            }
        });
        mRatingBarFuwu.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                fuwu = rating + "";
            }
        });
        mRatingBarWuliu.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                wuliu = rating + "";
            }
        });
    }

    /*
    * 填充顺序
    * */
    private void showData() {
        final Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.myanim);

        mAdapter = new CommonAdapter<GoodsAssessBean>(MainActivity.this, assessImgHelp.getBeanList(), R.layout.item_assess) {
            @Override
            public void convert(ViewHolder helper, final GoodsAssessBean item, final int position) {
                ImageLoad.loadImgDefault(MainActivity.this, (ImageView) helper.getView(R.id.m_assess), item.getIcon());
                ((RatingBar) helper.getView(R.id.m_ratingBar_shop)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        item.setStarts(rating + "");
                    }
                });
                ((EditText) helper.getView(R.id.m_assess_edt)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        item.setContent(s + "");
                    }
                });
                final ImageView img1 = helper.getView(R.id.m_menmian1_icon);
                final ImageView img2 = helper.getView(R.id.m_menmian2_icon);
                final ImageView img3 = helper.getView(R.id.m_menmian3_icon);
                final ImageView imgclose1 = helper.getView(R.id.m_image_one);
                final ImageView imgclose2 = helper.getView(R.id.m_image_two);
                final ImageView imgclose3 = helper.getView(R.id.m_image_three);
                img1.setScaleType(ImageView.ScaleType.CENTER_CROP);//CENTER_CROP  FIT_XY
                img2.setScaleType(ImageView.ScaleType.CENTER_CROP);//CENTER_CROP  FIT_XY
                img3.setScaleType(ImageView.ScaleType.CENTER_CROP);//CENTER_CROP  FIT_XY
                int size = item.getBitMapListSize();
                if (size == 0) {
                    setImageViewVisibility(img1, true, img2, false, img3, false);
                    img1.setImageResource(R.mipmap.pj_tj);
                } else if (size == 1) {
                    setImageViewVisibility(img1, true, img2, true, img3, false);
                    img1.setImageBitmap(item.getBitmapList().get(0));
                    img2.setImageResource(R.mipmap.pj_tj);
                } else if (size == 2) {
                    setImageViewVisibility(img1, true, img2, true, img3, true);
                    img1.setImageBitmap(item.getBitmapList().get(0));
                    img2.setImageBitmap(item.getBitmapList().get(1));
                    img3.setImageResource(R.mipmap.pj_tj);
                } else if (size == 3) {
                    setImageViewVisibility(img1, true, img2, true, img3, true);
                    img1.setImageBitmap(item.getBitmapList().get(0));
                    img2.setImageBitmap(item.getBitmapList().get(1));
                    img3.setImageBitmap(item.getBitmapList().get(2));
                }
                img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("123", "img: " + img1.getId());
                        mAssessBean.setBean(position, 0);
                        new PopupWindows(MainActivity.this);
                    }
                });
                img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAssessBean.setBean(position, 1);
                        new PopupWindows(MainActivity.this);
                    }
                });
                img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAssessBean.setBean(position, 2);
                        new PopupWindows(MainActivity.this);
                    }
                });
                img1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (assessImgHelp.getBeanList().get(position).getImgListSize() > 0) {
                            img1.startAnimation(anim);
                            imgclose1.setVisibility(View.VISIBLE);
                        } else {
                            toast("请添加图片");
                        }


                        return true;
                    }
                });
                imgclose1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        assessImgHelp.longDelete(position, 0);
                        mAdapter.notifyDataSetChanged();
                        imgclose1.setVisibility(View.GONE);
                        toast("删除成功");

                    }
                });
                img2.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (assessImgHelp.getBeanList().get(position).getImgListSize() > 1) {
                            img2.startAnimation(anim);
                            imgclose2.setVisibility(View.VISIBLE);
                        } else {
                            toast("请添加图片");
                        }


                        return true;
                    }
                });
                imgclose2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        assessImgHelp.longDelete(position, 1);
                        mAdapter.notifyDataSetChanged();
                        imgclose2.setVisibility(View.GONE);
                        toast("删除成功");

                    }
                });
                img3.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (assessImgHelp.getBeanList().get(position).getImgListSize() > 2) {
                            img3.startAnimation(anim);
                            imgclose3.setVisibility(View.VISIBLE);
                        } else {
                            toast("请添加图片");
                        }


                        return true;
                    }
                });
                imgclose3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        assessImgHelp.longDelete(position, 2);
                        mAdapter.notifyDataSetChanged();
                        imgclose3.setVisibility(View.GONE);
                        toast("删除成功");

                    }
                });
            }

        };
        mAssessList.setAdapter(mAdapter);
    }

    public void setImageViewVisibility(ImageView img1, boolean boo1, ImageView img2, boolean boo2, ImageView img3, boolean boo3) {
        img1.setVisibility(boo1 ? View.VISIBLE : View.GONE);
        img2.setVisibility(boo2 ? View.VISIBLE : View.GONE);
        img3.setVisibility(boo3 ? View.VISIBLE : View.GONE);
    }

    @OnClick({R.id.m_title_return,R.id.m_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_title_return:
                break;
            case R.id.m_commit:
                Intent intent = new Intent(MainActivity.this, FinallyActivity.class);
                intent.putExtra("result", assessImgHelp.jsonSString());
                startActivity(intent);
                break;
        }
    }

    /**
     * 弹出popup
     *
     * @author lijipei
     */
    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(mLie, Gravity.BOTTOM, 0, 0);
            update();

            View cView = view.findViewById(R.id.item_popupwindows_view);
            TextView bt1 = (TextView) view
                    .findViewById(R.id.item_popupwindows_camera);
            TextView bt2 = (TextView) view
                    .findViewById(R.id.item_popupwindows_Photo);
            TextView bt3 = (TextView) view
                    .findViewById(R.id.item_popupwindows_cancel);

            cView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dismiss();
                }
            });
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //网络版本号
                    num = Integer.parseInt(Build.VERSION.RELEASE.
                            replace(".", ""));
//                if(num < 600) {
                    Uri imageUri = null;
                    String fileName = null;
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //删除上一次截图的临时文件
                    SharedPreferences sharedPreferences = getSharedPreferences("temp", Context.MODE_WORLD_WRITEABLE);
                    deletePhotoAtPathAndName(Environment.getExternalStorageDirectory().getAbsolutePath(), sharedPreferences.getString("tempName", ""));

                    //保存本次截图临时文件名字
                    fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("tempName", fileName);
                    editor.commit();
                    imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName));
                    Log.e("asdddd", "asdasdas" + imageUri.toString());
                    fileNameTwo = imageUri;
                    //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    Log.e("走这里", "openCameraIntent" + openCameraIntent);
                    startActivityForResult(openCameraIntent, PHOTO_REQUEST_CAMERA);
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (mPermissionsUtils.getPermissionRead()) {
                        return;
                    }
                    Intent intent = new Intent(MainActivity.this, MultiImageSelectorActivity.class);
                    // 是否显示拍摄图片
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
                    // 最大可选择图片数量
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                    // 选择模式
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                    // 默认选择
                    if (mSelectPath != null && mSelectPath.size() > 0) {
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
                    }
                    startActivityForResult(intent, REQUEST_IMAGE);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE) {

                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (mSelectPath != null && mSelectPath.size() > 0) {
                    bitmap = BitmapUtils.getBitmapFromFile(mSelectPath.get(0), 64, 64);
//                    mAccountIcons.setImageBitmap(bitmap);
                    Log.e("123", "拍照回调");
                    qiNiuUpload(bitmap);
                } else {
                    toast("选取图片失败");
                }
            } else if (requestCode == PHOTO_REQUEST_CAMERA) {
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();
                } else {
                    String fileName = getSharedPreferences("temp", Context.MODE_WORLD_WRITEABLE).getString("tempName", "");
                    uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName));
                }

                Intent intent = new Intent(MainActivity.this, ImageEditActivity.class);
                startActivityForResult(intent, CROP_PICTURE);
            } else if (requestCode == CROP_PICTURE) {
                Bitmap photo = null;
                if (data.getStringExtra("imagePath").equals("success")) {
                    Uri uri = Uri.parse(UserInfoUtils.getTemporaryUserIcon(MainActivity.this));
                    Log.e("haha", uri.getPath());
                    photo = ImageEditActivity.convertToBitmap(uri.getPath(), 800, 600);
                    Log.e("haha", "photo::" + photo.toString() + "\t" + photo.getByteCount() + "\t " + photo.getRowBytes());
                    bitmap = photo;
//                    mAccountIcons.setImageBitmap(bitmap);
                    Log.e("123", "tokentoken");
                    qiNiuUpload(bitmap);
                }
            }
        }
    }

    public static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static void deletePhotoAtPathAndName(String path, String fileName) {
        if (checkSDCardAvailable()) {
            File folder = new File(path);
            File[] files = folder.listFiles();
            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i].getName());
                if (files[i].getName().equals(fileName)) {
                    files[i].delete();
                }
            }
        }
    }

    /**
     * 图片上传七牛服务器
     */
    private void qiNiuUpload(final Bitmap bitmap) {
        UploadManager uploadManager = new UploadManager();
        byte[] data = getBytesFromBitmap(bitmap);
        String key = String.valueOf(System.currentTimeMillis()) + ".jpg";


        uploadManager.put(data, key, mToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    assessImgHelp.doQiNiuDone(mAssessBean.getOutIndex(), bitmap, key, mAssessBean.getInIndex());
                    mAdapter.notifyDataSetChanged();
                    toast("上传成功");
                } else {
                    toast("上传失败");
                }
            }
        }, null);
    }
    private void getToken() {
        AsyHttpClicenUtils.getNewInstance(mRight).asyHttpClicenUtils(this, TokenBean.class, mRight, new IUpdateUI<TokenBean>() {
            @Override
            public void update(TokenBean allData, LoadingAndRetryManager retryManager) {
                if (!allData.isSuccess()) {
                    toast(allData.getMsg());
                    retryManager.showEmpty();
                    return;
                }
                mToken = allData.getData().get(0).getToken();
//                Log.e("123", "获取七牛token:" + mToken);
//                if (!mToken.equals("") && bitmap != null) {
//                    qiNiuUpload(bitmap);
//                    Log.e("123", "" + bitmap);
//                }
                retryManager.showContent();
            }

            @Override
            public void sendFail(ExceptionType s, LoadingAndRetryManager retryManager) {
               retryManager.showRetry();
            }
        }).post("http://ahshop_b2c.aohuanit.com:56670/api/index/qntoken", new RequestParams(), true);
    }
    /**
     * 把bitmap转化为bytes
     *
     * @param bitmap 源Bitmap
     * @return Byte数组
     */
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
