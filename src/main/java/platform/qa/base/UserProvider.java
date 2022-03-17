package platform.qa.base;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import platform.qa.configuration.MasterConfig;
import platform.qa.configuration.RegistryConfig;
import platform.qa.entities.User;

import java.util.concurrent.atomic.AtomicReference;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserProvider {
    private static UserProvider instance;
    private final RegistryConfig registryConfig = MasterConfig.getInstance().getRegistryConfig();

    private AtomicReference<User> dataUser = new AtomicReference<>();
    private AtomicReference<User> registryUser = new AtomicReference<>();


    public User getDataUser() {
        return dataUser.updateAndGet((user) -> {
            User currentUser = registryConfig.initUser(user, "auto-user-data");
            return registryConfig.refreshUserToken(currentUser);
        });
    }

    public User getUserByName(String userName) {
        return registryUser.updateAndGet((user) -> {
            User currentUser = registryConfig.initUser(user, userName);
            return registryConfig.refreshUserToken(currentUser);
        });
    }

    public static UserProvider getInstance() {
        if (instance == null) {
            instance = new UserProvider();
        }
        return instance;
    }


}
