package com.example.shopping.storage;

public class Constants {
    public static final String PREFERENCE_NAME = "com.example.SHOPPING";
    public static final String PRODUCT="product";
    public  static class LoginTable
    {
        public static final String EMAIL = "email";
        public static final String REMEMBER_ME = "remember_me";
        public static final String ID ="id" ;
    }
    public static class SignUpTable {
        public static final String TABLE_NAME = "SIGN_UPS";
        public static final String SIGN_UP_ID = "SIGN_UP_ID";
        public static final String SIGN_UP_NAME = "SIGN_UP_NAME";
        public static final String SIGN_UP_PASSWORD = "SIGN_UP_PASSWORD";
        public static final String SIGN_UP_EMAIL = "SIGN_UP_EMAIL";
    }

    public static class ProductTable{
        public static final String TABLE_NAME = "PRODUCTS";
        public static final String PRODUCT_ID = "PRODUCT_ID";
        public static final String PRODUCT_NAME = "PRODUCT_NAME";
        public static final String PRODUCT_QUANTITIES="PRODUCT_QUANTITIES";
        public static final String PRODUCT_COST="PRODUCT_COST";
        public static final String SIGN_UP_ID="Sign_Up_Id";
    }
}
