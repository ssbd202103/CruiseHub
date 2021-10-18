package pl.lodz.p.it.ssbd2021.ssbd03.common.endpoints;

/**
 * Interfejs determinujący Endpoint inicjujący transakcje aplikacyjne
 */
public interface TransactionalEndpoint {
    /**
     * Metoda określa czy ostatnio wykonana transakcja w ramach implementującego endpointa została wycofana
     *
     * @return Boolean określający czy transakcja powoływana przez metodę została wycofana
     */
    boolean isTransactionRolledBack();

    /**
     * Zwraca identyfikator ostatniej transakcji aplikacyjnej w ramach implementującego endpointa
     *
     * @return Identyfikator transakcji
     */
    String getLastTransactionID();
}
