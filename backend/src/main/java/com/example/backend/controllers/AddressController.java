package com.example.backend.controllers;

import com.example.backend.models.Address;
import com.example.backend.services.AddressServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/address")
public class AddressController {
    @Autowired
    private AddressServices addressServices;
    @PostMapping
    public ResponseEntity<String> addAddress(@RequestBody Address address) {
        try {
            addressServices.addAddress(address);
            return ResponseEntity.ok("Address is added");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add address: " + e.getMessage());
        }
    }

    @GetMapping ("getAddressByCustomerId/{id}")
    public List<Address> getAddressByCustomerId(@PathVariable("id") Long customerId){
        return addressServices.getAddressByCustomerId(customerId);
    }
}
