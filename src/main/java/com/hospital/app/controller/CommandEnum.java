package com.hospital.app.controller;

import com.hospital.app.controller.commands.AddScheduleCommand;
import com.hospital.app.controller.commands.AdminCommand;
import com.hospital.app.controller.commands.CreateAppointmentCommand;
import com.hospital.app.controller.commands.CreateDoctorCommand;
import com.hospital.app.controller.commands.CreatePatientCommand;
import com.hospital.app.controller.commands.DeleteScheduleCommand;
import com.hospital.app.controller.commands.DeleteSimpleCommand;
import com.hospital.app.controller.commands.EditSimpleCommand;
import com.hospital.app.controller.commands.LoginCommand;
import com.hospital.app.controller.commands.LogoutCommand;
import com.hospital.app.controller.commands.MedicCommand;
import com.hospital.app.controller.commands.PatientInfoCommand;
import com.hospital.app.controller.commands.SimpleCommand;

/**
 * This enum is a repository of commands.
 *
 * @author Sinkevych Olena
 */
public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    ADMIN {
        {
            this.command = new AdminCommand();
        }
    },
    ADD_SCHEDULE {
        {
            this.command = new AddScheduleCommand();
        }
    },
    PATIENT_INFO {
        {
            this.command = new PatientInfoCommand();
        }
    },
    EDIT_SIMPLE {
        {
            this.command = new EditSimpleCommand();
        }
    },
    EDIT_DOCTOR {
        {
            this.command = new CreateDoctorCommand();
        }
    },
    EDIT_APPOINTMENT {
        {
            this.command = new CreateAppointmentCommand();
        }
    },
    DELETE_SCHEDULE {
        {
            this.command = new DeleteScheduleCommand();
        }
    },
    MEDIC {
        {
            this.command = new MedicCommand();
        }
    },
    SIMPLE {
        {
            this.command = new SimpleCommand();
        }
    },
    DELETE_SIMPLE {
        {
            this.command = new DeleteSimpleCommand();
        }
    },
    EDIT_PATIENT {
        {
            this.command = new CreatePatientCommand();
        }
    };
    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
