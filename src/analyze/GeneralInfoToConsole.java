package analyze;

import Helpers.ConsoleHelper;
import rawdata.ParameterNumber;

public class GeneralInfoToConsole extends GeneralInfo {

    @Override
    public void execute() {
        ConsoleHelper.writeMessage("**********************************************");
        ConsoleHelper.writeMessage("Продолжительность записи: " + getWholeRecTime());
        ConsoleHelper.writeMessage("Количество запусков дизеля: " + getDiselStartQuant());
        ConsoleHelper.writeMessage("Самая долгая сессия: " + getRecordingTime(getMaxFromColumn(ParameterNumber.ROW_NUMBER)));
        ConsoleHelper.writeMessage("Максимальная температура гидравлического масла: " + getMaxFromColumn(ParameterNumber.HYD_OIL_TEMP));
        ConsoleHelper.writeMessage("Максимальная температура ОЖ ДВС: " + getMaxFromColumn(ParameterNumber.ENGINE_TEMP));
        ConsoleHelper.writeMessage("Максимальная температура наддувочного воздуха: " + maxAirTurbo());
        ConsoleHelper.writeMessage("Максимальные обороты дизеля: " + getMaxFromColumn(ParameterNumber.ENGINE_RPM));
        int leftMotRpm = getMaxFromColumn(ParameterNumber.MOTOR_L_RPM);
        ConsoleHelper.writeMessage("Максимальные обороты левого борта: " + leftMotRpm + " об/мин | Скорость: " + getMaxSpeed(leftMotRpm, 56));
        int rightMotRpm = getMaxFromColumn(ParameterNumber.MOTOR_R_RPM);
        ConsoleHelper.writeMessage("Максимальные обороты правого борта: " + rightMotRpm + " об/мин | Скорость: " + getMaxSpeed(rightMotRpm, 56));
        ConsoleHelper.writeMessage("Максимальное давление левого борта: " + getMaxFromColumn(ParameterNumber.PUMP_L_PRESS));
        ConsoleHelper.writeMessage("Максимальное давление правого борта: " + getMaxFromColumn(ParameterNumber.PUMP_R_PRESS));
        ConsoleHelper.writeMessage("Максимальное давление в тормозном контуре: " + getMaxFromColumn(ParameterNumber.BRAKE_PRESS));
        ConsoleHelper.writeMessage("**********************************************");
    }
}
