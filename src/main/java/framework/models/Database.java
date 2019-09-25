package framework.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Database {
    private String url;
    private String user;
    private String password;
    private String driverName;
}
