package com.epam.hospital.repository.elements;

import com.epam.hospital.model.*;
import com.epam.hospital.repository.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentCardRepository extends GlobalRepository<AppointmentCard> {
    private static AppointmentCardRepository  appointmentCardRepository;

    private AppointmentCardRepository() {
    }

    public static AppointmentCardRepository getRepository(){
        if (appointmentCardRepository==null)  {
            appointmentCardRepository = new AppointmentCardRepository();
            connectionPool = ConnectionPool.getInstance();
        }
        return appointmentCardRepository;
    }

    public List<AppointmentCard> getAllAppointmentCards(int appointment_id) throws DBException{
        return appointmentCardRepository.findAll(Constants.GET_ALL_CARDS_BY_APPOINTMENT, appointment_id);
    }

    public boolean create(AppointmentCard appointmentCard) throws DBException {

        int idAppointmentCard = appointmentCardRepository.insert(getQueryInsert(appointmentCard, true),null, appointmentCard.getAppointment().getId(),appointmentCard.getDescription());
            return idAppointmentCard >=0;

    }
    public boolean delete(AppointmentCard appointmentCard) throws DBException {
        return appointmentCardRepository.delete(getQueryInsert(appointmentCard, false),null, appointmentCard.getId());
    }

    @Override
    protected AppointmentCard readByResultSet(ResultSet rs) throws SQLException, DBException {
        while(rs.next()){
            return getAppointmentCard(rs);
        }
        return null;
    }

    private AppointmentCard getAppointmentCard(ResultSet rs) throws SQLException, DBException {
        Appointment appointment=AppointmentRepository.getRepository().readByID(rs.getInt(Fields.APPOINTMENT_ID));
        AppointmentCard appointmentCard = AppointmentCard.createAppointmentCard(
                appointment,
                getMedical(rs.getInt(Fields.TYPE), rs.getString(Fields.MEDICAL_NAME)),
                rs.getString(Fields.DESCRIPTION));
        appointmentCard.setId(rs.getInt(Fields.ID));
        return appointmentCard;
    }

    private Medical getMedical(int numType, String name){
        Medical medical;
        if (numType==1){
            medical= Medication.createInstance(name);
        }
        else if (numType==2){
            medical= Procedure.createInstance(name);
        }
        else{
            medical= Operation.createInstance(name);
        }
        return medical;
    }
    private String getQueryInsert(AppointmentCard appointmentCard, boolean isInsert){
        if (appointmentCard.getMedical() instanceof Medication){
            return isInsert?Constants.ADD_CARD_MEDICATION:Constants.DELETE_CARD_MEDICATION_BY_APPOINTMENT;
        }
        else if (appointmentCard.getMedical() instanceof Procedure){
            return isInsert?Constants.ADD_CARD_PROCEDURE:Constants.DELETE_CARD_PROCEDURE_BY_APPOINTMENT;
        }
        else{
            return isInsert?Constants.ADD_CARD_OPERATION:Constants.DELETE_CARD_OPERATION_BY_APPOINTMENT;
        }
    }

    @Override
    protected List<AppointmentCard> findByResultSet(ResultSet rs) throws SQLException, DBException {
        List<AppointmentCard> list = new ArrayList<>();
        while(rs.next()){
            list.add(getAppointmentCard(rs));
        }
        return list;
    }
}
