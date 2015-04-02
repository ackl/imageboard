package imageboard.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import imageboard.model.UsersModel;
import imageboard.model.UserRoleModel;
import imageboard.model.PostsModel;
import imageboard.model.KeycodeModel;
import imageboard.model.ThreadsModel;
import imageboard.dao.UsersDao;

@Service
public class UsersService implements UserDetailsService {

    private UsersDao dao;

    @Autowired
    public UsersService(UsersDao dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersModel u;
        u = dao.selectUserByUsername(username);
        if (u == null)
            throw new UsernameNotFoundException("user name not found");

        return buildUserFromUserEntity(u);
    }

    public UsersModel selectUserByUsername(String username) throws UsernameNotFoundException {
        UsersModel u = dao.selectUserByUsername(username);
        if (u == null)
            throw new UsernameNotFoundException("user name not found");

        return u;
    }

    //public UsersModel getUser(int id) {
    //}
    public String createUser(String username, String password) {
        dao.insertUser(username, password);
        return "ok";
    }

    public String createRole(String username, String role) {
        dao.insertRole(username, role);
        return "ok";
    }

    public String createRegistrationKeycode(String keycode, long expiry) {
        dao.insertKeycode(keycode, expiry);
        return keycode;
    }

    public boolean checkKeycodeValid(String keycode) {
        KeycodeModel keycodeModel = dao.selectKeycodeByKeycode(keycode);
        if (keycodeModel.getValid()) {
            if (keycodeModel.getExpiry() > new Date().getTime()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String updateUserProfileImage(String username, String imageUrl) {
        dao.updateUserByImageUrl(username, imageUrl);
        return "ok";
    }

    public String updateUserColourScheme(String username, String colourScheme) {
        dao.updateUserByColourScheme(username, colourScheme);
        return "ok";
    }

    private User buildUserFromUserEntity(UsersModel userEntity) {
        // convert model user to spring security user
        String username = userEntity.getUsername();
        String password = userEntity.getPassword();
        boolean enabled = userEntity.getEnabled();
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
        //authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));

        User springUser = new User(username, password, authList);
        //User springUser = new User(username, password, enabled,
                //accountNonExpired, credentialsNonExpired, accountNonLocked,
                //authorities);
        return springUser;
    }
}
