package com.example.TexiBooking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DriverRequest {

    private String name;
    private String email;
    private int age;
    private String phone;
}
