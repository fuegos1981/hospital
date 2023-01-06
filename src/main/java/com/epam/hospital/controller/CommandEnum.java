package com.epam.hospital.controller;

import com.epam.hospital.controller.commands.*;

/**
 * This enum is a repository of commands.
 * @author Sinkevych Olena
 *
 */
public enum CommandEnum {
    LOGIN {
        {this.command = new LoginCommand();}
    },
    LOGOUT {
        {this.command = new LogoutCommand();}
    },
    ADMIN {
        {this.command = new AdminCommand();}
    },
    ADD_SCHEDULE {
        {this.command = new AddScheduleCommand();}
    },
    PATIENT_INFO {
        {this.command = new PatientInfoCommand();}
    },
    EDIT_SIMPLE {
        {this.command = new EditSimpleCommand();}
    },
    EDIT_DOCTOR {
        {this.command = new CreateDoctorCommand();}
    },
    EDIT_APPOINTMENT {
        {this.command = new CreateAppointmentCommand();}
    },
    DELETE_SCHEDULE {
        {this.command = new DeleteScheduleCommand();}
    },
    MEDIC {
        {this.command = new MedicCommand();}
    },
    EDIT_PATIENT {
        {this.command = new CreatePatientCommand();}
    };
    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
