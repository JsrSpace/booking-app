package org.jsr.mvc.bookingapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "services")
@Builder
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    public String name;

    private Duration durationMinutes;

    @DecimalMin(value = "0.00", inclusive = false, message = "Price cannot be less than 0")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;


}
