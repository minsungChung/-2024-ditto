package hanghae99.ditto.newsfeed.service;

import hanghae99.ditto.newsfeed.dto.request.NewsfeedRequest;
import hanghae99.ditto.newsfeed.dto.response.NewsfeedResponse;

import java.util.List;

public interface NewsfeedService {
    List<NewsfeedResponse> showNewsfeed();

    void createNewsfeed(NewsfeedRequest newsfeedRequest);
}
