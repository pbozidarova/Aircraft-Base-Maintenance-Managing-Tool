package managing.tool.aop;

import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityChangeConfig {
    private int updatingCount;
    private int creatingCount;

    public int getUpdatingCount() {
        return updatingCount;
    }

    public EntityChangeConfig setUpdatingCount(int updatingCount) {
        this.updatingCount = updatingCount;
        return this;
    }

    public int getCreatingCount() {
        return creatingCount;
    }

    public EntityChangeConfig setCreatingCount(int creatingCount) {
        this.creatingCount = creatingCount;
        return this;
    }
}
