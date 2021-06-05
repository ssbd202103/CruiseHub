package pl.lodz.p.it.ssbd2021.ssbd03.utils;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.interceptor.Interceptors;
import java.util.Properties;

@Interceptors(TrackingInterceptor.class)
public class TransactionRepeater {
    private static final Properties securityProperties = PropertiesReader.getSecurityProperties();
    private static final int repeatCount = Integer.parseInt(securityProperties.getProperty("transaction.repeat.count"));

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
            } //TODO QA CHECK information about exception and repeating transaction should be logged in catch block
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


