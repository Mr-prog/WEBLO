import com.fastcgi.FCGIInterface;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;


public class Server {
    private final FCGIInterface fcgi;


    public Server() {
        this.fcgi = new FCGIInterface();
        Locale.setDefault(Locale.US);
    }

    private static final String HTTP_RESPONSE = """
            Content-Type: application/json
            Content-Length: %d
            
            %s
            """;


    private void sendResponse(String jResponse) {
        var response = String.format(HTTP_RESPONSE, jResponse.getBytes(StandardCharsets.UTF_8).length, jResponse);

        System.out.println(response);
    }


    private HashMap<String, String> parseRequest(String request) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        String[] equalities = request.split("&");
        if (equalities.length > 3) {
            throw new IllegalArgumentException("Request must contain 3 parameters");
        }

        HashMap<String, String> parsedValues = new HashMap<>();

        for (String equality : equalities) {
            String[] keyValue = equality.split("=");
            parsedValues.put(keyValue[0], keyValue[1]);
        }
        return parsedValues;
    }


    public void run() {
        while (fcgi.FCGIaccept() >= 0) {
            var executionStart = System.nanoTime();
            String method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");

            if (method.equals("GET")) {
                try {
                    String request = FCGIInterface.request.params.getProperty("QUERY_STRING");
                    HashMap<String, String> valuesMap;
                    valuesMap = parseRequest(request);

                    var x = Integer.parseInt(valuesMap.get("x"));
                    var y = Float.parseFloat(valuesMap.get("y"));
                    var r = Float.parseFloat(valuesMap.get("r"));

                    if (Validator.checkX(x) && Validator.checkY(y) && Validator.checkR(r)) {
                        sendResponse("""
                                {
                                "error": "invalid data"
                                }
                                """);
                    } else {
                        sendResponse("""
                                {
                                "x": %d,
                                "y": %.2f,
                                "r": %.2f,
                                "execution_time": "%tS",
                                "result": "Hit"
                                }
                                """.formatted(x, y, r, System.nanoTime() - executionStart));
                    }
                } catch (IllegalArgumentException e) {
                    sendResponse("""
                    {
                    "error": "Request contains more than 3 values"
                    }
                    """);
                } catch (ArrayIndexOutOfBoundsException e) {
                    sendResponse("""
                    {
                    "error": "Request contains empty values"
                    }
                    """);
                }

            }
        }
    }
}
