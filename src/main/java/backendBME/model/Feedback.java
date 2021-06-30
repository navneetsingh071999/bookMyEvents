package backendBME.model;


import javax.persistence.*;
@Entity
@Table(name="feedback")
public class Feedback {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="feedbackId",nullable = false)
    private RegistrationEvent feedbackId;
    @Column(name="feedback")
    private String feedback;

    public Feedback(RegistrationEvent feedbackId, String feedback) {
        this.feedbackId = feedbackId;
        this.feedback = feedback;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RegistrationEvent getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(RegistrationEvent feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
