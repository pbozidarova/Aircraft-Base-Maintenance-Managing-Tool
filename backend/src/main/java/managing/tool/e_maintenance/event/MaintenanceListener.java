package managing.tool.e_maintenance.event;

import lombok.AllArgsConstructor;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.service.MaintenanceService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.transaction.Transactional;

@Component
@AllArgsConstructor
public class MaintenanceListener {

    private final MaintenanceService maintenanceService;

    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void afterCompletion(MaintenanceEvent maintenanceEvent){

        this.maintenanceService.allocateRandomTasksToMaintenance(maintenanceEvent.getMaintenanceNum());
    }

}
