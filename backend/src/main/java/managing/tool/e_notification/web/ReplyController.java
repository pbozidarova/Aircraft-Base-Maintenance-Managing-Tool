package managing.tool.e_notification.web;


import lombok.AllArgsConstructor;
import managing.tool.e_notification.model.dto.ReplyViewDto;
import managing.tool.e_notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
