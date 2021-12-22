package final_latte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ReceiverThread2 extends Thread{
	Socket client;
	BufferedReader clientin;//占쎄깻占쎌뵬占쎌뵠占쎈섧占쎈뱜揶쏉옙 癰귣�沅∽옙�뮉 筌롫뗄苑�筌욑옙
	PrintWriter clientout;//占쎄깻占쎌뵬占쎌뵠占쎈섧占쎈뱜占쎈퓠野껓옙 癰귣�沅∽옙�뮉 筌롫뗄苑�筌욑옙
	//SerialArduinoLEDControl serialobj;//占쎈뻻�뵳�딅섰 占쎈꽰占쎈뻿
	SendAndReceiveSerial serialobj;
	OutputStream serialout;//占쎈뻻�뵳�딅섰占쎈꽰占쎈뻿占쎈퓠占쎄퐣 占쎈툡占쎈あ占쎌뵠占쎈걗嚥∽옙 占쎈쑓占쎌뵠占쎄숲�몴占� 占쎄땀癰귣�沅→묾占�
	
	public ReceiverThread2(Socket client, OutputStream serialout) {
		super();
		this.client = client;
		this.serialout = serialout;
		try {
			clientin = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			clientout = new PrintWriter(this.client.getOutputStream(),true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		super.run();
		while(true) {
			try {
				String msg = clientin.readLine();
				System.out.println("�겢�씪�씠�뼵�듃媛� 蹂대궦 硫붿꽭吏� : "+msg);
				if(msg.equals("led_on")) {
					serialout.write('o');
				}else if(msg.equals("led_off")) {
					serialout.write('f');
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
