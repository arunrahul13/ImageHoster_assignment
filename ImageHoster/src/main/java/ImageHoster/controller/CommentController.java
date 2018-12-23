package ImageHoster.controller;
import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
public class CommentController
{

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    //This method is used to create the comment by saving it into the database and then redirects to the same page.
    @RequestMapping(value = "/images/{id}/{title}/comments", method = RequestMethod.POST)
    private String createComment(@RequestParam("comment")String comment, @PathVariable("id") int id, HttpSession session)
    {
        Image image = imageService.getImage(id);
        User user=(User) session.getAttribute("loggeduser");
        LocalDate localDate = LocalDate.now();
        Comment newComment=new Comment();
        newComment.setText(comment);
        newComment.setCreatedDate(localDate);
        newComment.setUser(user);
        newComment.setImage(image);
        commentService.addComment(newComment);

        return "redirect:/images/"+image.getId()+"/"+image.getTitle();
    }
}
