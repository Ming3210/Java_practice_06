# create database employee_db;
use employee_db;

create table department(
    de_id int primary key auto_increment,
    de_name varchar(100) not null unique ,
    de_description varchar(255),
    de_status bit
);

create table admin(
    username varchar(155) not null,
    password varchar(155) not null,
    status enum('ACTIVE','INACTIVE')
);

create table employee(
    em_id char(5) primary key,
    em_name varchar(150) not null ,
    em_email varchar(255) not null ,
    em_phone varchar(10) not null ,
    em_gender enum('MALE', 'FEMALE', 'OTHER'),
    em_salary_tier int not null check ( em_salary_tier>0 ),
    em_salary decimal(10,2) not null check ( em_salary>0 ),
    em_dob date not null ,
    em_address varchar(255) not null ,
    em_status enum('ACTIVE','INACTIVE','ONLEAVE','POLICYLEAVE'),
    de_id int ,
    foreign key (de_id) references department(de_id),
    role enum('HR','Employee'),
    username varchar(155) not null,
    password varchar(155) not null
);



DELIMITER //
CREATE PROCEDURE login(
    p_username_in VARCHAR(155),
    p_password_in VARCHAR(155),
    OUT return_code INT
)
BEGIN
    DECLARE user_exists INT DEFAULT 0;
    DECLARE user_status VARCHAR(20);

    -- Kiểm tra trong bảng admin
    SELECT COUNT(*) INTO user_exists
    FROM admin
    WHERE username = p_username_in AND password = p_password_in;

    IF user_exists > 0 THEN
        -- Kiểm tra status của admin
        SELECT status INTO user_status
        FROM admin
        WHERE username = p_username_in AND password = p_password_in;

        IF user_status = 'ACTIVE' THEN
            SET return_code = 1; -- Admin active
        ELSE
            SET return_code = 0; -- Admin inactive
        END IF;
    ELSE
        SET return_code = -1; -- User không tồn tại
    END IF;
END //
DELIMITER ;


# drop procedure login

DELIMITER //

CREATE PROCEDURE get_departments_paginated(
    IN p_offset INT,
    IN p_limit INT
)
BEGIN
    SELECT * FROM department
    LIMIT p_limit OFFSET p_offset;
END //

DELIMITER ;


DELIMITER //

CREATE PROCEDURE add_department(
    IN p_name VARCHAR(100),
    IN p_description VARCHAR(255),
    IN p_status BIT
)
BEGIN
    INSERT INTO department(de_name, de_description, de_status)
    VALUES (p_name, p_description, p_status);
END //

DELIMITER ;



DELIMITER //

CREATE PROCEDURE update_department(
    IN p_id INT,
    IN p_name VARCHAR(100),
    IN p_description VARCHAR(255),
    IN p_status BIT
)
BEGIN
    UPDATE department
    SET de_name = p_name,
        de_description = p_description,
        de_status = p_status
    WHERE de_id = p_id;
END //

DELIMITER ;



DELIMITER //

CREATE PROCEDURE delete_department_if_empty(
    IN p_id INT,
    OUT p_result INT
)
BEGIN
    DECLARE emp_count INT;

    SELECT COUNT(*) INTO emp_count
    FROM employee
    WHERE de_id = p_id;

    IF emp_count = 0 THEN
        DELETE FROM department WHERE de_id = p_id;
        SET p_result = 1; -- Xoá thành công
    ELSE
        SET p_result = 0; -- Không thể xoá
    END IF;
END //

DELIMITER ;



DELIMITER //

CREATE PROCEDURE search_department_by_name(
    IN p_keyword VARCHAR(100)
)
BEGIN
    SELECT * FROM department
    WHERE de_name LIKE CONCAT('%', p_keyword, '%');
END //

DELIMITER ;


DELIMITER //
CREATE PROCEDURE get_employees_paginated(
    IN p_offset INT,
    IN p_limit INT
)
BEGIN
    SELECT em_id, em_name, em_email, em_phone, em_salary, em_status, de_name
    FROM employee
             INNER JOIN department ON employee.de_id = department.de_id
    LIMIT p_offset, p_limit;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE add_employee(
    IN p_em_id CHAR(5),
    IN p_em_name VARCHAR(150),
    IN p_em_email VARCHAR(255),
    IN p_em_phone VARCHAR(10),
    IN p_em_salary DECIMAL(10,2),
    IN p_de_id INT
)
BEGIN
    DECLARE department_status ENUM('ACTIVE', 'INACTIVE');

    -- Kiểm tra trạng thái phòng ban
    SELECT de_status INTO department_status FROM department WHERE de_id = p_de_id;

    IF department_status = 'ACTIVE' THEN
        INSERT INTO employee(em_id, em_name, em_email, em_phone, em_salary, de_id, em_status)
        VALUES(p_em_id, p_em_name, p_em_email, p_em_phone, p_em_salary, p_de_id, 'ACTIVE');
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Department is not ACTIVE';
    END IF;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_employee(
    IN p_em_id CHAR(5),
    IN p_em_name VARCHAR(150),
    IN p_em_email VARCHAR(255),
    IN p_em_phone VARCHAR(10),
    IN p_em_salary DECIMAL(10,2),
    IN p_em_status ENUM('ACTIVE', 'INACTIVE', 'ONLEAVE', 'POLICYLEAVE')
)
BEGIN
    UPDATE employee
    SET em_name = p_em_name,
        em_email = p_em_email,
        em_phone = p_em_phone,
        em_salary = p_em_salary,
        em_status = p_em_status
    WHERE em_id = p_em_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE delete_employee(
    IN p_em_id CHAR(5)
)
BEGIN
    UPDATE employee
    SET em_status = 'INACTIVE'
    WHERE em_id = p_em_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE search_employee_by_name_age(
    IN p_name VARCHAR(150),
    IN p_min_age INT,
    IN p_max_age INT
)
BEGIN
    SELECT em_id, em_name, em_email, em_phone, em_salary, em_status, de_name
    FROM employee
             INNER JOIN department ON employee.de_id = department.de_id
    WHERE em_name LIKE CONCAT('%', p_name, '%')
      AND YEAR(CURDATE()) - YEAR(em_dob) BETWEEN p_min_age AND p_max_age;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sort_employees_by_salary()
BEGIN
    SELECT em_id, em_name, em_salary
    FROM employee
    ORDER BY em_salary DESC;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sort_employees_by_name()
BEGIN
    SELECT em_id, em_name
    FROM employee
    ORDER BY em_name ASC;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE count_employees_by_department()
BEGIN
    SELECT de_name, COUNT(*) AS employee_count
    FROM employee
             INNER JOIN department ON employee.de_id = department.de_id
    GROUP BY de_name;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE total_employee_count()
BEGIN
    SELECT COUNT(*) AS total_employees FROM employee;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE department_with_most_employees()
BEGIN
    SELECT de_name, COUNT(*) AS employee_count
    FROM employee
             INNER JOIN department ON employee.de_id = department.de_id
    GROUP BY de_name
    ORDER BY employee_count DESC
    LIMIT 1;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE department_with_highest_salary()
BEGIN
    SELECT de_name, MAX(em_salary) AS highest_salary
    FROM employee
             INNER JOIN department ON employee.de_id = department.de_id
    GROUP BY de_name
    ORDER BY highest_salary DESC
    LIMIT 1;
END //
DELIMITER ;