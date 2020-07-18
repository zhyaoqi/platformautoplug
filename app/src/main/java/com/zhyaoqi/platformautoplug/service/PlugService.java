package com.zhyaoqi.platformautoplug.service;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.zhyaoqi.platformautoplug.activity.SettingActivity;
import com.zhyaoqi.platformautoplug.util.SpUtils;

import java.util.List;
import java.util.Random;

public class PlugService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        if (rootNodeInfo == null){
            return;
        }

        String selectPlatformArr = SpUtils.getString(SettingActivity.SELECT_PLATFORM_ARRAY,"");
        if (selectPlatformArr.contains("快手极速版")){
            kuaishou(rootNodeInfo);
        }
        if (selectPlatformArr.contains("抖音极速版")){
            douyin(rootNodeInfo);
        }

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(this,"连接启动成功",Toast.LENGTH_LONG).show();
    }


    private void kuaishou(AccessibilityNodeInfo rootNodeInfo) {
        String kuaishouPackage = "com.kuaishou.nebula";
        if (!kuaishouPackage.equals(rootNodeInfo.getPackageName())){
            return;
        }
        List<AccessibilityNodeInfo> tabsNodes = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.kuaishou.nebula:id/tabs");
//
        if (checkNotEmpty(tabsNodes)) {
            AccessibilityNodeInfo childNode = tabsNodes.get(0);
            AccessibilityNodeInfo faxianNode = childNode.getChild(2);//找到发现这个节点

            faxianNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            sleep(2000,1000);

            List<AccessibilityNodeInfo> tupianNodes = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.kuaishou.nebula:id/open_long_atlas");
            //发现是  打开长图片
            if (checkNotEmpty(tupianNodes)) {
            } else {
                sleep(8000,8000);
            }
        }
    }

    private void douyin(AccessibilityNodeInfo rootNodeInfo){
        String douyinPackage = "com.ss.android.ugc.aweme.lite";
        if (!douyinPackage.equals(rootNodeInfo.getPackageName())){
            return;
        }

        AccessibilityNodeInfo nodes = getTheLastNode(rootNodeInfo,"首页");
        if (nodes != null) {
            try {
                long t = 12000+new Random().nextInt(9800);
                Thread.sleep(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nodes.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            nodes.recycle();
        }
    }



    private void sleep(int sleepTime){
        sleep(sleepTime,0);
    }


    private void sleep(int sleepTime,int sleepRandomTime){
        try {
            int randomTime = 0;
            if (sleepRandomTime>0){
                randomTime = new Random().nextInt(sleepRandomTime);
            }
            long t = sleepTime +randomTime;
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean checkNotEmpty(List list){
        return list!=null&& list.size()>0;
    }

    private AccessibilityNodeInfo getTheLastNode(AccessibilityNodeInfo rootNodeInfo,String... texts) {
        int bottom = 0;
        AccessibilityNodeInfo lastNode = null, tempNode;
        List<AccessibilityNodeInfo> nodes;

        for (String text : texts) {
            if (text == null) continue;

            nodes = rootNodeInfo.findAccessibilityNodeInfosByText(text);

            if (nodes != null && !nodes.isEmpty()) {
                tempNode = nodes.get(nodes.size() - 1);
                if (tempNode == null) return null;
                Rect bounds = new Rect();
                tempNode.getBoundsInScreen(bounds);
                if (bounds.bottom > bottom) {
                    bottom = bounds.bottom;
                    lastNode = tempNode;
                }
            }
        }
        return lastNode;
    }
}
