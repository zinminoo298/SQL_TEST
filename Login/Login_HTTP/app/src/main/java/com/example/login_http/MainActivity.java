package com.example.login_http;

 import android.os.Build;
 import android.os.StrictMode;
 import android.provider.Settings;
 import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

 import org.apache.http.HttpEntity;
 import org.apache.http.HttpResponse;
 import org.apache.http.HttpVersion;
 import org.apache.http.NameValuePair;
 import org.apache.http.client.HttpClient;
 import org.apache.http.client.entity.UrlEncodedFormEntity;
 import org.apache.http.client.methods.HttpPost;
 import org.apache.http.impl.client.DefaultHttpClient;
 import org.apache.http.message.BasicNameValuePair;
 import org.apache.http.params.BasicHttpParams;
 import org.apache.http.params.CoreProtocolPNames;
 import org.apache.http.params.HttpParams;
 import org.apache.http.util.EntityUtils;
 import org.w3c.dom.Entity;

 import java.util.ArrayList;
 import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

   public void doLogin (View v){

        if(Build.VERSION.SDK_INT>=10){
            StrictMode.ThreadPolicy policy = StrictMode.ThreadPolicy.LAX;
            StrictMode.setThreadPolicy(policy );
        }

        String email = ((EditText)findViewById(R.id.editTextEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();

//       Toast.makeText(getApplicationContext(), email +" " + password, Toast.LENGTH_SHORT).show();


       try{
           HttpParams params = new BasicHttpParams();
           params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

           HttpClient httpClient = new DefaultHttpClient(params);
           HttpPost httpPost = new HttpPost("https://teamdronex.com/auth/login");

           List<NameValuePair>pairs = new ArrayList<>();
           pairs.add(new BasicNameValuePair("email",email));
           pairs.add(new BasicNameValuePair("password",password));

           httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));

           HttpResponse response = httpClient.execute(httpPost);
           HttpEntity entity = response.getEntity();

           String responseString = EntityUtils.toString(entity);
           Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_SHORT).show();


       }
       catch(Exception e){
           Toast.makeText(getApplicationContext(),"NetWork Failed", Toast.LENGTH_SHORT).show();
       }
    }



}
