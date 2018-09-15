package com.bwie.majunbao.ui.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bwie.majunbao.R;
import com.bwie.majunbao.api.UserApi;
import com.bwie.majunbao.common.Constants;
import com.bwie.majunbao.contract.LoginContract;
import com.bwie.majunbao.contract.UploadContract;
import com.bwie.majunbao.entity.UploadIconEntity;
import com.bwie.majunbao.entity.UserEntity;
import com.bwie.majunbao.eventbus.UploadIconEventBus;
import com.bwie.majunbao.model.LoginModel;
import com.bwie.majunbao.presenter.UploadPresenter;
import com.bwie.majunbao.utils.EncryptUtil;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.BaseMvpActivity;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;
import majunbao.bwie.com.jingdong_base_marster.net.RetrofitUtils;
import majunbao.bwie.com.jingdong_base_marster.utils.SharedPreferencesUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SetZiLiaoActivity extends BaseMvpActivity<UploadContract.UploadModel, UploadContract.UploadPresenter> implements UploadContract.IUploadView, View.OnClickListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.touxiang)
    LinearLayout touxiang;
    @BindView(R.id.phone)
    LinearLayout phone;
    @BindView(R.id.username)
    LinearLayout username;
    @BindView(R.id.xingbie)
    LinearLayout xingbie;
    @BindView(R.id.birthday)
    LinearLayout birthday;
    @BindView(R.id.settx)
    ImageView settx;
    @BindView(R.id.tel)
    TextView tel;
    @BindView(R.id.name)
    TextView tname;

    //成员变量
    private File file;
    private String mMuser;
    private String mMpwd;
    private String mPhone;
    private String mHeadPic;
    private String mNickName;
    private String mUserId;
    private String mSessionId;
    private SharedPreferences loginSp;
    private Bitmap mBitmap;
    private static final String TAG = "RxPermissionsSample";

    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_REQUEST_CODE = 3;

    //调用照相机返回图片文件
    private File tempFile;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_set_zi_liao;
    }

    @Override
    protected void initData() {
        super.initData();
        //得到loginsp存的值
        loginSp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String nickName = loginSp.getString("nickName", "");//昵称
        String headPic = loginSp.getString("headPic", "");//头像
        String Phone = loginSp.getString("Phone", "");//账号
        String pwd = loginSp.getString("pwd", "");//密码
        String sessionId = loginSp.getString("sessionId", "");//sessionId
        String userId = loginSp.getString("userId", "");//userId
        String sex = loginSp.getString("sex", "");//性别
        String birthday = loginSp.getString("birthday", "");//生日
        if (nickName.equals("") && headPic.equals("") && Phone.equals("")) {

        } else {
            //赋值昵称
            tname.setText(nickName);
            //赋值头像
            settx.setImageURI(Uri.parse(headPic));
            //赋值账号
            tel.setText(Phone);
            // TODO: 2018/9/13 生日和性别没有设置
        }
    }

    @Override
    protected void initView() {
        super.initView();
        //file = new File(Environment.getExternalStorageDirectory() + "/1603.png");
        //点击事件
        touxiang.setOnClickListener(this);
        username.setOnClickListener(this);
    }



    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //设置头像
            case R.id.touxiang:
                Toast.makeText(this, "点击了头像", Toast.LENGTH_SHORT).show();
                DialogPlus dialog = DialogPlus.newDialog(this)
                        .setContentHolder(new ViewHolder(R.layout.pop_list))
                        .setExpanded(true, ActionBar.LayoutParams.WRAP_CONTENT)
                        .setInAnimation(R.anim.abc_fade_in)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {

                            }
                        })
                        .setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(final DialogPlus dialog, View view) {
                                switch (view.getId()){
                                    //取消
                                    case R.id.quxiao :
                                        //取消
                                        Toast.makeText(SetZiLiaoActivity.this, "取消", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        break;
                                        //调用相机
                                    case R.id.xiangji:
                                        getPicFromCamera();
                                        dialog.dismiss();
                                        break;
                                        //调用相册
                                    case R.id.xiangce:
                                        getPicFromAlbm();
                                        dialog.dismiss();
                                        break;
                                }
                            }
                        })
                        .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                dialog.show();
                break;
                //设置昵称
            case R.id.username:
                break;
        }
    }

    @Override
    public void success(UploadIconEntity uploadIconEntity) {
        Log.i("lll","成功"+uploadIconEntity.getMessage());
        String headPath = uploadIconEntity.getHeadPath();
        Log.i("lll",headPath);
        //loginSp.edit().putString("headIcon",headPath).commit();
        /*得到新的头像值,和新的sessionId,昵称以及账号并存进去*/
        //EventBus发送事件到登陆页面那一集首页
        UploadIconEventBus uploadIconEventBus = new UploadIconEventBus(headPath);
        EventBus.getDefault().postSticky(uploadIconEventBus);
        loginSp.edit().putString("headPic",headPath).commit();
    }


    /**
     * 从相机获取图片
     */
    private void getPicFromCamera() {
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(SetZiLiaoActivity.this, "com.bwie.majunbao", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            Log.e("dasd", contentUri.toString());
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
    }


    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CROP_REQUEST_CODE);
        Log.i("lll","5");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:   //调用相机后返回
                if (resultCode == RESULT_OK) {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(SetZiLiaoActivity.this, "com.bwie.majunbao", tempFile);
                        cropPhoto(contentUri);
                        Log.i("lll","3");

                    } else {
                        cropPhoto(Uri.fromFile(tempFile));
                        Log.i("lll","4");

                    }
                }
                break;
            case ALBUM_REQUEST_CODE:    //调用相册后返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    cropPhoto(uri);
                    Log.i("lll","2");
                }
                break;
            case CROP_REQUEST_CODE:     //调用剪裁后返回
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Log.i("lll","1");
                    //在这里获得了剪裁后的Bitmap对象，可以用于上传
                    Bitmap image = bundle.getParcelable("data");
                    //设置到ImageView上
                    settx.setImageBitmap(image);
                    //也可以进行一些保存、压缩等操作后上传
                    String path = saveImage("crop", image);
                    Log.i("lll","file"+tempFile);
                    Log.i("lll","path"+path);
                    String sessionId = loginSp.getString("sessionId", "");
                    String userId = loginSp.getString("userId", "");
                    presenter.uploadIcon(sessionId,userId, new File(path));
                }
                break;
        }
    }

    public String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());

        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void failure(String msg) {

    }

    @Override
    public IBasePresenter initBasePresenter() {
        return new UploadPresenter();
    }

    @Override
    public void failure() {

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
