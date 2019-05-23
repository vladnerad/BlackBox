package aggregates;

public abstract class FanPump {
    private int minCurrent;
    private int maxCurrent;

    public int getMinCurrent() {
        return minCurrent;
    }

    public int getMaxCurrent() {
        return maxCurrent;
    }
}
