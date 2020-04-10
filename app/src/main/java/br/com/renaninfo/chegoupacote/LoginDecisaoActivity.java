package br.com.renaninfo.chegoupacote;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.renaninfo.chegoupacote.util.Preferencias;
import br.com.renaninfo.chegoupacote.vo.UsuarioLogado;

public class LoginDecisaoActivity extends AppCompatActivity {

    public static final String EXTRA_LOGIN_TYPE = "loginType";
    public static final String LOGIN_TYPE_ENTRAR = "ENTRAR";
    public static final String LOGIN_TYPE_CRIAR = "CRIAR";

    private Button btnLoginDecisaoJaTenhoConta;
    private Button btnLoginDecisaoCriarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_decisao);

        btnLoginDecisaoJaTenhoConta = (Button) findViewById(R.id.btnLoginDecisaoJaTenhoConta);
        btnLoginDecisaoCriarConta = (Button) findViewById(R.id.btnLoginDecisaoCriarConta);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            getUsuarioFromPreferences();
            if (UsuarioLogado.getInstance().isNivelUsuario()) {
                Intent returnIntent = new Intent(this, UsuarioActivity.class);
                returnIntent.putExtra("result", "Success");
//            setResult(MainActivity.REQUEST_CODE_LOGIN_SUCCESS,returnIntent);
                Toast.makeText(LoginDecisaoActivity.this, "Destruir LoginDecisao activity", Toast.LENGTH_LONG ).show();
                finish();
                startActivity(returnIntent);
            } else if (UsuarioLogado.getInstance().isNivelAdmin()) {
                Intent returnIntent = new Intent(this, AdminActivity.class);
                returnIntent.putExtra("result", "Success");
//            setResult(MainActivity.REQUEST_CODE_LOGIN_SUCCESS,returnIntent);
                Toast.makeText(LoginDecisaoActivity.this, "Destruir LoginDecisao activity", Toast.LENGTH_LONG ).show();
                finish();
                startActivity(returnIntent);
            } else if (UsuarioLogado.getInstance().isNivelEntregador()) {
                Intent returnIntent = new Intent(this, EntregadorActivity.class);
                returnIntent.putExtra("result", "Success");
//            setResult(MainActivity.REQUEST_CODE_LOGIN_SUCCESS,returnIntent);
                Toast.makeText(LoginDecisaoActivity.this, "Destruir LoginDecisao activity", Toast.LENGTH_LONG ).show();
                finish();
                startActivity(returnIntent);
            }
        }
    }

    private void getUsuarioFromPreferences() {
        Preferencias preferencias = new Preferencias(LoginDecisaoActivity.this);
        preferencias.loadUsuarioLogado();
    }

    public void btnLoginDecisaoJaTenhoContaOnClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(EXTRA_LOGIN_TYPE, LOGIN_TYPE_ENTRAR);
        startActivity(intent);
    }

    public void btnLoginDecisaoCriarContaOnClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(EXTRA_LOGIN_TYPE, LOGIN_TYPE_CRIAR);
        startActivity(intent);
    }

}
