package analyze;

import aggregates.FanPump;
import rawdata.ParameterNumber;

public class FanDriveAnalyzer {

    private Integer[][] rawValues;
    private FanPump fanPump;
    private int minHydOilTemp = 60,
            maxHydOilTemp = 80,
            minCoolantTemp = 85,
            maxCoolantTemp = 95,
            minTurboAirTemp = 50,
            maxTurboAirTemp = 70;

    public FanDriveAnalyzer(Integer[][] rawValues, FanPump fanPump) {
        this.rawValues = rawValues;
        this.fanPump = fanPump;
    }

    private int fanPercentage(Integer[] row){
        int hydOil;
        if(row[ParameterNumber.HYD_OIL_TEMP.ordinal()]<=minHydOilTemp) hydOil = 0;
        else if(row[ParameterNumber.HYD_OIL_TEMP.ordinal()]>=maxHydOilTemp) hydOil = 100;
        else hydOil = (row[ParameterNumber.HYD_OIL_TEMP.ordinal()] - minHydOilTemp) * 100 / (maxHydOilTemp - minHydOilTemp);

        int oilDiesel;
        if(row[ParameterNumber.ENGINE_TEMP.ordinal()]<=minCoolantTemp) oilDiesel = 0;
        else if(row[ParameterNumber.ENGINE_TEMP.ordinal()]>=maxCoolantTemp) oilDiesel = 100;
        else oilDiesel = (row[ParameterNumber.ENGINE_TEMP.ordinal()] - minCoolantTemp) * 100 / (maxCoolantTemp - minCoolantTemp);

        int turboAir;
        if(row[ParameterNumber.TURBO_TEMP.ordinal()]<=minTurboAirTemp) turboAir = 0;
        else if(row[ParameterNumber.TURBO_TEMP.ordinal()]>=maxTurboAirTemp) turboAir = 100;
        else turboAir = (row[ParameterNumber.TURBO_TEMP.ordinal()] - minTurboAirTemp) * 100 / (maxTurboAirTemp - minTurboAirTemp);

        return Math.max(Math.max(hydOil, oilDiesel), turboAir);
    }

    public void checkFanCurr(){
        int supplyErrorCount = 0;
        int fanMaxCurrError = 0;
        int fanMinCurrError = 0;
        int fanOffWhenNeed = 0;

        for (Integer[] rawValue : rawValues) {
            if(rawValue[ParameterNumber.ENGINE_RPM.ordinal()] > 500) {

                if (rawValue[ParameterNumber.CURR_FAN_PUMP.ordinal()] == 0)
                    supplyErrorCount++;

                else if (fanPercentage(rawValue) == 0 && rawValue[ParameterNumber.CURR_FAN_PUMP.ordinal()] < fanPump.getMaxCurrent() - 15)
                    fanMaxCurrError++;

                else if (fanPercentage(rawValue) >= 100 && rawValue[ParameterNumber.CURR_FAN_PUMP.ordinal()] > fanPump.getMinCurrent() + 15
                        && rawValue[ParameterNumber.TURBO_TEMP.ordinal()] != 196) //handicap
                    fanMinCurrError++;
            }

            if(fanPercentage(rawValue) > 0 &&  rawValue[ParameterNumber.CURR_FAN_PUMP.ordinal()] == 0)
                fanOffWhenNeed++;
        }
        if(supplyErrorCount > 0) System.out.println("Fan-Drive pump valve supply error: " + supplyErrorCount);
        if(fanMaxCurrError > 0) System.out.println("Fan-Drive max current error: " + fanMaxCurrError);
        if(fanMinCurrError > 0) System.out.println("Fan-Drive min current error: " + fanMinCurrError);
        if(fanOffWhenNeed > 0) System.out.println("Fan-Drive had been off when it be needed: " + fanOffWhenNeed);
    }
}
