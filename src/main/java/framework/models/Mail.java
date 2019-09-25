package framework.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mail {
    private String user;
    private String password;
    private String protocol;
    private String host;
}
