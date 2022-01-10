package platform.qa.base;

import lombok.Getter;
import platform.qa.entities.Db;
import platform.qa.clients.oc.OkdClient;
import platform.qa.config.RegistryConfig;
import platform.qa.entities.Service;

import java.util.concurrent.atomic.AtomicReference;

public class Config extends RegistryConfig {
    @Getter
    private Db primaryDb;
    private AtomicReference<Service> dataFactoryService = new AtomicReference<>();

    public Config() {
        OkdClient ocClient = new OkdClient(cluster, ocNamespace);
        var userDb = ocClient.getCredentials(properties.getProperty("oc.db.secrets"));
        String dbMainUrl = "jdbc:postgresql://citus-master." + ocNamespace + ".svc.cluster.local/";
        primaryDb = Db.builder()
                .url(dbMainUrl)
                .dbClass(properties.getProperty("db.driver-class-name"))
                .user(userDb.getLogin())
                .password(userDb.getPassword())
                .build();
    }

    public Service getDataFactoryService() {
        return dataFactoryService.updateAndGet((service) -> getDataFactory());
    }
}
