import NotificationsActiveOutlinedIcon from '@material-ui/icons/NotificationsActiveOutlined';
import DashboardIcon from '@material-ui/icons/Dashboard';
// import AssignmentIcon from '@material-ui/icons/Assignment';
// import ListIcon from '@material-ui/icons/List';
import AccountBalanceIcon from '@material-ui/icons/AccountBalance';
import BuildIcon from '@material-ui/icons/Build';
import LocationCityIcon from '@material-ui/icons/LocationCity';
import PeopleIcon from '@material-ui/icons/People';
import LocalAirportIcon from '@material-ui/icons/LocalAirport';
import CloudUploadIcon from '@material-ui/icons/CloudUpload';
import SendIcon from '@material-ui/icons/Send';
import AttachFileIcon from '@material-ui/icons/AttachFile';


export const BACKEND_URL = 'http://localhost:8000';
export const SESSION_ATTRIBUTE_NAME = 'authenticatedUser';

export const MESSAGES = {
    initialLoad : "Please select a row in order to edit it!",
    successLogingIn: "Successfully logged in!",
    successLoaded: "Successfully loaded! ",
    allData: "All data!",
    successCreated : "Successfully created! ",
    successUpdated : "Successfully updated! ",
    errorNonExistant : "Does not exist! ",
    errorExist : "Already exist such company number or email! ",
    errorSomethingIsWrong: "We are so sorry! Something is wrong. Please try again later!",
    empty: "",
}

export const ICONS_MAPPING = {
    dashboard: <DashboardIcon />,
    notifications: <NotificationsActiveOutlinedIcon />,
    tasks: <AccountBalanceIcon />,
    maintenance: <BuildIcon />,
    users: <PeopleIcon />,
    facility: <LocationCityIcon />,
    aircraft: <LocalAirportIcon />,
    update: <CloudUploadIcon />,
    create: < SendIcon/>,
    attach: <AttachFileIcon/>
}

export const DASHBOARD_CARDS = {
    tasks: {
        title: 'Maintenance Tasks',
        description: '',
        image: '',
    },
    maintenance: {
        title: 'Maintenance Events',
        description: '',
        image: '',
    },
    notifications: {
        title: 'Notifications',
        description: '',
        image: '',
    },
    aircraft: {
        title: 'Aircraft',
        description: '',
        image: "https://images.unsplash.com/photo-1530545124313-ce5e8eae55af?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1051&q=80",
    },
    facilities: {
        title: 'Facilities',
        description: '',
        image: '',
    }

}

export const FETCH_DATA_KEY = {
    facility: 'facilityViewDtoList',
    aircraftRegistration: 'aircraftViewDtoList',
    manager: 'userViewDtoList',
    responsibleEngineer: 'userViewDtoList',
    tasks : 'taskViewDtoList',
    maintenance: 'maintenanceViewDtoList',
    notifications: 'notificationViewDtoList',  
    
}

export const GLOBAL_SELECT_FIELDS = {
    //allocate select fields and request mapping for the backend
    facility: ['facilities', 'name'],
    aircraftRegistration: ['aircraft', 'aircraftRegistration'],
    manager: ['users', 'companyNum', 'lastName', 'firstName'],
    responsibleEngineer: 'users',
    //TODO!!!!
    // authory: "Authority",
    // role: "Role"
}

//USERS--------------------------------------------
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

//MAINTENANCE--------------------------------------------
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

//NOTIFICATIONS--------------------------------------------
export const NOTIFICATIONS_HEADER_DATA = {
    notificationNum: 'Notification Number',
    author: 'Author',
    status: 'Status',
    classification: 'Classification',
    dueDate: 'Due date',
    // maintenanceNum: 'Maintenance Number',
    // taskNum: 'Task Number'

    // aircraftRegistration: 'Aircraft Registration',
    // startDate: 'Start Date',
    // endDate: 'End Date',
    // facility: 'Facility',
    // responsibleEngineer: 'Responsible Engineer'
}
export const NOTIFICATIONS_BOOLEAN_FIELDS = {}
export const NOTIFICATIONS_DISABLED_FIELDS ={}
export const REPLIES_HEADER_DATA ={
    description: 'Description',
    author: 'Author',
    title: 'Title',
    date: 'Date',
    attachments: 'Attachments'
}


//TASKS--------------------------------------------
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

//FACILITIES--------------------------------------------
export const FACILITIES_HEADER_DATA = {
    name: 'Name',
    city: 'City',
    country: 'Country',
    capacity: 'Capacity',
    manager: 'Manager',
    
}
export const FACILITIES_BOOLEAN_FIELDS = {}
export const FACILITIES_DISABLED_FIELDS ={}

//AIRCRAFT--------------------------------------------
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

