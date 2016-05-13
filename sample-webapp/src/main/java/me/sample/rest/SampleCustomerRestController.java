package me.sample.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.codahale.metrics.Counter;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.sample.hibernate.service.CustomerService;
import me.sample.rest.request.CreateCustomerRequest;
import me.sample.util.SampleMetrics;

@RestController
@RequestMapping(value = "/")
public class SampleCustomerRestController {

  private static final Counter customerRestCounter = SampleMetrics.METRIC_REGISTRY.counter("customer-rest-counter");

  ObjectMapper mapper = new ObjectMapper();

  @Autowired
  CustomerService customerService;

  private static final Logger logger = LoggerFactory.getLogger(SampleCustomerRestController.class);

  @RequestMapping(value = "/customer")
  public ModelAndView view() {
    logger.info("customer view");
    return new ModelAndView("view-customer.jsp");
  }

  @RequestMapping(value = "/customer/edit"/* , method = RequestMethod.POST */)
  public ModelAndView edit() {
    return new ModelAndView("customer-edit.jsp");
  }

  @RequestMapping(value = "/customer/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public long create(@RequestBody CreateCustomerRequest request) {
    customerRestCounter.inc();
    logger.debug("request: {}", request);

    long customerId = customerService.create(request.getLogin(), request.getFullName(), request.getPasswordHash());

    return customerId;
  }
}
