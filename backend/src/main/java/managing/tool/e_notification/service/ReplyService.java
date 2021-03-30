package managing.tool.e_notification.service;

import managing.tool.e_notification.model.ReplyEntity;

import java.io.FileNotFoundException;
import java.util.List;

public interface ReplyService {

    void seedReplies() throws FileNotFoundException;
    boolean areRepliesUploaded();

    List<ReplyEntity> findAll();

}
