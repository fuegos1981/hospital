package com.epam.hospital.repository.elements;

import com.epam.hospital.model.*;
import com.epam.hospital.repository.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AppointmentRepository extends GlobalRepository<Appointment> {
    private static AppointmentRepository  appointmentRepository;

    private AppointmentRepository() {
    }


    public static AppointmentRepository getRepository(){
        if (appointmentRepository==null)  {
            appointmentRepository = new AppointmentRepository();
            //connectionPool = ConnectionPool.getInstance();
        }
        return appointmentRepository;
    }

    public Appointment readByID(int id) throws DBException{
        return appointmentRepository.read(Constants.GET_APPOINTMENT_BY_ID,id);
    }
    public List<Appointment> getAllAppointments() throws DBException {
        return appointmentRepository.findAll(Constants.GET_ALL_APPOINTMENTS);
    }
    public List<Appointment> getAllAppointments(Map<String, Integer> selection) throws DBException {

        return appointmentRepository.findAll(Constants.GET_ALL_APPOINTMENTS+RepositoryUtils.getSelectionString(selection),
                selection.values().toArray());
    }
    public boolean create(Appointment appointment) throws DBException {

        int idAppointment = appointmentRepository.insert(Constants.ADD_APPOINTMENT,
                appointment.getDateCreate(),
                appointment.getDiagnosis().getId(),
                appointment.getPatient().getId(),
                appointment.getDoctor().getId(),
                appointment.getMedication(),
                appointment.getProcedure(),
                appointment.getOperation());
        appointment.setId(idAppointment);
        return idAppointment>0;
    }

    public boolean updateAppointment(Appointment appointment) throws DBException {

        Object[] objects = {appointment.getDateCreate(), appointment.getDiagnosis().getId(),
                appointment.getPatient().getId(), appointment.getDoctor().getId(),
                appointment.getMedication(),appointment.getProcedure(), appointment.getOperation(), appointment.getId()};
        return appointmentRepository.update(Constants.UPDATE_APPOINTMENT, objects);
    }
    public boolean delete(Appointment appointment) throws DBException {
        List<Object[]> listFilters = new ArrayList<>();
        listFilters.add(new Object[]{appointment.getId()});

        return appointmentRepository.delete(Constants.DELETE_APPOINTMENT, appointment.getId());
    }

    @Override
    protected Appointment readByResultSet(ResultSet rs) throws SQLException, DBException {
        while(rs.next()){
            return getAppointment(rs);
        }
        return null;
    }

    private Appointment getAppointment(ResultSet rs) throws SQLException, DBException {
        Date dateCreate= rs.getDate(Fields.DATE_CREATE);
        Diagnosis diagnosis= (Diagnosis) SimpleRepository.getRepository(Constants.DIAGNOSIS).readByID(rs.getInt(Fields.DIAGNOSIS_ID));
        Patient patient= PatientRepository.getRepository().readByID(rs.getInt(Fields.PATIENT_ID));
        Doctor doctor = DoctorRepository.getRepository().readByID(rs.getInt(Fields.DOCTOR_ID));
        Appointment appointment = Appointment.createAppointment(dateCreate, diagnosis,patient, doctor);
        appointment.setMedication(rs.getString(Fields.MEDICATION));
        appointment.setProcedure(rs.getString(Fields.PROCEDURE));
        appointment.setOperation(rs.getString(Fields.OPERATION));
        appointment.setId(rs.getInt(Fields.ID));
        return appointment;
    }

    @Override
    protected List<Appointment> findByResultSet(ResultSet rs) throws SQLException, DBException {
        List<Appointment> list = new ArrayList<>();
        while(rs.next()){
            list.add(getAppointment(rs));
        }
        return list;
    }

    public List<Appointment> readAppointmentByPatientID(int patientId) throws DBException {
        return appointmentRepository.findAll(Constants.GET_ALL_APPOINTMENTS_BY_PATIENT, patientId);
    }
    public int getSize() throws DBException {
        return appointmentRepository.readSize(Constants.GET_SIZE_APPOINTMENT);
    }
}
