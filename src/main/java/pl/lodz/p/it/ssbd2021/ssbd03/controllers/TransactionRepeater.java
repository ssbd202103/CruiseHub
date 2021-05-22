package pl.lodz.p.it.ssbd2021.ssbd03.controllers;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;

public class TransactionRepeater {
    private static final int repeatCount = 4;

    public static void tryAndRepeat(RepeatVoidInterface repeatInterface) throws BaseAppException {
        for (int i = 0; i < repeatCount - 1; i++) {
            try {
                repeatInterface.execute();
                return;
            } catch (BaseAppException ignored) {
            }
        }
        repeatInterface.execute();
    }

    public static <T> T tryAndRepeat(RepeatResultInterface<T> repeatInterface) throws BaseAppException {
        for (int i = 0; i < repeatCount - 1; i++) {
            try {
                return repeatInterface.execute();
            } catch (BaseAppException ignored) {
            }
        }
        return repeatInterface.execute();
    }

    @FunctionalInterface
    public interface RepeatVoidInterface {
        void execute() throws BaseAppException;
    }

    @FunctionalInterface
    public interface RepeatResultInterface<T> {
        T execute() throws BaseAppException;
    }
}


