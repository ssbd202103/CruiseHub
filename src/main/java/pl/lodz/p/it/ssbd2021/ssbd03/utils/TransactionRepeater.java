package pl.lodz.p.it.ssbd2021.ssbd03.utils;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.interceptor.Interceptors;
import java.util.Properties;

@Interceptors(TrackingInterceptor.class)
@Log
public class TransactionRepeater {
    private static final Properties securityProperties = PropertiesReader.getSecurityProperties();
    private static final int REPEAT_COUNT = Integer.parseInt(securityProperties.getProperty("transaction.repeat.count"));

    public static void tryAndRepeat(RepeatVoidInterface repeatInterface) throws BaseAppException {
        for (int i = 0; i < REPEAT_COUNT; i++) {
            try {
                repeatInterface.execute();
                return;
            } catch (BaseAppException e) {
                log.warning(getRepeatLogMessage(e, i + 1));
            }
        }
        repeatInterface.execute();
    }

    public static <T> T tryAndRepeat(RepeatResultInterface<T> repeatInterface) throws BaseAppException {
        for (int i = 0; i < REPEAT_COUNT; i++) {
            try {
                return repeatInterface.execute();
            } catch (BaseAppException e) {
                log.warning(getRepeatLogMessage(e, i + 1));
            }
        }
        return repeatInterface.execute();
    }

    private static String getRepeatLogMessage(Throwable t, int repeatNumber) {
        return String.format("Exception %s caught in repeat interface" +
                "\nProceeding to transaction repeat number: %s ", t.getMessage(), repeatNumber);
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


