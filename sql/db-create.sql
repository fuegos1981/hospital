DROP database IF EXISTS hospitaldb;

CREATE database hospitaldb;

USE hospitaldb;

CREATE TABLE roles (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE
);

CREATE TABLE gender (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE
);
CREATE TABLE patient (
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

CREATE TABLE doctor (
id INT PRIMARY KEY auto_increment,
last_name VARCHAR(50),
first_name VARCHAR(50),
category_id INT,
login VARCHAR(20) UNIQUE,
password VARCHAR(20),
role_id INT,
	foreign key (role_id) references roles(id),
    foreign key (category_id) references category(id)
);


CREATE TABLE appointment (
	id INT PRIMARY KEY auto_increment,
	date_create datetime,
    diagnosis_id INT,
    patient_id INT,
    doctor_id INT,
	medication_description VARCHAR(200),
    procedure_description VARCHAR(200),
    operation_description VARCHAR(200),
    foreign key (diagnosis_id) references diagnosis(id),
    foreign key (patient_id) references patient(id),
    foreign key (doctor_id) references doctor(id)
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

INSERT INTO gender (name) VALUES ('male');
INSERT INTO gender (name) VALUES ('female');

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


INSERT INTO patient (last_name, first_name, birthday, email, gender_id) VALUES ('Petrov', 'Sergey','1980-10-1','ff@ab.com',1);
INSERT INTO patient (last_name, first_name, birthday, email, gender_id) VALUES ('Andreeva', 'Inna','1985-2-11','ff@ab.com',2);
INSERT INTO patient (last_name, first_name, birthday, email, gender_id) VALUES ('Belova', 'Maria','1991-10-5','ff@ab.com',2);
INSERT INTO patient (last_name, first_name, birthday, email, gender_id) VALUES ('Korban', 'Vitaliy','1995-3-7','ff@ab.com',1);

INSERT INTO patient (last_name, first_name, birthday, email, gender_id) VALUES ('Sergeev', 'Ivan','1990-10-1','ff@ab.com',1);
INSERT INTO patient (last_name, first_name, birthday, email, gender_id) VALUES ('Sergeeva', 'Anna','1988-2-11','ff@ab.com',2);
INSERT INTO patient (last_name, first_name, birthday, email, gender_id) VALUES ('Belov', 'Ivan','1995-11-15','ff@ab.com',1);
INSERT INTO patient (last_name, first_name, birthday, email, gender_id) VALUES ('Korban', 'Andrey','1994-8-9','ff@ab.com',1);

INSERT INTO patient (last_name, first_name, birthday, email, gender_id) VALUES ('Lebedev', 'Mark','1973-11-2','ff@ab.com',1);
INSERT INTO patient (last_name, first_name, birthday, email, gender_id) VALUES ('Lebedeva', 'Vera','1985-3-22','ff@ab.com',2);
INSERT INTO patient (last_name, first_name, birthday, email, gender_id) VALUES ('Ivanov', 'Sergey','1993-10-10','ff@ab.com',1);
INSERT INTO patient (last_name, first_name, birthday, email, gender_id) VALUES ('Korban', 'Evgeniy','1994-8-9','ff@ab.com',1);

INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Ignat', 'Petr',1,'IgnatP','111',1);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Ignat', 'Vera',2,'IgnatV','111',2);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Arno', 'Sergey',1,'ArnoS','111',3);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Muhin', 'Oleg',3,'MuhinO','111',2);

INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Vetrov', 'Igor',1,'VetrovI','111',1);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Kostin', 'Ivan',2,'KostinI','111',2);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Ileev', 'Sergey',1,'IleevS','111',3);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Zubina', 'Olena',3,'ZubinaO','111',2);

INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Orlova', 'Inna',1,'OrlovaI','111',2);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Coval', 'Andrey',2,'CovalA','111',2);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Sergeev', 'Denis',1,'SergeevD','111',3);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Kuzin', 'Dmitriy',3,'KuzinD','111',2);

INSERT INTO appointment (date_create, diagnosis_id, patient_id, doctor_id, medication_description) VALUES ('2022-10-12', '2', '3', '4','noshpa 3t in day, 7 days');
INSERT INTO appointment (date_create, diagnosis_id, patient_id, doctor_id, medication_description) VALUES ('2022-10-14', '2', '3', '4','loperamid 3t in day, 7 days');
INSERT INTO appointment (date_create, diagnosis_id, patient_id, doctor_id, medication_description) VALUES ('2022-10-15', '3', '3', '1', 'not use sugar');

INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES (1, 5,'2022-11-28');
INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES (1, 6,'2022-11-29');
INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES (2, 8,'2022-11-28');
INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES (2, 8,'2022-11-29');