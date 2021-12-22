package event;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
public class ArduinoSerialUsingEvent {
	public static void main(String[] args) {		
		try {
			CommPortIdentifier comPortIdentifier = CommPortIdentifier.getPortIdentifier("COM8");
			if(comPortIdentifier.isCurrentlyOwned()) {
				System.out.println("사용할 수 없습니다");
			}else {
				System.out.println("사용가능");							
				CommPort commPort = comPortIdentifier.open("basic_serial", 3000);	
				System.out.println(commPort);				
				if(commPort instanceof SerialPort) {
					SerialPort serialPort = (SerialPort)commPort;
					serialPort.setSerialPortParams(
							9600, 
							SerialPort.DATABITS_8, 												 
							SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
					//시리얼통신을 통해 데이터 통신을 하기 위해서 스트림 객체를 얻기
					InputStream in = serialPort.getInputStream();
					OutputStream out = serialPort.getOutputStream();					
					
					//event처리를 통해 아두이노에서 보내오는 데이터를 읽기
					serialPort.addEventListener(new SerialListener(in));
					serialPort.notifyOnDataAvailable(true);
				}
			}
		} catch (NoSuchPortException e) {
			e.printStackTrace();
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}		 
	}
}