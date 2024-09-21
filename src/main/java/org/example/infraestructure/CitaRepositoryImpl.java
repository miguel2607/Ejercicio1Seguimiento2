package org.example.infraestructure.repositoryImpl;

import org.example.domain.Cita;
import org.example.interfaces.CitaRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CitaRepositoryImpl implements CitaRepository {
    private static final String FILE_NAME = "Cita.dat";
    @Override
    public List<Cita> findAllCita() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<Cita>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Cita findByIdCita(int idCita) {
        return findAllCita().stream()
                .filter(p -> p.getIdCita() == idCita)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void saveCita(Cita cita) {
        List<Cita> citas = findAllCita();
        citas.add(cita);
        saveAll(citas);
    }

    @Override
    public void updateCita(Cita cita) {
        List<Cita> citas = findAllCita();
        citas = citas.stream()
                .map(p -> p.getIdCita() == cita.getIdCita() ? cita : p)
                .collect(Collectors.toList());
        saveAll(citas);
    }

    @Override
    public void deleteCita(int idCita) {
        List<Cita> citas = findAllCita();
        citas = citas.stream()
                .filter(p -> p.getIdCita() != idCita)
                .collect(Collectors.toList());
        saveAll(citas);
    }

    private void saveAll(List<Cita> citas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(citas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
