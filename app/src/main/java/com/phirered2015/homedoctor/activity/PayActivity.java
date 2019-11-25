package com.phirered2015.homedoctor.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.api.RequestHttpURLConnection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class PayActivity extends AppCompatActivity {
    final String apiBaseUrl = "https://kapi.kakao.com/v1/payment/";
    final String linkBaseUrl = "https://developers.kakao.com/";
    DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("user");
    FirebaseAuth mAuth;
    String readyUrl;
    WebView webViewPay;
    Context mContext;
    String tid;
    final String itemCode = "001"; //TODO: 이거 Intent 넘어오는 값으로 바꿔야 함
    final String quantity = "1"; //TODO: 이거 Intent 넘어오는 값으로 바꿔야 함

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        getSupportActionBar().hide();
        mContext = this;
        mAuth = FirebaseAuth.getInstance();
        // TODO: 로그인 정보는 앱 세션을 통해 넘겨야 함
        mAuth.signInWithEmailAndPassword("davkim1030@gmail.com", "431012");

        webViewPay = findViewById(R.id.webview_pay);
        webViewPay.getSettings().setJavaScriptEnabled(true);
        readyUrl = apiBaseUrl + getString(R.string.kakao_pay_ready_url);

        ContentValues payData = new ContentValues();
        // TODO: Intent로 http body 추가 필요
        payData.put("cid", getString(R.string.kakao_pay_cid));
        payData.put("partner_order_id", getString(R.string.kakao_pay_partner_order_id));
        payData.put("partner_user_id", getString(R.string.kakao_pay_partner_user_id));
        payData.put("item_code", itemCode);
        payData.put("item_name", "의자");
        payData.put("quantity", quantity);
        payData.put("total_amount", "22000");
        payData.put("vat_amount", "2000");
        payData.put("tax_free_amount", "0");
        payData.put("approval_url", linkBaseUrl + getString(R.string.kakao_pay_approval_url));
        payData.put("fail_url", linkBaseUrl + getString(R.string.kakao_pay_fail_url));
        payData.put("cancel_url", linkBaseUrl + getString(R.string.kakao_pay_cancel_url));

        // AsyncTask를 통해 HttpURLConnection 수행.
        NetworkTask networkTask = new NetworkTask(readyUrl, payData);
        networkTask.execute();
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            JSONObject result;
            // ready api의 경우
            if(s.contains("app_url")){
                try {
                    result = new JSONObject(s);                                             // JSON 데이터 파싱
                    webViewPay.loadUrl(result.getString("next_redirect_app_url"));  // 결제창으로 webView redirect
                    webViewPay.setWebChromeClient(new WebChromeClient());                   // 네이티브 웹으로 켜지 말고 앱 내에서 웹 구현
                    webViewPay.setWebViewClient(new WebViewClientClass());

                    tid = result.getString("tid");                                  // 결제 번호 저장
                    // 카톡으로 넘어가는 부분
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(result.getString("android_app_scheme"))));
                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
            // approve api의 경우
            else if(s.contains("aid")){

                database = database.child(mAuth.getUid()).child("purchased").child(tid);
                database.child("product_id").setValue(itemCode);
                database.child("quantity").setValue(quantity);
                database.child("status").setValue("결제 완료");
                // TODO: Intent에 extra 붙여서 결과 알려주기
                startActivity(new Intent(mContext, PaySuccessActivity.class));
                finish();
            }

        }
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null && url.startsWith("intent://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                    if (existPackage != null) {
                        startActivity(intent);
                    }
                    return true;
                }catch (Exception e) {
                    e.printStackTrace();
                }
            } else{
                view.loadUrl(url);
                String pgToken = Uri.parse(webViewPay.getUrl()).getQueryParameter("pg_token");
                String approveUrl = apiBaseUrl + "approve";

                ContentValues approveData = new ContentValues();
                approveData.put("cid", getString(R.string.kakao_pay_cid));
                approveData.put("tid", tid);
                approveData.put("partner_order_id", getString(R.string.kakao_pay_partner_order_id));
                approveData.put("partner_user_id", getString(R.string.kakao_pay_partner_user_id));
                approveData.put("pg_token", pgToken);

                // AsyncTask를 통해 HttpURLConnection 수행.
                NetworkTask nt = new NetworkTask(approveUrl, approveData);
                nt.execute();

            }
            view.loadUrl(url);
            return false;
        }
    }
}