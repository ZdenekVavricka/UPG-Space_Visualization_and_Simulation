package galaxy_animation;

/**
 * Třída pro ukládání dat s časovým údajem
 * @param <T> datový typ
 */
public class DataWithTime<T> {
    /**
     * Globální proměnná dat
     */
    T data;

    /**
     * Globální proměnná času
     */
    double time;

    /**
     * Konstruktor datové struktury
     * @param data data
     * @param time čas
     */
    public DataWithTime(T data, double time){
        this.data = data;
        this.time = time;
    }

    /**
     * Getter pro získání dat
     * @return data
     */
    public T getData() {
        return data;
    }

    /**
     * Setter pro nastavení dat
     * @param data data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Getter pro získání času
     * @return čas
     */
    public double getTime() {
        return time;
    }

    /**
     * Setter pro nastavení času
     * @param time čas
     */
    public void setTime(int time) {
        this.time = time;
    }
}
