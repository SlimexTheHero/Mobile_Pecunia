package com.mobilecomputing.pecunia.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CurrencyManager {
    public final static String CURRENCY_API_URL = "https://api.exchangeratesapi.io/latest";

    /**
     * This function requests the currency api
     * @param baseCurrency Rates are quoted against the Euro by default. Quote against a different currency by using
     *                     this param
     * @return HashMap with the country code as Key and the currency as value
     */
    public HashMap<String, Object> getCurrencyValue(String baseCurrency) {
        HashMap<String, Object> currencyMap = new HashMap<String, Object>();
        try {
            URL url = new URL(CURRENCY_API_URL+"?base="+baseCurrency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("HTTP Error Code: " + connection.getResponseCode());
            }
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String output;
            String result = "";
            while ((output = bufferedReader.readLine()) != null) {
                result += output;
            }
            connection.disconnect();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode node = objectMapper.readValue(result,ObjectNode.class);
            node = objectMapper.readValue(node.get("rates").toString(),ObjectNode.class);
            currencyMap= objectMapper.convertValue(node, new TypeReference<HashMap<String, Object>>() {});
            return currencyMap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencyMap;
    }
}
