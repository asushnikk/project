package training.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import training.project.domain.User;
import training.project.dto.UserDto;
import training.project.mapper.UserMapper;
import training.project.repository.ScoreRepository;
import training.project.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private  UserMapper userMapper;

    @Autowired
    private  ScoreRepository scoreRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByName(s);

        if (user == null) {
            throw new UsernameNotFoundException("not found");
        }
        return user;
    }

    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getOne(user.getId());
    }

    public String getUserRoom(Model model, String error) {
        User user = getCurrentUser();
        model.addAttribute("scores", user.getScores());
        model.addAttribute("user", user);
        model.addAttribute("error", error);
        model.addAttribute("security", new MySecurityService(scoreRepository));

        return "userRoom";
    }

    public Model getUserRegistrationForm(Model model, String error) {
        model.addAttribute("user", new User());
        model.addAttribute("error", error);

        return model;
    }

    public String createUser(UserDto user, RedirectAttributes redirectAttributes) {

        try {
            isValidUser(user);
        } catch (Exception ex) {
            redirectAttributes.addAttribute("error", "already used!");
            return "redirect:/registration";
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(userMapper.toUser(user));

        return "redirect:/login";
    }

    private void isValidUser(UserDto user) {
        User foundUser = userRepository.findByName(user.getName());

        if (foundUser != null) {
            throw new RuntimeException("already used!");
        }
    }
}