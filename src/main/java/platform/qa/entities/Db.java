package platform.qa.entities;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Db {
    private String url;
    private String scheme;
    private String dbClass;
    private String user;
    private String password;
}
