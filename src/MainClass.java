public class MainClass {
    public static void main(String[] args) {
//        RawValues rawValues = new RawValues("C:\\Users\\vpriselkov\\IdeaProjects\\BlackBox\\src\\23.04 ГСТ20.CSV");
        RawValues rawValues = new RawValues("C:\\Users\\vpriselkov\\IdeaProjects\\BlackBox\\src\\mock.CSV");
        Controller controller = new Controller(rawValues.getRawValues());
        DataVerificator dataVerificator = new DataVerificator(rawValues.getRawValues());

        if (dataVerificator.checkData()) {
            System.out.println("Продолжительность записи: " + controller.getWholeRecTime());
            System.out.println("Количество запусков дизеля: " + controller.getDiselStartQuant());
            System.out.println("Самая долгая сессия: " + controller.getRecordingTime(controller.getMaxFromColumn(ParameterNumber.ROW_NUMBER.ordinal())));
            System.out.println("Максимальная температура гидравлического масла: " + controller.getMaxFromColumn(ParameterNumber.HYD_OIL_TEMP.ordinal()));
            System.out.println("Максимальная температура масла ДВС: " + controller.getMaxFromColumn(ParameterNumber.ENGINE_TEMP.ordinal()));
            System.out.println("Максимальная температура наддувочного воздуха: " + controller.getMaxFromColumn(ParameterNumber.TURBO_TEMP.ordinal()));
            System.out.println("Максимальные обороты дизеля: " + controller.getMaxFromColumn(ParameterNumber.ENGINE_RPM.ordinal()));
            int leftMotRpm = controller.getMaxFromColumn(ParameterNumber.MOTOR_L_RPM.ordinal());
            System.out.println("Максимальные обороты левого борта: " + leftMotRpm + " об/мин | Скорость: " + controller.getMaxSpeed(leftMotRpm, 78));
            int rightMotRpm = controller.getMaxFromColumn(ParameterNumber.MOTOR_R_RPM.ordinal());
            System.out.println("Максимальные обороты правого борта: " + rightMotRpm + " об/мин | Скорость: " + controller.getMaxSpeed(rightMotRpm, 78));
            System.out.println("Максимальное давление левого борта: " + controller.getMaxFromColumn(ParameterNumber.PUMP_L_PRESS.ordinal()));
            System.out.println("Максимальное давление правого борта: " + controller.getMaxFromColumn(ParameterNumber.PUMP_R_PRESS.ordinal()));
            System.out.println("Максимальное давление в тормозном контуре: " + controller.getMaxFromColumn(ParameterNumber.BRAKE_PRESS.ordinal()));
        }
    }
    }

