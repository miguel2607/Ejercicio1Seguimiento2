package org.example.aplication;

import org.example.aplication.service.CitaService;
import org.example.aplication.service.PacienteService;
import org.example.aplication.service.PacienteServiceImpl;
import org.example.aplication.service.CitaServiceImpl;
import org.example.domain.Cita;
import org.example.domain.Paciente;
import org.example.infraestructure.repositoryImpl.CitaRepositoryImpl;
import org.example.infraestructure.repositoryImpl.PacienteRepositoryImpl;
import org.example.interfaces.CitaRepository;
import org.example.interfaces.PacienteRepository;

import javax.swing.JOptionPane;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    private static final PacienteService pacienteService;
    private static CitaService citaService;

    static {
        PacienteRepository pacienteRepository = new PacienteRepositoryImpl();
        pacienteService = new PacienteServiceImpl(pacienteRepository);
        CitaRepository citaRepository = new CitaRepositoryImpl();
        citaService = new CitaServiceImpl(citaRepository);
    }

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            String opcion = JOptionPane.showInputDialog(
                    "1. lista de pacientes\n" +
                            "2. Crear un paciente nuevo \n" +
                            "3. Actualizar paciente que ya esta en la lista\n" +
                            "4. Eliminar paciente de la lista \n" +
                            "5. Lista de citas\n" +
                            "6. Crear nueva cita\n" +
                            "7. Actualizar una cita\n" +
                            "8. Eliminar una cita\n" +
                            "9. Salir\n"

            );

            if (opcion == null) {
                salir = true;
                continue;
            }

            switch (opcion) {
                case "1":
                    listarPacientes();
                    break;
                case "2":
                    crearPaciente();
                    break;
                case "3":
                    actualizarPaciente();
                    break;
                case "4":
                    eliminarPaciente();
                    break;
                case "5":
                    listasCitas();
                    break;
                case "6":
                    crearCita();
                    break;
                case "7":
                    actualizarCita();
                    break;
                case "8":
                    eliminarCita();
                    break;
                case "9":
                    salir = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }
        }
    }

    private static void listasCitas() {
        List<Cita> citas = citaService.findAllCita();
        if (citas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay citas registradas");
        } else {
            StringBuilder mensaje = new StringBuilder("Listado de citas:\n");
            for (Cita cita : citas) {
                mensaje.append(cita).append("\n");
            }
            JOptionPane.showMessageDialog(null, mensaje.toString());
        }
    }

    private static void crearCita() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Date fecha = null;
        Date hora = null;

        while (fecha == null) {
            String fechaInput = JOptionPane.showInputDialog("Ingrese la fecha de la cita (formato YYYY-MM-DD):");
            try {
                fecha = dateFormat.parse(fechaInput);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Error: Formato de fecha inválido.");
            }
        }

        while (hora == null) {
            String horaInput = JOptionPane.showInputDialog("Ingrese la hora de la cita (formato HH:MM):");
            try {
                hora = timeFormat.parse(horaInput);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Error: Formato de hora inválido.");
            }
        }

        String idPacienteInput = JOptionPane.showInputDialog("Ingrese el ID del paciente para la cita:");
        int idPaciente = Integer.parseInt(idPacienteInput);

        Paciente paciente = pacienteService.findById(idPaciente);
        if (paciente == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el paciente con ID " + idPaciente);
            return;
        }

        String motivoInput = JOptionPane.showInputDialog("Ingrese el motivo de la cita:");

        List<Cita> citas = citaService.findAllCita();
        int idCita = citas.stream().mapToInt(Cita::getIdCita).max().orElse(0) + 1;

        Cita nuevaCita = new Cita();
        nuevaCita.setIdCita(idCita);
        nuevaCita.setFecha(fecha);
        nuevaCita.setHora(hora);
        nuevaCita.setPaciente(paciente);
        nuevaCita.setMotivo(motivoInput);

        try {
            citaService.saveCita(nuevaCita);
            JOptionPane.showMessageDialog(null, "Cita creada exitosamente con ID: " + idCita);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private static void listarPacientes() {
        List<Paciente> pacientes = pacienteService.findAll();
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay pacientes registrados.");
        } else {
            StringBuilder mensaje = new StringBuilder("Listado de pacientes:\n");
            for (Paciente paciente : pacientes) {
                mensaje.append(paciente).append("\n");
            }
            JOptionPane.showMessageDialog(null, mensaje.toString());
        }
    }

    private static void crearPaciente() {
        String codInput = JOptionPane.showInputDialog("Ingrese el código del paciente:");
        int cod = Integer.parseInt(codInput);

        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del paciente:");
        String edadInput = JOptionPane.showInputDialog("Ingrese la edad del paciente:");
        int edad = Integer.parseInt(edadInput);

        String genero = JOptionPane.showInputDialog("Ingrese el género del paciente:");
        String direccion = JOptionPane.showInputDialog("Ingrese la dirección del paciente:");
        String telefonoInput = JOptionPane.showInputDialog("Ingrese el número de teléfono del paciente:");
        long telefono = Long.parseLong(telefonoInput);

        Paciente paciente = new Paciente();
        paciente.setId(cod);
        paciente.setNombre(nombre);
        paciente.setEdad(edad);
        paciente.setGenero(genero);
        paciente.setDireccion(direccion);
        paciente.setTelefono(telefono);

        try {
            pacienteService.save(paciente);
            JOptionPane.showMessageDialog(null, "Paciente creado exitosamente.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private static void actualizarCita() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        String idCitaInput = JOptionPane.showInputDialog("Ingrese el ID de la cita:");
        int idCita = Integer.parseInt(idCitaInput);

        Cita cita = citaService.findByIdCita(idCita);
        if (cita == null) {
            JOptionPane.showMessageDialog(null, "No se encontró la cita con ID " + idCita);
            return;
        }

        String newFecha = JOptionPane.showInputDialog("Ingrese la nueva fecha de la cita (dejar en blanco para no cambiar):");
        if (!newFecha.isEmpty()) {
            try {
                Date fecha = dateFormat.parse(newFecha);
                cita.setFecha(fecha);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Error en el formato de la fecha.");
                return;
            }
        }

        String newHora = JOptionPane.showInputDialog("Ingrese la nueva hora de la cita (dejar en blanco para no cambiar):");
        if (!newHora.isEmpty()) {
            try {
                Date hora = timeFormat.parse(newHora);
                cita.setHora(hora);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Error en el formato de la hora.");
                return;
            }
        }

        String motivo = JOptionPane.showInputDialog("Ingrese el nuevo motivo de la cita (dejar en blanco para no cambiar):");
        if (!motivo.isEmpty()) {
            cita.setMotivo(motivo);
        }

        try {
            citaService.updateCita(cita);
            JOptionPane.showMessageDialog(null, "Cita actualizada exitosamente.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private static void actualizarPaciente() {
        String idInput = JOptionPane.showInputDialog("Ingrese el ID del paciente a actualizar:");
        int id = Integer.parseInt(idInput);

        Paciente paciente = pacienteService.findById(id);
        if (paciente == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el paciente con ID " + id);
            return;
        }

        String nombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del paciente (dejar en blanco para no cambiar):");
        if (!nombre.isEmpty()) {
            paciente.setNombre(nombre);
        }

        String edadInput = JOptionPane.showInputDialog("Ingrese la nueva edad del paciente (dejar en blanco para no cambiar):");
        if (!edadInput.isEmpty()) {
            int edad = Integer.parseInt(edadInput);
            paciente.setEdad(edad);
        }

        String genero = JOptionPane.showInputDialog("Ingrese el nuevo género del paciente (dejar en blanco para no cambiar):");
        if (!genero.isEmpty()) {
            paciente.setGenero(genero);
        }

        String direccion = JOptionPane.showInputDialog("Ingrese la nueva dirección del paciente (dejar en blanco para no cambiar):");
        if (!direccion.isEmpty()) {
            paciente.setDireccion(direccion);
        }

        String telefonoInput = JOptionPane.showInputDialog("Ingrese el nuevo número de teléfono del paciente (dejar en blanco para no cambiar):");
        if (!telefonoInput.isEmpty()) {
            long telefono = Long.parseLong(telefonoInput);
            paciente.setTelefono(telefono);
        }

        try {
            pacienteService.update(paciente);
            JOptionPane.showMessageDialog(null, "Paciente actualizado exitosamente.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private static void eliminarCita() {
        String idInput = JOptionPane.showInputDialog("Ingrese el ID de la cita a eliminar:");
        int id = Integer.parseInt(idInput);

        Cita cita = citaService.findByIdCita(id);
        if (cita == null) {
            JOptionPane.showMessageDialog(null, "No se encontró ninguna cita con el ID " + id);
            return;
        }

        citaService.deleteCita(id);
        JOptionPane.showMessageDialog(null, "Cita eliminada exitosamente.");
    }

    private static void eliminarPaciente() {
        String idInput = JOptionPane.showInputDialog("Ingrese el ID del paciente a eliminar:");
        int id = Integer.parseInt(idInput);

        Paciente paciente = pacienteService.findById(id);
        if (paciente == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el paciente con ID " + id);
            return;
        }

        pacienteService.delete(id);
        JOptionPane.showMessageDialog(null, "Paciente eliminado exitosamente.");
    }
}

