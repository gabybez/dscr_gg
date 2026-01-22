package es.unavarra.tlm.examenmaster;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity //
public class Access {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private boolean valid;

    @NotNull
    private Date created_at;

    //Tras escribir esto, voy a Build > Assemble 'app' run configuration y se completa automaticamente los getters y setters


    @Generated(hash = 1374150842)
    public Access(Long id, @NotNull String username, boolean valid,
            @NotNull Date created_at) {
        this.id = id;
        this.username = username;
        this.valid = valid;
        this.created_at = created_at;
    }

    @Generated(hash = 1253708747)
    public Access() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getValid() {
        return this.valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Date getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

}