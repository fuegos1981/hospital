DROP database IF EXISTS hospitaldb;

CREATE database hospitaldb;

USE hospitaldb;

CREATE TABLE roles (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE
);
CREATE TABLE status (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE
);

CREATE TABLE gender (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE
);
CREATE TABLE person (
id INT PRIMARY KEY auto_increment,
	last_name VARCHAR(50),
    first_name VARCHAR(50),
    birthday date,
    email VARCHAR(50),
    gender_id INT,
	foreign key (gender_id) references gender(id)
);

CREATE TABLE category (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE
);

CREATE TABLE diagnosis (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE
);
CREATE TABLE procedures (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE
);
CREATE TABLE medication (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE
);

CREATE TABLE operation (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE
);

CREATE TABLE doctor (
id INT PRIMARY KEY auto_increment,
person_id INT,
category_id INT,
	foreign key (person_id) references person(id),
    foreign key (category_id) references category(id)
);


CREATE TABLE patient (
id INT PRIMARY KEY auto_increment,
person_id INT,
	foreign key (person_id) references person(id)
);

CREATE TABLE users (
	id INT PRIMARY KEY auto_increment,
	login VARCHAR(20) UNIQUE,
    password VARCHAR(20),
    role_id INT,
    foreign key (role_id) references roles(id)
);

CREATE TABLE appointment (
	id INT PRIMARY KEY auto_increment,
	date_create datetime,
    diagnosis_id INT,
    patient_id INT,
    doctor_id INT, 
    status_id INT,
    foreign key (diagnosis_id) references diagnosis(id),
    foreign key (patient_id) references patient(id),
    foreign key (doctor_id) references doctor(id),
    foreign key (status_id) references status(id)
);
CREATE TABLE appointment_procedure (
id INT PRIMARY KEY auto_increment,
	appointment_id INT REFERENCES appointment(id) ,
	procedure_id INT REFERENCES procedures(id),
    description VARCHAR(200),
    UNIQUE (appointment_id, procedure_id),
    foreign key (appointment_id) references appointment(id),
    foreign key (procedure_id) references procedures(id)
);

CREATE TABLE appointment_medication (
id INT PRIMARY KEY auto_increment,
	appointment_id INT REFERENCES appointment(id) ,
	medication_id INT REFERENCES medication(id),
    description VARCHAR(200),
    UNIQUE (appointment_id, medication_id),
    foreign key (appointment_id) references appointment(id),
    foreign key (medication_id) references medication(id)
);

CREATE TABLE appointment_operation (
id INT PRIMARY KEY auto_increment,
	appointment_id INT REFERENCES appointment(id) ,
	operation_id INT REFERENCES operation(id),
    description VARCHAR(200),
    UNIQUE (appointment_id, operation_id),
    foreign key (appointment_id) references appointment(id),
    foreign key (operation_id) references operation(id)
);

CREATE TABLE schedule (
	id INT PRIMARY KEY auto_increment,
	doctor_id INT,
    patient_id INT,
    visit_time datetime,
    foreign key (doctor_id) references doctor(id),
    foreign key (patient_id) references patient(id)
);





INSERT INTO roles (name) VALUES ('admin');
INSERT INTO roles (name) VALUES ('doctor');
INSERT INTO roles (name) VALUES ('nurse');

INSERT INTO status (name) VALUES ('new');
INSERT INTO status (name) VALUES ('in progress');
INSERT INTO status (name) VALUES ('done');
INSERT INTO status (name) VALUES ('canceled');

INSERT INTO users (login, password, role_id) VALUES ('admin', '111',1);
INSERT INTO users (login, password, role_id) VALUES ('doctor', '111',2);
INSERT INTO users (login, password, role_id) VALUES ('nurse', '111',3);

INSERT INTO gender (name) VALUES ('male');
INSERT INTO gender (name) VALUES ('female');


INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Petrov', 'Sergey','1980-10-1','ff@ab.com',1);
INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Andreeva', 'Inna','1985-2-11','ff@ab.com',2);
INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Belova', 'Maria','1991-10-5','ff@ab.com',2);
INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Korban', 'Vitaliy','1995-3-7','ff@ab.com',1);

INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Sergeev', 'Ivan','1990-10-1','ff@ab.com',1);
INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Sergeeva', 'Anna','1988-2-11','ff@ab.com',2);
INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Belov', 'Ivan','1995-11-15','ff@ab.com',1);
INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Korban', 'Andrey','1994-8-9','ff@ab.com',1);

INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Lebedev', 'Mark','1973-11-2','ff@ab.com',1);
INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Lebedeva', 'Vera','1985-3-22','ff@ab.com',2);
INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Ivanov', 'Sergey','1993-10-10','ff@ab.com',1);
INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Korban', 'Evgeniy','1994-8-9','ff@ab.com',1);

INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Ignat', 'Petr','1978-1-5','ff@ab.com',1);
INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Ignat', 'Vera','1978-2-21','ff@ab.com',2);
INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Arno', 'Sergey','1992-10-14','ff@ab.com',1);
INSERT INTO person (last_name, first_name, birthday, email, gender_id) VALUES ('Muhin', 'Oleg','1995-9-9','ff@ab.com',1);

INSERT INTO category (name) VALUES ('pediatrician');
INSERT INTO category (name) VALUES ('traumatologist');
INSERT INTO category (name) VALUES ('surgeon');

INSERT INTO category (name) VALUES ('Anesthesiologist');
INSERT INTO category (name) VALUES ('Cardiologist');
INSERT INTO category (name) VALUES ('Orthopedist');
INSERT INTO category (name) VALUES ('Gastroenterologist');
INSERT INTO category (name) VALUES ('Dermatologist');
INSERT INTO category (name) VALUES ('Gynecologist');
INSERT INTO category (name) VALUES ('Urologist');
INSERT INTO category (name) VALUES ('Ophthalmologist');
INSERT INTO category (name) VALUES ('Therapist');
INSERT INTO category (name) VALUES ('Nurse');
INSERT INTO doctor (person_id, category_id) VALUES (1,2);
INSERT INTO doctor (person_id, category_id) VALUES (2,3);
INSERT INTO doctor (person_id, category_id) VALUES (3,2);
INSERT INTO doctor (person_id, category_id) VALUES (4,3);

INSERT INTO patient (person_id) VALUES (5);
INSERT INTO patient (person_id) VALUES (6);
INSERT INTO patient (person_id) VALUES (7);
INSERT INTO patient (person_id) VALUES (8);
INSERT INTO patient (person_id) VALUES (9);
INSERT INTO patient (person_id) VALUES (10);
INSERT INTO patient (person_id) VALUES (11);
INSERT INTO patient (person_id) VALUES (12);
INSERT INTO patient (person_id) VALUES (13);
INSERT INTO patient (person_id) VALUES (14);
INSERT INTO patient (person_id) VALUES (15);
INSERT INTO patient (person_id) VALUES (16);

INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES (1, 5,'2022-11-28');
INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES (1, 6,'2022-11-29');
INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES (2, 8,'2022-11-28');
INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES (2, 8,'2022-11-29');


INSERT INTO diagnosis (name) VALUES ('rhinitis');
INSERT INTO diagnosis (name) VALUES ('tonsillitis');
INSERT INTO diagnosis (name) VALUES ('quinsy');
INSERT INTO diagnosis (name) VALUES ('bronchitis');
INSERT INTO diagnosis (name) VALUES ('pneumonia');
INSERT INTO diagnosis (name) VALUES ('tuberculosis');
INSERT INTO diagnosis (name) VALUES ('cancer');
INSERT INTO diagnosis (name) VALUES ('tumour');
INSERT INTO diagnosis (name) VALUES ('diabetes');
INSERT INTO diagnosis (name) VALUES ('anaemia');
INSERT INTO diagnosis (name) VALUES ('migraine');
INSERT INTO diagnosis (name) VALUES ('hypertension');
INSERT INTO diagnosis (name) VALUES ('arthritis');
INSERT INTO diagnosis (name) VALUES ('rheumatism');
INSERT INTO diagnosis (name) VALUES ('cyst');
INSERT INTO diagnosis (name) VALUES ('myoma');
INSERT INTO diagnosis (name) VALUES ('meningitis');
INSERT INTO diagnosis (name) VALUES ('asthma');
INSERT INTO diagnosis (name) VALUES ('allergy');
INSERT INTO diagnosis (name) VALUES ('stroke');
INSERT INTO diagnosis (name) VALUES ('heart attack');
INSERT INTO diagnosis (name) VALUES ('myocardial infarction');
INSERT INTO diagnosis (name) VALUES ('leukemia');
INSERT INTO diagnosis (name) VALUES ('hepatitis');
INSERT INTO diagnosis (name) VALUES ('renal failure');
INSERT INTO diagnosis (name) VALUES ('diphtheria');
INSERT INTO diagnosis (name) VALUES ('herpes');
INSERT INTO diagnosis (name) VALUES ('psoriasis');
INSERT INTO diagnosis (name) VALUES ('brain concussion');
INSERT INTO diagnosis (name) VALUES ('insomnia');
INSERT INTO diagnosis (name) VALUES ('infertility');
INSERT INTO diagnosis (name) VALUES ('blindness');
INSERT INTO diagnosis (name) VALUES ('deafness');
INSERT INTO diagnosis (name) VALUES ('flat-footedness');
INSERT INTO diagnosis (name) VALUES ('appendicitis');
INSERT INTO diagnosis (name) VALUES ('stomach ulcer');
INSERT INTO diagnosis (name) VALUES ('gastritis');
INSERT INTO diagnosis (name) VALUES ('cirrhosis');
INSERT INTO diagnosis (name) VALUES ('epilepsy');
INSERT INTO diagnosis (name) VALUES ('acne');
INSERT INTO diagnosis (name) VALUES ('myopia');
INSERT INTO diagnosis (name) VALUES ('long sight');
INSERT INTO diagnosis (name) VALUES ('conjunctivitis');
INSERT INTO diagnosis (name) VALUES ('dysentery');
INSERT INTO diagnosis (name) VALUES ('mumps');
INSERT INTO diagnosis (name) VALUES ('chicken pox');
INSERT INTO diagnosis (name) VALUES ('measles');
INSERT INTO diagnosis (name) VALUES ('German measles');
INSERT INTO diagnosis (name) VALUES ('malaria');
INSERT INTO diagnosis (name) VALUES ('cholera');


