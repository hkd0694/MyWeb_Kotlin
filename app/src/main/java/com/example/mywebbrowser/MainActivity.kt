package com.example.mywebbrowser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        //기본값은 google 홈페이지로 등록한다.
        webView.loadUrl("http://www.google.com")
        
        //Context 메뉴 등록
        registerForContextMenu(webView)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item?.itemId) {
            R.id.action_google, R.id.action_home -> {
                webView.loadUrl("http://www.google.com")
                return true
            }
            R.id.action_naver -> {
                webView.loadUrl("http://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                webView.loadUrl("http://www.daum.net")
                return true
            }
            R.id.action_call -> {
                //암시적 인텐트
                // Intent 클래스에 정의된 액션 중 하나를 지정하는 것, Action_DIal 은 전화 다이얼을 입력해주는 액션이다.
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:010-6690-0694")
                //intent.resolveActivity() 메서드는 이 인텐트를 수행하는 액티비티가 있는지를 검사하여 반환한다.
                //null이 반환된다면 수행하는 액티비티가 없는 것. (전화 앱이 없는 태블릿 같은 경우)
                if(intent.resolveActivity(packageManager) != null) startActivity(intent)
                return true
            }
            R.id.action_send_text -> {
                sendSMS("031-123-4567",webView.url)
            }
            R.id.action_email -> {
                email("gksrudehd123@naver.com","좋은 사이트",webView.url)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    //뒤로가기 버튼
    //웹튜로 홈페이지를 이동하다가 뒤로가기를 누르게 되면 해당 웹페이지의 이전 페이지로 넘어가야하지만 앱이 종료된다.
    //그것을 방지하고자 override된 onBackPressed 함수 안에다가 만약 웹 페이지에서 뒤로갈 페이지가 있다면
    //해당 웹페이지 전으로 가고, 만약 이전 웹페이지가 없다면 앱을 종료시킨다.
    override fun onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack()
        } else super.onBackPressed()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context,menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_share -> {
                share(webView.url)
            }
            R.id.action_browser -> {
                browse(webView.url)
            }
        }
        return super.onContextItemSelected(item)
    }
}
