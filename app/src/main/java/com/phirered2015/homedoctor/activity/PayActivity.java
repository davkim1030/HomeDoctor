package com.phirered2015.homedoctor.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.phirered2015.homedoctor.R;
import com.phirered2015.homedoctor.api.RequestHttpURLConnection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class PayActivity extends AppCompatActivity {
    final String apiBaseUrl = "https://kapi.kakao.com/v1/payment/";
    final String linkBaseUrl = "https://developers.kakao.com/";

    WebView webViewPay;
    Context mContext;
    String tid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        mContext = this;

        webViewPay = findViewById(R.id.webview_pay);

        String readyUrl = apiBaseUrl + getString(R.string.kakao_pay_ready_url);

        ContentValues payData = new ContentValues();
        payData.put("cid", getString(R.string.kakao_pay_cid));
        payData.put("partner_order_id", getString(R.string.kakao_pay_partner_order_id));
        payData.put("partner_user_id", getString(R.string.kakao_pay_partner_user_id));
        payData.put("item_name", "초코파이");
        payData.put("quantity", "1");
        payData.put("total_amount", "2200");
        payData.put("vat_amount", "200");
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
                Log.e("RESULT", result.get("android_app_scheme").toString().split("=")[1]);
                webViewPay.loadUrl(result.getString("next_redirect_mobile_url"));
                tid = result.getString("tid");
                // 카톡으로 넘어가는 부분
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(result.getString("android_app_scheme"))));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
