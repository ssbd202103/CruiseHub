package pl.lodz.p.it.ssbd2021.ssbd03.utils;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints.TransactionalEndpoint;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.TransactionRepeaterException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.ejb.EJBTransactionRolledbackException;
import javax.interceptor.Interceptors;
import java.util.Properties;

/**
 * Klasa służąca do powtarzania transakcji aplikacyjnych
 */
@Interceptors(TrackingInterceptor.class)
@Log
public class TransactionRepeater {
    private static final Properties securityProperties = PropertiesReader.getSecurityProperties();
    private static final int REPEAT_COUNT = Integer.parseInt(securityProperties.getProperty("transaction.repeat.count"));

    /**
     * Metoda do wykonania i ewentualnego powtórzenia w przypadku błędu metody nie zwracającej wartości
     *
     * @param endpoint        Endpoint zaczynający transakcję
     * @param repeatInterface Interfejs funkcyjny opakowujący metodę do powtórzenia
     * @throws BaseAppException Bazowy wyjątek aplikacyjny
     */
    public static void tryAndRepeat(TransactionalEndpoint endpoint, RepeatVoidInterface repeatInterface) throws BaseAppException {
        for (int i = 0; i < REPEAT_COUNT; i++) {
            try {
                repeatInterface.execute();
                checkTransactionStatus(endpoint);
                return;
            } catch (BaseAppException | EJBTransactionRolledbackException e) {
                log.warning(getRepeatLogMessage(e, i + 1));
            }
        }
        repeatInterface.execute();
    }

    /**
     * Metoda do wykonania i ewentualnego powtórzenia w przypadku błędu metody zwracającej określoną wartość
     *
     * @param endpoint        Endpoint zaczynający transakcję
     * @param repeatInterface Interfejs funkcyjny opakowujący metodę do powtórzenia
     * @param <T>             Typ parametru zwracanego przez metodę
     * @return Wynik metody
     * @throws BaseAppException Bazowy wyjątek aplikacyjny
     */
    public static <T> T tryAndRepeat(TransactionalEndpoint endpoint, RepeatResultInterface<T> repeatInterface) throws BaseAppException {
        for (int i = 0; i < REPEAT_COUNT; i++) {
            try {
                T result = repeatInterface.execute();
                checkTransactionStatus(endpoint);
                return result;
            } catch (BaseAppException | EJBTransactionRolledbackException e) {
                log.warning(getRepeatLogMessage(e, i + 1));
            }
        }
        return repeatInterface.execute();
    }

    /**
     * Interfejs funkcyjny opakowujący daną metodę
     */
    @FunctionalInterface
    public interface RepeatVoidInterface {

        void execute() throws BaseAppException;
    }

    /**
     * Interfejs funkcyjny opakowujący daną metodę
     */
    @FunctionalInterface
    public interface RepeatResultInterface<T> {

        T execute() throws BaseAppException;
    }

    private static void checkTransactionStatus(TransactionalEndpoint transactionEndpoint) throws TransactionRepeaterException {
        if (transactionEndpoint.isTransactionRolledBack()) {
            throw TransactionRepeaterException.transactionStatusRolledBack(transactionEndpoint.getLastTransactionID());
        }
    }

    private static String getRepeatLogMessage(Throwable t, int repeatNumber) {
        return String.format("Exception %s caught in repeat interface" +
                "\nProceeding to transaction repeat number: %s ", t.getMessage(), repeatNumber);
    }
}


