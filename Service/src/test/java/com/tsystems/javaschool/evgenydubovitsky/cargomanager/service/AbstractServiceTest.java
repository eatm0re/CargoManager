package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DAOFacade;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.UserDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.UserDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.User;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl.AbstractService;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class AbstractServiceTest {

    private UserDAO userDAO;
    private AbstractService<User, UserDTO> userService;

    private List<User> allUsers = Arrays.asList(
            new User(1L, "AdminTest", "admin", User.Post.STUFF),
            new User(2L, "UserTest", "user", User.Post.DRIVER)
    );

    private List<UserDTO> allUsersDTOExpected = Arrays.asList(
            new UserDTO(allUsers.get(0)),
            new UserDTO(allUsers.get(1))
    );

    @Before
    public void setUp() throws Exception {
        userDAO = mock(UserDAO.class);
        DAOFacade daoFacade = mock(DAOFacade.class);
        when(daoFacade.getUserDAO()).thenReturn(userDAO);
        when(daoFacade.getDAO(User.class)).thenReturn(userDAO);
        userService = new UserServiceImpl();
        userService.setDao(daoFacade);
    }

    @Test
    public void getAllUsers_1() throws Exception {
        when(userDAO.selectAll()).thenReturn(allUsers);
        List<UserDTO> users = userService.getAll();
        assertEquals(users, allUsersDTOExpected);
    }

    @Test
    public void getAllUsers_2() throws Exception {
        try {
            when(userDAO.selectAll()).thenReturn(Collections.emptyList());
            userService.getAll();
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("No one user found", e.getMessage());
        }
    }

    @Test
    public void findUserById_1() throws Exception {
        when(userDAO.selectById(2L)).thenReturn(allUsers.get(1));
        UserDTO user = userService.findById(2L);
        assertEquals(user, allUsersDTOExpected.get(1));
    }

    @Test
    public void findUserById_2() throws Exception {
        try {
            when(userDAO.selectById(2L)).thenReturn(null);
            userService.findById(2L);
        } catch (EntityNotFoundException e) {
            assertEquals("User #2 not found", e.getMessage());
        }
    }
}
