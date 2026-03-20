package com.example.webflux.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    private  Long id;
    private String firstName;
    private String lastName;
}
