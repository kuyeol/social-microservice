package org.acme.ext.android;


import org.acme.ext.android.entity.NewsEntity;
import org.acme.ext.android.entity.TopicEntity;
import org.acme.ext.android.model.Topic;
import org.acme.ext.android.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/android")
public class AndroidResource {

    private final TopicService topicService;


    public AndroidResource(TopicService topicService) {
        this.topicService = topicService;
    }


    @GetMapping("/topics")
    public ResponseEntity getTopic(@RequestParam("id") String id) {


        Optional<Optional<List>> op = Optional.empty();

        try {
            op = Optional.ofNullable(topicService.getAllNews(id));

        } catch (MethodArgumentTypeMismatchException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("dd");
        }


        return ResponseEntity.status(HttpStatus.OK).body(op);
    }

    @PostMapping(path = "/news")
    public ResponseEntity createNews(@RequestBody NewsEntity news) {

   //    news.setTopic(news.getTopic());
       topicService.save(news);
     //   topicService.createNews(news);

        return ResponseEntity.status(HttpStatus.CREATED).body(news);
    }

    @PostMapping
    public ResponseEntity createTopic(@RequestBody TopicEntity topic) {

        topicService.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }


}
