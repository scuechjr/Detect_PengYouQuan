package com.example.swee1.pengyouquan;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.swee1.pengyouquan.dao.PengYouQuanDao;
import com.example.swee1.pengyouquan.domain.ConfigBean;
import com.example.swee1.pengyouquan.domain.enums.JobTypeEnum;
import com.example.swee1.pengyouquan.service.ConfigService;

public class MainActivity extends AppCompatActivity {
    public static boolean saveExistingData = false;
    private ConfigBean config = new ConfigBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PengYouQuanDao.initDatabase(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        config = ConfigService.getInstance().getFirst();
        if (null == config) {
            config = new ConfigBean();
        } else {
            // 朋友圈检查需要在微信页面下操作，如果打开检查器，说明已经退出微信页面，所以默认停止检测
            config.setType(JobTypeEnum.STOP.getValue());
            config.setRun(false);
            ConfigService.getInstance().update(config);
        }
        initViewData();
    }

    private void initView() {
        bindView();
        setListener();
    }

    private Button jumpToSettingBtn = null;
    private Button showNoticeBtn = null;
    private Button jumpToContactBtn = null;
    private EditText startMarkNameEdit = null;
    private Switch delFlagSwitch = null;
    private Switch runFlagSwitch = null;
    private Button openWxBtn = null;
    private void bindView() {
        jumpToSettingBtn = (Button) findViewById(R.id.btn_setting);
        showNoticeBtn = (Button) findViewById(R.id.btn_notice);
        jumpToContactBtn = (Button) findViewById(R.id.btn_contact);
        startMarkNameEdit = (EditText) findViewById(R.id.edit_start_wx_mark_name);
        delFlagSwitch = (Switch) findViewById(R.id.switch_del_flag);
        runFlagSwitch = (Switch) findViewById(R.id.switch_run_flag);
        openWxBtn = (Button) findViewById(R.id.btn_open_wx);
    }

    private void setListener() {
        jumpToSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this).setTitle("提示")//设置对话框标题
                        .setMessage("是否保留现有扫描结果")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveExistingData = true;
                                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);//跳转到辅助功能设置页
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveExistingData = false;
                                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);//跳转到辅助功能设置页
                                startActivity(intent);
                                finish();
                            }
                        })
                        .show(); // 在按键响应事件中显示此对话框

            }
        });

        jumpToContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        showNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this).setTitle("使用说明")//设置对话框标题
                        .setMessage("1 请赋予本软件辅助选项权限，之后打开微信即可\n" +
                                "2 请确保扫描过程中联网亮屏并不退出微信界面\n" +
                                "3 结果列表中会显示限制天数和疑似屏蔽的好友列表，疑似屏蔽是指发朋友圈少于两条的好友\n" +
                                "4 请在扫描结束后关闭辅助功能选项\n" +
                                "5 如果开启服务后没有反应，可尝试重启手机\n" +
                                "6 如果觉得app不错，记得来github上给star喔。微信测试版本：6.5.13\n" +
                                "7 后期会在github上更新，地址：https://github.com/PP8818/Detect_PengYouQuan_Shield\n" +
                                "8 本软件根据github开源代码修改完成，地址：https://github.com/PP8818/Detect_PengYouQuan_Shield"
                        ).show();//在按键响应事件中显示此对话框
            }
        });

        runFlagSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                config.setStartMarkName(startMarkNameEdit.getText().toString());
                if (runFlagSwitch.isChecked()) {
                    config.setRun(runFlagSwitch.isChecked());
                    config.setType(JobTypeEnum.START.getValue());
                } else {
                    config.setRun(runFlagSwitch.isChecked());
                    config.setType(JobTypeEnum.STOP.getValue());
                }
                config.setDeleteForbiddenVisitPengYouQuan(delFlagSwitch.isChecked());
                if (null == config.getId()) {
                    ConfigService.getInstance().add(config);
                } else {
                    ConfigService.getInstance().update(config);
                }
                PrintUtils.log("save", config.toJSONString());
                Toast.makeText(MainActivity.this, "修改保存成功", Toast.LENGTH_LONG).show();
            }
        });

        openWxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager packageManager = MainActivity.this.getPackageManager();
                Intent intent = packageManager.getLaunchIntentForPackage("com.tencent.mm");
                startActivity(intent);
            }
        });
    }

    private void initViewData() {
        PrintUtils.log("get", config.toJSONString());
        startMarkNameEdit.setText(config.getStartMarkName());
        delFlagSwitch.setChecked(config.getDeleteForbiddenVisitPengYouQuan());
        runFlagSwitch.setChecked(config.getRun());
    }
}
