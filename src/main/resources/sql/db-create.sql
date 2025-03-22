CREATE DATABASE hospitaldb;

CREATE SCHEMA IF NOT EXISTS hospital_schema;

SET search_path TO hospital_schema;

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);

CREATE TABLE gender (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);

CREATE TABLE patient (
    id SERIAL PRIMARY KEY,
    last_name VARCHAR(50),
    first_name VARCHAR(50),
    birthday DATE,
    email VARCHAR(50),
    gender_id INT,
    FOREIGN KEY (gender_id) REFERENCES gender(id)
);

CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);

CREATE TABLE diagnosis (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);

CREATE TABLE doctor (
    id SERIAL PRIMARY KEY,
    last_name VARCHAR(50),
    first_name VARCHAR(50),
    category_id INT,
    login VARCHAR(20) UNIQUE,
    password VARCHAR(50),
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE appointment (
    id SERIAL PRIMARY KEY,
    date_create TIMESTAMP,
    diagnosis_id INT,
    patient_id INT,
    doctor_id INT,
    medication VARCHAR(200),
    procedures VARCHAR(200),
    operation VARCHAR(200),
    FOREIGN KEY (diagnosis_id) REFERENCES diagnosis(id),
    FOREIGN KEY (patient_id) REFERENCES patient(id),
    FOREIGN KEY (doctor_id) REFERENCES doctor(id)
);

CREATE TABLE schedule (
    id SERIAL PRIMARY KEY,
    doctor_id INT,
    patient_id INT,
    visit_time TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    FOREIGN KEY (patient_id) REFERENCES patient(id)
);

INSERT INTO roles (name) VALUES ('admin'), ('doctor'), ('nurse');
INSERT INTO gender (name) VALUES ('male'), ('female');

INSERT INTO category (name) VALUES
('Anesthesiologist'), ('Cardiologist'), ('Orthopedist'), ('Gastroenterologist'),
('Dermatologist'), ('Gynecologist'), ('Urologist'), ('Ophthalmologist'),
('Therapist'), ('Nurse');

INSERT INTO diagnosis (name) VALUES
('Rhinitis'), ('Tonsillitis'), ('Quinsy'), ('Bronchitis'), ('Pneumonia'),
('Tuberculosis'), ('Cancer'), ('Tumour'), ('Diabetes'), ('Anaemia'),
('Migraine'), ('Hypertension'), ('Arthritis'), ('Rheumatism'), ('Cyst'),
('Myoma'), ('Meningitis'), ('Asthma'), ('Allergy'), ('Stroke'),
('Heart attack'), ('Myocardial infarction'), ('Leukemia'), ('Hepatitis'),
('Renal failure'), ('Diphtheria'), ('Herpes'), ('Flat-footedness'),
('Appendicitis'), ('Stomach ulcer'), ('Gastritis'), ('Cirrhosis'),
('Epilepsy'), ('Acne'), ('Myopia'), ('Long sight'), ('Conjunctivitis'),
('Dysentery'), ('Mumps'), ('Chicken pox'), ('Measles'), ('German measles'),
('Malaria'), ('Cholera');

INSERT INTO patient (last_name, first_name, birthday, email, gender_id) VALUES
('Petrov', 'Sergey','1980-10-1','ff@ab.com',1),
('Andreeva', 'Inna','1985-2-11','ff@ab.com',2),
('Belova', 'Maria','1991-10-5','ff@ab.com',2),
('Korban', 'Vitaliy','1995-3-7','ff@ab.com',1),
('Sergeev', 'Ivan','1990-10-1','ff@ab.com',1),
('Sergeeva', 'Anna','1988-2-11','ff@ab.com',2),
('Belov', 'Ivan','1995-11-15','ff@ab.com',1),
('Korban', 'Andrey','1994-8-9','ff@ab.com',1),
('Lebedev', 'Mark','1973-11-2','ff@ab.com',1),
('Lebedeva', 'Vera','1985-3-22','ff@ab.com',2),
('Ivanov', 'Sergey','1993-10-10','ff@ab.com',1),
('Korban', 'Evgeniy','1994-8-9','ff@ab.com',1);

INSERT INTO doctor (last_name, first_name, category_id, login, password, role_id) VALUES
('Ignat', 'Petr',1,'IgnatP','698d51a19d8a121ce581499d7b701668',1),
('Ignat', 'Vera',2,'IgnatV','698d51a19d8a121ce581499d7b701668',2),
('Arno', 'Sergey',1,'ArnoS','698d51a19d8a121ce581499d7b701668',3),
('Muhin', 'Oleg',3,'MuhinO','698d51a19d8a121ce581499d7b701668',2),
('Vetrov', 'Igor',1,'VetrovI','698d51a19d8a121ce581499d7b701668',1),
('Kostin', 'Ivan',2,'KostinI','698d51a19d8a121ce581499d7b701668',2),
('Ileev', 'Sergey',1,'IleevS','698d51a19d8a121ce581499d7b701668',3),
('Zubina', 'Olena',3,'ZubinaO','698d51a19d8a121ce581499d7b701668',2),
('Orlova', 'Inna',1,'OrlovaI','698d51a19d8a121ce581499d7b701668',2),
('Coval', 'Andrey',2,'CovalA','698d51a19d8a121ce581499d7b701668',2),
('Sergeev', 'Denis',1,'SergeevD','698d51a19d8a121ce581499d7b701668',3),
('Kuzin', 'Dmitriy',3,'KuzinD','698d51a19d8a121ce581499d7b701668',2);

INSERT INTO appointment (date_create, diagnosis_id, patient_id, doctor_id, medication) VALUES
('2022-10-12', 2, 3, 4, 'noshpa 3t in day, 7 days'),
('2022-10-14', 2, 3, 4, 'loperamid 3t in day, 7 days'),
('2022-10-15', 3, 3, 1, 'all without sugar');

INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES
(1, 5,'2022-11-28'),
(1, 6,'2022-11-29'),
(2, 8,'2022-11-28'),
(2, 8,'2022-11-29');