# Kotlin Study (3/9) - 2019/10/31

Kotlin을 공부하기 위해 간단한 앱부터 복잡한 앱까지 만들어 봄으로써 Kotlin에 대해 하기!

총 9개의 앱 중 세 번째 앱

프로젝트명 : MyWebBrowser

기능

* 웹 페이지를 탐색한다.
  
* 홈 메뉴를 클릭하여 첫 페이지로 온다.
  
* 메뉴에는 검색 사이트와 개발자 정보가 표시된다.
  
* 페이지를 문자나 메일로 공유할 수 있다.

핵심 구성 요소

* WebView : 웹 페이지를 표시하는 뷰
  
* Option Menu : 상단 툴바에 표시하는 메뉴
  
* Context Menu : 뷰를 롱클릭하면 표시되는 메뉴

* Implicit(암시적) Intent : 문자, 이메일 보내기와 같이 미리 정의된 인텐트

라이브러리 설명

* Anko Library : 인텐트, 다이얼로그, 로그 등을 구현하는 데 도움이 되는 라이브러리

# Context Menu

특정 뷰를 길게 클릭하고 있으면 표시되는 메뉴를 Context(컨텍스트) 메뉴라고 한다.

1. 메뉴 리소스를 생성한다.
2. onCreateContextMenu() 메서드를 재정의하여 메뉴를 붙인다.
3. onContextItemSelected() 메서드를 재정의하고 메뉴를 선택했을 때의 분기를 처리한다.
4. refisterForContextMenu(View view) 메서드에 컨텍스트 메뉴가 표시되었으면 하는 뷰를 지정한다.

> #### Context 메뉴 작성

```Kotlin
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context,menu)
    }
```

위의 코드처럼 MainACtivity에 onCreateContextMenu() 함수를 override 해주고, menuInflater.inflate() 메서드를 통해 메뉴 리소스를 액티비티의 컨텍스트 메뉴로 사용한다.

> #### Context 메뉴 클릭 이벤트 처리

```kotlin

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

```

위의 코드는 컨텍스트를 등록한 View를 길게 누르게 되면 나오는 ContextItem 클릭 이벤트로 해당 itemID에 따라 해당 함수를 부르는 코드입니다.

>#### Context 메뉴를 표시할 뷰 등록

```kotlin


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Context 메뉴 등록
        registerForContextMenu(webView)

    }
}
```

registerForContextMenu() 메서드를 등록을 하게 되면 해당 뷰를 길게 클릭하면 컨텍스트 메뉴가 표시가 된다.

## MyWeb_Kotlin을 통해 배운 것들

### apply() 함수

apply() 함수는 블록에 자신의 객체를 리시버 객체로 전달하고, 자기 자신 객체가 반환된다. 주로 객체의 상태를 변환시키고자 할 때 사용한다.

```kotlin

    val result = car?.apply{
        car.setColor(Color.RED)
        car.SetPrice(1000)
    }
```

`MyWeb_Kotlin`에서 사용 예제

```kotlin
        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }
```

웹뷰를 사용할 땐 항상 두 가지의 설정이 필요하다.
1. `javaScriptEnabled` 기능을 킨다.
2. `WebViewClient` 클래스를 지정해야한다.

위의 코드에선 apply 함수를 사용하여 블록안에 webView 자기 자신이 들어가 웹뷰에 필요한 조건 2가지를 설정하고 있다. apply() 함수가 끝나면 webView는 웹뷰를 사용할 수 있는 조건이 만족하게 된다.


### 암시적(Implicit) 인텐트

안드로이드에는 미리 정의된 인텐트들이 있는데 이러한 것들을 암시적 인텐트라고 한다. 대표적으로 전화걸기 기능이 있다.

```kotlin

    //암시적 인텐트
    // Intent 클래스에 정의된 액션 중 하나를 지정하는 것, Action_DIal 은 전화 다이얼을 입력해주는 액션이다.
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:010-6690-0694")
    //intent.resolveActivity() 메서드는 이 인텐트를 수행하는 액티비티가 있는지를 검사하여 반환한다.
    //null이 반환된다면 수행하는 액티비티가 없는 것. (전화 앱이 없는 태블릿 같은 경우)
    if(intent.resolveActivity(packageManager) != null) startActivity(intent)

```

Action_DIAL 액션은 전화 다ㅣ얼을 입역해주는 액션으로 Intent 클래스에 정의된 액션 중 하나이다. 

intent.resolveActivity() 메서드는 이 인텐트가 수행하는 액티비티가 있는지를 검사하여 반환하는 것이다. null이 반환된다는 것은 수행하는 액티비티가 없다는 것인데 태블릿 같은 경우가 해당된다.


>### `Anko Library를 활용한 암시적 인텐트`

Anko라이브러리를 사용하면 복잡한 암시적 인텐트 기능들을 보다 더 간단하게 코드로 구현할 수 있습니다.

ex) 웹 브라우저 열기

```kotlin

    val intent = Intent(Intent.VIEW)
    intent.data = Uri.parse("http://www.google.com")
    if(intent.resolveActivity(packageManager) != null){
        startActivity(intent)
    }
```

위의 코드는 웹 브라우저를 열때 사용하는 암시적 인텐트 코드입니다. 이것을 Anko Library를 사용한다면 엄청나게 간단하게 구현할 수 있다.

```kotlin

    browser("http://www.google.com")
```

위 코드처럼 한줄이면 해결된다. 웹 브라우저를 여면 인텐트 말고 다른 사용 예제 들이다.

>### Anko Library를 이용한 암시적 인텐트 종류들

* 전화걸기      -> makeCall(전화번호)
  
* 문자 보내기   -> sendSMS(전화번호,[문자열])
  
* 웹 브라우저에서 열기 -> browser(url)
  
* 문자열 공유 -> share(문자열,[제목])
  
* 이메일 보내기 -> email(받는 메일주소, [제목],[내용])

`MyWeb_Kotlin`에서 사용 예제

```kotlin


 override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item?.itemId) {
            R.id.action_send_text -> {
                sendSMS("031-123-4567",webView.url)
            }
            R.id.action_email -> {
                email("gksrudehd123@naver.com","좋은 사이트",webView.url)
            }
        }

        return super.onOptionsItemSelected(item)
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

```