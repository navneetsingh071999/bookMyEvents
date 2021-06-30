package backendBME.controllers;

import backendBME.model.TakeFeedback;
import backendBME.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="feedback")
public class FeedbackController {
   @Autowired
    private FeedbackService feedbackService;

   @PostMapping(value="/givefeedback/{id}")
    public String  addFeedback(@RequestBody TakeFeedback feedback, @PathVariable Long id){
       return feedbackService.addFeedback(feedback, id);

   }
   @PutMapping(value="/updatefeedback/{id}")
    public String  updateFeedback(@RequestBody TakeFeedback feedback, @PathVariable Long id){
       return feedbackService.updateFeedback(feedback, id);
   }

}
