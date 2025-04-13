@Entity
@Table(name = "user_group")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    @OneToMany(mappedBy = "userGroup")
    private List<User> users;

    @OneToMany(mappedBy = "userGroup")
    private List<Decentralization> decentralizations;

    // Getters, Setters
}
