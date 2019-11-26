package com.example.aigentestapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.aigentestapp.R;
import com.example.aigentestapp.utils.Constants;
import com.example.aigentestapp.utils.DialogUtil;
import com.example.aigentestapp.utils.Session;
import com.example.aigentestapp.utils.UtilPermission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.sign_in_btn)
    Button signInBtn;

    @BindView(R.id.emailEdtTxt)
    EditText emailEdtTxt;

    @BindView(R.id.pwdEdtTxt)
    EditText pwdEdtTxt;

    private UtilPermission utilPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        emailEdtTxt.setText(Constants.EMAILID);
        pwdEdtTxt.setText(Constants.PASSWORd);
        if (Session.GetInstance(this).IsLogin()) {

            Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(mainIntent);
            finish();
        } else {
            RequestPremission(new UtilPermission(this, new UtilPermission.PermissionCallBack() {
                @Override
                public void HavePermission() {
                    DialogUtil.showWhoopsDialog(LoginActivity.this, "HavePermission");

                }

                @Override
                public void NotAcceptPermission() {
                    DialogUtil.showWhoopsDialog(LoginActivity.this, "NotAcceptPermission");
                }

                @Override
                public void NotHavePermission() {
                    DialogUtil.showWhoopsDialog(LoginActivity.this, "NotHavePermission");
                    OpenPermissionDialog();
                }
            }, new String[]{Manifest.permission.CAMERA}));
        }
    }
    public void RequestPremission(UtilPermission utilPermission) {
        this.utilPermission = utilPermission;
        this.utilPermission.CheckPemission();
    }

    @OnClick(R.id.sign_in_btn)
    public void submit() {
        Session.GetInstance(LoginActivity.this).createLoginSession(emailEdtTxt.getText().toString(), pwdEdtTxt.getText().toString());
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }
    public void OpenPermissionDialog() {
        this.utilPermission.ShowPermissionDialog();
    }
}
