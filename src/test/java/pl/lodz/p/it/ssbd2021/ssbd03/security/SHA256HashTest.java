package pl.lodz.p.it.ssbd2021.ssbd03.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SHA256HashTest {

    @Test
    void generate() {
        SHA256Hash hash = new SHA256Hash();
        assertEquals("da583af0102c8c5c02b17a37bbf28929abd0bf6bc3bf666da3480b08af16f2fc", hash.generate("helloworld2115".toCharArray()));
    }

    @Test
    void verify() {
        SHA256Hash hash = new SHA256Hash();
        hash.verify("haslo2115".toCharArray(), "219c2cdf5b1e49888c0adf23911ad5eeabf8f1aa88f3c1420441073a39ffd945");
    }
}