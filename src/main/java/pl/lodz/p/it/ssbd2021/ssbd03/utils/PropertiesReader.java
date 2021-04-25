package pl.lodz.p.it.ssbd2021.ssbd03.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private static final String SECURITY_PROPERTIES_FILE = "security/security.properties";

    public static Properties getFileProperties(String fileName) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new FileNotFoundException("File " + fileName + " not found");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static Properties getSecurityProperties() {
        return getFileProperties(SECURITY_PROPERTIES_FILE);
    }

}
