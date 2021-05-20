package pl.lodz.p.it.ssbd2021.ssbd03.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SHA256HashTest {

    @Test
    void generate() {
        SHA256Hash hash = new SHA256Hash();
        assertEquals("da583af0102c8c5c02b17a37bbf28929abd0bf6bc3bf666da3480b08af16f2fc", hash.generate("helloworld2115".toCharArray()));
    }

    @Test
    void verify() {
        SHA256Hash hash = new SHA256Hash();
        hash.verify("12345678".toCharArray(), "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f");
    }
}