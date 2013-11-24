package com.upn.add;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

	
	Button Login;	
	EditText Usuario, Pass;	
	String usuario_fin,pass_fin,read;	
	
	String ip = "http://192.168.1.35/servicio_android";

	
	public JSONArray jsonArray;
	public String resultado;
	
    private ProgressDialog dialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
        
      //variables de botenes
  		Login = (Button)this.findViewById(R.id.btnLogin);
  		
  		//variables para texto
  		Usuario = (EditText)this.findViewById(R.id.loginEmail);
  		Pass = (EditText)this.findViewById(R.id.loginPassword);
  		
  		read = new String();
  	 		
  		dialog = new ProgressDialog(this);
  		dialog.setMessage("Ingresando...");
  		dialog.setTitle("Progreso");
  		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
  		dialog.setCancelable(false);
  		
  		resultado = "";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void logeado(String estado)
	{
		Log.d("logeado", estado);
		if(estado.compareTo("200")==0)
		{
			Intent i = new Intent(this,MenuprincipalActivity.class);
    		
			//enviamos datos al sgte. activity
    		i.putExtra("user", Usuario.getText().toString()); 
    		i.putExtra("pass", Pass.getText().toString());
    		
    		this.startActivity(i);
		}
		else
		{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    		alertDialog.setTitle("Informacion");
    		alertDialog.setMessage("Usuario y/o Password invalidos");
    		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
    		   public void onClick(DialogInterface dialog, int which) {
    		        			   
    		   }
    		});

    		alertDialog.show();
		}
	}
	
	 public void onLogin(View view)
	 {
	     
	    	Log.d("Login", "logeo"+Usuario.getText().toString()+Pass.getText().toString());
	    	
	    	usuario_fin = Usuario.getText().toString();
	    	pass_fin = Pass.getText().toString();
	  
	    	
	    	String url = ip+"/login.php?usuario="+usuario_fin+"&password="+pass_fin;
	    	
	    	build(url);  	
	  }
	   	     
	    
	    public String readJSONFeed(String URL) {
			StringBuilder stringBuilder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(URL);
			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				Log.d("status",String.valueOf(statusCode));
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						stringBuilder.append(line);
					}
				} else {
					Log.e("JSON", "Failed to download file");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return stringBuilder.toString();
		}
		
		public void build(String URL)
		{
			new ReadJSONFeedTask(this).execute(URL);
			
			Log.d("async", resultado);
		}
		
	    public class ReadJSONFeedTask extends AsyncTask<String, Void, String> {
	    	
	    	WeakReference<MainActivity> context;
	    	  
	    	public ReadJSONFeedTask(MainActivity activity)
	    	{
	    		context = new WeakReference<MainActivity>(activity);
	    	}
	    	
	        protected String doInBackground(String... urls) {
	        	
	            resultado = readJSONFeed(urls[0]);
	            
	            Log.d("resultado", resultado);
	            return resultado;
	        }
	        
	        protected void onPreExecute()
	        {
	        	dialog.setProgress(0);
	        	dialog.setMax(100);
	        	dialog.show();
	        }
	        protected void onPostExecute(String result) {
	              		
	        	if(result!=null)
	        	{
	        		resultado=result;
	        		MainActivity act = context.get();      		
	        		String id = new String();   	
	        		Log.d("resultado2", result);
	        	try {
	        		
	        		JSONObject topobj = new JSONObject(result);
	        		id = topobj.getString("mensaje");
	    			
	    			act.logeado(id); 
	    			
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	        
	        		  
	        	}
	        	else
	        	{
	        		jsonArray = new JSONArray();
	    			Log.i("JSON", "Number of elements: " + 
	    					jsonArray.length());
	        	}
	        	
	        	
	        	dialog.dismiss();
	        }
	    }
	    

}
