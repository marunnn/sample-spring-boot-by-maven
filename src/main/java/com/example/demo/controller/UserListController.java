package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.application.service.UserApplicationService;
import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.form.UserListForm;

@Controller
@RequestMapping("/user")
public class UserListController {
	
	@Autowired
	private UserService userService;	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private UserApplicationService appService;
	
	/*
	 * ユーザー一覧画面を表示
	 */
	@GetMapping("/list")
	public String getUserList(@ModelAttribute UserListForm form, Model model) {
		
		// formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);
		
		// ユーザー検索
		List<MUser> userList = userService.getUsers(user);
		
		// Modelに登録
		model.addAttribute("userList", userList);
		
		// ユーザー一覧画面を表示
		return "user/list";
		
	}
	
	/*
	 * ユーザー検索処理
	 */
	@PostMapping("/list")
	public String postUserList(@ModelAttribute UserListForm form, Model model) {
		
		// formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);
		
		// ユーザー検索
		List<MUser> userList = userService.getUsers(user);
		
		// Modelに登録
		model.addAttribute("userList", userList);
		
		// ユーザー一覧画面を表示
		return "user/list";
		
	}
	
	/**
	 * ユーザー一覧ダウンロード処理
	 * @param form
	 * @return
	 * @throws IOException
	 */
    @PostMapping("/list/download")
    public ResponseEntity<byte[]> downloadUserList(@ModelAttribute UserListForm form) throws IOException {
    	
        // formをMUserクラスに変換
        MUser user = modelMapper.map(form, MUser.class);

        // ユーザー検索
        List<MUser> userList = userService.getUsers(user);

        // CSVファイル保存
        String fileName = "user.csv";
        appService.saveUserCsv(userList, fileName);

        // CSVファイル取得
        byte[] bytes = appService.getCsv(fileName);

        HttpHeaders header = new HttpHeaders();

        // HTTPヘッダーの設定
        header.add("Content-Type", MediaType.ALL_VALUE + "; charset=utf-8");
        header.setContentDispositionFormData("filename", fileName);

        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
        
    }

}
