package platform.qa.db;

import platform.qa.entities.Db;
import platform.qa.entities.User;

import javax.sql.DataSource;
import com.jcabi.aspects.Cacheable;
import com.jolbox.bonecp.BoneCPDataSource;

public class DataSourceInitializer {

    @Cacheable(forever = true)
    public static DataSource getSource(Db db, String schema) {
        BoneCPDataSource src = new BoneCPDataSource();

        src.setDriverClass(db.getDbClass());
        src.setJdbcUrl(db.getUrl().concat(schema));
        src.setUser(db.getUser());
        src.setPassword(db.getPassword());
        src.setAcquireRetryAttempts(3);
        return src;
    }

    @Cacheable(forever = true)
    public static DataSource getSource(Db db, User user, String schema) {
        BoneCPDataSource src = new BoneCPDataSource();
        src.setDriverClass(db.getDbClass());
        src.setJdbcUrl(db.getUrl().concat(schema));
        src.setUser(user.getLogin());
        src.setPassword(user.getPassword());
        src.setAcquireRetryAttempts(3);
        return src;
    }
}
