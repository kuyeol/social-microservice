package org.acme.ext.android;


import org.acme.ext.android.entity.NewsEntity;
import org.acme.ext.android.entity.TopicEntity;
import org.acme.ext.android.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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


    @GetMapping(value = "/news{id}")
    @ResponseBody
    public ResponseEntity getNewsByID(@PathVariable("id") String id) {

     Object news = topicService.getNews(id);

        return ResponseEntity.status(HttpStatus.OK).body(news);
    }

    @GetMapping("/topics")
    public ResponseEntity getTopic(@RequestParam("id") String id) {


        Optional<Optional<List>> op = Optional.empty();

        try {
            op = Optional.empty();


        } catch (MethodArgumentTypeMismatchException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("dd");
        }


        return ResponseEntity.status(HttpStatus.OK).body(op);
    }


    @PostMapping(path = "/news")
    public ResponseEntity createNews(@RequestBody String topicid) {

        topicService.createNews(topicid);

        return ResponseEntity.status(HttpStatus.CREATED).body("??");
    }




    @PostMapping("/topic")
    public ResponseEntity createTopic(@RequestBody String topic) {

        TopicEntity tobj = new TopicEntity();
        topicService.createTopic(tobj);
        return ResponseEntity.status(HttpStatus.CREATED).body("topics.toString()");
    }


    private TopicEntity mockTopicCreate() {
        TopicEntity topic = new TopicEntity();

        topic.setName("test");

        return topic;
    }

}
