package com.epam.hospital.repository.elements;

import com.epam.hospital.model.*;
import com.epam.hospital.repository.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentRepository extends GlobalRepository<Appointment> {
    private static AppointmentRepository  appointmentRepository;

    private AppointmentRepository() {
    }


    public static AppointmentRepository getRepository(){
        if (appointmentRepository==null)  {
            appointmentRepository = new AppointmentRepository();
            connectionPool = ConnectionPool.getInstance();
        }
        return appointmentRepository;
    }

    public Appointment readByID(int id) throws DBException{
        return appointmentRepository.read(Constants.GET_APPOINTMENT_BY_ID,id);
    }
    public List<Appointment> getAllAppointments() throws DBException {
        return appointmentRepository.findAll(Constants.GET_ALL_APPOINTMENTS);
    }

    public boolean create(Appointment appointment) throws DBException {
        Map<String, List<Object[]>> batch = getBatch(appointment);
        int idAppointment = appointmentRepository.insert(Constants.ADD_APPOINTMENT,batch,
                appointment.getDateCreate(),
                appointment.getDiagnosis().getId(),
                appointment.getPatient().getId(),
                appointment.getDoctor().getId());
        appointment.setId(idAppointment);

        return idAppointment>0;
    }

    private Map<String, List<Object[]>> getBatch(Appointment appointment) {
        Map<String,List<Object[]>> batch = new HashMap<>();
        List<AppointmentCard> listMed = appointment.getListCard().stream()
                .filter(e->e.getMedical() instanceof Medication)
                .collect(Collectors.toList());
        List<AppointmentCard> listProc = appointment.getListCard().stream()
                .filter(e->e.getMedical() instanceof Procedure)
                .collect(Collectors.toList());
        List<AppointmentCard> listOper = appointment.getListCard().stream()
                .filter(e->e.getMedical() instanceof Operation)
                .collect(Collectors.toList());
        if (listMed.size()>0){
            batch.put(Constants.ADD_CARD_MEDICATION,getListObjects(listMed));
        }
        if (listProc.size()>0){
            batch.put(Constants.ADD_CARD_PROCEDURE,getListObjects(listProc));
        }
        if (listOper.size()>0){
            batch.put(Constants.ADD_CARD_MEDICATION,getListObjects(listOper));
        }
        return batch;
    }

    private static List<Object[]> getListObjects(List<AppointmentCard> ListCards){
        List<Object[]> list = new ArrayList<>();
        for (AppointmentCard appointmentCard:   ListCards) {
            list.add(new Object[]{appointmentCard.getAppointment().getId()
                    ,appointmentCard.getMedical().getId()
                    ,appointmentCard.getDescription()});
        }
        return list;
    }

    public boolean delete(Appointment appointment) throws DBException {
        Map<String,List<Object[]>> batch = new HashMap<>();
        List<Object[]> listFilters = new ArrayList<>();
        listFilters.add(new Object[]{appointment.getId()});

        batch.put(Constants.DELETE_CARD_MEDICATION_BY_APPOINTMENT, listFilters);
        batch.put(Constants.DELETE_CARD_PROCEDURE_BY_APPOINTMENT,listFilters);
        batch.put(Constants.DELETE_CARD_OPERATION_BY_APPOINTMENT,listFilters);

        return appointmentRepository.delete(Constants.DELETE_APPOINTMENT, batch, appointment.getId());
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
}
