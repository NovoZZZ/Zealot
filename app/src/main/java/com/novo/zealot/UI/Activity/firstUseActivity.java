package com.novo.zealot.UI.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.novo.zealot.R;
import com.novo.zealot.Utils.DataUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class firstUseActivity extends Activity {

    ImageButton ib_avatarFirstTime;
    //头像Bitmap
    private Bitmap avatar;

    //存储路径
    private static String path = "/sdcard/myAvatar/";// sd路径

    //个人信息文件名
    String fileName = "personalData";

    EditText et_name, et_age, et_height, et_weight;

    Button btn_infoConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_use);


        //获取头像控件
        ib_avatarFirstTime = findViewById(R.id.ib_avatarFirstTime);
        ib_avatarFirstTime.setImageDrawable(getResources().getDrawable(R.drawable.icon_avatar_black_48dp));

        //确认按钮
        btn_infoConfirm = findViewById(R.id.btn_infoConfirm);
        btn_infoConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean datasAreCorrect = false;
                String name = et_name.getText().toString();
                String age = et_age.getText().toString();
                String height = et_height.getText().toString();
                String weight = et_weight.getText().toString();

                if (name.length() == 0 || age.length() == 0 || height.length() == 0 || weight.length() == 0) {
                    Toast.makeText(firstUseActivity.this, "数据不能为空!", Toast.LENGTH_SHORT).show();
                } else if (name.length() > 8) {
                    Toast.makeText(firstUseActivity.this, "昵称不能超过8个字节!", Toast.LENGTH_SHORT).show();
                } else if (!DataUtil.isNumeric(age) || !DataUtil.isNumeric(height) || !DataUtil.isNumeric(weight)
                        || Integer.parseInt(age) > 100 || Integer.parseInt(age) < 0
                        || Integer.parseInt(height) > 300 || Integer.parseInt(height) < 0
                        || Integer.parseInt(weight) > 200 || Integer.parseInt(weight) < 0) {
                    Toast.makeText(firstUseActivity.this, "年龄、身高、体重输入有误!", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });

        /*
        点击头像进行更换头像
         */
        ib_avatarFirstTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
    }

    /**
     * 选择头像来源
     */
    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(firstUseActivity.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(firstUseActivity.this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "avatar.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/avatar.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    avatar = extras.getParcelable("data");
                    if (avatar != null) {
                        setPicToView(avatar);// 保存在SD卡中
                        ib_avatarFirstTime.setImageBitmap(avatar);// 用ImageButton显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "avatar.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 存储个人信息至本地
     */
    private void savePersonalData() {
        OutputStream outputStream = null;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            String name = et_name.getText().toString() + "\n";
            String age = et_age.getText().toString() + "\n";
            String height = et_height.getText().toString() + "\n";
            String weight = et_weight.getText().toString() + "\n";

            bufferedWriter.write(name);
            bufferedWriter.write(age);
            bufferedWriter.write(height);
            bufferedWriter.write(weight);
            bufferedWriter.close();

            //标记非第一次使用
            String isFirstUse = "false";
            OutputStream firstUseOS = openFileOutput("config", Context.MODE_PRIVATE);

            //这个地方复制粘贴忘记改导致找了三个小时的BUG，纪念一下 2019年05月30日18:50:18
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(firstUseOS));
            bw.write(isFirstUse);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出时存储数据
        savePersonalData();
    }
}
