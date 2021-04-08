package managing.tool.e_notification.web;


import lombok.AllArgsConstructor;
import managing.tool.e_notification.model.dto.ReplyResponseDto;
import managing.tool.e_notification.model.dto.ReplyViewDto;
import managing.tool.e_notification.service.NotificationService;
import managing.tool.exception.NotFoundInDb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static managing.tool.constants.GlobalConstants.NOTFOUNDERROR;

@RestController
@CrossOrigin
@RequestMapping("/replies")
@AllArgsConstructor
public class ReplyController {

    private final NotificationService notificationService;

    @GetMapping("/{notificationNum}")
    public ResponseEntity<List<ReplyViewDto>> getRepliesForNotification(@PathVariable String notificationNum){
        List<ReplyViewDto> communication =
                this.notificationService.getCommunication(notificationNum);

        return ResponseEntity.ok().body(communication);
    }

    @PutMapping("/{notificationNum}/create")
    public ResponseEntity<ReplyViewDto> createReplyForNotification(
            @RequestHeader("authorization") String jwt,
            @PathVariable String notificationNum, @RequestBody ReplyResponseDto replyToCreate ) throws IOException {

        if(!this.notificationService.notificationExists(notificationNum)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, notificationNum), "notificationNum");
        }

        ReplyViewDto replyCreated = this.notificationService.createReply(notificationNum, jwt, replyToCreate);

        return new ResponseEntity<>(replyCreated, HttpStatus.OK);
    }

}
