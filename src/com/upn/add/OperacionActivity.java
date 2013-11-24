package com.upn.add;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.upn.add.MainActivity.ReadJSONFeedTask;

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

public class OperacionActivity extends Activity {
	
	String num1, num2;
	EditText enum1, enum2;
	
	 private ProgressDialog dialog;
	 
	 String ip = "http://192.168.1.35/servicio_android";
	 
	String memory = "";
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	
	Global global;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_operacion);
		
		enum1 = (EditText)this.findViewById(R.id.numero1);
		enum2 = (EditText)this.findViewById(R.id.numero2);
		
	
		global = (Global)this.getApplication();
		
		dialog = new ProgressDialog(this);
  		dialog.setMessage("Calculando...");
  		dialog.setTitle("Progreso");
  		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
  		dialog.setCancelable(false);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.operacion, menu);
		return true;
	}

	public void onSumar(View v)
	{
		num1 = enum1.getText().toString();
		num2 = enum2.getText().toString();
		
		if(num1.matches("\\d+") && num2.matches("\\d+"))
		{			
			Date date = new Date();
			
			String url = ip+"/sumar.php?valor1="+num1+"&valor2="+num2;	
			
			memory = "\t\t\t\t\t\t"+ dateFormat.format(date);		
			
			//agregamos elemento a la lista del objeto aplicacion
			global.agregar_elemento(memory);			
			memory = num1+"+"+num2;		
			global.agregar_elemento(memory);
			
	    	build(url); 
		}
		else
		{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    		alertDialog.setTitle("Informacion");
    		alertDialog.setMessage("Datos invalidos");
    		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
    		   public void onClick(DialogInterface dialog, int which) {
    		        			   
    		   }
    		});

    		alertDialog.show();
		}
	}
	
	public void onRestar(View v)
	{
		num1 = enum1.getText().toString();
		num2 = enum2.getText().toString();
		
		if(num1.matches("\\d+") && num2.matches("\\d+"))
		{
			String url = ip+"/restar.php?valor1="+num1+"&valor2="+num2;
			Date date = new Date();
			memory = "\t\t\t\t\t\t"+ dateFormat.format(date);		
			global.agregar_elemento(memory);
			
			memory = num1+"-"+num2;		
			global.agregar_elemento(memory);
			
	    	build(url); 
		}
		else
		{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    		alertDialog.setTitle("Informacion");
    		alertDialog.setMessage("Datos invalidos");
    		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
    		   public void onClick(DialogInterface dialog, int which) {
    		        			   
    		   }
    		});

    		alertDialog.show();
		}
	}
	
	public void onMultiplicar(View v)
	{
		num1 = enum1.getText().toString();
		num2 = enum2.getText().toString();
		
		if(num1.matches("\\d+") && num2.matches("\\d+"))
		{
			String url = ip+"/multiplicar.php?valor1="+num1+"&valor2="+num2;
			
			Date date = new Date();			
			memory = "\t\t\t\t\t\t"+ dateFormat.format(date);		
			global.agregar_elemento(memory);
			
			memory = num1+"x"+num2;		
			global.agregar_elemento(memory);
			
	    	build(url); 
	    	
	    	
		}
		else
		{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    		alertDialog.setTitle("Informacion");
    		alertDialog.setMessage("Datos invalidos");
    		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
    		   public void onClick(DialogInterface dialog, int which) {
    		        			   
    		   }
    		});

    		alertDialog.show();
		}
	}
	
	public void onDividir(View v)
	{
		num1 = enum1.getText().toString();
		num2 = enum2.getText().toString();
		
		if(num1.matches("\\d+") && num2.matches("\\d+") && num2.compareTo("0")!=0)
		{
			String url = ip+"/dividir.php?valor1="+num1+"&valor2="+num2;
			
			Date date = new Date();			
			memory = "\t\t\t\t\t\t"+ dateFormat.format(date);		
			global.agregar_elemento(memory);
			
			memory = num1+"/"+num2;		
			global.agregar_elemento(memory);
			
	    	build(url); 
		}
		else
		{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    		alertDialog.setTitle("Informacion");
    		alertDialog.setMessage("Datos invalidos");
    		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
    		   public void onClick(DialogInterface dialog, int which) {
    		        			   
    		   }
    		});

    		alertDialog.show();
		}
	}
	
	public void onHistorico(View v)
	{
		Intent i = new Intent(this,HistoricoActivity.class);
		
		
		this.startActivity(i);
		
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
		
		
	}
	
    public class ReadJSONFeedTask extends AsyncTask<String, Void, String> {
    	
    	WeakReference<OperacionActivity> context;
    	  
    	public ReadJSONFeedTask(OperacionActivity activity)
    	{
    		context = new WeakReference<OperacionActivity>(activity);
    	}
    	
        protected String doInBackground(String... urls) {
        	
            String resultado = readJSONFeed(urls[0]);
            
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
        		
        		OperacionActivity act = context.get();      		
        		String id = new String();   	
        		Log.d("resultado2", result);
        	try {
        		
        		JSONObject topobj = new JSONObject(result);
        		id = topobj.getString("total");
    			
    			//act.logeado(id); 
        		Log.d("total", id);
    			
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        
        		  
        	}
        	else
        	{
        		
        	}
        	
        	
        	dialog.dismiss();
        }
    }
    
    
}
