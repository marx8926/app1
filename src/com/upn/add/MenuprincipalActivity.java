package com.upn.add;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MenuprincipalActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	public void onNuevaOperacion(View v)
	{
		Intent i = new Intent(this,OperacionActivity.class);
		this.startActivity(i);
	}
	
	public void onHistorico(View v)
	{
		Intent i = new Intent(this,HistoricoActivity.class);
		this.startActivity(i);
	}

}
