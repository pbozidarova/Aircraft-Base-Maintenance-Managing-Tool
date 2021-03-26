import NotificationsActiveOutlinedIcon from '@material-ui/icons/NotificationsActiveOutlined';
import DashboardIcon from '@material-ui/icons/Dashboard';
import AssignmentIcon from '@material-ui/icons/Assignment';
import ListIcon from '@material-ui/icons/List';
import LocationCityIcon from '@material-ui/icons/LocationCity';
import PeopleIcon from '@material-ui/icons/People';
import LocalAirportIcon from '@material-ui/icons/LocalAirport';
import CloudUploadIcon from '@material-ui/icons/CloudUpload';
import SendIcon from '@material-ui/icons/Send';


export const BACKEND_URL = 'http://localhost:8000';
export const SESSION_ATTRIBUTE_NAME = 'authenticatedUser';

export const MESSAGES = {
    initialLoad : "Please select a row in order to edit it!",
    successLoaded: "Successfully loaded!",
    successCreated : "Successfully created!",
    successUpdated : "Successfully updated!",
    errorNonExistant : "Does not exist!",
    errorExist : "Already exist such company number or email!",
    empty: "",
}
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
    ENGINEER: 'Engineer',
    roles: 'Roles'
}
export const MAINTENANCE_HEADER_DATA = {
    maintenanceNum: 'Maintenance Number',
    aircraftRegistration: 'Aircraft Registration',
    startDate: 'Start Date',
    endDate: 'End Date',
    status: 'Status',
    facility: 'Facility',
    responsibleEngineer: 'Responsible Engineer'
}
export const MAINTENANCE_BOOLEAN_FIELDS = {}
export const MAINTENANCE_DISABLED_FIELDS ={}


export const NOTIFICATIONS_HEADER_DATA = {
    // maintenanceNum: 'Maintenance Number',
    // aircraftRegistration: 'Aircraft Registration',
    // startDate: 'Start Date',
    // endDate: 'End Date',
    // status: 'Status',
    // facility: 'Facility',
    // responsibleEngineer: 'Responsible Engineer'
}
export const NOTIFICATIONS_BOOLEAN_FIELDS = {}
export const NOTIFICATIONS_DISABLED_FIELDS ={}


export const TASKS_HEADER_DATA = {
    taskNum: 'Task Number',
    code : 'Code',
    description : 'Description',
    preparedBy: "Prepared by",
    status: "Status",
}

export const TASKS_DISABLED_FIELDS = {
    // taskNum: 'Task Number',
    // code : 'Code',
    // preparedBy: "Prepared by",
    // status: "Status",
}
export const TASKS_BOOLEAN_FIELDS ={
    toolingAvailable: 'Tooling is available',
    jobCardsPrepared: 'Jobcards are prepared',
    qualityAssured: 'Quality is assured'
}
export const FACILITIES_HEADER_DATA = {
    name: 'Name',
    city: 'City',
    country: 'Country',
    capacity: 'Capacity',
    manager: 'Manager',
    
}
export const FACILITIES_BOOLEAN_FIELDS = {}
export const FACILITIES_DISABLED_FIELDS ={}

export const AIRCRAFT_HEADER_DATA = {
    aircraftRegistration : "Registration",
    serialNumber : "Serial Number",
    aircraftType : "Type",
    aircraftModel : "Model",
    operatorName: "Operator",
    operatorICAOCode : "Operator ICAO Code",
    engineManufacturer : "Engine Manufacturer",
    engineModelSeries : "Engine Model Series",
    
}
export const AIRCRAFT_BOOLEAN_FIELDS ={}
export const AIRCRAFT_DISABLED_FIELDS ={}

export const ICONS_MAPPING = {
    dashboard: <DashboardIcon />,
    notifications: <NotificationsActiveOutlinedIcon />,
    tasks: <AssignmentIcon />,
    maintenance: <ListIcon />,
    users: <PeopleIcon />,
    facility: <LocationCityIcon />,
    aircraft: <LocalAirportIcon />,
    update: <CloudUploadIcon />,
    create: < SendIcon/>,

}