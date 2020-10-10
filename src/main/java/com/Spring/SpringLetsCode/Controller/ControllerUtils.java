package com.Spring.SpringLetsCode.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {
    static Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> result = new TreeMap<>();

        for (FieldError f : bindingResult.getFieldErrors()) {
            if (!result.containsKey(f.getField() + "Error")) {
                result.put(f.getField() + "Error", f.getDefaultMessage());
            }
        }
        return result;
    }
}
