package aggregates;

public class BoschFanPump extends FanPump {
    private int minCurrent = 200;
    private int maxCurrent = 600;

    @Override
    public int getMinCurrent() {
        return minCurrent;
    }

    @Override
    public int getMaxCurrent() {
        return maxCurrent;
    }
}
