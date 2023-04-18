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
password VARCHAR(50),
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
	medication VARCHAR(200),
    procedures VARCHAR(200),
    operation VARCHAR(200),
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

INSERT INTO diagnosis (name) VALUES ('Rhinitis');
INSERT INTO diagnosis (name) VALUES ('Tonsillitis');
INSERT INTO diagnosis (name) VALUES ('Quinsy');
INSERT INTO diagnosis (name) VALUES ('Bronchitis');
INSERT INTO diagnosis (name) VALUES ('Pneumonia');
INSERT INTO diagnosis (name) VALUES ('Tuberculosis');
INSERT INTO diagnosis (name) VALUES ('Cancer');
INSERT INTO diagnosis (name) VALUES ('Tumour');
INSERT INTO diagnosis (name) VALUES ('Diabetes');
INSERT INTO diagnosis (name) VALUES ('Anaemia');
INSERT INTO diagnosis (name) VALUES ('Migraine');
INSERT INTO diagnosis (name) VALUES ('Hypertension');
INSERT INTO diagnosis (name) VALUES ('Arthritis');
INSERT INTO diagnosis (name) VALUES ('Rheumatism');
INSERT INTO diagnosis (name) VALUES ('Cyst');
INSERT INTO diagnosis (name) VALUES ('Myoma');
INSERT INTO diagnosis (name) VALUES ('Meningitis');
INSERT INTO diagnosis (name) VALUES ('Asthma');
INSERT INTO diagnosis (name) VALUES ('Allergy');
INSERT INTO diagnosis (name) VALUES ('Stroke');
INSERT INTO diagnosis (name) VALUES ('Heart attack');
INSERT INTO diagnosis (name) VALUES ('Myocardial infarction');
INSERT INTO diagnosis (name) VALUES ('Leukemia');
INSERT INTO diagnosis (name) VALUES ('Hepatitis');
INSERT INTO diagnosis (name) VALUES ('Renal failure');
INSERT INTO diagnosis (name) VALUES ('Diphtheria');
INSERT INTO diagnosis (name) VALUES ('Herpes');
INSERT INTO diagnosis (name) VALUES ('Flat-footedness');
INSERT INTO diagnosis (name) VALUES ('Appendicitis');
INSERT INTO diagnosis (name) VALUES ('Stomach ulcer');
INSERT INTO diagnosis (name) VALUES ('Gastritis');
INSERT INTO diagnosis (name) VALUES ('Cirrhosis');
INSERT INTO diagnosis (name) VALUES ('Epilepsy');
INSERT INTO diagnosis (name) VALUES ('Acne');
INSERT INTO diagnosis (name) VALUES ('Myopia');
INSERT INTO diagnosis (name) VALUES ('Long sight');
INSERT INTO diagnosis (name) VALUES ('Conjunctivitis');
INSERT INTO diagnosis (name) VALUES ('Dysentery');
INSERT INTO diagnosis (name) VALUES ('Mumps');
INSERT INTO diagnosis (name) VALUES ('Chicken pox');
INSERT INTO diagnosis (name) VALUES ('Measles');
INSERT INTO diagnosis (name) VALUES ('German measles');
INSERT INTO diagnosis (name) VALUES ('Malaria');
INSERT INTO diagnosis (name) VALUES ('Cholera');


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

INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Ignat', 'Petr',1,'IgnatP','698d51a19d8a121ce581499d7b701668',1);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Ignat', 'Vera',2,'IgnatV','698d51a19d8a121ce581499d7b701668',2);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Arno', 'Sergey',1,'ArnoS','698d51a19d8a121ce581499d7b701668',3);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Muhin', 'Oleg',3,'MuhinO','698d51a19d8a121ce581499d7b701668',2);

INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Vetrov', 'Igor',1,'VetrovI','698d51a19d8a121ce581499d7b701668',1);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Kostin', 'Ivan',2,'KostinI','698d51a19d8a121ce581499d7b701668',2);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Ileev', 'Sergey',1,'IleevS','698d51a19d8a121ce581499d7b701668',3);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Zubina', 'Olena',3,'ZubinaO','698d51a19d8a121ce581499d7b701668',2);

INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Orlova', 'Inna',1,'OrlovaI','698d51a19d8a121ce581499d7b701668',2);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Coval', 'Andrey',2,'CovalA','698d51a19d8a121ce581499d7b701668',2);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Sergeev', 'Denis',1,'SergeevD','698d51a19d8a121ce581499d7b701668',3);
INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES ('Kuzin', 'Dmitriy',3,'KuzinD','698d51a19d8a121ce581499d7b701668',2);

INSERT INTO appointment (date_create, diagnosis_id, patient_id, doctor_id, medication) VALUES ('2022-10-12', '2', '3', '4','noshpa 3t in day, 7 days');
INSERT INTO appointment (date_create, diagnosis_id, patient_id, doctor_id, medication) VALUES ('2022-10-14', '2', '3', '4','loperamid 3t in day, 7 days');
INSERT INTO appointment (date_create, diagnosis_id, patient_id, doctor_id, medication) VALUES ('2022-10-15', '3', '3', '1', 'all without sugar');

INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES (1, 5,'2022-11-28');
INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES (1, 6,'2022-11-29');
INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES (2, 8,'2022-11-28');
INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES (2, 8,'2022-11-29');