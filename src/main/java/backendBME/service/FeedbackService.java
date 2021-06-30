package backendBME.service;

import backendBME.model.Feedback;
import backendBME.model.RegistrationEvent;
import backendBME.model.TakeFeedback;
import backendBME.repository.EventRepo;
import backendBME.repository.FeedbackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepo feedbackRepo;
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private EmailSender emailSender;

    public String addFeedback(TakeFeedback feedback, Long id) {
        RegistrationEvent  event = eventRepo.findById(id).get();
        if(event.isApproved()) {
            feedbackRepo.save(new Feedback(event, feedback.getFeedback()));
            //.................................................
            //Send Event Updated mail
            emailSender.sendSimpleEmail(SecurityContextHolder.getContext().getAuthentication().getName(),
                    "Feedback Received! \n " +
                            "Thank you for your valuable feedback, we will take a note on the given feedback.  \n" +
                            "Wishing you a great journey ahead. \n" +
                            "Regards,\n" +
                            "Team Book My Events.",
                    "Feedback submitted With Book My Events");
            //....................................................

            return "Feedback Submitted";
        }else {
            return "Can not give feedback for not approved events";
        }
    }

    public String updateFeedback(TakeFeedback feedback, Long id){
    RegistrationEvent event = eventRepo.findById(id).get();
    feedbackRepo.save(new Feedback(event, feedback.getFeedback()));
        //Send Event Updated mail
        emailSender.sendSimpleEmail (SecurityContextHolder.getContext().getAuthentication().getName(),
                "Updated Feedback Received! \n " +
                        "Thank you for your valuable feedback, we will take a note on the given feedback.  \n" +
                        "Wishing you a great journey ahead. \n" +
                        "Regards,\n" +
                        "Team Book My Events.",
                "Feedback updated With Book My Events");
        //....................................................
         return "Feedback Updated";
    }
}
