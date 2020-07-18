package com.zhyaoqi.platformautoplug.activity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.zhyaoqi.platformautoplug.R;
import com.zhyaoqi.platformautoplug.util.SpUtils;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    public static final String SELECT_PLATFORM_ARRAY = "SELECT_PLATFORM_ARRAY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        ArrayList<String> platformList = new ArrayList<>();
        platformList.add("快手极速版");
        platformList.add("抖音极速版");

        LinearLayout layoutPlatformCheckbox = findViewById(R.id.layout_platform_checkbox);

        for (final String platform:platformList){
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(platform);
            layoutPlatformCheckbox.addView(checkBox);

            String selectPlatformArr = SpUtils.getString(SELECT_PLATFORM_ARRAY,"");
            if (selectPlatformArr.contains(platform)){
                checkBox.setChecked(true);
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String selectPlatformArr = SpUtils.getString(SELECT_PLATFORM_ARRAY,"");
                    if (isChecked){
                        if (!selectPlatformArr.contains(platform)){
                            String finalStr = selectPlatformArr+","+platform;
                            SpUtils.putString(SELECT_PLATFORM_ARRAY,selectPlatformArr+","+platform);
                        }
                    }else{
                        if (selectPlatformArr.contains(platform)){
                            String finalStr = selectPlatformArr.replace(","+platform,"");
                            SpUtils.putString(SELECT_PLATFORM_ARRAY,selectPlatformArr.replace(","+platform,""));
                        }
                    }
                }
            });
        }

    }
}
