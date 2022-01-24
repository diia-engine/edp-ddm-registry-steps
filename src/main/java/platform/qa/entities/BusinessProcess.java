package platform.qa.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusinessProcess {
    public List<UserTask> userTask;
    public List<ServiceTask> serviceTask;
    public String id;
    public String name;

    @Getter
    @Setter
    public static class UserTask {
        public String id;
        public String name;
        public String formKey;
        public String assignee;
    }

    @Getter
    @Setter
    public static class ServiceTask {
        public String id;
        public String name;
    }
}
