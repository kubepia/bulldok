package io.nogada.sambulldok.auth;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * AuthController
 */
@RestController
public class AuthController {
   // @Autowired
   // ObjectFactory<HttpSession> httpSessionFactory;

   // private () {

   // }
   // @Autowired
   // private HttpSession httpSession;
   private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

   private final Gson gson = new Gson();

   @PostMapping(value = "/api/v1/login", consumes = "application/json", produces = "application/json")
   public String login(@RequestBody final String entity) {
      logger.info("====Login api v1=====");
      logger.info(entity);

      final Type type = new TypeToken<Map<String, String>>() {
      }.getType();
      final Map<String, String> map = gson.fromJson(entity, type);
      // httpSession.setAttribute("name", "value");
      // Object obj = httpSession.getAttribute("name");

      if (!map.containsKey("password")) {
         throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is not provided");
      }
      if ("passw0rd".equals(map.get("password"))) {
         return "{ \"status\" : \"OK\" }";
      } else {
         throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is not correct");
      }

   }

   @GetMapping(value = "/user/{id}")
   public String getUserInfo(@PathVariable final String id) {
      final String str = gson.toJson(new User(id));
      return str;
   }

}