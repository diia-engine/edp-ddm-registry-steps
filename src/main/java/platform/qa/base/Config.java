package platform.qa.base;

import platform.qa.clients.oc.OkdClient;
import platform.qa.config.RegistryConfig;
import platform.qa.entities.Service;

import java.util.concurrent.atomic.AtomicReference;
import com.github.javafaker.Faker;

public class Config extends RegistryConfig {

    private AtomicReference<Service> dataFactoryService = new AtomicReference<>();
    private Service formProvider;

    public Config() {
    }

    public Service getDataFactoryService() {
        return dataFactoryService.updateAndGet((service) -> getDataFactory());
    }

    public Service getFormManagementProvider() {
        if (formProvider != null) {
            return formProvider;
        }
        var ocClient = new OkdClient(cluster, ocNamespace);
        int port = Integer.parseInt(new Faker().number().digits(4));
        String podFormProvider = ocClient.getOsClient().pods().withLabel("app=form-management-provider")
                .list().getItems().get(0)
                .getMetadata().getName();
        ocClient.getOsClient().pods().withName(podFormProvider).portForward(3001, port);
        formProvider = new Service("http://localhost:" + port + "/");
        return formProvider;
    }
}
