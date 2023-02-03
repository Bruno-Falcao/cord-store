package com.falcao.cordstore.utils;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAPI {

    @JsonProperty("data")
    private List<Object> data;

    private String message;
    private String[] error;

    private static ResponseAPI instance;

    public static ResponseAPI getInstance(String message) {
        if (instance == null) {
            instance = new ResponseAPI();
        }
        instance.setMessage(message);
        instance.setError(new String[0]);
        instance.setData(new ArrayList<>());
        return instance;
    }

    public static ResponseAPI getInstance(String message, String[] errors) {
        ResponseAPI instance = ResponseAPI.getInstance(message);
        instance.setError(errors);
        return instance;
    }

    public static ResponseAPI getInstance(String message, Exception ex) {
        ResponseAPI instance = getInstance(message,
                Arrays.stream(ex.getSuppressed())
                        .map(e -> e.getMessage())
                        .toArray(String[]::new));

        if (Objects.requireNonNull(ex.getCause().getMessage()).isEmpty()) {
            ArrayList<String> erros = new ArrayList<>(Arrays.asList(instance.getError()));
            erros.add(ex.getCause().getMessage());
            erros.sort(Comparator.reverseOrder());
            instance.setError(erros.stream().toArray(String[]::new));
        }

        return instance;
    }

    public static ResponseAPI getInstance(List<Object> data) {
        ResponseAPI instance = ResponseAPI.getInstance("");
        instance.setData(data);
        return instance;
    }
}