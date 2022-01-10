package platform.qa.db;

import static org.awaitility.Awaitility.await;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import platform.qa.base.ConstantWaits;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import com.jcabi.jdbc.JdbcSession;

public class TableInfoDb {
    protected final DataSource source;

    public TableInfoDb(DataSource source) {
        this.source = source;
    }

    @SneakyThrows
    public String waitAndGetEntity(String query, boolean toBeEmpty){
        final  List<String> list = Lists.newArrayList();

        await()
                .pollInterval(ConstantWaits.POLL_INTERVAL, TimeUnit.SECONDS)
                .atMost(ConstantWaits.WAIT_BETWEEN_MAINDB_REPLICADB, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    list.addAll(getValues(query));

                    Assertions.assertEquals(toBeEmpty, list.isEmpty(), "Waiting condition is not reached:");
                });

        return  list.isEmpty() ? null : list.get(0);

    }

    @SneakyThrows
    private  List<String> getValues(String query){
        List<String> list = Lists.newArrayList();
        return new  JdbcSession(source)
                .sql(query).select((resultSet, statement) -> {
                    while (resultSet.next()) {
                        list.add(resultSet.getString(1));
                    }
                    return list;
                });

    }

    @SneakyThrows
    public  <T> List<T>  waitAndGetEntity(String query,Class<T> clazz, boolean toBeEmpty){
        final  List<T> list = Lists.newArrayList();
        await()
                .pollInterval(ConstantWaits.POLL_INTERVAL, TimeUnit.SECONDS)
                .atMost(ConstantWaits.WAIT_BETWEEN_MAINDB_REPLICADB, TimeUnit.SECONDS)
                .untilAsserted(() -> {

                    list.addAll(getValues(query,clazz));

                    Assertions.assertEquals(toBeEmpty, list.isEmpty(), "Waiting condition is not reached:");
                });
        return  list;
    }

    @SneakyThrows
    public <T> List<T>  waitAndGetEntity(String query,Class<T> clazz){
        final  List<T> list = Lists.newArrayList();
        await()
                .pollInterval(ConstantWaits.POLL_INTERVAL, TimeUnit.SECONDS)
                .atMost(ConstantWaits.WAIT_BETWEEN_MAINDB_REPLICADB, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    boolean result = list.addAll(getValues(query,clazz));
                    Assert.assertNotNull( "No items in database!", result);
                });
        return  list;
    }

    @SneakyThrows
    private  <T> List<T> getValues(String query, Class<T> clazz) {
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        for (Field field : fields) {
            field.setAccessible(true);
        }
        List<T> list = Lists.newArrayList();
        return new  JdbcSession(source)
                .sql(query).select((resultSet, statement) -> {
                    while (resultSet.next()) {
                        T dto = null;
                        try {
                            dto = clazz.getConstructor().newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        for (Field field : fields) {
                            String name = field.getName();

                            try {
                                String value = resultSet.getString(name);
                                if (value == null) continue;
                                field.set(dto, field.getType().getConstructor(String.class).newInstance(value));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        list.add(dto);

                    }
                    return list;
                });



    }

    public List<String> getAllTablesFromRegistryScheme() throws SQLException {
        var query = "select table_name from information_schema.\"tables\" where table_schema = 'registry'";

        return new JdbcSession(source)
                .sql(query)
                .select(
                        (resultSet, statement) -> {
                            var names = new ArrayList<String>();
                            while (resultSet.next()) {
                                names.add(resultSet.getString(1));
                            }
                            return names;
                        }
                );
    }

    @Step("Перевірка відповідності створених таблиць")
    public List<String> getAllTablesNamesFromPublicScheme() throws SQLException {
        var query = "select table_name from information_schema.\"tables\" where table_schema = 'public'";

        return new JdbcSession(source)
                .sql(query)
                .select(
                        (resultSet, statement) -> {
                            var names = new ArrayList<String>();
                            while (resultSet.next()) {
                                names.add(resultSet.getString(1));
                            }
                            return names;
                        }
                );
    }


    @Step("Створення таблиці {tableName}")
    public void createCustomTable(String tableName) throws SQLException {
        var query = "CREATE TABLE "+tableName+" (\n" +
                "    PersonID int,\n" +
                "    LastName varchar(255),\n" +
                "    FirstName varchar(255),\n" +
                "    Address varchar(255),\n" +
                "    City varchar(255)\n" +
                ");";

         new JdbcSession(source)
                .sql(query)
                .execute();
    }

    @Step("Видалення таблиці {tableName}")
    public void dropCustomTable(String tableName) throws SQLException {
        var query = "DROP TABLE "+tableName;

        new JdbcSession(source)
                .sql(query)
                .execute();
    }

    @Step("Створення ролі")
    public void createRoleWithPermission(String roleName) throws SQLException {
        var query = "create user " +roleName+ " with encrypted password 'qwerty'";
        var query2 = "grant all privileges on database registry to " +roleName;
        new JdbcSession(source)
                .sql(query)
                .execute();

        new JdbcSession(source)
                .sql(query2)
                .execute();
    }

    @Step("Перевірка відповідності створених колонок")
    public HashMap<String, HashMap<String, String>> getAllColumnsForSpecificTables(List<String> tableNames) throws SQLException {
        String query = "SELECT column_name , data_type FROM information_schema.columns " +
                "WHERE table_name = '%s'";

        HashMap<String, HashMap<String, String>> map = new HashMap<>();
        for (var table : tableNames) {
            var columns = new JdbcSession(source)
                    .sql(String.format(query, table))
                    .select((resultSet, statement) -> {
                                var actualMap = new HashMap<String, String>();
                                while (resultSet.next()) {
                                    actualMap.put(resultSet.getString(1), resultSet.getString(2));
                                }
                                return actualMap;
                            }
                    );
            map.put(table, columns);
        }
        return map;
    }

    @Step("Перевірка відповідності створених обмежень")
    public HashMap<String, List<String>> getAllConstraintsForSpecificTables(List<String> tableNames) throws SQLException {
        String query = "select check_clause from information_schema.table_constraints c " +
                "join information_schema.check_constraints cc on c.constraint_name = cc.constraint_name" +
                " where c.constraint_type = 'CHECK' and c.table_name = '%s'";

        HashMap<String, List<String>> map = new HashMap<>();

        for (String table : tableNames) {

            var constraints = new JdbcSession(source)
                    .sql(String.format(query, table))
                    .select((resultSet, statement) -> {
                        var actualList = new ArrayList<String>();
                        while (resultSet.next()) {
                            actualList.add(resultSet.getString(1));
                        }
                        return actualList;
                    });
            map.put(table, constraints);
        }
        return map;

    }



    @Step("Перевірка відповідності primary key")
    public HashMap<String, String> getPrimaryKeys() throws SQLException {
        var actualMap = new HashMap<String, String>();
        String query = "SELECT KU.table_name, column_name FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS TC" +
                " INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS KU ON TC.CONSTRAINT_NAME = KU.CONSTRAINT_NAME" +
                " where constraint_type = 'PRIMARY KEY' and KU.table_name like 'pd%'";

        return new JdbcSession(source)
                .sql(query)
                .select((resultSet, statement) -> {
                            while (resultSet.next()) {
                                actualMap.put(resultSet.getString(1), resultSet.getString(2));
                            }
                            return actualMap;
                        }
                );
    }

    @Step("Отримання кількості записів в таблиці БД: {tableName} за виключенням авто-згенерованих значень")
    public  long getCountsOfRowsInTable(String tableName){
        String query ="SELECT COUNT(*) FROM "+tableName+" WHERE ddm_created_by ='admin'";
        return Long.valueOf(waitAndGetEntity(query, false));
    }


    @Step("Отримання кількості записів в таблиці БД: {tableName} без виключення авто-згенерованих значень")
    public  long getGeneralCountsOfRowsInTable(String tableName){
        String query ="SELECT COUNT(*) FROM "+tableName;
        return Long.valueOf(waitAndGetEntity(query, false));
    }


}
