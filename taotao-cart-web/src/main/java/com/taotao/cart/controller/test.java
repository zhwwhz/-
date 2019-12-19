package com.taotao.cart.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.sso.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        list.add("dd");

        for(String  rs : list)
        {
            if(rs.equals("aa") || rs.equals("bb") || rs.equals("cc"))
            {
                System.out.println(rs);
                break;
            }
        }
    }
}
