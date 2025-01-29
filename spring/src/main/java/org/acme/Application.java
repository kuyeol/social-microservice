package org.acme;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.function.Function;
import org.acme.ext.terran.entity.Barracks;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class Application implements CommandLineRunner
{

  final static String url = "http://localhost:8080/terran/ping";

  final static WebClient webClient = WebClient.builder().baseUrl( url ).build();

  static int loop;

  static Barracks  requestBody = new Barracks();

  static Timestamp current= new Timestamp( System.currentTimeMillis() );

  static String timeForm;


  @Override
  public void run(String... req)
  {
    this.loop = Integer.valueOf( req[0] );
    this.requestBody = new Barracks();



    Function<Integer, String> makeRequest = (stamp)->{

      requestBody.setId( current.getNanos() );
      requestBody.setName( String.valueOf( stamp ) );
      requestBody.setPublicstring( timeForm );
      requestBody.setSecret( timeForm );
      requestBody.setAge( current.getNanos() );

      return webClient.post()
                      .contentType( MediaType.APPLICATION_JSON )
                      .accept( MediaType.APPLICATION_JSON )
                      .bodyValue( requestBody )
                      .retrieve()
                      .bodyToMono( String.class )
                      .block();
    };

    for ( int i = 0 ; i < loop ; i++ ) {
      this.current          = new Timestamp( System.currentTimeMillis() );
      this.timeForm         = new SimpleDateFormat( "HH:mm:ss" ).format( current );
      makeRequest.apply(current.getNanos() );
    }
  }


  public static void main(String[] args)
  {

    SpringApplication.run( Application.class , "20" );
  }


}
