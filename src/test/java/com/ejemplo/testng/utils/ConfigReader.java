package com.ejemplo.testng.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;
    private static String currentEnvironment = System.getProperty("env", "qa"); // Valor por defecto: "qa"

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        String fileName = "config-" + currentEnvironment + ".properties";
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                System.out.println("No se pudo encontrar el archivo de propiedades: " + fileName);
                // Si el archivo no se encuentra, cargamos un archivo por defecto o lanzamos un error
                // Para este ejemplo, cargaremos un archivo "default" si existe, o dejaremos vacío.
                fileName = "config-qa.properties"; // Intentar cargar QA como fallback
                try (InputStream defaultInput = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {
                     if (defaultInput != null) {
                         properties.load(defaultInput);
                         System.out.println("Cargando archivo de propiedades por defecto: " + fileName);
                     } else {
                         System.err.println("Error: Archivo de propiedades por defecto no encontrado: " + fileName);
                     }
                }
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Método para cambiar el entorno (útil si se quiere cambiar dinámicamente)
    public static void setEnvironment(String env) {
        if (env != null && !env.isEmpty() && !currentEnvironment.equalsIgnoreCase(env)) {
            currentEnvironment = env.toLowerCase();
            loadProperties(); // Recargar propiedades para el nuevo entorno
        }
    }
}