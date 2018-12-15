package koreatech.cse.controller;

import koreatech.cse.domain.oauth2.facebook.like.FacebookLike;
import koreatech.cse.domain.oauth2.facebook.FacebookProfile;
import koreatech.cse.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;


@Controller
@RequestMapping("/facebook")
public class FacebookController {
    @Inject
    UserService userService;

    private static String facebook_likes_url = "https://graph.facebook.com/v3.2/me/likes";

    @RequestMapping(value = "/facebookLikes_request", method = RequestMethod.GET)
    public String facebookFriendsRequest(Model model, @RequestParam(name = "userId") Long userId) {
        AccessGrant accessGrant = userService.getFacebookAccessTokenMap().get(userId);
        FacebookProfile facebookProfile = userService.getFacebookProfile(accessGrant);


        model.addAttribute("name", facebookProfile.getName());
        model.addAttribute("email", facebookProfile.getEmail());
        model.addAttribute("num_friends", facebookProfile.getFriends().getSummary().getTotalCount());
        model.addAttribute("userId", userId);

        return "facebookLikesRequest";
    }

    @RequestMapping(value = "/facebookLikes", method = RequestMethod.GET)
    public String getFacebookLikes(Model model, @RequestParam(name = "userId") Long userId) {
        RestTemplate restTemplate = new RestTemplate();
        FacebookLike facebookLike = null;
        AccessGrant accessGrant = userService.getFacebookAccessTokenMap().get(userId);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + accessGrant.getAccessToken());

        try {
            ResponseEntity<FacebookLike> facebookLikesResponseEntity = restTemplate.exchange(
                    facebook_likes_url,
                    HttpMethod.GET,
                    new HttpEntity<Object>(headers),
                    FacebookLike.class
            );

            facebookLike = facebookLikesResponseEntity.getBody();
            System.out.println(facebookLike);
        } catch (HttpClientErrorException e) {
            System.out.println(e.getStatusCode() + ": " + e.getStatusText());
        }
        model.addAttribute("userId", userId);
        model.addAttribute("facebookLike", facebookLike);
        return "facebookLikes";
    }
}
