package com.MS.applications.UnlimitedServicesDriver.Utils;

import android.support.annotation.NonNull;

import com.MS.applications.UnlimitedServicesDriver.Models.APIResponse;
import com.MS.applications.UnlimitedServicesDriver.Models.PagedAPIResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static com.MS.applications.UnlimitedServicesDriver.Utils.AppConstants.DRIVER_TYPE;

public class JsonParser<T> {

    private static Type getType(final Class<?> rawClass, final Class<?> parameterClass) {
        return new ParameterizedType() {
            @NonNull
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{parameterClass};
            }

            @NonNull
            @Override
            public Type getRawType() {
                return rawClass;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }

        };
    }

    public static <T> APIResponse<T> getResponse(String response, final Class<T> dataClass) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        try {
            return gson.fromJson(response, getType(APIResponse.class, dataClass));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> PagedAPIResponse<T> getPagedResponse(String response, final Class<T> dataClass) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        try {
            return gson.fromJson(response, getType(PagedAPIResponse.class, dataClass));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getLoginParams(String email, String password, String FCM_Token) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("username", email);
            jsonParam.put("password", password);
            return jsonParam;
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject getResetPassParams(String email) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("email", email);
            return jsonParam;
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject getResetParams(String email, String pass, String code) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("email", email);
            jsonParam.put("reset_code", code);
            jsonParam.put("new_password", pass);
            return jsonParam;
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject getRegisterParams(String email, String password, String lastName, String phone, int MainRegionId) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Email", email);
            jsonParam.put("Password", password);
            jsonParam.put("Name", lastName);
            jsonParam.put("Phone", phone);
            jsonParam.put("MainRegionId", MainRegionId);
            return jsonParam;
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject getVerifyParams(int id, String email, String code) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("id", id);
            jsonParam.put("email", email);
            jsonParam.put("code", code);
            return jsonParam;
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject getResendCodeParams(String email) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("email", email);
            return jsonParam;
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject getTokenParam(String token) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("token", token);
            return jsonParam;
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject getItemsParams(String token, int limit) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("limit", limit);
            jsonParam.put("token", token);
            return jsonParam;
        } catch (Exception e) {
            return null;
        }
    }
}