
package com.atze.lump

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.atze.lump.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DashboardFragment())
            .commitNow()

        bind.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_dashboard -> supportFragmentManager.beginTransaction().replace(R.id.container, DashboardFragment()).commit()
                R.id.nav_trade -> supportFragmentManager.beginTransaction().replace(R.id.container, TradeFragment()).commit()
                R.id.nav_chat -> supportFragmentManager.beginTransaction().replace(R.id.container, ChatFragment()).commit()
            }
            true
        }
    }
}
