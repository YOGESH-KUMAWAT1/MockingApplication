package demo.application.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import demo.application.Entity.Post;
import demo.application.Repository.PostRepository;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/createNewPost")
    public ResponseEntity<?> createNewPost(@RequestBody Map<String, String> payload) {
        String postName = payload.get("postName");
        String postContents = payload.get("postContents");

        Post post = new Post();
        post.setPostName(postName);
        post.setPostContents(postContents);

        try {
            post = postRepository.save(post);
            String httpResponse = new RestTemplate().getForObject("http://worldtimeapi.org/api/timezone/Asia/Kolkata", String.class);

            Map<String, Object> response = new HashMap<>();
            response.put("db_post", post);
            response.put("http_outbound", httpResponse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
