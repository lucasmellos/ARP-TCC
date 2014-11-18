package com.example.arpbeta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;



import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;





import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources.Theme;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ipcartaosdac extends Activity{
 

	
	
	
	
 //Internet status flag
 Boolean isConnectionExist = false;

 // Connection detector class

 
@Override
protected void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.ipcartaosd);
	//declaração dos botões 
	final EditText textsalva =(EditText)findViewById(R.id.editText1);
	final TextView WifiState=(TextView)findViewById(R.id.textView1);
	Button btn=(Button)findViewById(R.id.button1);
	Button btn2=(Button)findViewById(R.id.button2);
	
	

	
	
	
	
	
	//area dos botões 
	btn2.setOnClickListener(new View.OnClickListener() {
		
		
		//botão voltar
		@Override
		public void onClick(View v) {
		
		
			Intent intent = new Intent();
            intent.setClass(ipcartaosdac.this, MainActivity.class);

            startActivity(intent);

            finish();
	
	}
		
	
	
	});
	

	
	btn.setOnClickListener(new View.OnClickListener() {
		//botão gravar
		@Override
		public void onClick(View v) {
		gravando();	
			
		}

		private void gravando() {
			
			String lstrNomeArq;
		     File arq;
		     byte[] dados;
		      try
		      {
		          lstrNomeArq = "IP.txt";
		             
		arq = new File(Environment.getExternalStorageDirectory(), lstrNomeArq);
		          FileOutputStream fos;
		             
		dados = textsalva.getText().toString().getBytes();
		             
		          fos = new FileOutputStream(arq);
		          fos.write(dados);
		          fos.flush();
		          fos.close();
		          Mensagem("Texto Salvo com sucesso!");
		         
		      } 
		      catch (Exception e) 
		      {
		        Mensagem("Erro : " + e.getMessage());
		        
		      }        
		}
		
		
		
		
		
		
		//mensagem de aviso
		private void Mensagem(String msg) 
		{
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
		} 
	});
}
}
