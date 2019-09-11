package command;

import analyze.GeneralInfo;
import exception.InterruptOperationException;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private static final Map<Operation, Command> allKnownCommandsMap = new HashMap<>();
    static {
        allKnownCommandsMap.put(Operation.GENERAL_INFO, new GeneralInfo());
//        allKnownCommandsMap.put(Operation.DIESEL_RPM_DROP_TEST, new InfoCommand());
//        allKnownCommandsMap.put(Operation.DPC_TEST, new DepositCommand());
//        allKnownCommandsMap.put(Operation.FAN_DRIVE_TEST, new WithdrawCommand());
//        allKnownCommandsMap.put(Operation.PRESSURE_ANALYZE, new ExitCommand());
//        allKnownCommandsMap.put(Operation.MOTO_HOUR_TEST, new ExitCommand());
    }

    private CommandExecutor() {
    }

    public static final void execute(Operation operation) throws InterruptOperationException {
        allKnownCommandsMap.get(operation).execute();
    }
}
