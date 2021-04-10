package managing.tool.aop;

import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityChangeConfig {
    private int updatingTotalCount;
    private int updatingPerformedCount;

    private int creatingTotalCount;
    private int creatingPerformedCount;

    public int getUpdatingTotalCount() {
        return updatingTotalCount;
    }

    public EntityChangeConfig setUpdatingTotalCount(int updatingTotalCount) {
        this.updatingTotalCount = updatingTotalCount;
        return this;
    }

    public int getUpdatingPerformedCount() {
        return updatingPerformedCount;
    }

    public EntityChangeConfig setUpdatingPerformedCount(int updatingPerformedCount) {
        this.updatingPerformedCount = updatingPerformedCount;
        return this;
    }

    public int getCreatingTotalCount() {
        return creatingTotalCount;
    }

    public EntityChangeConfig setCreatingTotalCount(int creatingTotalCount) {
        this.creatingTotalCount = creatingTotalCount;
        return this;
    }

    public int getCreatingPerformedCount() {
        return creatingPerformedCount;
    }

    public EntityChangeConfig setCreatingPerformedCount(int creatingPerformedCount) {
        this.creatingPerformedCount = creatingPerformedCount;
        return this;
    }
}
