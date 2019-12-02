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

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class PayActivity extends AppCompatActivity {
    final String apiBaseUrl = "https://kapi.kakao.com/v1/payment/";
    final String linkBaseUrl = "https://developers.kakao.com/";
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    String readyUrl;
    WebView webViewPay;
    Context mContext;
    String tid, itemCode, quantity, itemName, itemPrice, UID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        getSupportActionBar().hide();
        mContext = this;
        UID = getSharedPreferences("firebase_uid_pref", MODE_PRIVATE).getString("UID", "");
        Intent payIntent = getIntent();
        itemCode = payIntent.getStringExtra("itemCode");
        quantity = payIntent.getStringExtra("itemQuantity");
        itemName = payIntent.getStringExtra("itemName");
        itemPrice = payIntent.getStringExtra("itemPrice");

        // DB에서 상품 정보 가져오기
        webViewPay = findViewById(R.id.webview_pay);
        webViewPay.getSettings().setJavaScriptEnabled(true);
        readyUrl = apiBaseUrl + getString(R.string.kakao_pay_ready_url);

        ContentValues payData = new ContentValues();
        payData.put("cid", getString(R.string.kakao_pay_cid));
        payData.put("partner_order_id", getString(R.string.kakao_pay_partner_order_id));
        payData.put("partner_user_id", getString(R.string.kakao_pay_partner_user_id));
        payData.put("item_code", itemCode);
        payData.put("item_name", itemName);
        payData.put("quantity", quantity);
        payData.put("total_amount", itemPrice);
        payData.put("vat_amount", String.valueOf(Integer.valueOf(itemPrice) / 11));     // 부가세 10%
        payData.put("tax_free_amount", "0");
        payData.put("approval_url", linkBaseUrl + getString(R.string.kakao_pay_approval_url));
        payData.put("fail_url", linkBaseUrl + getString(R.string.kakao_pay_fail_url));
        payData.put("cancel_url", linkBaseUrl + getString(R.string.kakao_pay_cancel_url));

        // AsyncTask를 통해 HttpURLConnection 수행.
        NetworkTask networkTask = new NetworkTask(readyUrl, payData);
        networkTask.execute();


    }

    // 네트워크 통신 부분
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
                String exec = Integer.valueOf(itemCode) > 20 && Integer.valueOf(itemCode) <= 40? ".PNG" : ".jpg";
                database = database.child("user").child(UID).child("purchased").child(tid);
                database.child(itemCode).setValue(quantity);
                database.child("status").setValue("결제 완료");
                database.child("amount").setValue(itemPrice);
                database.child("thumbnail").setValue(itemCode + exec);
                database.child("title").setValue(itemName);
                // TODO: Intent에 extra 붙여서 결과 알려주기
                Intent intent = new Intent(mContext, PaySuccessActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

        }
    }

    // http response로 넘어온 url 이동 하는 부분
    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // kakaotalk 앱으로 결제할 경우
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
            }
            // approval 승인 받는 경우
            else{
                view.loadUrl(url);
                String pgToken = Uri.parse(webViewPay.getUrl()).getQueryParameter("pg_token");
                String approveUrl = apiBaseUrl + "approve";

                ContentValues approveData = new ContentValues();
                approveData.put("cid", getString(R.string.kakao_pay_cid));
                approveData.put("tid", tid);
                approveData.put("partner_order_id", getString(R.string.kakao_pay_partner_order_id));
                approveData.put("partner_user_id", getString(R.string.kakao_pay_partner_user_id));
                approveData.put("pg_token", pgToken);

                // 결제 승인
                NetworkTask nt = new NetworkTask(approveUrl, approveData);
                nt.execute();

            }
            view.loadUrl(url);
            return false;
        }
    }
}