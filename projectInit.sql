DROP DATABASE UniversityRegistrationSystem;
CREATE DATABASE UniversityRegistrationSystem;
USE UniversityRegistrationSystem;

/*
University Member Table
 - the memberType is either Instructor, Administrator, Undergraduate, or Graduate
	- that is how we distinguish between all the types of people
 - statusType is the part-time or full-time status
*/
CREATE TABLE UniversityMember(
	memberID INTEGER ,
    memberType CHAR(20),
    firstName CHAR(40),
    lastName CHAR(40),
    SIN INTEGER,
    dateOfBirth DATE,
    homeAddress CHAR(100),
    statusType CHAR(20),
	    yearLevel INTEGER,
    PRIMARY KEY(memberID)
);

/*
Course Table
 - courseName is the 4 letter name of the course (ex. ESOF)
 - courseCode is the 4 digit code of the course (ex. 3050)
 - subject is the name of the course
 - description is a brief summmary of the course
*/
CREATE TABLE Course(
	courseName CHAR(40),
    courseCode CHAR(10),
    subject CHAR(50),
    description TEXT,
    PRIMARY KEY(courseName, courseCode)
);

/*
Section Table
 - Section is the sections of each course being offered (ex. ESOF-3050-FA or FB)
 - courseName and courseCode are taken from the Course table
 - courseSection is the section of the course (ex. FA, FB)
 - memberID is the instructor who will be teaching that section
 - time is the start time of the lecture
 - capacity is the maximum number of students allowed in a course
*/
CREATE TABLE Section(
    courseName CHAR(40),
    courseCode CHAR(10),
    courseSection CHAR(20),
    memberID INTEGER,
    time TIME,
    capacity INTEGER,
    maxCapacity INTEGER,
    PRIMARY KEY (courseName, courseCode, courseSection,  memberID),
    FOREIGN KEY (courseName, courseCode) REFERENCES Course(courseName, courseCode) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (memberID) REFERENCES UniversityMember(memberID) ON DELETE NO ACTION ON UPDATE CASCADE
);

