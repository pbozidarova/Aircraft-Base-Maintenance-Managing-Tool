package managing.tool.aop;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
@AllArgsConstructor
public class UpdatingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatingAspect.class);
    private final EntityChangeConfig config;

    @After(value = "@annotation(TrackUpdating)")
    public void trackUpdating(TrackUpdating TrackUpdating) throws Throwable {
        String updatingID = TrackUpdating.updating();
        config.setUpdatingCount( config.getUpdatingCount() +1 );

        LOGGER.info(String.format("A total of %d updating requests have been made! %s", config.getUpdatingCount(), updatingID));
    }

    @After(value = "@annotation(TrackCreation)")
    public void trackCreating(TrackCreation TrackCreation) throws Throwable {
        String creatingID = TrackCreation.creating();
        config.setCreatingCount( config.getCreatingCount() +1 );

        LOGGER.info(String.format("A total of %d creating requests have been made! %s", config.getCreatingCount(), creatingID));
    }
}
