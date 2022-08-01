package com.bnawan.gituwu.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bnawan.gituwu.R
import com.bnawan.gituwu.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.settingHeader.headerTitle.text = resources.getString(R.string.setting)
        setSupportActionBar(binding.settingHeader.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
            it.setDisplayShowTitleEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> true
        }
    }
}