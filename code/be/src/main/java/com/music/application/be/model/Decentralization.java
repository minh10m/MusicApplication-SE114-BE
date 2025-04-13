@Entity
@Table(name = "decentralization")
public class Decentralization {

    @EmbeddedId
    private DecentralizationKey id;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private UserGroup userGroup;

    @ManyToOne
    @MapsId("functionId")
    @JoinColumn(name = "function_id")
    private Function function;

    // Getters, Setters
}

@Embeddable
public class DecentralizationKey implements Serializable {
    private Long groupId;
    private Long functionId;

    // equals(), hashCode()
}
