package com.novo.zealot.UI.Fragment;

/**
 * Created by Novo on 2019/5/29.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.dialog.myDialog.MyAlertInputDialog;
import com.novo.zealot.R;
import com.novo.zealot.UI.Activity.SettingActivity;
import com.novo.zealot.Utils.DataUtil;
import com.novo.zealot.Utils.GlobalUtil;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    Context context;

    //总距离、总时间
    TickerView tv_sumOfDistance, tv_sumOfTimes;
    int sumOfDistance = 0, sumOfTimes = 0;

    //头像
    ImageButton ib_avatar;
    //修改个人数据
    ImageButton ib_editName, ib_editAge, ib_editHeight, ib_editWeight;
    //头像Bitmap
    private Bitmap avatar;

    TextView tv_name, tv_age, tv_height, tv_weight;

    //存储路径
    private static String path = "/sdcard/myAvatar/";// sd路径

    //个人信息文件名
    String fileName = "personalData";

    //设置
    RelativeLayout rl_setting;


    public ProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d(TAG, "ProfileFragment Created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        context = view.getContext();

        //初始化
        tv_sumOfDistance = view.findViewById(R.id.tv_sumOfDistance);
        tv_sumOfDistance.setCharacterLists(TickerUtils.provideNumberList());
        tv_sumOfDistance.setAnimationDuration(1000);


        tv_sumOfTimes = view.findViewById(R.id.tv_sumOfTimes);
        tv_sumOfTimes.setCharacterLists(TickerUtils.provideNumberList());
        tv_sumOfTimes.setAnimationDuration(1000);

        tv_name = view.findViewById(R.id.tv_name);
        tv_age = view.findViewById(R.id.tv_age);
        tv_height = view.findViewById(R.id.tv_height);
        tv_weight = view.findViewById(R.id.tv_weight);

        //获取头像控件
        ib_avatar = view.findViewById(R.id.ib_avatar);

        /*
        点击头像进行更换头像
         */
        ib_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });

        //编辑昵称
        ib_editName = view.findViewById(R.id.ib_editName);
        ib_editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(view.getContext()).builder()
                        .setTitle("请输入")
                        .setEditText("");
                myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = myAlertInputDialog.getResult();
                        if (name.length() == 0) {
                            Toast.makeText(view.getContext(), "昵称不能为空!", Toast.LENGTH_SHORT).show();
                        } else if (name.length() > 8) {
                            Toast.makeText(view.getContext(), "昵称不能超过8个字节!", Toast.LENGTH_SHORT).show();
                        } else {
                            tv_name.setText(name);
                            Toast.makeText(view.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        }
                        myAlertInputDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();
            }
        });

        //修改年龄
        ib_editAge = view.findViewById(R.id.ib_editAge);
        ib_editAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(view.getContext()).builder()
                        .setTitle("请输入")
                        .setEditText("");
                myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String age = myAlertInputDialog.getResult();
                        if (age.length() == 0) {
                            Toast.makeText(view.getContext(), "年龄不能为空!", Toast.LENGTH_SHORT).show();
                        } else if (!DataUtil.isNumeric(age) || Integer.parseInt(age) < 0 || Integer.parseInt(age) > 100) {
                            Toast.makeText(view.getContext(), "输入有误!", Toast.LENGTH_SHORT).show();
                        } else {
                            tv_age.setText(age);
                            Toast.makeText(view.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        }
                        myAlertInputDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();
            }
        });

        //修改身高
        ib_editHeight = view.findViewById(R.id.ib_editHeight);
        ib_editHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(view.getContext()).builder()
                        .setTitle("请输入")
                        .setEditText("");
                myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String height = myAlertInputDialog.getResult();
                        if (height.length() == 0) {
                            Toast.makeText(view.getContext(), "身高不能为空!", Toast.LENGTH_SHORT).show();
                        } else if (!DataUtil.isNumeric(height) || Integer.parseInt(height) < 0 || Integer.parseInt(height) > 100) {
                            Toast.makeText(view.getContext(), "输入有误!", Toast.LENGTH_SHORT).show();
                        } else {
                            tv_height.setText(height);
                            Toast.makeText(view.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        }
                        myAlertInputDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();
            }
        });

        //修改体重
        ib_editWeight = view.findViewById(R.id.ib_editWeight);
        ib_editWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(view.getContext()).builder()
                        .setTitle("请输入")
                        .setEditText("");
                myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String weight = myAlertInputDialog.getResult();
                        if (weight.length() == 0) {
                            Toast.makeText(view.getContext(), "体重不能为空!", Toast.LENGTH_SHORT).show();
                        } else if (!DataUtil.isNumeric(weight) || Integer.parseInt(weight) < 0 || Integer.parseInt(weight) > 100) {
                            Toast.makeText(view.getContext(), "输入有误!", Toast.LENGTH_SHORT).show();
                        } else {
                            tv_weight.setText(weight);
                            Toast.makeText(view.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        }
                        myAlertInputDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();
            }
        });


        //启动时设置头像等信息
        initView();

        //设置
        rl_setting = view.findViewById(R.id.rl_setting);
        rl_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        return view;
    }

    /**
     * 加载头像及个人信息
     */
    private void initView() {
        Bitmap bt = BitmapFactory.decodeFile(path + "avatar.jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            ib_avatar.setImageDrawable(drawable);
        } else {
            ib_avatar.setImageDrawable(getResources().getDrawable(R.drawable.icon_avatar_black_48dp));
        }

        readPersonalData();
    }

    /**
     * 选择头像来源
     */
    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        View view = View.inflate(getActivity(), R.layout.dialog_select_photo, null);
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
                        ib_avatar.setImageBitmap(avatar);// 用ImageButton显示出来
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
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            String name = tv_name.getText().toString() + "\n";
            String age = tv_age.getText().toString() + "\n";
            String height = tv_height.getText().toString() + "\n";
            String weight = tv_weight.getText().toString() + "\n";

            bufferedWriter.write(name);
            bufferedWriter.write(age);
            bufferedWriter.write(height);
            bufferedWriter.write(weight);
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取个人信息
     */
    private void readPersonalData() {
        StringBuilder sBuilder = new StringBuilder();

        InputStream inputStream = null;
        try {
            inputStream = context.openFileInput(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String name, age, height, weight;

            //读取
            name = bufferedReader.readLine();
            age = bufferedReader.readLine();
            height = bufferedReader.readLine();
            weight = bufferedReader.readLine();

            //设置
            tv_name.setText(name);
            tv_age.setText(age);
            tv_height.setText(height);
            tv_weight.setText(weight);

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //查询总距离并传给控件
        sumOfDistance = GlobalUtil.getInstance().databaseHelper.queryAllDistance();
        //格式 XX.X
        sumOfDistance /= 100;
        double sumOfDistanceInKM = sumOfDistance / 10.0;
        tv_sumOfDistance.setText(sumOfDistanceInKM + "");

        //查询总次数
        sumOfTimes = GlobalUtil.getInstance().databaseHelper.queryNumOfTimes();
        tv_sumOfTimes.setText(sumOfTimes + "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //退出时存储数据
        savePersonalData();
    }
}
