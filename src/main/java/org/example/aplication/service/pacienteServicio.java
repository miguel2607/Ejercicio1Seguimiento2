package org.example.aplication.service;

import org.example.domain.Cita;

import java.util.List;

public interface  pacienteServicio {

    List<Cita> findAll();
    Cita findbyNOMBRE(String nombre);
    void save (Cita cita);
    void uptade(Cita cita);
    void delete(String nombre);





}
