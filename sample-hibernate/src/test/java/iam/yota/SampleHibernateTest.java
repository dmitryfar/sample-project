package iam.yota;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import me.sample.hibernate.DuplicateObjectException;
import me.sample.hibernate.SampleHbmConfig;
import me.sample.hibernate.dao.CustomerDao;
import me.sample.hibernate.model.Customer;
import me.sample.hibernate.model.PartnerMapping;
import me.sample.hibernate.service.CustomerService;
import me.sample.hibernate.service.PartnerMappingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { SampleHbmConfig.class })
public class SampleHibernateTest {
  private static final Logger logger = LoggerFactory.getLogger(SampleHibernateTest.class);

  @Autowired
  CustomerDao customerDao;

  @Autowired
  CustomerService customerService;

  @Autowired
  PartnerMappingService partnerMappingService;

  @Test
  public void testFindAllCustomer() {
    logger.info("customerService: {}", customerService);

    Customer abcCustomer = customerService.findByLogin("abc");
    logger.info("abcCustomer: {}", abcCustomer);

    List<Customer> customers = customerService.findAll();
    logger.info("customers: {}", customers);

    List<PartnerMapping> mappings = partnerMappingService.findAll();
    logger.info("mappings: {}", mappings);

    try {
      long id = customerService.create("abcde", "A B C D E", "pAsSwOrDhAsH123123123123");
      logger.info("id: {}", id);
    } catch (DuplicateObjectException e) {
      logger.error("Duplicated Customer id: " + e.getId());
    }

    abcCustomer = customerService.findByLogin("abc");
    logger.info("abcCustomer: {}", abcCustomer);
  }

  @Test
  public void testLongPasswordHash() {
    try {
      String passwordHash = "pAsSwOrDhAsH123123123123pAsSwOrDhAsH123123123123pAsSwOrDhAsH123123123123";
      //passwordHash = null;
      long id = customerService.create("abcde", "A B C D E", passwordHash );
      logger.info("id: {}", id);
    } catch (DuplicateObjectException e) {
      logger.error("Duplicated Customer id: " + e.getId());
    }
  }

  @Test
  public void testDeleteCustomer() {
    customerService.deleteByLogin("abcd");
    logger.info("abcd deleted");
  }
}
