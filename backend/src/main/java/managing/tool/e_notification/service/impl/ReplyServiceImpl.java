package managing.tool.e_notification.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_notification.model.ReplyEntity;
import managing.tool.e_notification.model.dto.ReplySeedDto;
import managing.tool.e_notification.repository.ReplyRepository;
import managing.tool.e_notification.service.CloudinaryService;
import managing.tool.e_notification.service.ReplyService;
import managing.tool.e_user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final UserService userService;
    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Override
    public void seedReplies() throws FileNotFoundException {
        if(areRepliesUploaded()){
            return;
        }

        ReplySeedDto[] dtos = this.gson.fromJson(new FileReader(GlobalConstants.REPLIES_MOCK_DATA_PATH), ReplySeedDto[].class);

        Arrays.stream(dtos).forEach(dto -> {
            ReplyEntity replyEntity = this.modelMapper.map(dto, ReplyEntity.class);
            replyEntity.setAuthor(this.userService.getRandomUser());
            replyEntity.setCreatedOn(LocalDateTime.now());

            this.replyRepository.save(replyEntity);
        });
    }

    @Override
    public boolean areRepliesUploaded() {
        return this.replyRepository.count() > 0;
    }

    @Override
    public List<ReplyEntity> findAll() {
        return this.replyRepository.findAll();
    }

    @Override
    public ReplyEntity saveReply(ReplyEntity reply) {
        return this.replyRepository.save(reply);
    }


}
