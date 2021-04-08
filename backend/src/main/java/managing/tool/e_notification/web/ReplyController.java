package managing.tool.e_notification.web;


import lombok.AllArgsConstructor;
import managing.tool.e_notification.model.dto.ReplyResponseDto;
import managing.tool.e_notification.model.dto.ReplyViewDto;
import managing.tool.e_notification.service.NotificationService;
import managing.tool.exception.NotFoundInDb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(value = "/{notificationNum}/create",
            method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public ResponseEntity<ReplyViewDto> createReplyForNotification(
            @RequestHeader("authorization") String jwt,
            @PathVariable String notificationNum,
            @RequestPart(value="attachment", required=false) MultipartFile attachment,
            @RequestPart("description") String description ) throws IOException {

        if(!this.notificationService.notificationExists(notificationNum)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, notificationNum), "notificationNum");
        }

        ReplyResponseDto replyToCreate = new ReplyResponseDto();
        replyToCreate.setDescription(description);

        ReplyViewDto replyCreated = this.notificationService.createReply(notificationNum, jwt, replyToCreate, attachment);

        return new ResponseEntity<>(replyCreated, HttpStatus.OK);
    }

}
