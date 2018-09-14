package com.bwie.majunbao.ui.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.bwie.majunbao.entity.UploadIconEntity;
import com.bwie.majunbao.entity.UserEntity;
import com.bwie.majunbao.eventbus.UploadIconEventBus;
import com.bwie.majunbao.model.LoginModel;
import com.bwie.majunbao.utils.EncryptUtil;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
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

public class SetZiLiaoActivity extends BaseMvpActivity<LoginContract.LoginModel, LoginContract.LoginPresenter> implements LoginContract.ILoginView, View.OnClickListener {


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

    @Override
    protected int setLayoutId() {
        return R.layout.activity_set_zi_liao;
    }

    @Override
    protected void initData() {
        super.initData();
        //设置默认存储位置以及名字
        file = new File(Environment.getExternalStorageDirectory() + "/1603b.png");
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
        file = new File(Environment.getExternalStorageDirectory() + "/1603.png");
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
                                        Toast.makeText(SetZiLiaoActivity.this, "相机", Toast.LENGTH_SHORT).show();
                                        //打开相机 MediaStore.ACTION_IMAGE_CAPTURE 打开相机的Action
                                        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        //在Sdcard 中创建文件 存入图片
                                        it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                        //1.意图   2.requestCode 请求码
                                        startActivityForResult(it, 1000);
                                        dialog.dismiss();
                                        break;
                                        //调用相册
                                    case R.id.xiangce:
                                        Toast.makeText(SetZiLiaoActivity.this, "相册", Toast.LENGTH_SHORT).show();
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

    //拍照完成后回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            //调取裁剪功能  om.android.camera.action.CROP 裁剪的Action
            Intent it = new Intent("com.android.camera.action.CROP");
            //得到图片设置类型
            it.setDataAndType(Uri.fromFile(file), "image/*");
            //是否支持裁剪 设置 true 支持  false 不支持
            it.putExtra("CROP", true);
            //设置比例大小  1:1
            it.putExtra("aspectX", 1);
            it.putExtra("aspectY", 1);
            //输出的大小
            it.putExtra("outputX", 250);
            it.putExtra("outputY", 250);
            //将裁剪好的图片进行返回到Intent中
            it.putExtra("return-data", true);
            startActivityForResult(it, 2000);
        }
        //点击完裁剪的完成以后会执行的方法
        if (requestCode == 2000 && resultCode == RESULT_OK) {
            mBitmap = data.getParcelableExtra("data");
            settx.setImageBitmap(mBitmap);
            //裁剪完成开始上传到服务器
            upLoadIcon(file);
        }
    }


    //上传头像
    @SuppressLint("CheckResult")
    private void upLoadIcon(File myfile) {
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(
                MediaType.parse("image/*"), myfile));//image/**/
        //得到loginsp里面的userid,sessionid
        final SharedPreferences loginsp = getSharedPreferences("login", MODE_PRIVATE);
        String userId = loginsp.getString("userId","");
        String sessionId = loginsp.getString("sessionId", "");
        RetrofitUtils.getInstance().createApi(Constants.BASE_url,UserApi.class).uploadIcon(sessionId,userId,filePart).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UploadIconEntity>() {
            @Override
            public void accept(UploadIconEntity uploadIconEntity) throws Exception {
                Log.i("vvv","成功"+uploadIconEntity.getMessage());
                Toast.makeText(SetZiLiaoActivity.this, "成功"+uploadIconEntity.getMessage(), Toast.LENGTH_SHORT).show();
                if (uploadIconEntity.getStatus().equals("0000")) {
                    String Phone = loginsp.getString("Phone", "");
                    final String pwd = loginsp.getString("pwd", "");
                    String mEncrypt = EncryptUtil.encrypt(pwd);
                    //重新网络请求,进行登陆,更新数据,获取新的sessionId数据
                    new LoginModel().login(Phone,mEncrypt).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UserEntity>() {
                        @Override
                        public void accept(UserEntity userEntity) throws Exception {
                            Log.i("mjb","闪传"+userEntity.getMessage());
                            //new LoginModel().login(phone,pwd)
                            //得到新的头像值,和新的sessionId,昵称以及账号并存进去
                            //EventBus发送事件到登陆页面那一集首页
                            UploadIconEventBus uploadIconEventBus = new UploadIconEventBus(userEntity.getResult().getSessionId(), userEntity.getResult().getUserInfo().getNickName(), userEntity.getResult().getUserInfo().getHeadPic(), userEntity.getResult().getUserInfo().getPhone());
                            EventBus.getDefault().postSticky(uploadIconEventBus);

                            String headPic1 = userEntity.getResult().getUserInfo().getHeadPic();
                            String sessionId1 = userEntity.getResult().getSessionId();
                           // String nickName1 = userEntity.getResult().getUserInfo().getNickName();
                           // String phone1 = userEntity.getResult().getUserInfo().getPhone();
                            //存值
                            loginsp.edit().putString("headPic",headPic1).commit();
                            loginsp.edit().putString("sessionId",sessionId1).commit();
                          //  loginsp.edit().putString("nickName",nickName1).commit();
                           // loginsp.edit().putString("Phone",phone1).commit();

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            // TODO: 2018/9/14 没有写请求失败
                        }
                    });
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("vvv","失败"+throwable.toString());
                Toast.makeText(SetZiLiaoActivity.this, "头像上传失败"+throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

















    @Override
    public void success(UserEntity userEntity) {

    }

    @Override
    public void failure(String msg) {

    }

    @Override
    public IBasePresenter initBasePresenter() {
        return null;
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
