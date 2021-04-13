package managing.tool.e_notification.service.impl;

import com.google.gson.Gson;
import managing.tool.e_notification.model.ReplyEntity;
import managing.tool.e_notification.repository.ReplyRepository;
import managing.tool.e_user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ReplyServiceImplTest {
    private ReplyServiceImpl testService;
    private ReplyEntity reply;

    Gson gson;

    @Mock
    UserService mockedUserService;
    @Mock
    ReplyRepository mockedReplyRepository;
    @Mock
    ModelMapper mockedModelMapper;

    @BeforeEach
    void setUp(){
        testService = new ReplyServiceImpl(mockedUserService, mockedReplyRepository, mockedModelMapper, gson);

        reply = new ReplyEntity();
        reply.setDescription("Very long test description!");
    }

    @Test
    void areRepliesUploaded(){
        Mockito.when(mockedReplyRepository.count()).thenReturn(1L);

        Assertions.assertTrue(testService.areRepliesUploaded());
    }

    @Test
    void findAllTest(){
        ReplyEntity replyEntity = new ReplyEntity();
        Mockito.when(mockedReplyRepository.findAll()).thenReturn(List.of(replyEntity));

        Assertions.assertEquals(1, testService.findAll().size());
    }

    @Test
    void replySaveTest(){
        Mockito.when(mockedReplyRepository.save(reply)).thenReturn(reply);

        Assertions.assertEquals(testService.saveReply(reply).getDescription(), reply.getDescription());
    }
}
