package pdp.uz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
public abstract class AbsAddDto implements Serializable {

    private String name;

    private boolean active;
}
