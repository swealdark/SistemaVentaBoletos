package com.moon.SistemaVentaBoletos;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;
    @FutureOrPresent(message = "La fecha debe ser actual o posterior")
    private LocalDateTime fecha;
    @NotBlank(message = "El lugar es obligatorio")
    private String lugar;
    @Min(value=1,message = "La capacidad debe mayor o igual a 1")
    private int capacidad;
    @Min(value = 1,message = "Debe haber al menos un boleto disponible")
    private int boletosDisponibles;
    @Min(value=0,message = "El precio debe ser un valor positivo")
    private double precio;


}
