package com.unir.orders.utils;

import org.springframework.http.ResponseEntity;

import java.util.Collections;

public class Validators {

    public static boolean esNumero(String cadena) {

        if (cadena == null || cadena.isEmpty()) {
            return false;
        }
        try {
            Long.parseLong(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
