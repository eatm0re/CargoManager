package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.UserDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.User;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.EntityExistsException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.EntityNotFoundException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.MissedParameterException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.WrongParameterException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
@WebAppConfiguration
public class UserServiceTest {

    private ServiceFacade service;

    @Autowired
    public void setService(ServiceFacade service) {
        this.service = service;
    }

    @Test
    public void findByLogin_1() throws Exception {
        assertEquals(
                new UserDTO(1, "admin", "admin", User.Post.STUFF),
                service.getUserService().findByLogin("admin")
        );
    }

    @Test
    public void findByLogin_2() throws Exception {
        assertEquals(
                new UserDTO(2, "user", "user", User.Post.DRIVER),
                service.getUserService().findByLogin("user")
        );
    }

    @Test
    public void findByLogin_3() throws Exception {
        try {
            service.getUserService().findByLogin("Test");
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("User Test not found", e.getMessage());
        }
    }

    @Test
    public void findByLogin_4() throws Exception {
        try {
            service.getUserService().findByLogin("Тест");
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Wrong user login", e.getMessage());
        }
    }

    @Test
    public void findByLogin_5() throws Exception {
        try {
            service.getUserService().findByLogin("");
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Wrong user login", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void register_1() throws Exception {
        UserDTO user = new UserDTO("Test1", "Test2", User.Post.DRIVER);
        user.setId(service.getUserService().register(user));
        assertEquals(
                user,
                service.getUserService().findByLogin("Test1")
        );
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void register_2() throws Exception {
        UserDTO user = new UserDTO("Test1", "Test2", User.Post.STUFF);
        user.setId(service.getUserService().register(user));
        assertEquals(
                user,
                service.getUserService().findByLogin("Test1")
        );
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void register_3() throws Exception {
        try {
            UserDTO user = new UserDTO("Тест1", "Test2", User.Post.DRIVER);
            service.getUserService().register(user);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Wrong user login", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void register_4() throws Exception {
        try {
            UserDTO user = new UserDTO("Test1", "Тест2", User.Post.DRIVER);
            service.getUserService().register(user);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Wrong user password", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void register_5() throws Exception {
        try {
            UserDTO user = new UserDTO("Test1", null, User.Post.DRIVER);
            service.getUserService().register(user);
            fail("Exception expected");
        } catch (MissedParameterException e) {
            assertEquals("User password must be specified", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void register_6() throws Exception {
        try {
            UserDTO user = new UserDTO(null, "Test2", User.Post.DRIVER);
            service.getUserService().register(user);
            fail("Exception expected");
        } catch (MissedParameterException e) {
            assertEquals("User login must be specified", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void register_7() throws Exception {
        try {
            UserDTO user = new UserDTO("Admin", "Test2", User.Post.DRIVER);
            service.getUserService().register(user);
            fail("Exception expected");
        } catch (EntityExistsException e) {
            assertEquals("User Admin is already exists", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void register_8() throws Exception {
        try {
            UserDTO user = new UserDTO("Test1", "Test2", null);
            service.getUserService().register(user);
            fail("Exception expected");
        } catch (MissedParameterException e) {
            assertEquals("User post must be specified", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_1() throws Exception {
        UserDTO user = new UserDTO("user", "Test", null);
        service.getUserService().change(user);
        user.setId(2);
        user.setPost(User.Post.DRIVER);
        assertEquals(
                user,
                service.getUserService().findByLogin("user")
        );
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_2() throws Exception {
        UserDTO user = new UserDTO("user", null, User.Post.STUFF);
        service.getUserService().change(user);
        user.setId(2);
        user.setPassword("user");
        assertEquals(
                user,
                service.getUserService().findByLogin("user")
        );
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_3() throws Exception {
        try {
            UserDTO user = new UserDTO(null, null, null);
            service.getUserService().change(user);
            fail("Exception expected");
        } catch (MissedParameterException e) {
            assertEquals("User login must be specified", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_4() throws Exception {
        try {
            UserDTO user = new UserDTO("Тест", null, null);
            service.getUserService().change(user);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Wrong user login", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_5() throws Exception {
        try {
            UserDTO user = new UserDTO("Test", null, null);
            service.getUserService().change(user);
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("User Test not found", e.getMessage());
        }
    }
}
