package com.upn.add;

import java.util.ArrayList;

import android.app.Application;

public class Global extends Application {
	
	ArrayList<String> lista = new ArrayList<String>();
	
	public void agregar_elemento(String elemento)
	{
		lista.add(elemento);
	}
	
	public ArrayList<String> retornar_lista()
	{
		return lista;
	}

}
