package com.micorservices.providerservice.dto;

 public record  CatalogDto (    Long id, String title, String description, 
    Double price, Long providerId
) 
{}