/*
CourseList Table
 - CourseList is the list of students enrolled in a course
 - courseName, courseCode, courseSection are from the Section table
*/
CREATE TABLE CourseList(
	memberID INTEGER,
    courseName CHAR(40),
    courseCode CHAR(10),
    courseSection CHAR(20),
    PRIMARY KEY (memberID, courseName, courseCode, courseSection),
    FOREIGN KEY (memberID) REFERENCES UniversityMember(memberID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (courseName, courseCode, courseSection) REFERENCES Section(courseName, courseCode, courseSection) ON DELETE CASCADE ON UPDATE CASCADE
);

/*
CourseGrades Table
 - The CourseGrades table is the grades of every student in a course they are enrolled in
 - memberID, courseName, courseCode, and courseSection are from the CourseList table
 - grade is the grade they have in the class
*/
CREATE TABLE CourseGrades(
    courseName CHAR(40),
    courseCode CHAR(10),
    courseSection CHAR(20),
    memberID INTEGER,
    grade INTEGER,
    PRIMARY KEY (memberID, courseName, courseCode, courseSection),
    FOREIGN KEY (memberID) REFERENCES UniversityMember(memberID) ON DELETE CASCADE,
    FOREIGN KEY (courseName, courseCode, courseSection) REFERENCES CourseList(courseName, courseCode, courseSection) ON DELETE CASCADE
);

/*
Login Table
 - Login table is the table that is used to determine a valid login
 - memberID is from the UniversityMember table
 - password is the password used to sign into the URS
 - memberType is the type of member they are, getting a different view depending on the memberType (student, instructor, administrator)
*/
CREATE TABLE Login(
	memberID INTEGER,
    password CHAR(50),
    memberType CHAR(20),
    PRIMARY KEY (memberID, password),
    FOREIGN KEY (memberID) REFERENCES UniversityMember(memberID) ON DELETE CASCADE
);


#### Entries ####
#Students
INSERT INTO UniversityMember VALUES (1231231, 'Undergraduate', 'John', 'Smith', 123456789, '1990-10-22', '123 Main Street, Thunder Bay, ON, P7Y 4L9, Canada', 'Part-Time', 2);
INSERT INTO UniversityMember VALUES (4215976, 'Undergraduate', 'Justin', 'Gates', 212512322, '1986-02-02', '421 Dundas Ave, Thunder Bay, ON, P4C 1T8, Canada', 'Part-Time', 3);
INSERT INTO UniversityMember VALUES (5188121, 'Undergraduate', 'Albert', 'Marteus', 782164524, '1992-12-08', '915 Bay Street, Thunder Bay, ON, P1K 7Z9, Canada', 'Part-Time', 2);
INSERT INTO UniversityMember VALUES (9015219, 'Undergraduate', 'Marcus', 'Potts', 976735653, '1990-04-05', '1303 Walnut Drive, Thunder Bay, ON, P8J 2L1, Canada', 'Part-Time', 4);
INSERT INTO UniversityMember VALUES (7537134, 'Undergraduate', 'Dimitri', 'Schtern', 145318586, '1989-01-20', '891 Winnipeg Ave, Thunder Bay, ON, P2P 5W1, Canada', 'Part-Time', 2);
INSERT INTO UniversityMember VALUES (0125392, 'Undergraduate', 'Austin', 'Maxine', 879021175, '1985-05-09', '1052 Parkdale Ave, Thunder Bay, ON, P5A 9M8, Canada', 'Part-Time', 1);
INSERT INTO UniversityMember VALUES (5329210, 'Undergraduate', 'Alexandra', 'West', 597901180, '1984-11-13', '777 Algoma Street, Thunder Bay, ON, P7C 1G9, Canada', 'Part-Time', 4);
INSERT INTO UniversityMember VALUES (1205189, 'Undergraduate', 'Landon', 'McDonald', 616839872, '1995-10-29', '623 Brodie Street, Thunder Bay, ON, P6M 2M1, Canada', 'Part-Time', 3);
INSERT INTO UniversityMember VALUES (2125890, 'Undergraduate', 'Peter', 'Chen', 324327530, '1989-02-26', '1234 Third Street, Thunder Bay, ON, P3K 8J6, Canada', 'Part-Time', 1);
INSERT INTO UniversityMember VALUES (5181205, 'Graduate', 'James', 'Wong', 186014558, '1982-12-26', '139 Franklin Ave, Thunder Bay, ON, P2K 7O2, Canada', 'Part-Time', 5);
INSERT INTO UniversityMember VALUES (2195182, 'Graduate', 'Nicole', 'Lowry', 324327530, '2000-05-04', '619 Victoria Street, Thunder Bay, ON, P9S 2W1, Canada', 'Part-Time', 6);
INSERT INTO UniversityMember VALUES (9518953, 'Graduate', 'Deandra', 'White', 772976152, '1988-02-20', '1214 Erendale Street, Thunder Bay, ON, P0P 3M3, Canada', 'Part-Time', 5);
INSERT INTO UniversityMember VALUES (7531923, 'Graduate', 'Zach', 'Scott', 592972413, '2002-07-23', '812 Loyola Place, Thunder Bay, ON, P4J 1R0, Canada', 'Part-Time', 5);
INSERT INTO UniversityMember VALUES (0124829, 'Undergraduate', 'Jessica', 'Fernley', 323359646, '1996-06-15', '421 Acorn Avenue, Thunder Bay, ON, P7T 1T9, Canada', 'Full-Time', 4);
INSERT INTO UniversityMember VALUES (1583815, 'Undergraduate', 'Trenton', 'Hobbs', 127577684, '1984-08-29', '1109 Cottonwood Crescent, Thunder Bay, ON, P1R 0G9, Canada', 'Full-Time', 2);
INSERT INTO UniversityMember VALUES (8215721, 'Undergraduate', 'Nicole', 'Young', 178020326, '1985-01-14', '921 Glasgow Street, Thunder Bay, ON, P8I 7X2, Canada', 'Full-Time', 1);
INSERT INTO UniversityMember VALUES (5292719, 'Undergraduate', 'Samantha', 'Martin Jr.', 533913098, '2002-12-10', '512 Jade Court, Thunder Bay, ON, P5B 9I0, Canada', 'Full-Time', 3);
INSERT INTO UniversityMember VALUES (1929148, 'Undergraduate', 'Tien', 'Wade', 205318702, '2000-11-17', '218 Lancaster Street, Thunder Bay, ON, PL0 4N4, Canada', 'Full-Time', 2);
INSERT INTO UniversityMember VALUES (7214933, 'Undergraduate', 'Tyler', 'Shin', 456789123, '1982-09-27', '721 King Street, Thunder Bay, ON, P4L 9P2, Canada', 'Full-Time', 4);
INSERT INTO UniversityMember VALUES (6216862, 'Undergraduate', 'Pablo', 'North', 192079634, '1989-06-21', '1512 Norah Crescent, Thunder Bay, ON, P9Z 3Z9, Canada', 'Full-Time', 1);
INSERT INTO UniversityMember VALUES (5320911, 'Undergraduate', 'Hannah', 'Baldwin', 047288825, '1999-11-18', '351 Oliver Road, Thunder Bay, ON, P7X 3E1, Canada', 'Full-Time', 2);
INSERT INTO UniversityMember VALUES (3190185, 'Undergraduate', 'Tony', 'Rich', 435839484, '1987-06-27', '521 Rona Street, Thunder Bay, ON, P5J 3M5, Canada', 'Full-Time', 3);
INSERT INTO UniversityMember VALUES (2109012, 'Undergraduate', 'Gia', 'Stevens', 186710588, '1992-03-14', '707 Silas Road, Thunder Bay, ON, P5J 9J0, Canada', 'Full-Time', 1);
INSERT INTO UniversityMember VALUES (4210305, 'Undergraduate', 'Pablo', 'North', 425643419, '1990-10-08', '512 Tokio Street, Thunder Bay, ON, P3E 6H3, Canada', 'Full-Time', 4);
INSERT INTO UniversityMember VALUES (2190182, 'Undergraduate', 'Reese', 'Kemp', 598704490, '1995-02-16', '807 Trillium Place, Thunder Bay, ON, P3E 5Y3, Canada', 'Full-Time', 2);
INSERT INTO UniversityMember VALUES (5031219, 'Undergraduate', 'Harper', 'Wyatt', 706460918, '1994-08-11', '972 Wilson Street, Thunder Bay, ON, P3E 2W1, Canada', 'Full-Time', 1);
INSERT INTO UniversityMember VALUES (6102213, 'Undergraduate', 'Asia', 'Brown', 833988144, '1988-03-01', '121 Yale Street, Thunder Bay, ON, P8I 5R2, Canada', 'Full-Time', 3);
INSERT INTO UniversityMember VALUES (1294194, 'Undergraduate', 'Zoey', 'Waller', 853225450, '1991-08-23', '345 Wren Court, Thunder Bay, ON, P8I 0L6, Canada', 'Full-Time', 2);
INSERT INTO UniversityMember VALUES (5901821, 'Undergraduate', 'Bruce', 'Harper', 308074144, '1987-09-24', '911 Hodder Ave, Thunder Bay, ON, P8I 1T2, Canada', 'Full-Time', 4);
INSERT INTO UniversityMember VALUES (0912942, 'Undergraduate', 'Jasmine Fritz', 'North', 083235525, '2002-02-23', '218 Syndicate Avenue, Thunder Bay, ON, P0T 9G2, Canada', 'Full-Time', 4);
INSERT INTO UniversityMember VALUES (5429195, 'Undergraduate', 'Nicole', 'Diaz', 864095537, '1982-07-01', '6218 Riverdale Street, Thunder Bay, ON, P0T 1D6, Canada', 'Full-Time', 2);
INSERT INTO UniversityMember VALUES (6942091, 'Undergraduate', 'Walker', 'Obrien', 983358142, '1983-02-24', '1123 Picard Ave, Thunder Bay, ON, P0T 1D1, Canada', 'Full-Time', 1);
INSERT INTO UniversityMember VALUES (3219998, 'Graduate', 'Aaron', 'Rogers', 906744957, '1994-02-23', '214 Ambrose Street, Thunder Bay, ON, P1Y 5K9, Canada', 'Full-Time', 6);
INSERT INTO UniversityMember VALUES (5153912, 'Graduate', 'Brandon', 'Bembry', 794231517, '1993-10-21', '951 15th Sideroad, Thunder Bay, ON, P1Y 4L0, Canada', 'Full-Time', 6);
INSERT INTO UniversityMember VALUES (1005190, 'Graduate', 'Pascal', 'Ingram', 945399607, '1998-11-16', '1521 High Street, Thunder Bay, ON, P1Y 1U7, Canada', 'Full-Time', 5);
INSERT INTO UniversityMember VALUES (8892149, 'Graduate', 'Kyle', 'Butler', 586518060, '1998-03-30', '721 Central Ave, Thunder Bay, ON, P4B 5J1, Canada', 'Full-Time', 5);
INSERT INTO UniversityMember VALUES (6661293, 'Graduate', 'Stephen', 'Thompson', 870679523, '1990-08-20', '777 Balmoral Street, Thunder Bay, ON, P4B 4L9, Canada', 'Full-Time', 6);
INSERT INTO UniversityMember VALUES (1024920, 'Graduate', 'Klay', 'Curry', 562539179, '1987-08-26', '821 James Street, Thunder Bay, ON, P4B 8L1, Canada', 'Full-Time', 5);
INSERT INTO UniversityMember VALUES (9218529, 'Graduate', 'Draymond', 'Brown', 728929658, '1990-02-22', '8519 Nick Street, Thunder Bay, ON, P7C 9U7, Canada', 'Full-Time', 6);


#Instructors
INSERT INTO UniversityMember VALUES (0646314, 'Instructor', 'Ken', 'Ironside', 147258369, '1984-08-31', '6351 Windsor Street, Thunder Bay, ON, P2U 3X7, Canada', 'Part-Time', -1);
INSERT INTO UniversityMember VALUES (2419215, 'Instructor', 'Chadwick', 'Boucher', 638799434, '1961-09-27', '941 Bankers Street, Thunder Bay, ON, P5X 7J3, Canada', 'Part-Time', -1);
INSERT INTO UniversityMember VALUES (6319921, 'Instructor', 'Margeret', 'Jordan', 698802186, '1991-09-28', '219 High Street, Thunder Bay, ON, P4N 3K5, Canada', 'Part-Time', -1);
INSERT INTO UniversityMember VALUES (0021521, 'Instructor', 'Yuri', 'Orslof', 698802186, '1974-03-17', '271 Bristol Court, Thunder Bay, ON, P8B 3M6, Canada', 'Full-Time', -1);
INSERT INTO UniversityMember VALUES (2419522, 'Instructor', 'Thomas', 'Andre', 298960408, '1966-02-28', '829 Third Street, Thunder Bay, ON, P0B 6G2, Canada', 'Full-Time', -1);
INSERT INTO UniversityMember VALUES (7214777, 'Instructor', 'Henry', 'Miller', 752264681, '1961-05-23', '124 Cherrygrove Street, Thunder Bay, ON, P2C 1B7, Canada', 'Full-Time', -1);
INSERT INTO UniversityMember VALUES (3450968, 'Instructor', 'Harrison', 'Ford', 431423924, '1973-03-23', '3582 John Street, Thunder Bay, ON, P5N 3A5, Canada', 'Full-Time', -1);
INSERT INTO UniversityMember VALUES (7892152, 'Instructor', 'Christina', 'Hemsworth', 254836716, '1983-05-19', '742 Frankwood Avenue, Thunder Bay, ON, P9A 4A4, Canada', 'Full-Time', -1);
INSERT INTO UniversityMember VALUES (2192100, 'Instructor', 'Nicholas', 'Emperius', 607400547, '1962-02-21', '682 Huntington Street, Thunder Bay, ON, P5F 2F5, Canada', 'Full-Time', -1);
INSERT INTO UniversityMember VALUES (4210012, 'Instructor', 'Natasha', 'Popov', 525491129, '1982-05-19', '482 Langworthy Crescent, Thunder Bay, ON, P1S 6B2, Canada', 'Full-Time', -1);
INSERT INTO UniversityMember VALUES (7821942, 'Instructor', 'Bill', 'Gates', 388639027, '1965-07-23', '2105 Mireault Crescent, Thunder Bay, ON, P9B 7Q3, Canada', 'Full-Time', -1);
INSERT INTO UniversityMember VALUES (5678762, 'Instructor', 'Steve', 'Jobbs', 668754218, '1984-05-28', '517 Nipigon Avenue, Thunder Bay, ON, P1Q 6L2, Canada', 'Full-Time', -1);
INSERT INTO UniversityMember VALUES (7544327, 'Instructor', 'Squid', 'Game', 849927954, '1964-11-21', '298 Porcupine Avenue, Thunder Bay, ON, P5L 1Q5, Canada', 'Full-Time', -1);

#Administrators
INSERT INTO UniversityMember VALUES (9000546, 'Administrator', 'Bailey', 'Hull', 369147258, '1968-04-15', '365 Fifth Street, Thunder Bay, ON, P6Z 3B2, Canada', 'Full-Time', -1);
INSERT INTO UniversityMember VALUES (5129851, 'Administrator', 'Precious', 'Davis', 570862430, '1961-09-03', '1213 Ridgeway Street, Thunder Bay, ON, P1M 5U2, Canada', 'Full-Time', -1);
INSERT INTO UniversityMember VALUES (2199921, 'Administrator', 'Shaquille', 'James', 812330796, '1976-07-24', '6217 Shirley Road, Thunder Bay, ON, P9N 3K1, Canada', 'Full-Time', -1);
INSERT INTO UniversityMember VALUES (8582188, 'Administrator', 'Hassan', 'Mohammed', 683611787, '1970-11-26', '521 Walsh Street, Thunder Bay, ON, P4S 9I4, Canada', 'Full-Time', -1);
INSERT INTO UniversityMember VALUES (7772147, 'Administrator', 'Karen', 'Winters', 447628671, '1985-01-10', '846 Oxford Street, Thunder Bay, ON, P8D 2V9, Canada', 'Full-Time', -1);

#Courses (Add Descriptions)
INSERT INTO Course VALUES ('ESOF', '3050', 'Software Engineering', 'A project oriented course in which students apply software engineering principles of requirements elicitation, specifications, design, implementation and testing to solve engineering problems. The course content focuses on object oriented methodology and the use of Unified Modeling Language (UML) to specify, visualize, construct and document the artifacts of the software system. Topics include: concepts of object orientation; UML modeling and class diagrams; developing software requirements; client-server architecture; software design patterns; software implementation and testing; basic architectural patterns.');
INSERT INTO Course VALUES ('ESOF', '3655', 'Principles of Operating Systems', 'Main components of modern operating systems; computer and OS architecture; processes and process management; threading; CPU scheduling; memory management; file management; I/O device management; with implementation examples taken from real-world operating systems including real-time OS and distributed OS; and coding exercises.');
INSERT INTO Course VALUES ('MATH', '3071', 'Discrete Mathematics', 'Basic set theory. Introduction to logic and proofs. Functions and relations. Mathematical induction and recursion. Algorithms; time estimates and orders of magnitude. Basic combinations. Graphs. Boolean algebras.');
INSERT INTO Course VALUES ('ENGL', '1012', 'Academic Communications', 'Intended to help students improve their communication skills: writing, reading, thinking, speaking, and listening, with the intent of bridging the gap between every day communication and academic writing. Students write an expository and a persuasive paper, summarize written texts, apply principles of clear and correct writing to their own compositions, and expand their writing skills so that they can write and speak more effectively for a variety of audiences and purposes.');
INSERT INTO Course VALUES ('BUSI', '4071', 'Strategic Management II', 'Applies the concepts of strategic management to real-life organizational situations. Students are required to analyze an existing organization and to submit a detailed report of their findings, analyses, and recommendations for the future. Although this is a half-course equivalent, this course will be scheduled over both fall and winter terms. It is strongly recommended that students consult the instructor and begin planning their activities before the end of their third year. The work in this course will build on the topics in 3071 and will include a detailed strategy implementation plan for an organization.');
INSERT INTO Course VALUES ('CHEM', '2211', 'Organic Chemistry I', 'Physical properties, chemical reactions, nomenclature and conformational properties of alkanes, alkenes and alkynes. Characteristic reactions of alcohols and alkyl halides. Reactions of various types of carbonyl compounds. Optical, geometric and diastereoisomerism. Introduction to the spectroscopic identification of organic compounds.');
INSERT INTO Course VALUES ('WOME', '1100', 'Women and Gender Studies', 'An interdisciplinary introduction, the course explores intersections of gender, race, class, sexuality, ability, age and other socially/historically shaped dimensions of identity. Discussions will include such topics as media, health, work, poverty, body and sexuality, violence, law and creativity, in local and global contexts.');
INSERT INTO Course VALUES ('MUSI', '4330', 'String Ensemble IV', 'The topic of this ensemble will vary from year to year. Consult the Department for further details.');
INSERT INTO Course VALUES ('SPAN', '1000', 'Introductory Spanish', 'A beginners course in the study of the Spanish language. Oral practice, dictation, essentials of grammar and writing.');
INSERT INTO Course VALUES ('NURS', '2055', 'Adult Illness Concepts I', 'Develops the student’s knowledge and understanding of the pathophysiological concepts of altered health states and application of the nursing process to the care of adults. The emphasis is on clinical manifestations of acute or chronic health alterations, physical assessment, diagnostics, and therapeutic nursing strategies to support clients as they move towards self-management of their health. A lab component offers students the opportunity to practise skills and interventions that promote the health and comfort of individuals.');
INSERT INTO Course VALUES ('MEDI', '5045', 'Social and Population Health I', 'Students develop their knowledge and understanding of the principles of primary health care and the Canadian health care system; public health; cultural/social/economic aspects of health and illness; history of disease; health promotion and disease prevention for individuals, communities, and populations; workplace health and safety; and research methods and critical appraisal, epidemiology, and statistics. Particular foci will include the determinants of health, health-related risk factors, interprofessional roles, and the impact of health policy on health.');
INSERT INTO Course VALUES ('EDUC', '6411', 'Cognition and Learning', 'Provides an analysis of epistemological theories through a critical examination of foundational and current research and a reflection on historical and philosophical orientations as they relate to contemporary issues in cognition and learning.');
INSERT INTO Course VALUES ('SOCI', '5111', 'Problem and Issues in Socialogy', 'A critical examination of theoretical problems and issues in Sociology.');
INSERT INTO Course VALUES ('COMP', '9800', 'Project Research/Writing', 'A full year course of directed research that may involve any combination of theory or application in an area of computer science, as agreed to by the student and the research project supervisor in a "learning contract", which states what is to be done in the research project, how and when it will be done, and how it will be evaluated. A significant portion of the work will involve the preparation of a written report along with a public presentation.');

#Section
INSERT INTO Section VALUES ('ESOF', '3050', 'FA', 0646314, '13:30:00', 45, 45);
INSERT INTO Section VALUES ('ESOF', '3050', 'FB', 0646314, '10:00:00', 10, 10);
INSERT INTO Section VALUES ('ESOF', '3655', 'FA', 7214777, '10:00:00', 45, 45);
INSERT INTO Section VALUES ('ESOF', '3655', 'FB', 7214777, '15:00:00', 25, 25);
INSERT INTO Section VALUES ('MATH', '3071', 'FA', 5678762, '08:30:00', 20, 20);
INSERT INTO Section VALUES ('MATH', '3071', 'FB', 2192100, '10:30:00', 20, 20);
INSERT INTO Section VALUES ('MATH', '3071', 'FC', 2419522, '12:30:00', 45, 45);
INSERT INTO Section VALUES ('MATH', '3071', 'FD', 4210012, '14:30:00', 15, 15);
INSERT INTO Section VALUES ('ENGL', '1012', 'FA', 7892152, '18:00:00', 15, 15);
INSERT INTO Section VALUES ('ENGL', '1012', 'FB', 7892152, '09:30:00', 20, 20);
INSERT INTO Section VALUES ('BUSI', '4071', 'FA', 7544327, '17:15:00', 10, 10);
INSERT INTO Section VALUES ('CHEM', '2211', 'FA', 3450968, '08:30:00', 50, 50);
INSERT INTO Section VALUES ('WOME', '1100', 'FA', 6319921, '09:00:00', 35, 35);
INSERT INTO Section VALUES ('MUSI', '4330', 'FA', 4210012, '14:30:00', 20, 20);
INSERT INTO Section VALUES ('SPAN', '1000', 'FA', 0021521, '20:00:00', 25, 25);
INSERT INTO Section VALUES ('NURS', '2055', 'FA', 7214777, '08:30:00', 60, 60);
INSERT INTO Section VALUES ('NURS', '2055', 'FB', 7214777, '12:30:00', 50, 50);
INSERT INTO Section VALUES ('NURS', '2055', 'FC', 5678762, '16:00:00', 50, 50);
INSERT INTO Section VALUES ('MEDI', '5045', 'FA', 2419522, '08:30:00', 50, 50);
INSERT INTO Section VALUES ('EDUC', '6411', 'FA', 2419215, '18:45:00', 20, 20);
INSERT INTO Section VALUES ('SOCI', '5111', 'FA', 7544327, '07:15:00', 25, 25);
INSERT INTO Section VALUES ('COMP', '9800', 'FA', 0646314, '12:30:00', 10, 10);
INSERT INTO Section VALUES ('COMP', '9800', 'FB', 2192100, '17:30:00', 39, 39);

#CourseList 
#Part Time Students
INSERT INTO CourseList VALUES (1231231, 'SPAN', '1000', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'SPAN' AND courseCode = '1000' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1231231, 'NURS', '2055', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'NURS' AND courseCode = '2055' AND courseSection = 'FC';

INSERT INTO CourseList VALUES (4215976, 'ENGL', '1012', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ENGL' AND courseCode = '1012' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (4215976, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (5188121, 'SPAN', '1000', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'SPAN' AND courseCode = '1000' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (9015219, 'NURS', '2055', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'NURS' AND courseCode = '2055' AND courseSection = 'FB';

INSERT INTO CourseList VALUES (7537134, 'WOME', '1100', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'WOME' AND courseCode = '1100' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (0125392, 'ENGL', '1012', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ENGL' AND courseCode = '1012' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (5329210, 'MUSI', '4330', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MUSI' AND courseCode = '4330' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5329210, 'SOCI', '5111', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'SOCI' AND courseCode = '5111' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (1205189, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';

INSERT INTO CourseList VALUES (2125890, 'WOME', '1100', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'WOME' AND courseCode = '1100' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (5181205, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5181205, 'SOCI', '5111', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'SOCI' AND courseCode = '5111' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (2195182, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (2195182, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (9518953, 'WOME', '1100', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'WOME' AND courseCode = '1100' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (9518953, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';

INSERT INTO CourseList VALUES (7531923, 'SPAN', '1000', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'SPAN' AND courseCode = '1000' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (7531923, 'WOME', '1100', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'WOME' AND courseCode = '1100' AND courseSection = 'FA';

#Full Time Students
INSERT INTO CourseList VALUES (0124829, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (0124829, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (0124829, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (0124829, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (0124829, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (1583815, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1583815, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1583815, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (1583815, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (1583815, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (8215721, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (8215721, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (8215721, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (8215721, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (8215721, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (5292719, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5292719, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5292719, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (5292719, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (5292719, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (1929148, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1929148, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1929148, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (1929148, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (1929148, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (7214933, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (7214933, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (7214933, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (7214933, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (7214933, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (6216862, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (6216862, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (6216862, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (6216862, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (6216862, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (5320911, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5320911, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5320911, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (5320911, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (5320911, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (3190185, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (3190185, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (3190185, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (3190185, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (3190185, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (2109012, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (2109012, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (2109012, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (2109012, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (2109012, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (4210305, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (4210305, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (4210305, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (4210305, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (4210305, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (2190182, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (2190182, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (2190182, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (2190182, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (2190182, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (5031219, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5031219, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5031219, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (5031219, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (5031219, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (6102213, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (6102213, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (6102213, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (6102213, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (6102213, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (1294194, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1294194, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1294194, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (1294194, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (1294194, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (5901821, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5901821, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5901821, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (5901821, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (5901821, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (0912942, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (0912942, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (0912942, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (0912942, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (0912942, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (5429195, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5429195, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5429195, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (5429195, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (5429195, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (6942091, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (6942091, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (6942091, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (6942091, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (6942091, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (5313903, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5313903, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5313903, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (5313903, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (5313903, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (2140915, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (2140915, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (2140915, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (2140915, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (2140915, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (7214905, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (7214905, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (7214905, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (7214905, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (7214905, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (2510933, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (2510933, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (2510933, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (2510933, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (2510933, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (6231593, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (6231593, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (6231593, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (6231593, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (6231593, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (2189218, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (2189218, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (2189218, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (2189218, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (2189218, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (7419953, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (7419953, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (7419953, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (7419953, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (7419953, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (0921355, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (0921355, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (0921355, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (0921355, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (0921355, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (0125825, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (0125825, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (0125825, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (0125825, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (0125825, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (8593295, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (8593295, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (8593295, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (8593295, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (8593295, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (3219998, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (3219998, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (3219998, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (3219998, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (3219998, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (5153912, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5153912, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (5153912, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (5153912, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (5153912, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (1005190, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1005190, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1005190, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (1005190, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (1005190, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (8892149, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (8892149, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (8892149, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (8892149, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (8892149, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (6661293, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (6661293, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (6661293, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (6661293, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (6661293, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (1024920, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1024920, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1024920, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (1024920, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (1024920, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (9218529, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (9218529, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (9218529, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (9218529, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (9218529, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (6314892, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (6314892, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (6314892, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (6314892, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (6314892, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (1521249, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1521249, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1521249, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (1521249, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (1521249, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';

INSERT INTO CourseList VALUES (1214201, 'ESOF', '3050', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3050' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1214201, 'ESOF', '3655', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'ESOF' AND courseCode = '3655' AND courseSection = 'FA';
INSERT INTO CourseList VALUES (1214201, 'MATH', '3071', 'FC');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'MATH' AND courseCode = '3071' AND courseSection = 'FC';
INSERT INTO CourseList VALUES (1214201, 'COMP', '9800', 'FB');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'COMP' AND courseCode = '9800' AND courseSection = 'FB';
INSERT INTO CourseList VALUES (1214201, 'CHEM', '2211', 'FA');
UPDATE Section SET capacity = capacity - 1 WHERE courseName = 'CHEM' AND courseCode = '2211' AND courseSection = 'FA';


#Part Time Students
INSERT INTO CourseGrades VALUES ('SPAN', '1000', 'FA', 1231231, 78);
INSERT INTO CourseGrades VALUES ('NURS', '2055', 'FC', 1231231, 67);
INSERT INTO CourseGrades VALUES ('ENGL', '1012', 'FA', 4215976, 89);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 4215976, 87);
INSERT INTO CourseGrades VALUES ('SOCI', '5111', 'FA', 5188121, 56);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 5188121, 65);
INSERT INTO CourseGrades VALUES ('MEDI', '5045', 'FA', 9015219, 34);
INSERT INTO CourseGrades VALUES ('NURS', '2055', 'FB', 9015219, 56);
INSERT INTO CourseGrades VALUES ('WOME', '1100', 'FA', 7537134, 77);
INSERT INTO CourseGrades VALUES ('EDUC', '6411', 'FA', 7537134, 66);
INSERT INTO CourseGrades VALUES ('BUSI', '4071', 'FA', 0125392, 32);
INSERT INTO CourseGrades VALUES ('EDUC', '6411', 'FA', 0125392, 56);
INSERT INTO CourseGrades VALUES ('MUSI', '4330', 'FA', 5329210, 87);
INSERT INTO CourseGrades VALUES ('SOCI', '5111', 'FA', 5329210, 98);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 1205189, 76);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FA', 1205189, 99);
INSERT INTO CourseGrades VALUES ('EDUC', '6411', 'FA', 2125890, 76);
INSERT INTO CourseGrades VALUES ('BUSI', '4071', 'FA', 2125890, 56);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 5181205, 76);
INSERT INTO CourseGrades VALUES ('SOCI', '5111', 'FA', 5181205, 59);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 2195182, 77);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 2195182, 88);
INSERT INTO CourseGrades VALUES ('WOME', '1100', 'FA', 9518953, 77);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 9518953, 92);
INSERT INTO CourseGrades VALUES ('SPAN', '1000', 'FA', 7531923, 83);
INSERT INTO CourseGrades VALUES ('WOME', '1100', 'FA', 7531923, 74);

#Full Time Students
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 0124829, 100);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 0124829, 89);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 0124829, 83);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 0124829, 95);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 0124829, 78);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 1583815, 80);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 1583815, 87);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 1583815, 75);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 1583815, 94);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 1583815, 69);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 8215721, 67);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 8215721, 55);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 8215721, 65);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 8215721, 56);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 8215721, 49);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 5292719, 50);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 5292719, 78);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 5292719, 95);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 5292719, 43);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 5292719, 88);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 1929148, 99);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 1929148, 98);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 1929148, 87);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 1929148, 78);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 1929148, 78);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 7214933, 95);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 7214933, 78);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 7214933, 76);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 7214933, 87);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 7214933, 66);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 6216862, 100);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 6216862, 89);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 6216862, 83);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 6216862, 95);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 6216862, 78);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 5320911, 80);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 5320911, 87);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 5320911, 75);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 5320911, 94);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 5320911, 69);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 3190185, 67);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 3190185, 55);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 3190185, 65);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 3190185, 56);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 3190185, 49);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 2109012, 50);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 2109012, 78);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 2109012, 95);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 2109012, 43);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 2109012, 88);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 4210305, 99);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 4210305, 98);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 4210305, 87);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 4210305, 78);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 4210305, 78);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 2190182, 95);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 2190182, 78);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 2190182, 76);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 2190182, 87);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 2190182, 66);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 5031219, 100);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 5031219, 89);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 5031219, 83);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 5031219, 95);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 5031219, 78);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 6102213, 80);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 6102213, 87);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 6102213, 75);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 6102213, 94);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 6102213, 69);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 1294194, 67);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 1294194, 55);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 1294194, 65);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 1294194, 56);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 1294194, 49);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 5901821, 50);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 5901821, 78);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 5901821, 95);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 5901821, 43);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 5901821, 88);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 0912942, 99);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 0912942, 98);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 0912942, 87);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 0912942, 78);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 0912942, 78);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 5429195, 95);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 5429195, 78);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 5429195, 76);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 5429195, 87);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 5429195, 66);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 6942091, 80);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 6942091, 87);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 6942091, 75);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 6942091, 94);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 6942091, 69);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 5313903, 67);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 5313903, 55);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 5313903, 65);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 5313903, 56);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 5313903, 49);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 2140915, 50);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 2140915, 78);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 2140915, 95);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 2140915, 43);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 2140915, 88);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 7214905, 99);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 7214905, 98);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 7214905, 87);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 7214905, 78);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 7214905, 78);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 2510933, 95);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 2510933, 78);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 2510933, 76);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 2510933, 87);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 2510933, 66);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 6231593, 100);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 6231593, 89);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 6231593, 83);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 6231593, 95);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 6231593, 78);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 2189218, 80);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 2189218, 87);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 2189218, 75);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 2189218, 94);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 2189218, 69);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 7419953, 67);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 7419953, 55);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 7419953, 65);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 7419953, 56);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 7419953, 49);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 0921355, 50);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 0921355, 78);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 0921355, 95);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 0921355, 43);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 0921355, 88);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 0125825, 99);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 0125825, 98);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 0125825, 87);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 0125825, 78);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 0125825, 78);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 8593295, 95);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 8593295, 78);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 8593295, 76);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 8593295, 87);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 8593295, 66);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 3219998, 100);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 3219998, 89);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 3219998, 83);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 3219998, 95);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 3219998, 78);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 5153912, 80);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 5153912, 87);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 5153912, 75);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 5153912, 94);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 5153912, 69);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 1005190, 67);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 1005190, 55);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 1005190, 65);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 1005190, 56);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 1005190, 49);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 8892149, 50);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 8892149, 78);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 8892149, 95);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 8892149, 43);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 8892149, 88);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 6661293, 99);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 6661293, 98);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 6661293, 87);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 6661293, 78);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 6661293, 78);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 1024920, 95);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 1024920, 78);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 1024920, 76);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 1024920, 87);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 1024920, 66);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 9218529, 100);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 9218529, 89);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 9218529, 83);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 9218529, 95);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 9218529, 78);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 6314892, 80);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 6314892, 87);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 6314892, 75);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 6314892, 94);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 6314892, 69);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 1521249, 67);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 1521249, 55);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 1521249, 65);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 1521249, 56);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 1521249, 49);
INSERT INTO CourseGrades VALUES ('ESOF', '3050', 'FA', 1214201, 50);
INSERT INTO CourseGrades VALUES ('ESOF', '3655', 'FA', 1214201, 78);
INSERT INTO CourseGrades VALUES ('MATH', '3071', 'FC', 1214201, 95);
INSERT INTO CourseGrades VALUES ('COMP', '9800', 'FB', 1214201, 43);
INSERT INTO CourseGrades VALUES ('CHEM', '2211', 'FA', 1214201, 88);

