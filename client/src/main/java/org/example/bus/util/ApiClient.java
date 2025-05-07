package org.example.bus.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author lakithaprabudh
 */
public class ApiClient {
    private static final String BASE_URL = "http://localhost:8080/bus-reservation-server/api";

    public static String get(String endpoint) throws IOException {
        HttpURLConnection conn = createConnection("GET", endpoint);
        return readResponse(conn);
    }

    public static String post(String endpoint, String jsonBody) throws IOException {
        HttpURLConnection conn = createConnection("POST", endpoint);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        return readResponse(conn);
    }

    private static HttpURLConnection createConnection(String method, String endpoint) throws IOException {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Accept", "application/json");
        return conn;
    }

    private static String readResponse(HttpURLConnection conn) throws IOException {
        int responseCode = conn.getResponseCode();
        InputStream stream = (responseCode >= 200 && responseCode < 300)
                ? conn.getInputStream()
                : conn.getErrorStream();

        String response;
        try (Scanner scanner = new Scanner(stream).useDelimiter("\\A")) {
            response = scanner.hasNext() ? scanner.next() : "";
        } finally {
            conn.disconnect();
        }

        if (responseCode >= 400) {
            throw new IOException("HTTP " + responseCode + ": " + response);
        }

        return response;
    }
}