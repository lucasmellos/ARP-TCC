package com.example.arpbeta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	public void LerArquivo() {
		String lstrNomeArq;
		File arq;
		String lstrlinha;
		try {
			lstrNomeArq = "IP.txt";

			arq = new File(Environment.getExternalStorageDirectory(),
					lstrNomeArq);
			BufferedReader br = new BufferedReader(new FileReader(arq));

			lstrlinha = br.readLine();

			// Mensagem("Texto Carregado com sucesso!");

		} catch (Exception e) {
			Mensagem("Va para as configurações gravar seu ip");
		}
	}

	// public static final String SERVERIP = "192.168.1.101"; // 'Within' the
	// emulator!
	public static final int SERVERPORT = 4444;
	 final char CHR0 = 0;
	 String finalprotocolo = "" + CHR0 + "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LerArquivo();
		Button btn = (Button) findViewById(R.id.button1);
		Button btn2 = (Button) findViewById(R.id.button2);
		Button btn3 = (Button) findViewById(R.id.button3);
		Button btn6 = (Button) findViewById(R.id.btnconfig);
		///////////////////////////////////////////////
	//	Button btn4 = (Button) findViewById(R.id.button4);
		
	//	Button btnMenu = (Button) findViewById(R.id.btnmenu);
		//Button btnDireita = (Button) findViewById(R.id.btndireita);
		//Button btnEsquerda = (Button) findViewById(R.id.btnesquerda);
		//Button btnBaixo = (Button) findViewById(R.id.btnbaixo);
	//	Button btnCima = (Button) findViewById(R.id.btncima);
	//	Button btnEnter = (Button) findViewById(R.id.btnenter);
		////////////////////////////////////////////////////
		// ir para pagina de configuraçoes

		btn6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ipcartaosdac.class);

				startActivity(intent);

				finish();
			}
		});

		// area dos bot�es

		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new Thread(new Client()).start();

			}
		});

		btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new Thread(new Client2()).start();
			}
		});

		btn3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Client3()).start();

			}
		});
		
		/////////////////////////////////////////////////
	/*	btn4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Client4()).start();

			}
		});

		btnMenu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Menuclass()).start();

			}
		});

		btnEsquerda.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Esquerdaclass()).start();

			}
		});

		btnDireita.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Direitaclass()).start();

			}
		});

		btnEnter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Enterclass()).start();

			}
		});

		btnCima.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Cimaclass()).start();

			}
		});

		btnBaixo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Baixoclass()).start();

			}
			
		});*//////////////////////////////////////////////////////////

	}

	// /

	// area das classes para o envio das mensagens

	public class Client implements Runnable {
		@Override
		public void run() {

			try {
				String lstrNomeArq;
				File arq;
				String SERVERIP;
				// try {
				lstrNomeArq = "IP.txt";

				arq = new File(Environment.getExternalStorageDirectory(),
						lstrNomeArq);
				BufferedReader br = new BufferedReader(new FileReader(arq));

				SERVERIP = br.readLine();
				// pegando ip na caixa de texto

				InetAddress serverAddr = InetAddress.getByName(SERVERIP);
				DatagramSocket socket = new DatagramSocket();
				byte[] buf = "/ARP/relay/0013A20040A13917/0/0".getBytes();

				DatagramPacket packet = new DatagramPacket(buf, buf.length,
						serverAddr, SERVERPORT);

				socket.send(packet);

			} catch (Exception e) {

			}
		}
	}

	public class Client2 implements Runnable {

		@Override
		public void run() {

			try {
				String lstrNomeArq;
				File arq;
				String SERVERIP;
				// try {
				lstrNomeArq = "IP.txt";

				arq = new File(Environment.getExternalStorageDirectory(),
						lstrNomeArq);
				BufferedReader br = new BufferedReader(new FileReader(arq));

				SERVERIP = br.readLine();
				// pegando ip na caixa de texto
				InetAddress serverAddr = InetAddress.getByName(SERVERIP);
				DatagramSocket socket = new DatagramSocket();
				byte[] buf = "/ARP/relay/0013A20040A13917/0/1".getBytes();

				DatagramPacket packet = new DatagramPacket(buf, buf.length,
						serverAddr, SERVERPORT);

				socket.send(packet);

			} catch (Exception e) {

			}

		}

	}

	public class Client3 implements Runnable {

		@Override
		public void run() {
			try {
				String lstrNomeArq;
				File arq;
				String SERVERIP;
				// try {
				lstrNomeArq = "IP.txt";

				arq = new File(Environment.getExternalStorageDirectory(),
						lstrNomeArq);
				BufferedReader br = new BufferedReader(new FileReader(arq));

				SERVERIP = br.readLine();
				// pegando ip na caixa de texto
				InetAddress serverAddr = InetAddress.getByName(SERVERIP);
				DatagramSocket socket = new DatagramSocket();
				byte[] buf = "/ARP/relay/0013A20040A13917/1/0".getBytes();

				DatagramPacket packet = new DatagramPacket(buf, buf.length,
						serverAddr, SERVERPORT);

				socket.send(packet);

			} catch (Exception e) {

			}

		}

	}

	public class Client4 implements Runnable {

		@Override
		public void run() {
			try {
				String lstrNomeArq;
				File arq;
				String SERVERIP;
				// try {
				lstrNomeArq = "IP.txt";

				arq = new File(Environment.getExternalStorageDirectory(),
						lstrNomeArq);
				BufferedReader br = new BufferedReader(new FileReader(arq));

				SERVERIP = br.readLine();
				// pegando ip na caixa de texto
				InetAddress serverAddr = InetAddress.getByName(SERVERIP);
				DatagramSocket socket = new DatagramSocket();
				byte[] buf = "/ARP/relay/0013A20040A13917/1/1".getBytes();

				DatagramPacket packet = new DatagramPacket(buf, buf.length,
						serverAddr, SERVERPORT);

				socket.send(packet);

			} catch (Exception e) {

			}

		}

	}

	public class Client5 implements Runnable {

		@Override
		public void run() {
			try {
				String lstrNomeArq;
				File arq;
				String SERVERIP;
				// try {
				lstrNomeArq = "IP.txt";

				arq = new File(Environment.getExternalStorageDirectory(),
						lstrNomeArq);
				BufferedReader br = new BufferedReader(new FileReader(arq));

				SERVERIP = br.readLine();
				// pegando ip na caixa de texto
				InetAddress serverAddr = InetAddress.getByName(SERVERIP);
				DatagramSocket socket = new DatagramSocket();
				byte[] buf = "/ARP/relay/0013A20040A13917/1/0".getBytes();

				DatagramPacket packet = new DatagramPacket(buf, buf.length,
						serverAddr, SERVERPORT);

				socket.send(packet);

			} catch (Exception e) {
				Mensagem("erro" + e.getMessage());
			}

		}

	}

	public class Menuclass implements Runnable {

		@Override
		public void run() {
			try {
				String lstrNomeArq;
				File arq;
				String SERVERIP;
				// try {
				lstrNomeArq = "IP.txt";

				arq = new File(Environment.getExternalStorageDirectory(),
						lstrNomeArq);
				BufferedReader br = new BufferedReader(new FileReader(arq));

				SERVERIP = br.readLine();
				// pegando ip na caixa de texto
				InetAddress serverAddr = InetAddress.getByName(SERVERIP);
				DatagramSocket socket = new DatagramSocket();
				byte[] buf = ("/ARP/IR/enviar/LigaTV"+finalprotocolo).getBytes();

				DatagramPacket packet = new DatagramPacket(buf, buf.length,
						serverAddr, SERVERPORT);

				socket.send(packet);

			} catch (Exception e) {
				Mensagem("erro" + e.getMessage());
			}

		}

	}

	public class Direitaclass implements Runnable {

		@Override
		public void run() {
			try {
				String lstrNomeArq;
				File arq;
				String SERVERIP;
				// try {
				lstrNomeArq = "IP.txt";

				arq = new File(Environment.getExternalStorageDirectory(),
						lstrNomeArq);
				BufferedReader br = new BufferedReader(new FileReader(arq));

				SERVERIP = br.readLine();
				// pegando ip na caixa de texto
				InetAddress serverAddr = InetAddress.getByName(SERVERIP);
				DatagramSocket socket = new DatagramSocket();
				byte[] buf = ("/ARP/IR/enviar/Direita"+finalprotocolo).getBytes();

				DatagramPacket packet = new DatagramPacket(buf, buf.length,
						serverAddr, SERVERPORT);

				socket.send(packet);

			} catch (Exception e) {
				Mensagem("erro" + e.getMessage());
			}

		}

	}

	public class Esquerdaclass implements Runnable {

		@Override
		public void run() {
			try {
				String lstrNomeArq;
				File arq;
				String SERVERIP;
				// try {
				lstrNomeArq = "IP.txt";

				arq = new File(Environment.getExternalStorageDirectory(),
						lstrNomeArq);
				BufferedReader br = new BufferedReader(new FileReader(arq));

				SERVERIP = br.readLine();
				// pegando ip na caixa de texto
				InetAddress serverAddr = InetAddress.getByName(SERVERIP);
				DatagramSocket socket = new DatagramSocket();
				byte[] buf = ("/ARP/IR/enviar/Esquerda"+finalprotocolo).getBytes();

				DatagramPacket packet = new DatagramPacket(buf, buf.length,
						serverAddr, SERVERPORT);

				socket.send(packet);

			} catch (Exception e) {
				Mensagem("erro" + e.getMessage());
			}

		}

	}

	public class Cimaclass implements Runnable {

		@Override
		public void run() {
			try {
				String lstrNomeArq;
				File arq;
				String SERVERIP;
				// try {
				lstrNomeArq = "IP.txt";

				arq = new File(Environment.getExternalStorageDirectory(),
						lstrNomeArq);
				BufferedReader br = new BufferedReader(new FileReader(arq));

				SERVERIP = br.readLine();
				// pegando ip na caixa de texto
				InetAddress serverAddr = InetAddress.getByName(SERVERIP);
				DatagramSocket socket = new DatagramSocket();
				byte[] buf = ("/ARP/IR/enviar/Cima"+finalprotocolo).getBytes();

				DatagramPacket packet = new DatagramPacket(buf, buf.length,
						serverAddr, SERVERPORT);

				socket.send(packet);

			} catch (Exception e) {
				Mensagem("erro" + e.getMessage());
			}

		}

	}

	public class Baixoclass implements Runnable {

		@Override
		public void run() {
			try {
				String lstrNomeArq;
				File arq;
				String SERVERIP;
				// try {
				lstrNomeArq = "IP.txt";

				arq = new File(Environment.getExternalStorageDirectory(),
						lstrNomeArq);
				BufferedReader br = new BufferedReader(new FileReader(arq));

				SERVERIP = br.readLine();
				// pegando ip na caixa de texto
				InetAddress serverAddr = InetAddress.getByName(SERVERIP);
				DatagramSocket socket = new DatagramSocket();
				byte[] buf = ("/ARP/IR/enviar/Baixo"+finalprotocolo).getBytes();

				DatagramPacket packet = new DatagramPacket(buf, buf.length,
						serverAddr, SERVERPORT);

				socket.send(packet);

			} catch (Exception e) {
				Mensagem("erro" + e.getMessage());
			}

		}

	}

	public class Enterclass implements Runnable {

		@Override
		public void run() {
			try {
				String lstrNomeArq;
				File arq;
				String SERVERIP;
				// try {
				lstrNomeArq = "IP.txt";

				arq = new File(Environment.getExternalStorageDirectory(),
						lstrNomeArq);
				BufferedReader br = new BufferedReader(new FileReader(arq));

				SERVERIP = br.readLine();
				// pegando ip na caixa de texto
				InetAddress serverAddr = InetAddress.getByName(SERVERIP);
				DatagramSocket socket = new DatagramSocket();
				byte[] buf = ("/ARP/IR/enviar/Enter"+finalprotocolo).getBytes();

				DatagramPacket packet = new DatagramPacket(buf, buf.length,
						serverAddr, SERVERPORT);

				socket.send(packet);

			} catch (Exception e) {
				Mensagem("erro" + e.getMessage());
			}

		}

	}

	private void Mensagem(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

}
