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
    public boolean isExecutable;
    public String text;

    @Getter
    @Setter
    public class UserTask {
        public String id;
        public String name;
        public String formKey;
        public String assignee;
        public String text;
    }

    @Getter
    @Setter
    public class ServiceTask {
        public String id;
        public String name;
        public String text;
    }
}
