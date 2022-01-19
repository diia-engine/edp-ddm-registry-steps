package platform.qa.base;

import platform.qa.config.RegistryConfig;
import platform.qa.entities.Service;

import java.util.concurrent.atomic.AtomicReference;

public class Config extends RegistryConfig {

    private AtomicReference<Service> dataFactoryService = new AtomicReference<>();

    public Config() {
    }

    public Service getDataFactoryService() {
        return dataFactoryService.updateAndGet((service) -> getDataFactory());
    }
}
