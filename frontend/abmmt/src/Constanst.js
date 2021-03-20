
export const BACKEND_URL = 'http://localhost:8000';
export const SESSION_ATTRIBUTE_NAME = 'authenticatedUser';

export const USERS_HEADER_DATA = {
    companyNum: 'Company Number',
    firstName : 'First Name',
    lastName : 'Last Name',
    email : 'Email',
    facility: "Facility",
    roles : 'Roles',
}
export const USERS_BOOLEAN_FIELDS = {
    
    USER : 'User',
    ADMIN: 'Admin',
    MECHANIC: 'Mechanic',
    ENGINEER: 'Engineer'

}


export const TASKS_HEADER_DATA = {
    taskNum: 'Task Number',
    description : 'Description',
    code : 'Code',
    
}

export const FACILITIES_HEADER_DATA = {
    name: 'Name',
    city: 'City',
    country: 'Country',
    capacity: 'Capacity',
    manager: 'Manager',
    
}

export const AIRCRAFT_HEADER_DATA = {
    serialNumber : "Serial Number",
    operatorName: "Operator",
    operatorICAOCode : "Operator ICAO Code",
    aircraftType : "Type",
    aircraftModel : "Model",
    aircraftRegistration : "Registration",
    engineManufacturer : "Engine Manufacturer",
    engineModelSeries : "Engine Model Series",
    
}