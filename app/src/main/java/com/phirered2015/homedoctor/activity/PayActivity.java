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

import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.api.RequestHttpURLConnection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class PayActivity extends AppCompatActivity {
    final String apiBaseUrl = "https://kapi.kakao.com/v1/payment/";
    final String linkBaseUrl = "https://developers.kakao.com/";
    String readyUrl;
    WebView webViewPay;
    Context mContext;
    String tid;
    int state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        getSupportActionBar().hide();
        mContext = this;
        state = 0;

        webViewPay = findViewById(R.id.webview_pay);
        webViewPay.getSettings().setJavaScriptEnabled(true);
        readyUrl = apiBaseUrl + getString(R.string.kakao_pay_ready_url);

        ContentValues payData = new ContentValues();
        payData.put("cid", getString(R.string.kakao_pay_cid));
        payData.put("partner_order_id", getString(R.string.kakao_pay_partner_order_id));
        payData.put("partner_user_id", getString(R.string.kakao_pay_partner_user_id));
        payData.put("item_name", "iPhone 11 블랙 64GB");
        payData.put("quantity", "1");
        payData.put("total_amount", "1100");
        payData.put("vat_amount", "100");
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
            try {
                result = new JSONObject(s);
                webViewPay.loadUrl(result.getString("next_redirect_app_url"));
                webViewPay.setWebChromeClient(new WebChromeClient());
                webViewPay.setWebViewClient(new WebViewClientClass());

                tid = result.getString("tid");
                state = 1;
                JSONObject next = new JSONObject();
                // 카톡으로 넘어가는 부분
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(result.getString("android_app_scheme"))));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
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
                // 여기서 디비에 저장하는 코드 필요

            }
            view.loadUrl(url);
            return false;
        }
    }
}