#Login
INSERT INTO Login VALUES (1231231, 'password', 'student');
INSERT INTO Login VALUES (4215976, 'password', 'student');
INSERT INTO Login VALUES (5188121, 'password', 'student');
INSERT INTO Login VALUES (9015219, 'password', 'student');
INSERT INTO Login VALUES (7537134, 'password', 'student');
INSERT INTO Login VALUES (0125392, 'password', 'student');
INSERT INTO Login VALUES (5329210, 'password', 'student');
INSERT INTO Login VALUES (1205189, 'password', 'student');
INSERT INTO Login VALUES (2125890, 'password', 'student');
INSERT INTO Login VALUES (5181205, 'password', 'student');
INSERT INTO Login VALUES (2195182, 'password', 'student');
INSERT INTO Login VALUES (9518953, 'password', 'student');
INSERT INTO Login VALUES (7531923, 'password', 'student');
INSERT INTO Login VALUES (0124829, 'password', 'student');
INSERT INTO Login VALUES (1583815, 'password', 'student');
INSERT INTO Login VALUES (8215721, 'password', 'student');
INSERT INTO Login VALUES (5292719, 'password', 'student');
INSERT INTO Login VALUES (1929148, 'password', 'student');
INSERT INTO Login VALUES (7214933, 'password', 'student');
INSERT INTO Login VALUES (6216862, 'password', 'student');
INSERT INTO Login VALUES (5320911, 'password', 'student');
INSERT INTO Login VALUES (3190185, 'password', 'student');
INSERT INTO Login VALUES (2109012, 'password', 'student');
INSERT INTO Login VALUES (4210305, 'password', 'student');
INSERT INTO Login VALUES (2190182, 'password', 'student');
INSERT INTO Login VALUES (5031219, 'password', 'student');
INSERT INTO Login VALUES (6102213, 'password', 'student');  
INSERT INTO Login VALUES (1294194, 'password', 'student');
INSERT INTO Login VALUES (5901821, 'password', 'student');
INSERT INTO Login VALUES (0912942, 'password', 'student');
INSERT INTO Login VALUES (5429195, 'password', 'student');
INSERT INTO Login VALUES (6942091, 'password', 'student');
INSERT INTO Login VALUES (5313903, 'password', 'student');
INSERT INTO Login VALUES (2140915, 'password', 'student');
INSERT INTO Login VALUES (7214905, 'password', 'student');
INSERT INTO Login VALUES (2510933, 'password', 'student');
INSERT INTO Login VALUES (6231593, 'password', 'student'); 
INSERT INTO Login VALUES (2189218, 'password', 'student');
INSERT INTO Login VALUES (7419953, 'password', 'student');
INSERT INTO Login VALUES (0921355, 'password', 'student');
INSERT INTO Login VALUES (0125825, 'password', 'student');
INSERT INTO Login VALUES (8593295, 'password', 'student');
INSERT INTO Login VALUES (3219998, 'password', 'student');
INSERT INTO Login VALUES (5153912, 'password', 'student');
INSERT INTO Login VALUES (1005190, 'password', 'student');
INSERT INTO Login VALUES (8892149, 'password', 'student');
INSERT INTO Login VALUES (6661293, 'password', 'student');
INSERT INTO Login VALUES (1024920, 'password', 'student');
INSERT INTO Login VALUES (9218529, 'password', 'student');
INSERT INTO Login VALUES (6314892, 'password', 'student');
INSERT INTO Login VALUES (1521249, 'password', 'student');
INSERT INTO Login VALUES (1214201, 'password', 'student');

