package platform.qa.base;

import lombok.Getter;
import platform.qa.entities.Db;
import platform.qa.clients.oc.OkdClient;
import platform.qa.config.RegistryConfig;
import platform.qa.entities.Service;

import java.util.concurrent.atomic.AtomicReference;
import com.github.javafaker.Faker;

public class Config extends RegistryConfig {
    @Getter
    private Db primaryDb;
    private AtomicReference<Service> dataFactoryService = new AtomicReference<>();

    public Config() {
        OkdClient ocClient = new OkdClient(cluster, ocNamespace);
        var userDb = ocClient.getCredentials(properties.getProperty("oc.db.secrets"));
        String dbMainUrl = "jdbc:postgresql://citus-master." + ocNamespace + ".svc.cluster.local/";
        if (localRun) {
            int portMain = Integer.valueOf(new Faker().number().digits(4));
            dbMainUrl = "jdbc:postgresql://localhost:"+portMain+"/";

            String podMaster = ocClient.getOsClient().pods().withLabel("app=citus-master")
                    .list().getItems().get(0)
                    .getMetadata().getName();

            ocClient.getOsClient().pods().withName(podMaster).portForward(5432, portMain);
        }
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
