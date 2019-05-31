package com.demo.rxjava.ui.operator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.demo.rxjava.R
import com.demo.rxjava.utils.AppConstant
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class IntervalExampleActivity : AppCompatActivity() {

    //Reference : https://blog.mindorks.com/understanding-rxjava-timer-delay-and-interval-operators

    companion object{

        val TAG = "IntervalExampleActivity"
    }

    private lateinit var btn: Button
    private lateinit var textView: TextView
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        btn = findViewById(R.id.btn)
        textView = findViewById(R.id.textView)

        btn.setOnClickListener{
          doSomeWork()
        }
    }

    override fun onPause() {
        super.onPause()
        //compositeDisposable.dispose()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
    fun doSomeWork(){

        compositeDisposable = CompositeDisposable()

         val disposable =Observable.interval(0,2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())

         //compositeDisposable added DisposableObserver type observer
          //compositeDisposable.add(disposable)
    }

    private fun getObserver(): Observer<Long>{

        return object : Observer<Long>{
            override fun onSubscribe(d: Disposable) {

                textView.append("onNext() : ${d.isDisposed}")
                textView.append(AppConstant.LINE_SEPARATOR)
            }

            override fun onNext(value: Long) {

                textView.append("onNext()")
                textView.append(AppConstant.LINE_SEPARATOR)
                textView.append("value : $value")
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(MapExampleActivity.TAG, "onNext : $value")
            }

            override fun onError(e: Throwable) {
                textView.append("onError : ${e.message}")
                textView.append(AppConstant.LINE_SEPARATOR)

                Toast.makeText(this@IntervalExampleActivity, "Interval operator", Toast.LENGTH_SHORT).show()

                Log.d(MapExampleActivity.TAG, "onError : ${e.message}")
            }

            override fun onComplete() {
                textView.append("onComplete")
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(MapExampleActivity.TAG, "onComplete")
            }

        }
    }

    private fun getDisposableObserver(): DisposableObserver<Long>{

        return object : DisposableObserver<Long>(){

            override fun onNext(value: Long) {

                textView.append("onNext()")
                textView.append(AppConstant.LINE_SEPARATOR)
                textView.append("value : $value")
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(MapExampleActivity.TAG, "onNext : $value")
            }

            override fun onError(e: Throwable) {
                textView.append("onError : ${e.message}")
                textView.append(AppConstant.LINE_SEPARATOR)

                Toast.makeText(this@IntervalExampleActivity, "Interval operator", Toast.LENGTH_SHORT).show()

                Log.d(MapExampleActivity.TAG, "onError : ${e.message}")
            }

            override fun onComplete() {
                textView.append("onComplete")
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(MapExampleActivity.TAG, "onComplete")
            }

        }
    }
}
