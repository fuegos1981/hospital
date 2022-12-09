package com.epam.hospital.controller;

import com.epam.hospital.controller.commands.*;

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
    CREATE_PATIENT {
        {this.command = new CreatePatientCommand();}
    };
    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
