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
import io.reactivex.disposables.Disposable


class SimpleExampleActivity : AppCompatActivity() {

    companion object{
        val TAG = "SimpleExampleActivity"
    }

    private lateinit var btn: Button
    private lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.btn)
        textView = findViewById(R.id.textView)
        btn.setOnClickListener{
            doSomeWork()
        }
    }

    private fun doSomeWork(){

        // Observable.

        /*Observable.just("Football","Cricket")
            .subscribe(getObserver())*/

        // OR we can do
        // Observable with function

        getObservable().subscribe(getObserver())
    }

    private fun getObservable() : Observable<String>{

        return Observable.just("Cricket","Footbal")
    }

    private fun getObserver(): Observer<String> {
        return object : Observer<String>{

            override fun onSubscribe(d: Disposable) {

                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: String) {

                textView.append("onNext: value : $value")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " onNext : value : $value")

            }

            override fun onError(e: Throwable) {
                textView.append(" onError " + e.message)
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " onError " + e.message)
            }

            override fun onComplete() {
                textView.append(" onComplete ")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " onError ")
            }

        }
    }
}