INSERT INTO procedures (name) VALUES ('Urine sample');
INSERT INTO procedures (name) VALUES ('Blood sample');
INSERT INTO procedures (name) VALUES ('massage');
INSERT INTO procedures (name) VALUES ('Magnetotherapy');

INSERT INTO medication (name) VALUES ('loperamide');
INSERT INTO medication (name) VALUES ('sodium chloride');
INSERT INTO medication (name) VALUES ('folic acid');
INSERT INTO medication (name) VALUES ('biotin');

INSERT INTO operation (name) VALUES ('пластика нижней губы');
INSERT INTO operation (name) VALUES ('пневмотомия');
INSERT INTO operation (name) VALUES ('Удаление кисты');
INSERT INTO operation (name) VALUES ('Мастектомия');

INSERT INTO appointment (date_create, diagnosis_id, patient_id, doctor_id) VALUES ('2022-10-12', '2', '3', '4');
INSERT INTO appointment (date_create, diagnosis_id, patient_id, doctor_id) VALUES ('2022-10-14', '2', '3', '4');
INSERT INTO appointment (date_create, diagnosis_id, patient_id, doctor_id) VALUES ('2022-10-15', '3', '3', '1');

INSERT INTO appointment_medication (appointment_id, medication_id, description) VALUES ('1', '2', 'aaaaa');
INSERT INTO appointment_medication (appointment_id, medication_id, description) VALUES ('1', '3', 'bbbbbb');
INSERT INTO appointment_medication (appointment_id, medication_id, description) VALUES ('2', '3', 'dddddd');
INSERT INTO appointment_medication (appointment_id, medication_id, description) VALUES ('2', '4', 'ffffff');
INSERT INTO appointment_medication (appointment_id, medication_id, description) VALUES ('3', '1', 'ttyuiioi');

INSERT INTO appointment_procedure (appointment_id, procedure_id, description) VALUES ('1', '2', 'rrrrrr');
INSERT INTO appointment_procedure (appointment_id, procedure_id, description) VALUES ('1', '3', 'tttttt');
INSERT INTO appointment_procedure (appointment_id, procedure_id, description) VALUES ('2', '3', 'yyyyyyyy');
INSERT INTO appointment_procedure (appointment_id, procedure_id, description) VALUES ('2', '4', 'uuuuuu');
INSERT INTO appointment_procedure (appointment_id, procedure_id, description) VALUES ('3', '1', 'uutrei');

INSERT INTO appointment_operation (appointment_id, operation_id, description) VALUES ('1', '2', 'ooooo');
INSERT INTO appointment_operation (appointment_id, operation_id, description) VALUES ('1', '3', 'rrrrr');
INSERT INTO appointment_operation (appointment_id, operation_id, description) VALUES ('2', '3', 'wwwww');
INSERT INTO appointment_operation (appointment_id, operation_id, description) VALUES ('2', '4', 'ddddd');
INSERT INTO appointment_operation (appointment_id, operation_id, description) VALUES ('3', '1', 'llllll');
