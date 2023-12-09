package com.example.caffeinedemo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<String> getAddress(@PathVariable("id") long customerId) {
        return ResponseEntity.ok(addressService.getAddress(customerId));
    }
    @GetMapping("/address2/{id}")
    public ResponseEntity<String> getAddress2(@PathVariable("id") long customerId) {
        return ResponseEntity.ok(addressService.getAddress2(customerId));
    }
}
