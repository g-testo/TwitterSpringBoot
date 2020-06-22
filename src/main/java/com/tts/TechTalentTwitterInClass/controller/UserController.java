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
    
    private void setFollowingStatus(List<User> users, List<User> usersFollowing, Model model) {
        HashMap<String,Boolean> followingStatus = new HashMap<>();
        String username = userService.getLoggedInUser().getUsername();
//      followingStatus:
//      {
//      	"sstark": true,
//      	"anotherUser": false,
//      	"anothernotherUser": true
//      }
        for (User user : users) {
            if(usersFollowing.contains(user)) {
                followingStatus.put(user.getUsername(), true);
            }else if (!user.getUsername().equals(username)) {
                followingStatus.put(user.getUsername(), false);
        	}
        }
        
        model.addAttribute("followingStatus", followingStatus);
    }

    

    
    
    @GetMapping("/users/{username}")
	public String displayUser(@PathVariable String username, Model model) {
    	
	    User user = userService.findByUsername(username);
	    List<Tweet> tweets = tweetService.findAllByUser(user);
	    model.addAttribute("tweetList", tweets);
	    model.addAttribute("user", user);
	    
	    User loggedInUser = userService.getLoggedInUser();
	    List<User> following = loggedInUser.getFollowing();
	    boolean isFollowing = false;
	    for (User followedUser : following) {
	        if (followedUser.getUsername().equals(username)) {
	            isFollowing = true;
	        }
	    }
	    model.addAttribute("following", isFollowing);

	    boolean isSelfPage = loggedInUser.getUsername().equals(username);
	    model.addAttribute("isSelfPage", isSelfPage);

	    return "user";
	}
    
    @GetMapping(value = "/users")
    public String displayUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        setTweetCounts(users, model);
        
        User loggedInUser = userService.getLoggedInUser();
        List<User> usersFollowing = loggedInUser.getFollowing();
        setFollowingStatus(users, usersFollowing, model);

        
        
        
        return "users";
    }

    
}