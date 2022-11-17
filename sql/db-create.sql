DROP database IF EXISTS hospitaldb;

CREATE database hospitaldb;

USE hospitaldb;

CREATE TABLE roles (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE
);

CREATE TABLE gender (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE,
    name_ru VARCHAR(50) UNIQUE
);

CREATE TABLE person (
id INT PRIMARY KEY auto_increment,
	last_name VARCHAR(50),
    first_name VARCHAR(50),
    birthday date,
    gender_id INT,
	foreign key (gender_id) references gender(id)
);

CREATE TABLE category (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE,
    name_ru VARCHAR(50) UNIQUE
);

CREATE TABLE diagnosis (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE,
    name_ru VARCHAR(50) UNIQUE
);
CREATE TABLE procedures (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE,
    name_ru VARCHAR(50) UNIQUE
);
CREATE TABLE medication (
id INT PRIMARY KEY auto_increment,
	name VARCHAR(50) UNIQUE,
    name_ru VARCHAR(50) UNIQUE
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
    foreign key (diagnosis_id) references diagnosis(id),
    foreign key (patient_id) references patient(id)
);

CREATE TABLE appointment_procedure (
	appointment_id INT REFERENCES appointment(id) on delete cascade,
	procedure_id INT REFERENCES procedures(id) on delete cascade,
    description VARCHAR(200),
    date_start date,
    date_end date,
	UNIQUE (appointment_id, procedure_id)
);

CREATE TABLE appointment_medication (
	appointment_id INT REFERENCES appointment(id) on delete cascade,
	medication_id INT REFERENCES medication(id) on delete cascade,
    description VARCHAR(200),
	UNIQUE (appointment_id, medication_id)
);

CREATE TABLE appointment_operation (
	appointment_id INT REFERENCES appointment(id) on delete cascade,
	operation_id INT REFERENCES operation(id) on delete cascade,
    description VARCHAR(200),
	UNIQUE (appointment_id, operation_id)
);
INSERT INTO roles (name) VALUES ('Admin');
INSERT INTO roles (name) VALUES ('doctor');
INSERT INTO roles (name) VALUES ('nurse');

INSERT INTO gender (name, name_ru) VALUES ('male','мужской');
INSERT INTO gender (name, name_ru) VALUES ('female','женский');

INSERT INTO person (last_name, first_name, birthday, gender_id) VALUES ('Petrov', 'Sergey','1980-10-1',1);
INSERT INTO person (last_name, first_name, birthday, gender_id) VALUES ('Andreeva', 'Inna','1985-2-11',1);
INSERT INTO person (last_name, first_name, birthday, gender_id) VALUES ('Belova', 'Maria','1991-12-5',1);
INSERT INTO person (last_name, first_name, birthday, gender_id) VALUES ('Korban', 'Vitaliy','1995-3-7',1);

INSERT INTO category (name, name_ru) VALUES ('pediatrician','педиатр');
INSERT INTO category (name, name_ru) VALUES ('traumatologist','травматолог');
INSERT INTO category (name, name_ru) VALUES ('surgeon','хирург');

INSERT INTO category (name, name_ru) VALUES ('Anesthesiologist','анестезиолог');
INSERT INTO category (name, name_ru) VALUES ('Cardiologist','кардиолог');
INSERT INTO category (name, name_ru) VALUES ('Orthopedist','ортопед');
INSERT INTO category (name, name_ru) VALUES ('Gastroenterologist','гастроэнтеролог');
INSERT INTO category (name, name_ru) VALUES ('Dermatologist','дерматолог');
INSERT INTO category (name, name_ru) VALUES ('Gynecologist','гинеколог');
INSERT INTO category (name, name_ru) VALUES ('Urologist','уролог');
INSERT INTO category (name, name_ru) VALUES ('Ophthalmologist','офтальмолог');
INSERT INTO category (name, name_ru) VALUES ('Therapist','физиотерапевт');
INSERT INTO category (name, name_ru) VALUES ('Nurse','медсестра');

INSERT INTO doctor (person_id, category_id) VALUES (1,2);
INSERT INTO doctor (person_id, category_id) VALUES (2,3);

INSERT INTO patient (person_id) VALUES (3);
INSERT INTO patient (person_id) VALUES (4);

