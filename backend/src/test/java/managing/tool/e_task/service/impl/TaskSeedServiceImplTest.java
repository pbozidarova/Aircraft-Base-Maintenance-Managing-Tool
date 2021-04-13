package managing.tool.e_task.service.impl;

import com.google.gson.Gson;
import managing.tool.e_task.model.TaskEntity;
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
    private TaskEntity taskExisting;

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

        taskExisting = new TaskEntity();
    }

    @Test
    void tasksAreImported(){
        Mockito.when(mockedTaskRepository.count()).thenReturn(1L);

        Assertions.assertTrue(testService.areTasksUploaded());
    }

    @Test
    void getRandomTaskListTest(){

        Mockito.when(mockedTaskRepository.count())
                .thenReturn((long)5);
        Mockito.when(mockedRandom.nextInt(1))
                .thenReturn(2);
        Mockito.when(mockedTaskRepository.getOne(3L))
                .thenReturn(taskExisting);

        Assertions.assertTrue(testService.getRandomTaskList().size() == 1);
    }
}
