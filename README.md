# EmployeeManager API

The **EmployeeManager API** is a basic CRUD API designed to efficiently manage employees within an organization. It operates on two primary database tables: the `employee` table and the `supervisor_employee_link` table.

## Tables:

1. **Employee Table:**
   - Stores basic information about employees such as name, position, etc.

2. **Supervisor_Employee_Link Table:**
   - Facilitates the establishment of supervisor-employee relationships.
   - Additional fields like "feedback" are included in the schema as placeholders for potential future expansion.

## Supervisorship Restrictions:
- **Single Supervisor Rule:**
  - Each employee is limited to having only one supervisor in the management system.
- **Hierarchical Constraints:**
  - An employee 'A' can only manage employee 'B' if 'B' is not already at a higher hierarchical level than 'A'.
- **Unlimited Subordinate Management:**
  - An employee can manage any number of other employees within the organization.

### Design Rationale:
The decision to use two separate tables, as opposed to a single `employee` table with a supervisor reference, was made to allow for potential future expansion and to accommodate additional information specific to the supervisor-employee relationship. The "feedback" field is an example of this


## Example api calls

### POST
**Create Employee**  
`localhost:8080/employee`

**Body**  
Raw (JSON)
```json
{
    "firstName": "Jackson",
    "lastName": "Park",
    "position": "Front Desk"
}
```

### GET
**Get All Employees**  
`localhost:8080/employee`

### PATCH
**Update Employee**  
`localhost:8080/employee/3`

**Body**  
Raw (JSON)
```json
{
    "firstName": "Rudo",
    "lastName": "Doom",
    "position": "Retailer"
}
```

### DELETE
**Delete Employee**  
`localhost:8080/employee/8`

### POST
**Create Link**  
`localhost:8080/supervisor-link?supervisorId=11&employeeId=3`

**Query Params**  
- supervisorId: 11
- employeeId: 3

### DELETE
**Delete Link**  
`localhost:8080/supervisor-link/1`

### PATCH
**Edit Link**  
`localhost:8080/supervisor-link?supervisorId=11&employeeId=3`

**Query Params**  
- supervisorId: 11
- employeeId: 3
