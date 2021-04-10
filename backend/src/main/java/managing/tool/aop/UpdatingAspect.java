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

    @Around(value = "@annotation(TrackUpdating)")
    public void trackUpdating(ProceedingJoinPoint pjp, TrackUpdating TrackUpdating) throws Throwable {
        String updatingMethod = TrackUpdating.updatingMethod();
        try{
            pjp.proceed();
            config.setUpdatingPerformedCount( config.getUpdatingPerformedCount() +1 );
            config.setUpdatingTotalCount(config.getUpdatingTotalCount() + 1);

            LOGGER.info( logString(updatingMethod, config.getUpdatingPerformedCount(),config.getUpdatingTotalCount()));

        }catch (Throwable errorThrown){
//            LOGGER.error("An error prevented the updating method to proceed.", errorThrown);
            config.setUpdatingTotalCount(config.getUpdatingTotalCount() + 1);
            LOGGER.info( logString(updatingMethod, config.getUpdatingPerformedCount(),config.getUpdatingTotalCount()));
            throw errorThrown;
        }
    }

    @Around(value = "@annotation(TrackCreation)")
    public void trackCreating(ProceedingJoinPoint pjp, TrackCreation TrackCreation) throws Throwable {
        String creatingMethod = TrackCreation.creatingMethod();

        try{
            pjp.proceed();
            config.setCreatingPerformedCount( config.getCreatingPerformedCount() +1 );
            config.setCreatingTotalCount(config.getCreatingTotalCount() + 1);

            LOGGER.info( logString(creatingMethod, config.getCreatingPerformedCount(),config.getCreatingTotalCount()));

        }catch (Throwable errorThrown){
//            LOGGER.error("An error prevented the updating method to proceed.", errorThrown);
            config.setUpdatingTotalCount(config.getUpdatingTotalCount() + 1);
            LOGGER.info( logString(creatingMethod, config.getUpdatingPerformedCount(),config.getUpdatingTotalCount()));
            throw errorThrown;
        }
    }

    private String logString(String methodName, int performed, int total){
        return String.format("Method %s has been performed. A total of %d update request%s have been made of which %d %s successful! ",
                methodName,
                total,
                total == 1 ? "" : "s",
                performed,
                performed == 1 ? "has" : "have"
        );
    }
}
