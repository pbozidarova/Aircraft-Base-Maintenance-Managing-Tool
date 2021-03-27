package managing.tool.e_notification.web;

import lombok.AllArgsConstructor;
import managing.tool.e_notification.model.dto.NotificationViewDto;
import managing.tool.e_notification.service.NotificationService;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static managing.tool.constants.GlobalConstants.FOUNDERROR;
import static managing.tool.constants.GlobalConstants.NOTFOUNDERROR;

@RestController
@CrossOrigin
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationCUDController {

    private final NotificationService notificationService;

    @PutMapping("/{notificationNum}/update")
    public ResponseEntity<NotificationViewDto> updateSingleNotification(
            @PathVariable String notificationNum, @RequestBody NotificationViewDto notificationDataForUpdate ){

        if(!this.notificationService.notificationExists(notificationNum)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, notificationNum), "notificationNum");
        }
//        if(this.notificationService.emailExistsForAnotherUser(userDataForUpdate.getEmail(), companyNum)){
//            throw new FoundInDb(String.format(FOUNDERROR, userDataForUpdate.getEmail()), "email");
//        }

        NotificationViewDto notificationUpdated = this.notificationService.updateNotification(notificationDataForUpdate);

        return new ResponseEntity<>(notificationUpdated, HttpStatus.OK);
    }

    @PutMapping("/{notificationNum}/create")
    public ResponseEntity<NotificationViewDto> createSingleUser(
            @PathVariable String notificationNum, @RequestBody NotificationViewDto notificationNewData ){

        if(this.notificationService.notificationExists(notificationNum)){
            throw new FoundInDb(String.format(FOUNDERROR, notificationNum), "notificationNum");
        }

        NotificationViewDto notificationCreated = this.notificationService.createNotification(notificationNewData);
        return new ResponseEntity<>(notificationCreated, HttpStatus.OK);
    }

}