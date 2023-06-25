package com.example.demo.domain.user.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MUser {
	
	private String userId;
	
	private String password;
	
	private String userName;
	
	private Date birthday;
	
	private Integer age;
	
	private Integer gender;
	
	private Integer departmentId;
	
	private String role;
	
	private Department department;
	
	private List<Salary> salaryList;
	
	/**
	 * CSV文字列の作成
	 * @return
	 */
    public String toCsv() {
    	
        String genderStr = null;
        
        if (gender == 1) {
            genderStr = "男性";
        } else {
            genderStr = "女性";
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String csv = userId + ", " + userName + ", " + sdf.format(birthday) + ", " + age + ", " + genderStr + "\r\n";
        
        return csv;
        
    }

}
