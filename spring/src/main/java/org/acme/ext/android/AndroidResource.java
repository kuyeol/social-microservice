package org.acme.ext.android;


import org.acme.ext.android.entity.NewsEntity;
import org.acme.ext.android.entity.TopicEntity;
import org.acme.ext.android.model.NewsDto;
import org.acme.ext.android.model.TopicModel;
import org.acme.ext.android.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/android")
public class AndroidResource
{


    private final TopicService topicService;
	
		

    public AndroidResource(TopicService topicService) {

        this.topicService = topicService;
    }


    @GetMapping(value = "/news")
    public ResponseEntity<Optional<NewsDto>> getNewsByID(@RequestParam("id") String id) {

        Optional<NewsDto> news;
        try {
            news = topicService.getNews(id);
            return ResponseEntity.status(HttpStatus.OK).body(news);
        } catch (Exception e) {
            //   throw new RuntimeException(e);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(Optional.of(NewsDto.toDto(new NewsEntity(new TopicEntity(-1)))));
        }
    }


    @GetMapping("/topics")
    public ResponseEntity getTopic(@RequestParam("id") String id) {

        TopicModel topicModel = new TopicModel();
        try {
            topicModel = topicService.getTopic(id);
        } catch (Exception e) {
            //topicModel.id="0";
            return ResponseEntity.status(HttpStatus.OK).body(topicModel);
        }
        return ResponseEntity.status(HttpStatus.OK).body(topicModel);
    }


    @PostMapping(path = "/news")
    public ResponseEntity createNews(@RequestBody Optional<NewsDto> dto) {

        TopicModel topicModel = new TopicModel();


        try {
            topicModel = topicService.getTopic(dto.get().topicId());

            if (topicModel == null) {

                dto = Optional.of(NewsDto.toDto(new NewsEntity(new TopicEntity(-1))));
            } else {

                dto = Optional.ofNullable(topicService.createNews(dto.get()));

            }

        } catch (Exception e) {

            dto = Optional.of(NewsDto.toDto(new NewsEntity(new TopicEntity(-1))));

            return ResponseEntity.status(HttpStatus.CREATED).body(Optional.of(dto));
        }
        TopicEntity topicEntity = new TopicEntity();

        NewsDto newsDto = new NewsDto.Builder().addTopicEntity(topicEntity).build();


        return ResponseEntity.status(HttpStatus.CREATED).body(newsDto);
    }


    @PostMapping("/topic")
    public ResponseEntity createTopic(@RequestBody TopicModel topic) {

        topic = topicService.createTopic(topic);

        return ResponseEntity.status(HttpStatus.CREATED).body(Optional.of(topic));
    }

    @GetMapping("/topic/list")
    public ResponseEntity getTopicList() {

        List<TopicModel> list = topicService.getTopicList();

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


}
