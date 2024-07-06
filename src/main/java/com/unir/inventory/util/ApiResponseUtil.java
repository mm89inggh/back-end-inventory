package com.unir.inventory.util;

import com.unir.inventory.resources.model.ApisResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ApiResponseUtil {

    public static <T> ResponseEntity<ApisResponse<T>> ok(T data) {
        ApisResponse<T> api = new ApisResponse<>("Successful", data);
        return new ResponseEntity<>(api, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApisResponse<T>> internalError(String msm, T data) {
        ApisResponse<T> api = new ApisResponse<>("Error: " + msm, data);
        return new ResponseEntity<>(api, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity<ApisResponse<T>> notFound() {
        ApisResponse<T> api = new ApisResponse<>("No se encontró ningun resultado", null);
        return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<ApisResponse<T>> created(T data) {
        ApisResponse<T> api = new ApisResponse<>("Successful", data);
        return new ResponseEntity<>(api, HttpStatus.CREATED);
    }

}
