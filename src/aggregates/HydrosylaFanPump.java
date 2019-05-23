package aggregates;

public class HydrosylaFanPump extends FanPump {
    private int minCurrent = 50;
    private int maxCurrent = 760;

    @Override
    public int getMinCurrent() {
        return minCurrent;
    }

    @Override
    public int getMaxCurrent() {
        return maxCurrent;
    }
}
