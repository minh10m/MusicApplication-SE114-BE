@Entity
@Table(name = "functions")
public class Function {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String functionName;
    private String accessedScreen;

    @OneToMany(mappedBy = "function")
    private List<Decentralization> decentralizations;

    // Getters, Setters
}
