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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CancateExampleActivity : AppCompatActivity() {

    companion object{
        val TAG = "CancateExampleActivity"
    }

    private lateinit var btn: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        btn = findViewById(R.id.btn)
        textView = findViewById(R.id.textView)

        btn.setOnClickListener{

            doSomeWork()
        }
    }

    private fun doSomeWork(){

        // reference of Array
        //https://www.i-programmer.info/programming/other-languages/10896-the-programmers-guide-to-kotlin-arrays-a-strings.html
        //https://kotlinlang.org/docs/reference/basic-types.html

        val aString = arrayOf("A1","A2","A3","A4","A5")
        val bString = arrayOf("B1","B2","B3","B4","B5")

        //Reference of Cancat & Merge operators.

        //https://blog.mindorks.com/rxjava-operator-concat-vs-merge

        val aObservable = Observable.fromArray(*aString)
        val bObservable = Observable.fromArray(*bString)

        Observable.concat(aObservable, bObservable)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(getObserver())
       }

    private fun getObserver(): Observer<String>{

        return object : Observer<String>{

            override fun onSubscribe(d: Disposable) {

                textView.append("onSubscribe : ${d.isDisposed}")
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(MapExampleActivity.TAG, "onSubscribe : ${d.isDisposed}")
            }

            override fun onNext(str: String) {

                textView.append("onNext()")
                textView.append(AppConstant.LINE_SEPARATOR)
                textView.append(str)
                textView.append(AppConstant.LINE_SEPARATOR)

                Log.d(MapExampleActivity.TAG, "onNext : $str")
            }

            override fun onError(e: Throwable) {

                textView.append("onError : ${e.message}")
                textView.append(AppConstant.LINE_SEPARATOR)

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
