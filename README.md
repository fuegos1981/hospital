
## Description

The system administrator has access to a list of doctors by category (pediatrician, traumatologist, surgeon, ...) 
and a list of patients. Implement the ability to sort:
1) patients:
- alphabetically;
- by date of birth;
2) doctors:
- alphabetically;
- by category;
- by number of patients.
  The administrator registers patients and doctors in the system and appoints a doctor to the patient.
  The doctor determines the diagnosis, makes appointments to the patient (procedures, medications, operations), 
- which are recorded in the Hospital Card. The appointment can be made by a Nurse (procedures, medications) or a Doctor 
- (any appointment).
  The patient can be discharged from the hospital, with a definitive diagnosis recorded.
  (Optional: implement the ability to save / export a document with information about the patient's discharge).

### Details
Used relational database MySQL
Receive a connection using the following method:
`ConnectionPool.getConnection()`
connection string from the properties file `app.properties`.

***

### Details about the database
In a `sql` directory at the root and save the database creation script in it (`db-create.sql`).

![img.png](img.png)