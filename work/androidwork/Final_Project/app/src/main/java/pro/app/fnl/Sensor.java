package pro.app.fnl;

public class Sensor {
    String temp;
    String gas;
    String fire;

    public Sensor() {
    }

    public Sensor(String temp,String gas, String fire) {
        this.temp = temp;
        this.gas = gas;
        this.fire = fire;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getFire() {
        return fire;
    }

    public void setFire(String fire) {
        this.fire = fire;
    }
}


