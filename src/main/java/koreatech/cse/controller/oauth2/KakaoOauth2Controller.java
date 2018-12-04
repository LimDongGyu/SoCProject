package koreatech.cse.controller.oauth2;

import koreatech.cse.service.UserService;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/oauth")
public class KakaoOauth2Controller {
    private static String kakao_client_id = "fc4dc7a4471fd0b4991245eb28514ee2";      //본인 것으로 추가하기
    private static String kakao_client_secret = "5tlx8m5sD5wOSUZaHKfeJpo8Ttyre19x";  //본인 것으로 추가하기
    private static String kakao_authorization_endpoint = "https://kauth.kakao.com/oauth/authorize";
    private static String kakao_token_endpoint = "https://kauth.kakao.com/oauth/token";
    private static String kakao_redirect_uri = "http://localhost:8080/oauth/kakao_callback";

    public static String kakao_rest_api_app_key = "fc4dc7a4471fd0b4991245eb28514ee2";   //본인 것으로 추가하기

    @Inject
    private UserService userService;

    private OAuth2Template kakaoOauthTemplate = new OAuth2Template(
            kakao_client_id,
            kakao_client_secret,
            kakao_authorization_endpoint,
            kakao_token_endpoint
    );

    @RequestMapping("/kakao_authorization_code")
    public String kakao_authorization_code() {
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri(kakao_redirect_uri);

        String authorizeUrl = kakaoOauthTemplate.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);
        System.out.println("authorizeUrl: " + authorizeUrl);

        return "redirect:" + authorizeUrl;
    }


    @RequestMapping("/kakao_callback")
    public String kakao_callback(Model model, HttpServletRequest request, @RequestParam String code) throws Exception {
        System.out.println("Kakao Callback is called!!");
        System.out.println(code);

        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setOutputStreaming(false);
        kakaoOauthTemplate.setRequestFactory(simpleClientHttpRequestFactory);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
        multiValueMap.add("client_id", kakao_client_id);
        multiValueMap.add("client_secret", kakao_client_secret);

        AccessGrant accessGrant = kakaoOauthTemplate.exchangeForAccess(
                code,
                kakao_redirect_uri,
                multiValueMap
        );

        System.out.println("------ AccessGrant ------");
        System.out.println(accessGrant.getAccessToken());
        System.out.println(accessGrant.getRefreshToken());
        System.out.println(accessGrant.getExpireTime());
        System.out.println(accessGrant.getScope());
        System.out.println();

        return userService.kakaoLogin(request, accessGrant);
    }
}
