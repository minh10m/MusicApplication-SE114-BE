@Entity
@Table(name = "user_profile_settings")
public class UserProfileSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String language;
    private String theme;
    private String streamingQuality;
    private String downloadQuality;

    // Getters, Setters
}
