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

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/createNewPost")
    public ResponseEntity<?> createNewPost(@RequestBody Post post) {
        try {
            
            Post savedPost = postRepository.save(post);

            
            String response = restTemplate.getForObject("http://worldtimeapi.org/api/timezone/Asia/Kolkata", String.class);

            
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("db_post", savedPost);
            responseBody.put("http_outbound", response);

            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
