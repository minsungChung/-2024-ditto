package hanghae99.ditto.post.dto.response;


import hanghae99.ditto.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long postId;
    private String title;

    public PostResponse(Post post){
        this(post.getId(), post.getTitle());
    }
}
