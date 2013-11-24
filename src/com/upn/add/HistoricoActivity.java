package com.upn.add;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoricoActivity extends Activity {

	Intent intent;
	ArrayList<String> lista;
	ListView vlista;
	Global global;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historico);
		
		global = (Global) this.getApplication();
		
		vlista = (ListView) this.findViewById(R.id.listaHistorico);
		
	
		
		lista = global.retornar_lista();
		
		// Crear un adapter para mostrar la lista
        ArrayAdapter<String> arrayAdapter =      
        new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, lista);
        //Ponemos el adapter
        vlista.setAdapter(arrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.historico, menu);
		return true;
	}

	@Override
	public void onBackPressed()
	{
		finish();
	}
	
}
