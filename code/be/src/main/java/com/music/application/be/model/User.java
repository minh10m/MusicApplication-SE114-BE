@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private UserGroup userGroup;

    private String username;
    private String email;
    private String phone;
    private String password;
    private String avatar;
    private String streamingQuality;
    private String downloadQuality;
    private String language;
    private String theme;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<SearchHistory> searchHistory;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    // Getters, Setters
}
