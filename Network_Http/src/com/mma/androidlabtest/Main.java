package com.mma.androidlabtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity {

	EditText et_output;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        et_output = (EditText)findViewById (R.id.et_output);
        Button btnDownload = (Button)findViewById (R.id.btnDownload);
        btnDownload.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EditText et_url = (EditText)findViewById (R.id.et_url);
				et_output.setText (getURL ("" + et_url.getText(), null));
			}
		});
    }
    
	public String getURL (String url, List <NameValuePair> headers) {
		try {
	    	if (headers != null){
	    		HttpPost httpPost = new HttpPost (new URI (url));
	    		httpPost.addHeader("pragma","no-cache");
	    		httpPost.setEntity(new UrlEncodedFormEntity (headers, HTTP.UTF_8));
		    	DefaultHttpClient httpClient = new DefaultHttpClient();
	    		HttpResponse httpResponse = httpClient.execute(httpPost);
		    	InputStream inputStream = httpResponse.getEntity().getContent();
		    	return inputStreamToString (inputStream);
		    } else {
	    		HttpGet httpGet = new HttpGet (new URI (url));
	    		httpGet.addHeader("pragma","no-cache");
		    	DefaultHttpClient httpClient = new DefaultHttpClient();
		    	HttpResponse httpResponse = httpClient.execute(httpGet);
		    	InputStream inputStream = httpResponse.getEntity().getContent();
		    	return inputStreamToString (inputStream);
		    }
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), 5000).show();
		}
		return "";
	}
	
	public String inputStreamToString (InputStream inputStream) {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader (inputStreamReader);
		StringBuilder stringBuilder = new StringBuilder();
		try { 
			String line;   
			while ((line = bufferedReader.readLine()) != null) {   
				stringBuilder.append(line).append("\n");  
			}  
			inputStream.close();
		} catch (IOException e) {
		}
		return stringBuilder.toString(); 
	}
}
