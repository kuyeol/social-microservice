package org.acme.ext.android;


import org.acme.ext.android.entity.TopicEntity;
import org.acme.ext.android.model.NewsDto;
import org.acme.ext.android.model.TopicModel;
import org.acme.ext.android.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.util.Optional;

@RestController
@RequestMapping("/android")
public class AndroidResource
{


    private final TopicService topicService;


    public AndroidResource(TopicService topicService) {

        this.topicService = topicService;
    }


    @GetMapping(value = "/news/{id}")
    public ResponseEntity getNewsByID(@RequestParam("id") String id) {

        Optional<NewsDto> news = topicService.getNews(id);

        return ResponseEntity.status(HttpStatus.OK).body(news);
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity getTopic(@RequestParam("id") String id) {


        TopicModel topicModel = topicService.getTopic(id);

        try {

            topicModel = topicService.getTopic(id);

        } catch (MethodArgumentTypeMismatchException e) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        }


        return ResponseEntity.status(HttpStatus.OK).body(topicModel);
    }


    @PostMapping(path = "/news")
    public ResponseEntity createNews(@RequestBody NewsDto dto) {

        topicService.createNews(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("rep");
    }


    @PostMapping("/topic")
    public ResponseEntity createTopic(@RequestBody TopicModel topic) {

        topicService.createTopic(topic);

        return ResponseEntity.status(HttpStatus.CREATED).body("topics.toString()");
    }


}
