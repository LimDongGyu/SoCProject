package koreatech.cse.controller;

import koreatech.cse.controller.oauth2.KakaoOauth2Controller;
import koreatech.cse.domain.oauth2.kakao.KakaoProfile;
import koreatech.cse.domain.oauth2.kakao.book.KakaoBook;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Controller
@RequestMapping("/kakao")
public class KakaoController {
    @Inject
    UserService userService;

    public static String kakao_book_rest_uri = "https://dapi.kakao.com/v3/search/book?target=title";

    @RequestMapping(value = "/kakao_book_request", method = RequestMethod.GET)
    public String kakaoBookRequest(Model model, @RequestParam(name = "userId") Integer userId) {
        AccessGrant accessGrant = userService.getKakaoAccessTokenMap().get(userId);
        KakaoProfile kakaoProfile = userService.getKakaoProfile(accessGrant);

        model.addAttribute("nickname", kakaoProfile.getProperties().getNickname());
        model.addAttribute("profile_image", kakaoProfile.getProperties().getProfileImage());
        model.addAttribute("userId", userId);
        return "kakaoBookRequest";
    }

    @RequestMapping(value = "/kakao_book", method = RequestMethod.GET)
    public String kakaoBook(Model model, @RequestParam(name = "searchWord") String searchWord) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        KakaoBook kakaoBook = null;
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Authorization", "KakaoAK " + KakaoOauth2Controller.kakao_rest_api_app_key);

            ResponseEntity<KakaoBook> kakaoBookResponseEntity = restTemplate.exchange(
                    kakao_book_rest_uri + "&query=" + searchWord,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    KakaoBook.class
            );

            kakaoBook = kakaoBookResponseEntity.getBody();
            System.out.println("!!!! " + kakaoBook);
        } catch (HttpClientErrorException e) {
            System.out.println(e.getStatusCode() + ": " + e.getStatusText());
        }
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("kakaoBook", kakaoBook);
        return "kakaoBook";
    }

}
