package org.example.aplication.service;

import org.example.domain.Cita;

import java.util.List;

public class PacienteServicioIMPL implements  pacienteServicio{



    @Override
    public List<Cita> findAll() {
        return List.of();
    }

    @Override
    public Cita findbyNOMBRE(String nombre) {
        return null;
    }

    @Override
    public void save(Cita cita) {

    }

    @Override
    public void uptade(Cita cita) {

    }

    @Override
    public void delete(String nombre) {

    }
}
