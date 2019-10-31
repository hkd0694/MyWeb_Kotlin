package com.example.mywebbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        webView.loadUrl("http://www.google.com")

        //EditText의 setOnEditorActionListener 에디트 텍스트가 선택되고 글자가 입력될 때마나다 호출된다.
        //인자로는 반응한 뷰, 액선 ID, 이벤트 세가지 이며, 여기서는 뷰와 이벤트를 사용하지 않기 때문에 _ 로 대치할 수 있다.

        urlEditText.setOnEditorActionListener { _, actionId, _ ->
            //actionID는 EditorInfo 클래스에 상수로 정의된 값 중에서 검색 버튼에 해당하는 상수와 비교하여 검색 버튼이 눌렸는지 확인한다.
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                webView.loadUrl(urlEditText.text.toString())
                true
            } else{
                false
            }
        }
    }
    //뒤로가기 버튼 구현
    //웹튜로 홈페이지를 이동하다가 뒤로가기를 누르게 되면 해당 웹페이지의 이전 페이지로 넘어가야하지만 앱이 종료된다.
    //그것을 방지하고자 override된 onBackPressed 함수 안에다가 만약 웹 페이지에서 뒤로갈 페이지가 있다면
    //해당 웹페이지 전으로 가고, 만약 이전 웹페이지가 없다면 앱을 종료시킨다.
    override fun onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack()
        } else super.onBackPressed()
    }
}
