package com.example._configuration.micrometer;

import io.micrometer.observation.Observation.Context;
import io.micrometer.observation.ObservationPredicate;
import io.micrometer.observation.ObservationView;
import java.util.Objects;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.observation.ServerRequestObservationContext;

// https://github.com/spring-projects/spring-boot/issues/34801
@Configuration
class ObservationConfiguration {

  static final Set<String> ignores;

  static {
    ignores = Set.of("/actuator", "/favicon.ico");
  }

  @Bean
  ObservationPredicate observationPredicateFiltering() {
    return (name, context) -> {
      Context root = getRoot(context);
      if (root instanceof ServerRequestObservationContext serverContext) {
        String request = serverContext.getCarrier().getPath().toString();
        if (ignores.stream().anyMatch(ignore -> request.startsWith(ignore))) return false;
      }
      return true;
    };
  }

  private Context getRoot(Context current) {
    ObservationView parent = current.getParentObservation();
    if (Objects.isNull(parent)) return current;
    return getRoot((Context) parent.getContextView());
  }
}
