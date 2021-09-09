package todo_list.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="itemstable")
public class Item {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="description")
    private String description;
    @Column(name="created")
    private Timestamp created;
    @Column(name="isDone")
    private boolean isDone;

    public Item() {
    }

    public Item(String description) {
        this.description = description;
        this.created = new Timestamp(System.currentTimeMillis());
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && isDone == item.isDone && Objects.equals(description, item.description) && Objects.equals(created, item.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, isDone);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", isDone=" + isDone +
                '}';
    }
}
