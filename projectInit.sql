DROP DATABASE UniversityRegistrationSystem;
CREATE DATABASE UniversityRegistrationSystem;
USE UniversityRegistrationSystem;

CREATE TABLE UniversityMember(
	memberID INTEGER,
	memberType CHAR(20),
	firstName CHAR(20),
    lastName CHAR(20),
    SIN INTEGER,
    dateOfBirth DATE,
    homeAddress CHAR(50),
    statusType CHAR(20),
    PRIMARY KEY(memberID)
);

CREATE TABLE Course(
	courseName CHAR(40),
    courseCode CHAR(10),
    subject CHAR(20),
    description CHAR(200),
    PRIMARY KEY(courseName, courseCode)
);

CREATE TABLE Section(
    courseName CHAR(40),
    courseCode CHAR(10),
    courseSection CHAR(20),
    memberID INTEGER,
    time TIME,	#need to figure out how to add day to this, maybe change to string?
    PRIMARY KEY (courseName, courseCode, courseSection,  memberID),
    FOREIGN KEY (courseName, courseCode) REFERENCES Course(courseName, courseCode) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (memberID) REFERENCES UniversityMember(memberID) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE CourseGrades(
    courseName CHAR(40),
    courseCode CHAR(10),
    courseSection CHAR(20),
    memberID INTEGER,
    grade INTEGER,
    PRIMARY KEY (courseName, courseCode, courseSection,  memberID),
    FOREIGN KEY (courseName, courseCode, courseSection) REFERENCES Section(courseName, courseCode, courseSection) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (memberID) REFERENCES UniversityMember(memberID) ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO UniversityMember VALUES (1358946, 'Student', 'John', 'Smith', 123456789, '1990-10-22', '123 Main Street, Township, ON, P7Y 4L9, Canada', 'Part-Time');
INSERT INTO UniversityMember VALUES (4582654, 'Student', 'Jessica', 'Fernley', 456789123, '1996-01-06', '123 Second Street, Township, ON, P7Y 4L9, Canada', 'Full-Time');
INSERT INTO UniversityMember VALUES (0646314, 'Instructor', 'Ken', 'Ironside', 147258369, '1984-08-31', '123 Third Street, Township, ON, P7Y 4L9, Canada', 'Part-Time');
INSERT INTO UniversityMember VALUES (9000546, 'Administrator', 'Bailey', 'Hull', 369147258, '1968-04-15', '123 Fourth Street, Township, ON, P7Y 4L9, Canada', 'Part-Time');

INSERT INTO Course VALUES ('ESOF', '3050', 'Software Engineering', 'This is the description of the course...');
INSERT INTO Course VALUES ('MATH', '3071', 'Discrete Mathematics', 'This is the description of the course...');

INSERT INTO Section VALUES ('ESOF', '3050', 'FA', 0646314, '13:30:00');
INSERT INTO Section VALUES ('ESOF', '3050', 'FB', 0646314, '10:00:00');
INSERT INTO Section VALUES ('MATH', '3071', 'FA', 0646314, '08:30:00');

INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 1358946, 90);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FB', 4582654, 82);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FA', 1358946, 96);


commit;

SELECT * FROM UniversityMember
WHERE memberType = 'student';