INSERT INTO Login VALUES (0646314, 'password', 'instructor');
INSERT INTO Login VALUES (2419215, 'password', 'instructor');
INSERT INTO Login VALUES (6319921, 'password', 'instructor');
INSERT INTO Login VALUES (0021521, 'password', 'instructor');
INSERT INTO Login VALUES (2419522, 'password', 'instructor');
INSERT INTO Login VALUES (7214777, 'password', 'instructor');
INSERT INTO Login VALUES (3450968, 'password', 'instructor');
INSERT INTO Login VALUES (7892152, 'password', 'instructor');
INSERT INTO Login VALUES (2192100, 'password', 'instructor');
INSERT INTO Login VALUES (4210012, 'password', 'instructor');
INSERT INTO Login VALUES (7821942, 'password', 'instructor');
INSERT INTO Login VALUES (5678762, 'password', 'instructor');
INSERT INTO Login VALUES (7544327, 'password', 'instructor');

INSERT INTO Login VALUES (9000546, 'password', 'administrator');
INSERT INTO Login VALUES (5129851, 'password', 'administrator');
INSERT INTO Login VALUES (2199921, 'password', 'administrator');
INSERT INTO Login VALUES (8582188, 'password', 'administrator');
INSERT INTO Login VALUES (7772147, 'password', 'administrator');


commit;
