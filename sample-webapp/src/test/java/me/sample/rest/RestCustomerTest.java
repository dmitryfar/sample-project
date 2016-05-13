package me.sample.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.MessageDigest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.TestCase;
import me.sample.hibernate.SampleHbmConfig;
import me.sample.hibernate.model.Customer;
import me.sample.hibernate.service.CustomerService;
import me.sample.rest.request.CreateCustomerRequest;
import me.sample.util.HexUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class RestCustomerTest extends TestCase {
  private static final Logger logger = LoggerFactory.getLogger(RestCustomerTest.class);

  @Configuration
  @ComponentScan(basePackages = { "me.sample" }, excludeFilters = {
      @Filter(type = FilterType.ANNOTATION, value = Configuration.class) })
  @Import(value = SampleHbmConfig.class)
  public static class SpringConfig {

  }

  private MockMvc mockMvc;

  @Autowired
  private SampleCustomerRestController sampleCustomerRestController;

  @Autowired
  private CustomerService customerService;

  ObjectMapper mapper = new ObjectMapper();

  @Override
  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(sampleCustomerRestController).build();
  }

  @Test
  public void testCreateCustomer() throws Exception {
    // clean before create
    String login = "abcd";
    customerService.deleteByLogin(login );

    CreateCustomerRequest request = new CreateCustomerRequest();
    request.setLogin(login);
    request.setFullName("A B C D");

    String password = "abcdpassword";

    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    messageDigest.update(password.getBytes());
    String passwordHash = HexUtil.toHexString(messageDigest.digest());

    request.setPasswordHash(passwordHash);

    MvcResult mvcResult = mockMvc.perform(post("/customer/create").contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(request))).andExpect(status().isOk()).andReturn();

    MockHttpServletResponse httpResponse = mvcResult.getResponse();

    // ObjectValueResponse response =
    // mapper.readValue(httpResponse.getContentAsByteArray(),
    // ObjectValueResponse.class);
    JsonNode responseNode = mapper.readTree(httpResponse.getContentAsString());

    logger.info("responseNode: {}", responseNode);
    assertNotNull(responseNode);

    Customer customer = customerService.findByLogin(login);
    assertNotNull(customer);
  }
}
