package event;

import java.io.IOException;
import java.io.InputStream;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

//이벤트에 대한 처리를 하는 리스너객체를 작성
public class SerialListener implements SerialPortEventListener{
	//데이터가 입력되면 이벤트가 발생하므로 시리얼통신을 통해 데이터를 읽을 수 있도록 input객체를
	//리스너가 갖고 있어야 한다.
	private InputStream in;
	public SerialListener(InputStream in) {
		super();
		this.in = in;
	}
	//이벤트가 발생하면 호출되는 메소드
	@Override
	public void serialEvent(SerialPortEvent event) {
		/*switch(event.getEventType()) {
		 	case SerialPortEvent.BI:
		 	case SerialPortEvent.RI:
		}*/
		//전달되는 데이터가 있는 경우 발생하는 이벤트타입
		if(event.getEventType()==SerialPortEvent.DATA_AVAILABLE) {
			try {
				int data = in.available();//전달되는 데이터 사이즈
				byte[] bytedata = new byte[data];//그 사이즈만큼 byte배열을 만든다
				in.read(bytedata, 0, data);//bytedata배열에 읽은 데이터가 저장
				//출력하기
				System.out.print("받은 데이터:"+new String(bytedata));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
