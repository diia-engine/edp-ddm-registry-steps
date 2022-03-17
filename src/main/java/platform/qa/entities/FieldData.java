package platform.qa.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import platform.qa.enums.FieldType;

@AllArgsConstructor
@Getter
@Setter
public class FieldData {
    public String name;
    public FieldType type;
    public String value;
}
