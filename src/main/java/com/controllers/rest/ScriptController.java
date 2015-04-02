package com.controllers.rest;

import com.annotations.AdminOnly;
import com.daos.ScriptRepository;
import com.models.AuthUser;
import com.models.Script;
import com.services.AuthenticationService;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional(propagation = Propagation.REQUIRED)
@RequestMapping("api/scripts")
@CacheConfig(cacheNames = "scripts")
public class ScriptController {

  static final Logger logger = Logger.getLogger(ScriptController.class);

  @Value("classpath:/examples/scriptExample.json")
  private Resource scriptExample;

  @Autowired
  private AuthenticationService authentication;

  @Autowired
  private ScriptRepository scriptRepository;

  @RequestMapping(value = "")
  public String getScriptExample() throws IOException {
    return IOUtils.toString(scriptExample.getInputStream());
  }

  @AdminOnly
  @RequestMapping(value = "new", method = RequestMethod.POST)
  public Script postNewScript(@RequestBody Script script) {
    AuthUser owner = authentication.getAuthenticatedUser();

    if (script.getName() == null || script.getContent() == null) {
      throw new IllegalArgumentException("One or more required fields are empty. {name | content}");
    }

    script.setOwner(owner);
    scriptRepository.save(script);

    return script;
  }

}
