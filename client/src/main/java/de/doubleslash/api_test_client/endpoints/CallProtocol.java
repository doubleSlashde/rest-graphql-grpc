package de.doubleslash.api_test_client.endpoints;

import de.doubleslash.api_test_client.model.Beer;
import de.doubleslash.api_test_client.model.Request;
import de.doubleslash.api_test_client.model.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
public class CallProtocol {

  private final GraphQL graphQL;

  private final GRPC grpc;

  private final Rest rest;

  @PostMapping("/{protocolType}")
  public Response makeProtocolRequest(@PathVariable ProtocolType protocolType, @RequestBody Request request) {
    StopWatch stopWatch = new StopWatch();

    BeerRepository protocol = switch (protocolType) {
      case REST -> rest;
      case GRAPHQL -> graphQL;
      case GRPC -> grpc;
    };

    stopWatch.start();

    List<Beer> beers = switch (request.getMethod()) {
      case NOTHING -> protocol.getRandomBeer();
      case ID -> protocol.getBeerWithId(request.getNumber());
      case AMOUNT -> protocol.getBeers(request.getNumber());
    };

    stopWatch.stop();

    return Response.builder()
        .time(stopWatch.getTime())
        .data(beers)
        .build();
  }

  public enum ProtocolType {
    REST,
    GRAPHQL,
    GRPC
  }
}
