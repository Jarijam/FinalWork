package android.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SerialArduinoLEDControl {
	OutputStream out;
	public void connect(String portName) {
		//COM포트가 실제 존재하고 사용가능한 상태인지 확인		
		try {
			CommPortIdentifier comPortIdentifier = CommPortIdentifier.getPortIdentifier(portName);
			if(comPortIdentifier.isCurrentlyOwned()) {
				System.out.println("사용할 수 없습니다");
			}else {
				System.out.println("사용가능");
				//port가 사용가능하면 포트를 열고 포트객체를 가져오기
				//=> 포트를 통해 통신할 수 있는 input/output객체를 가져올 수 있다.
				//=> 매개변수1 : 포트를 열고 사용하는 프로그램의 이름
				//=> 매개변수2 : 포트를 열고 통신하기 위해서 기다리는 시간(mills)				
				CommPort commPort = comPortIdentifier.open("basic_serial", 3000);	
				System.out.println(commPort);
				//comm포트는 종류가 두 가지
				//=> Serial 포트인지 Parallel포트인지 확인
				//SerialPort, ParallelPort
				if(commPort instanceof SerialPort) {
					SerialPort serialPort = (SerialPort)commPort;
					//SerialPort에 대한 설정
					//=> 통신속도, 데이터길이, stop bit, parity bit를 설정
					serialPort.setSerialPortParams(
							9600, //Serial port통신속도
							SerialPort.DATABITS_8, //데이터길이 
												   //한 번에 8bit씩 데이터가 전송
							SerialPort.STOPBITS_1,//stop bit(끝을 표시)
							SerialPort.PARITY_NONE);//시리얼통신에서 송수신되는 데이터의 오류를 검출하기 위해 사용
					//시리얼포트와 통신할 수 있도록 input/output스트림객체 구하기
					InputStream in = serialPort.getInputStream();
					out = serialPort.getOutputStream();
					
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
		}		 
	}
	//시리얼 출력을 위한 OutputStream리턴
	public OutputStream getOutput() {
		return out;
	}
}
