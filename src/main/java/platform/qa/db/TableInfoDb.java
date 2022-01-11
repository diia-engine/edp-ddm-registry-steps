package platform.qa.db;

import static org.awaitility.Awaitility.await;

import lombok.SneakyThrows;
import platform.qa.base.ConstantWaits;

import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import com.jcabi.jdbc.JdbcSession;

public class TableInfoDb {
    protected final DataSource source;

    public TableInfoDb(DataSource source) {
        this.source = source;
    }

    @SneakyThrows
    public List<Map> waitAndGetEntity(String query){
        final  List<Map> list = Lists.newArrayList();

        await()
                .pollInterval(ConstantWaits.POLL_INTERVAL, TimeUnit.SECONDS)
                .atMost(ConstantWaits.WAIT_BETWEEN_MAINDB_REPLICADB, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    list.addAll(getValues(query));

                    Assert.assertNotNull( "No items in database!", list);
                });

        return  list.isEmpty() ? null : list;

    }


    @SneakyThrows
    private  List<Map> getValues(String query){
        List<Map> list = Lists.newArrayList();
        return new JdbcSession(source)
                .sql(query).select((resultSet, statement) -> {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (resultSet.next()) {
                        Map<String,Object> resultRow = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++){
                            resultRow.put(metaData.getColumnName(i),resultSet.getString(i));
                        }
                        list.add(resultRow);
                    }
                    return list;
                });
    }
}
