package org.example.interfaces;

import org.example.domain.Cita;
import org.example.domain.Paciente;

import java.util.List;

public interface CitaRepository {
    List<Cita> findAllCita();
    Cita findByIdCita(int idCita);
    void saveCita(Cita cita);
    void updateCita(Cita cita);
    void deleteCita(int idCita);
}
