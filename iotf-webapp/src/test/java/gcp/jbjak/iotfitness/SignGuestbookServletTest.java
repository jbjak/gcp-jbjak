package gcp.jbjak.iotfitness;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import com.googlecode.objectify.ObjectifyService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignGuestbookServletTest {

  private SignGuestbookServlet signGuestbookServlet;
  private Closeable closeable = null;    // Objectify


  private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig())
          .setEnvIsLoggedIn(true)
          .setEnvAuthDomain("localhost")
          .setEnvEmail("test@localhost");

  @Before
  public void setupSignGuestBookServlet() {
    helper.setUp();
    OfyHelper.register();   // Needed for Objectify
    closeable = ObjectifyService.begin();  // Objectify

    signGuestbookServlet = new SignGuestbookServlet();
  }

  @After
  public void tearDownHelper() throws IOException {
    if(closeable != null) {
      closeable.close();  // Objectify
    }
    helper.tearDown();
  }

  @Test
  public void testDoPost() throws IOException, EntityNotFoundException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    String guestbookName = "TestGuestbook";
    String testContent = "Test Content";

    when(request.getParameter("guestbookName")).thenReturn(guestbookName);
    when(request.getParameter("content")).thenReturn(testContent);

    Date priorToRequest = new Date();

    signGuestbookServlet.doPost(request, response);

    Date afterRequest = new Date();

    verify(response).sendRedirect("/guestbook.jsp?guestbookName=TestGuestbook");

    User currentUser = UserServiceFactory.getUserService().getCurrentUser();

    Entity greeting = DatastoreServiceFactory.getDatastoreService().prepare(new Query()).asSingleEntity();

    assertEquals(guestbookName, greeting.getKey().getParent().getName());
    assertEquals(testContent, greeting.getProperty("content"));
    assertEquals(currentUser.getEmail(), greeting.getProperty("author_email"));

    Date date = (Date) greeting.getProperty("date");
    assertTrue("The date in the entity [" + date + "] is prior to the request being performed",
        priorToRequest.before(date) || priorToRequest.equals(date));
    assertTrue("The date in the entity [" + date + "] is after to the request completed",
        afterRequest.after(date) || afterRequest.equals(date));
  }
}
