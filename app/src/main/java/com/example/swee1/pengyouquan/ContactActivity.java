package com.example.swee1.pengyouquan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.swee1.pengyouquan.domain.FriendBean;
import com.example.swee1.pengyouquan.domain.Job;
import com.example.swee1.pengyouquan.service.FriendService;
import com.example.swee1.pengyouquan.util.JobUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactActivity extends AppCompatActivity {
    ListView contactView;
    SimpleAdapter simpleAdapter;
    List<FriendBean> orginData = new ArrayList<>();
    List<Map<String, Object>> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactView = (ListView) findViewById(R.id.contacts);
        simpleAdapter = new SimpleAdapter(this, data, R.layout.item, new String[]{"contactName", "deletedDesc", "desc"}, new int[]{R.id.txt_contact_name, R.id.txt_deleted_desc, R.id.txt_desc});
        contactView.setAdapter(simpleAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        data.clear();
        data.addAll(dataAdapter(FriendService.getInstance().queryAll()));
        simpleAdapter.notifyDataSetChanged();
    }

    private List<Map<String, Object>> dataAdapter(List<FriendBean> list) {
        List<Map<String, Object>> data = new ArrayList<>();
        if (null == data) {
            return data;
        }
        for (FriendBean contact : list) {
            Map<String, Object> item = new HashMap<>();
            if (null != contact.getMarkName()) {
                item.put("contactName", contact.getMarkName() + "(" + contact.getUserId() + ")");
            } else {
                item.put("contactName", contact.getNickName() + "(" + contact.getUserId() + ")");
            }
            item.put("deletedDesc", contact.isForbiddenVisitPengYouQuan() ? "屏蔽" : "开放");
            item.put("desc", null != contact.getDesc() ? contact.getDesc() : "友谊小船还在继续...");

            data.add(item);
        }
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 为ActionBar扩展菜单项
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 处理动作按钮的点击事件
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_show_forbidden:
                data.clear();
                orginData = FriendService.getInstance().queryForbidden();
                data.addAll(dataAdapter(orginData));
                simpleAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_show_all:
                data.clear();
                orginData = FriendService.getInstance().queryAll();
                data.addAll(dataAdapter(orginData));
                simpleAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_export:
                showInputParamsDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static StringBuilder EDIT_URL_SB = new StringBuilder();
    private void showInputParamsDialog() {
        AlertDialog.Builder exportDialog = new AlertDialog.Builder(ContactActivity.this);
        final View dialogView = LayoutInflater.from(ContactActivity.this).inflate(R.layout.export_dialog,null);
        final EditText editUrl = (EditText) dialogView.findViewById(R.id.edit_url);
        if (EDIT_URL_SB.length() > 0) {
            editUrl.setText(EDIT_URL_SB.toString());
        }
        exportDialog.setTitle("导出配置");
        exportDialog.setView(dialogView);
        exportDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        exportDialog.setPositiveButton("导出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = editUrl.getText().toString();
                if (null == url || url.length() < 5) {
                    Toast.makeText(ContactActivity.this, "参数不合法，无法导出！", Toast.LENGTH_LONG);
                } else {
                    EDIT_URL_SB.setLength(0);
                    EDIT_URL_SB.append(url);
                    JobUtils.export(editUrl.getText().toString(), orginData);
                }
            }
        });
        exportDialog.show();
    }
}