INSERT INTO diagnosis (name, name_ru) VALUES ('rhinitis', 'ринит');
INSERT INTO diagnosis (name, name_ru) VALUES ('tonsillitis', 'тонзиллит');
INSERT INTO diagnosis (name, name_ru) VALUES ('quinsy', 'ангина');
INSERT INTO diagnosis (name, name_ru) VALUES ('bronchitis', 'бронхит');
INSERT INTO diagnosis (name, name_ru) VALUES ('pneumonia', 'пневмония');
INSERT INTO diagnosis (name, name_ru) VALUES ('tuberculosis', 'туберкулез');
INSERT INTO diagnosis (name, name_ru) VALUES ('cancer', 'рак');
INSERT INTO diagnosis (name, name_ru) VALUES ('tumour', 'опухоль');
INSERT INTO diagnosis (name, name_ru) VALUES ('diabetes', 'диабет');
INSERT INTO diagnosis (name, name_ru) VALUES ('anaemia', 'анемия');
INSERT INTO diagnosis (name, name_ru) VALUES ('migraine', 'мигрень');
INSERT INTO diagnosis (name, name_ru) VALUES ('hypertension', 'гипертония');
INSERT INTO diagnosis (name, name_ru) VALUES ('arthritis', 'артрит');
INSERT INTO diagnosis (name, name_ru) VALUES ('rheumatism', 'ревматизм');
INSERT INTO diagnosis (name, name_ru) VALUES ('cyst', 'киста');
INSERT INTO diagnosis (name, name_ru) VALUES ('myoma', 'миома');
INSERT INTO diagnosis (name, name_ru) VALUES ('meningitis', ' менингит');
INSERT INTO diagnosis (name, name_ru) VALUES ('asthma', 'астма');
INSERT INTO diagnosis (name, name_ru) VALUES ('allergy', 'аллергия');
INSERT INTO diagnosis (name, name_ru) VALUES ('stroke', 'инсульт');
INSERT INTO diagnosis (name, name_ru) VALUES ('heart attack', 'сердечный приступ');
INSERT INTO diagnosis (name, name_ru) VALUES ('myocardial infarction', 'инфаркт миокарда');
INSERT INTO diagnosis (name, name_ru) VALUES ('leukemia', 'лейкоз');
INSERT INTO diagnosis (name, name_ru) VALUES ('hepatitis', 'гепатит');
INSERT INTO diagnosis (name, name_ru) VALUES ('renal failure', 'почечная недостаточность');
INSERT INTO diagnosis (name, name_ru) VALUES ('diphtheria', 'дифтерия');
INSERT INTO diagnosis (name, name_ru) VALUES ('herpes', 'герпес');
INSERT INTO diagnosis (name, name_ru) VALUES ('psoriasis', 'псориаз');
INSERT INTO diagnosis (name, name_ru) VALUES ('brain concussion', 'сотрясение мозга');
INSERT INTO diagnosis (name, name_ru) VALUES ('insomnia', 'бессонница');
INSERT INTO diagnosis (name, name_ru) VALUES ('infertility', 'бесплодие');
INSERT INTO diagnosis (name, name_ru) VALUES ('blindness', 'слепота');
INSERT INTO diagnosis (name, name_ru) VALUES ('deafness', 'глухота');
INSERT INTO diagnosis (name, name_ru) VALUES ('flat-footedness', 'плоскостопие');
INSERT INTO diagnosis (name, name_ru) VALUES ('appendicitis', 'аппендицит');
INSERT INTO diagnosis (name, name_ru) VALUES ('stomach ulcer', 'язва желудка');
INSERT INTO diagnosis (name, name_ru) VALUES ('gastritis', 'гастрит');
INSERT INTO diagnosis (name, name_ru) VALUES ('cirrhosis', 'цирроз');
INSERT INTO diagnosis (name, name_ru) VALUES ('epilepsy', 'эпилепсия');
INSERT INTO diagnosis (name, name_ru) VALUES ('acne', 'угри');
INSERT INTO diagnosis (name, name_ru) VALUES ('myopia', 'близорукость');
INSERT INTO diagnosis (name, name_ru) VALUES ('long sight', 'дальнозоркость');
INSERT INTO diagnosis (name, name_ru) VALUES ('conjunctivitis', 'конъюнктивит');
INSERT INTO diagnosis (name, name_ru) VALUES ('dysentery', 'дизентерия');
INSERT INTO diagnosis (name, name_ru) VALUES ('mumps', 'свинка');
INSERT INTO diagnosis (name, name_ru) VALUES ('chicken pox', 'ветрянка');
INSERT INTO diagnosis (name, name_ru) VALUES ('measles', 'корь');
INSERT INTO diagnosis (name, name_ru) VALUES ('German measles', 'краснуха');
INSERT INTO diagnosis (name, name_ru) VALUES ('malaria', 'малярия');
INSERT INTO diagnosis (name, name_ru) VALUES ('cholera', 'холера');


INSERT INTO procedures (name, name_ru) VALUES ('Urine sample', 'анализ мочи');
INSERT INTO procedures (name, name_ru) VALUES ('Blood sample', 'анализ крови');
INSERT INTO procedures (name, name_ru) VALUES ('massage', 'массаж');
INSERT INTO procedures (name, name_ru) VALUES ('Magnetotherapy', 'магнитотерапия');

INSERT INTO medication (name, name_ru) VALUES ('loperamide', 'лоперамид');
INSERT INTO medication (name, name_ru) VALUES ('sodium chloride', 'хлорид натрия');
INSERT INTO medication (name, name_ru) VALUES ('folic acid', 'фолиевая кислота');
INSERT INTO medication (name, name_ru) VALUES ('biotin', 'биотин');

INSERT INTO operation (name) VALUES ('пластика нижней губы');
INSERT INTO operation (name) VALUES ('пневмотомия');
INSERT INTO operation (name) VALUES ('Удаление кисты');
INSERT INTO operation (name) VALUES ('Мастектомия');
