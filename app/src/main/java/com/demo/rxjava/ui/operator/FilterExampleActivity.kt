package com.demo.rxjava.ui.operator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.demo.rxjava.R
import com.demo.rxjava.utils.AppConstant
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FilterExampleActivity : AppCompatActivity() {

    companion object{
        val TAG = "FilterExampleActivity"
    }

    private lateinit var btn: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        btn = findViewById<Button>(R.id.btn).also{
            it.setOnClickListener{view ->
                doSomeWork()
            }
        }
        textView = findViewById(R.id.textView)

      }

    fun doSomeWork(){

        Observable.just(1,2,3,4,5,6)
            .filter{value ->

                return@filter value % 2 == 0
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())
    }

    fun getObserver() : Observer<Int>{

        return object : Observer<Int>{

            override fun onSubscribe(d: Disposable) {

                textView.append("onSubscribe : ${d.isDisposed}")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG," onSubscribe ${d.isDisposed}")
            }

            override fun onNext(value: Int) {

                textView.append("onNext")
                textView.append(AppConstant.LINE_SEPARATOR)
                textView.append("value : $value")
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(TAG," onSubscribe $value")
            }

            override fun onError(e: Throwable) {

                textView.append("onError : ${e.message}")
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(TAG," onError ${e.message}")
            }

            override fun onComplete() {
                textView.append(" onComplete ")
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(TAG," onComplete ")
            }

        }
    }
}
