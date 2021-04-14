package managing.tool.e_notification.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_notification.model.NotificationStatusEnum;
import managing.tool.e_notification.service.NotificationService;
import managing.tool.e_notification.service.NotificationValidationService;
import managing.tool.e_task.service.TaskService;
import managing.tool.exception.NotFoundInDb;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

import static managing.tool.constants.GlobalConstants.NOTFOUND_SELECT_ERROR;

@Service
@AllArgsConstructor
public class NotificationValidationServiceImpl implements NotificationValidationService {

    private final MaintenanceService maintenanceService;
    private final TaskService taskService;


    @Override
    public void validateStatus(String status) {
        if(!Arrays.stream(NotificationStatusEnum.values()).collect(Collectors.toList()).contains(status.toUpperCase())) {
            throw new NotFoundInDb(
                    String.format(NOTFOUND_SELECT_ERROR, status, "status"),
                    "status"
            );
        }
    }

    @Override
    public void validateClassification(String classification) {
        if(classification == null) return;

        if(!Arrays.stream(NotificationStatusEnum.values()).collect(Collectors.toList()).contains(classification.toUpperCase())) {
            throw new NotFoundInDb(
                    String.format(NOTFOUND_SELECT_ERROR, classification, "classification"),
                    "classification"
            );
        }
    }
    @Override
    public void validateIfMaintenanceExists(String maintenanceNum) {
        if(!this.maintenanceService.maintenanceExists(maintenanceNum)){
            throw new NotFoundInDb(
                    String.format(NOTFOUND_SELECT_ERROR, maintenanceNum, "maintenance"),
                    "maintenance number"
            );
        }
    }
    @Override
    public void validateIfTaskExists(String taskNum) {
        if(!this.taskService.taskExists(taskNum)){
            throw new NotFoundInDb(
                    String.format(NOTFOUND_SELECT_ERROR, taskNum, "task"),
                    "task number"
            );
        }
    }



}
