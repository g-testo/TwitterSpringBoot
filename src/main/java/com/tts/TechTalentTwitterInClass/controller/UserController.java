package com.tts.TechTalentTwitterInClass.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tts.TechTalentTwitterInClass.model.Tweet;
import com.tts.TechTalentTwitterInClass.model.User;
import com.tts.TechTalentTwitterInClass.service.TweetService;
import com.tts.TechTalentTwitterInClass.service.UserService;

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TweetService tweetService;
    
    private void setTweetCounts(List<User> users, Model model) {
        HashMap<String,Integer> tweetCounts = new HashMap<>();
        for (User user : users) {
            List<Tweet> tweets = tweetService.findAllByUser(user);
//           tweetCounts:
//            {
//            	"sstark": 2,
//            	"anotherUser": 5,
//            	"anothernotherUser": 3
//            }
            tweetCounts.put(user.getUsername(), tweets.size());
        }
        model.addAttribute("tweetCounts", tweetCounts);
    }

    
    
    @GetMapping("/users/{username}")
	public String displayUser(@PathVariable String username, Model model) {
	    User user = userService.findByUsername(username);
	    List<Tweet> tweets = tweetService.findAllByUser(user);
	    model.addAttribute("tweetList", tweets);
	    model.addAttribute("user", user);
	    return "user";
	}
    
    @GetMapping(value = "/users")
    public String displayUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        setTweetCounts(users, model);
        return "users";
    }

    
}