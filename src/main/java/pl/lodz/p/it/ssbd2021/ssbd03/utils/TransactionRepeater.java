package pl.lodz.p.it.ssbd2021.ssbd03.utils;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.interceptor.Interceptors;

@Interceptors(TrackingInterceptor.class)
public class TransactionRepeater {
    private static final int repeatCount = 3;

    public static void tryAndRepeat(RepeatVoidInterface repeatInterface) throws BaseAppException {
        for (int i = 0; i < repeatCount; i++) {
            try {
                repeatInterface.execute();
                return;
            } catch (BaseAppException ignored) {
            }
        }
        repeatInterface.execute();
    }

    public static <T> T tryAndRepeat(RepeatResultInterface<T> repeatInterface) throws BaseAppException {
        for (int i = 0; i < repeatCount; i++) {
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


