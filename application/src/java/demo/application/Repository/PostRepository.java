package demo.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.application.Entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
