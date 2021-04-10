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
import aircraftImg from '../src/images/aircraft.jfif'
import facilitiesImg from '../src/images/facilities.jfif'
import maintenanceImg from '../src/images/maintenance.jfif'
import notificationsImg from '../src/images/notifications.jfif'
import tasksImg from '../src/images/tasks.jfif'
import usersImg from '../src/images/users.jfif'


export const BACKEND_URL = 'http://localhost:8000';
export const SESSION_ATTRIBUTE_NAME = 'authenticatedUser';

export const MESSAGES = {
    welcomeMsg : "Welcome to the Aircraft Base Maintenance Management Tool! ",
    usersInfo: "If you login with N90909, you will be an Engineer and Admin, and if you login with N70707 you will be Mechanic and User.",
    initialAdvice : "Please select a row in order to edit it!",
    successLogingIn: "Successfully logged in!",
    successLogOut: "Successfully logged out! We are expecting you again soon!",
    successLoaded: "Successfully loaded! ",
    allData: "All data!",
    allTasks: "You are reviewing all the tasks that our highly trained maintenance staff is capable of performing on an aircraft! ",
    successCreated : "Successfully created! ",
    successUpdated : "Successfully updated! ",

    taskEditInfo: "You are not allowed to edit the task number! Your name will be automaticaly added to the preparing team. The status of the task is OK whenever the three checkboxes are checked and NOT_OK in any other case!",
    // taskMaintenanceTooltip: "Check in which maintenance the task is performed",
    // taskNotifTooltip: "Check notifications for this task",
    
    maintenanceEditInfo: "You are not allowed to edit the maintenance number! The status of the maintenance could be UPCOMING, OPENED or CLOSED based on the dates specified!",
    
    notificationsEditInfo: "The notification number is automaticaly composed of a unique number, maintenace and a task number! You are not allowed to close a notification without classifying it!",
    
    facilitiesEditInfo: "You are not allowed to edit the facility name!",

    aircraftEditInfo: "You are not allowed to edit the aircraft registration!",


    pleaseWait: "Please wait!",
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
    facilities: <LocationCityIcon />,
    aircraft: <LocalAirportIcon />,
    update: <CloudUploadIcon />,
    create: < SendIcon/>,
    attach: <AttachFileIcon/>
}

export const DASHBOARD_CARDS = {
    tasks: {
        title: 'Maintenance Tasks',
        description: 'A point in a maintenance program that consists of a complex steps.',
        image: tasksImg
        // image: 'https://images.unsplash.com/photo-1534190239940-9ba8944ea261?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=1189&q=80',
        // image: 'https://images.unsplash.com/photo-1600492515568-8868f609511e?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1119&q=80',
    },
    maintenance: {
        title: 'Maintenance Events',
        description: 'A collection of tasks combined and performed on an aircraft',
        image: maintenanceImg,
        // image: 'https://images.unsplash.com/photo-1598621961279-557cec9ba744?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=967&q=80',
    },
    notifications: {
        title: 'Notifications',
        description: 'Means of communication between the technician and the mechanic',
        image: notificationsImg,
        // image: 'https://images.unsplash.com/photo-1544717301-9cdcb1f5940f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80',
    },
    aircraft: {
        title: 'Aircraft',
        description: 'Common database of simple data of every aircraft that the company has mainteained at some point in time.',
        image: aircraftImg,
        // image: "https://images.unsplash.com/photo-1530545124313-ce5e8eae55af?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1051&q=80",
    },
    facilities: {
        title: 'Facilities',
        description: 'A list with the MRO organizations where the company is phisically performing maintenace',
        image: facilitiesImg,
        // image: 'https://images.unsplash.com/photo-1590233033212-95081b4e52fb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80',
    },
    users: {
        title: 'Employees',
        description: 'The employees are the user of the application!',
        image: usersImg,
        // image: "https://images.unsplash.com/photo-1524087497080-748556d9282b?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1190&q=80"
        // image: 'https://images.unsplash.com/photo-1485310818226-f01c4269687f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80',
    }

}

export const FETCH_DATA_KEY = {
    facility: 'facilityViewDtoList',
    aircraftRegistration: 'aircraftViewDtoList',
    manager: 'userViewDtoList',
    responsibleEngineer: 'userViewDtoList',
    tasks : 'taskViewDtoList',
    maintenanceNum: 'maintenanceViewDtoList',
    taskNum: 'taskViewDtoList',  
    
}

export const GLOBAL_SELECT_FIELDS = {
    //allocate select fields and request mapping for the backend
    maintenanceNum: ['maintenance', 'maintenanceNum'],
    aircraftRegistration: ['aircraft', 'aircraftRegistration'],
    facility: ['facilities', 'name'],
    responsibleEngineer: 'users',
    manager: ['users', 'companyNum', 'lastName', 'firstName'],
    taskNum: ['tasks', 'taskNum'],
    status: 'status',
    classification: 'classification'
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
export const MAINTENANCE_EDIT_FIELDS ={
    maintenanceNum: 'Maintenance Number',
    aircraftRegistration: 'Aircraft Registration',
    facility: 'Facility',
    responsibleEngineer: 'Responsible Engineer',
    startDate: 'Start Date',
    endDate: 'End Date',
}

//NOTIFICATIONS--------------------------------------------
export const NOTIFICATIONS_HEADER_DATA = {
    notificationNum: 'Notification Number',
    author: 'Author',
    status: 'Status',
    classification: 'Classification',
    dueDate: 'Due date',
    maintenanceNum: 'Maintenance Number',
    taskNum: 'Task Number'
}
export const NOTIFICATIONS_BOOLEAN_FIELDS = {
    
}
export const NOTIFICATION_EDIT_FIELDS ={
    // notificationNum: 'Notification Number',
    status: 'Status',
    classification: 'Classification',
    dueDate: 'Due date',

}
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
    preparedBy: 'Prepared by',
    status: 'Status',
}
export const TASK_EDIT_FIELDS = {
    taskNum: 'Task Number',
    code : 'Code',
    description : 'Description',
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
export const FACILITY_EDIT_FIELDS = FACILITIES_HEADER_DATA
// {
//     name: 'Name',
//     city: 'City',
//     country: 'Country',
//     capacity: 'Capacity',
//     manager: 'Manager',
// }

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
export const AIRCRAFT_EDIT_FIELDS = AIRCRAFT_HEADER_DATA

