package com.example.TexiBooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerResponse {
    private String customerName;
    private int customerAge;
    private String emailId;
    private String customerPhone;
}
