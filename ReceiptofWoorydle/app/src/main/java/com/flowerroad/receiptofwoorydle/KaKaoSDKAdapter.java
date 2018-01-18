package com.flowerroad.receiptofwoorydle;

import android.content.Context;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;

/**
 * Created by gywls on 2018-01-16.
 */
/*요기서는 카카오톡에서 어떤 정보를 가져와서 이 정보에 대한 처리를 담당하는 곳이다.

그리고 Adapter를 만들기 위해 클래스를 하나 더 만들어서 KakaoAdpater를 extends하여 구현을 시작한다.*/
public class KaKaoSDKAdapter extends KakaoAdapter {
    @Override
    public ISessionConfig getSessionConfig() {
        return new ISessionConfig() {
            @Override
            public AuthType[] getAuthTypes() {
                return new AuthType[]{
                        AuthType.KAKAO_LOGIN_ALL
                };
            }
            @Override
            public boolean isUsingWebviewTimer() {
                return false;
            }
            @Override
            public boolean isSecureMode() {
                return false;
            }
            @Override
            public ApprovalType getApprovalType() {
                return ApprovalType.INDIVIDUAL;
            }
            @Override
            public boolean isSaveFormData() {
                return false;
            }
        };
    }
    @Override
    public IApplicationConfig getApplicationConfig() {
        return new IApplicationConfig() {
            @Override
            public Context getApplicationContext() {
                return GlobalApplication.getGlobalApplicationContext();
            }
        };
       /*우리는 카카오톡 로그인 정보를 가져오기 때문에 AuthType은 KAKAO_LOGIN_ALL 이며
로그인 한 정보를 안드로이드에 쿠키(?)를 남겨두고 싶다면 isSaveFromData를 return true; 로 해주고 아니라면
return false; 로 해준다.*/
    }
}