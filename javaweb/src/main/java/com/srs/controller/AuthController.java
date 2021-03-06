package com.srs.controller;

import com.srs.bind.CurrentUser;
import com.srs.po.SysUser;
import com.srs.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping (value = "auth", produces = { APPLICATION_JSON_UTF8_VALUE})
public class AuthController {

    @Autowired
    AuthService authService;

    @GetMapping (value = "init", produces = { APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity init ( @CurrentUser SysUser user ) throws JSONException {
        return ResponseEntity.ok (authService.init (user ) );
    }

    @PostMapping ("login_success")
    public ResponseEntity loginPage ( ) {
        return ResponseEntity.ok ( ).build ( );
    }

    @GetMapping ("logout_success")
    public ResponseEntity logoutPage ( ) {
        return ResponseEntity.ok ( ).build ( );
    }

    @GetMapping ("login_failure")
    public ResponseEntity loginFailurePage ( ) {
        return ResponseEntity.status (401 ).build ( );
    }

}
