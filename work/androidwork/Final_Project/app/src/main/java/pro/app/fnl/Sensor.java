package pro.app.fnl;

public class Sensor {
    String temp;
    String coll;
    String gas;
    String fire;

    public Sensor() {
    }

    public Sensor(String temp, String coll, String gas, String fire) {
        this.temp = temp;
        this.coll = coll;
        this.gas = gas;
        this.fire = fire;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getColl() {
        return coll;
    }

    public void setColl(String coll) {
        this.coll = coll;
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


