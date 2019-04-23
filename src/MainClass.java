public class MainClass {
    public static void main(String[] args) {
        RawValues rawValues = new RawValues("C:\\Users\\vpriselkov\\IdeaProjects\\BlackBox\\src\\23.04 ГСТ20.CSV");
        Controller controller = new Controller(rawValues.getRawValues());
        System.out.println("Продолжительность записи: " + controller.getWholeRecTime());
        System.out.println("Количество запусков дизеля: " + controller.getDiselStartQuant());
        System.out.println("Самая долгая сессия: " + controller.getRecordingTime(controller.getMaxFromColumn(ParametersNumber.ROW_NUMBER)));
        System.out.println("Максимальная температура гидравлического масла: " + controller.getMaxFromColumn(ParametersNumber.HYD_OIL_TEMP));
        System.out.println("Максимальная температура масла ДВС: " + controller.getMaxFromColumn(ParametersNumber.ENGINE_TEMP));
        System.out.println("Максимальная температура наддувочного воздуха: " + controller.getMaxFromColumn(ParametersNumber.TURBO_TEMP));
        System.out.println("Максимальные обороты дизеля: " + controller.getMaxFromColumn(ParametersNumber.ENGINE_RPM));
        int leftMotRpm = controller.getMaxFromColumn(ParametersNumber.MOTOR_L_RPM);
        System.out.println("Максимальные обороты левого борта: " + leftMotRpm + " об/мин | Скорость: " + controller.getMaxSpeed(leftMotRpm, 78));
        int rightMotRpm = controller.getMaxFromColumn(ParametersNumber.MOTOR_R_RPM);
        System.out.println("Максимальные обороты правого борта: " + rightMotRpm + " об/мин | Скорость: " + controller.getMaxSpeed(rightMotRpm, 78));
        System.out.println("Максимальное давление левого борта: " + controller.getMaxFromColumn(ParametersNumber.PUMP_L_PRESS));
        System.out.println("Максимальное давление правого борта: " + controller.getMaxFromColumn(ParametersNumber.PUMP_R_PRESS));
        System.out.println("Максимальное давление в тормозном контуре: " + controller.getMaxFromColumn(ParametersNumber.BRAKE_PRESS));
        }
    }

