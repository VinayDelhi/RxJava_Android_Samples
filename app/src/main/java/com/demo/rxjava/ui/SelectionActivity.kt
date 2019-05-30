
package com.demo.rxjava.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.demo.rxjava.R
import com.demo.rxjava.ui.networking.NetworkingActivity
import com.demo.rxjava.ui.search.SearchActivity

class SelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
    }

    fun startOperatorsActivity(view : View){

        startActivity(Intent(this@SelectionActivity, OperatorsActivity::class.java))
    }

    fun startNetworkingActivity(view : View){

        startActivity(Intent(this@SelectionActivity, NetworkingActivity::class.java))
    }

    fun startSearchActivity(view : View){

        startActivity(Intent(this@SelectionActivity, SearchActivity::class.java))
    }
}
