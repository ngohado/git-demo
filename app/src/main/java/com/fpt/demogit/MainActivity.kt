package com.fpt.demogit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fpt.demogit.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) return

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
    }
}

/**
 * bugfix/issue55
 *
 * feature/...
 *
 * refactor/....
 *
 * hotfix/...
 *
 * release/v1.0.0
 */