package com.alpha.alphaapp.ui.register;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.BaseFragment;

/**
 * Created by kenway on 17/5/24 16:39
 * Email : xiaokai090704@126.com
 */

public class PhoneRegisterFragment extends BaseFragment {

    private EditText et_accout, et_pw, et_insurepw, et_verify,et_phoneVerify;
    private Button btn_register;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_phone;
    }

    @Override
    protected void initViews(View root) {
        et_accout = (EditText)root.findViewById(R.id.register_frag_et_phone);
        et_pw = (EditText) root.findViewById(R.id.register_frag_et_pw);
        et_insurepw = (EditText) root.findViewById(R.id.register_frag_et_insurepw);
        et_verify = (EditText) root.findViewById(R.id.register_frag_et_verifycode);
        et_phoneVerify= (EditText) root.findViewById(R.id.register_frag_et_phoneverifycode);
        btn_register = (Button) root.findViewById(R.id.register_btn_register);
    }

    @Override
    protected void initEnvent() {

    }

    @Override
    protected void initData() {

    }
}
