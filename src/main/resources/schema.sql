CREATE TABLE if not exists employee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE if not exists supervisor_employee_link (
    supervisor_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    feedback VARCHAR(255),
    PRIMARY KEY (supervisor_id, employee_id),
    FOREIGN KEY (supervisor_id) REFERENCES employee(id),
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);