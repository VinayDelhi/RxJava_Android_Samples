package com.demo.rxjava.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.demo.rxjava.R
import com.demo.rxjava.ui.operator.*

class OperatorsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opretors)
    }

    fun startSimpleActivity(view: View){

        startActivity(Intent(this@OperatorsActivity, SimpleExampleActivity::class.java))
    }

    fun startMapActivity(view: View){

        startActivity(Intent(this@OperatorsActivity, MapExampleActivity::class.java))
    }

    fun startZipActivity(view: View){

        startActivity(Intent(this@OperatorsActivity, ZipExampleActivity::class.java))
    }

    fun startTimerActivity(view: View){

        startActivity(Intent(this@OperatorsActivity, TimerExampleActivity::class.java))
    }

    fun startFilterActivity(view: View){

        startActivity(Intent(this@OperatorsActivity, FilterExampleActivity::class.java))
    }

    fun startConcatActivity(view: View){

        startActivity(Intent(this@OperatorsActivity, CancateExampleActivity::class.java))
    }

    fun startMergeActivity(view: View){

        startActivity(Intent(this@OperatorsActivity, MergeExampleActivity::class.java))
    }

    fun startDelayActivity(view: View){

        startActivity(Intent(this@OperatorsActivity, DelayExampleActivity::class.java))
    }

    fun startIntervalActivity(view: View){

        startActivity(Intent(this@OperatorsActivity, IntervalExampleActivity::class.java))
    }

}
