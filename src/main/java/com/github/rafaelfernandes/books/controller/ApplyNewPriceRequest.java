package com.github.rafaelfernandes.books.controller;

import lombok.Data;

@Data
public class ApplyNewPriceRequest {

    private String typeOfApply;
    private Long id;
    private Long percentage;

}
