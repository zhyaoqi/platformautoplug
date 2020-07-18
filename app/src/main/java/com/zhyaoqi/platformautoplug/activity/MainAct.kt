package com.zhyaoqi.platformautoplug.activity

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.accessibility.AccessibilityManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zhyaoqi.platformautoplug.R
import com.zhyaoqi.platformautoplug.util.SpUtils

class MainAct : AppCompatActivity(), AccessibilityManager.AccessibilityStateChangeListener {
    private var textStatus: TextView? = null
    private var imageStatus: ImageView? = null

    private var accessibilityManager: AccessibilityManager? = null

    /**
     * 获取 HongbaoService 是否启用状态
     *
     * @return
     */
    private val isServiceEnabled: Boolean
        get() {
            val accessibilityServices = accessibilityManager!!.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC)
            for (info in accessibilityServices) {
                if (info.id == "$packageName/.service.PlugService") {
                    return true
                }
            }
            return false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SpUtils.initSpUtil(this);

        textStatus = findViewById(R.id.text_status)

        imageStatus = findViewById(R.id.image_status)


        accessibilityManager = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        accessibilityManager!!.addAccessibilityStateChangeListener(this)
        updateServiceStatus()

    }

    fun startPlug(v: View) {
        try {
            Toast.makeText(this, "点击插件来开启！", Toast.LENGTH_SHORT).show()
            val accessibleIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(accessibleIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "遇到一些问题,请手动打开系统设置>无障碍服务>自动插件(ฅ´ω`ฅ)", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }

    }

    fun startSetting(v:View){
        startActivity(Intent(this,SettingActivity::class.java))
    }

    override fun onAccessibilityStateChanged(enabled: Boolean) {
        updateServiceStatus()
    }

    /**
     * 更新当前 HongbaoService 显示状态
     */
    private fun updateServiceStatus() {
        if (isServiceEnabled) {
            textStatus!!.text = "关闭插件"
            imageStatus!!.setBackgroundResource(R.mipmap.ic_stop)
        } else {
            textStatus!!.text = "开启插件"
            imageStatus!!.setBackgroundResource(R.mipmap.ic_start)
        }
    }
}
