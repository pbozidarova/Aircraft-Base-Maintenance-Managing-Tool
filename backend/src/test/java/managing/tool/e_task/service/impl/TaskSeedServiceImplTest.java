package managing.tool.e_task.service.impl;

import com.google.gson.Gson;
import managing.tool.e_task.repository.TaskRepository;
import managing.tool.e_user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Random;

@ExtendWith(MockitoExtension.class)
public class TaskSeedServiceImplTest {
    private TaskSeedServiceImpl testService;
    private Gson gson;

    @Mock
    TaskRepository mockedTaskRepository;
    @Mock
    UserService mockedUserService;
    @Mock
    ModelMapper mockedModelMapper;
    @Mock
    Random mockedRandom;

    @BeforeEach
    void setUp(){
        testService = new TaskSeedServiceImpl(mockedTaskRepository, mockedUserService, mockedModelMapper, gson, mockedRandom);
    }

    @Test
    void tasksAreImported(){
        Mockito.when(mockedTaskRepository.count()).thenReturn(1L);

        Assertions.assertTrue(testService.areTasksUploaded());
    }
}
