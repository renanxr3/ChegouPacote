package br.com.renaninfo.chegoupacote;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.renaninfo.chegoupacote.util.Base64Custom;
import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.util.Constants;
import br.com.renaninfo.chegoupacote.util.Preferencias;
import br.com.renaninfo.chegoupacote.vo.Unidade;
import br.com.renaninfo.chegoupacote.vo.Usuario;
import br.com.renaninfo.chegoupacote.vo.UsuarioLogado;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText txtLogin;
    private EditText txtSenha;
    private EditText txtNome;

    private EditText txtCriarContaSenha;
    private EditText txtCriarContaLogin;
    private EditText txtCriarContaNome;

    private String idUsuario;
    private String nomeUsuario;
    private String emailUsuario;
    private String emailB24Usuario;
    private String senhaUsuario;
    private Usuario.NivelUsuario nivelUsuario; // TODO: USUARIO
    private List<Unidade> unidadesUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent thisIntent = getIntent();
        String loginType = thisIntent.getStringExtra(LoginDecisaoActivity.EXTRA_LOGIN_TYPE);

        if (LoginDecisaoActivity.LOGIN_TYPE_ENTRAR.equals(loginType)) {
            setContentView(R.layout.activity_login);

            txtLogin = (EditText) findViewById(R.id.txtLogin);
            txtSenha = (EditText) findViewById(R.id.txtSenha);

            if (Constants.TESTING) {
                txtLogin.setText("usuario1@gmail.com");
                txtSenha.setText("senha1");
            }
        } else if (LoginDecisaoActivity.LOGIN_TYPE_CRIAR.equals(loginType)) {
            setContentView(R.layout.activity_login_criarconta);

            txtCriarContaNome = (EditText) findViewById(R.id.txtCriarContaNome);
            txtCriarContaLogin = (EditText) findViewById(R.id.txtCriarContaLogin);
            txtCriarContaSenha = (EditText) findViewById(R.id.txtCriarContaSenha);

            if (Constants.TESTING) {
                txtCriarContaNome.setText("Usuario 2");
                txtCriarContaLogin.setText("usuario2@gmail.com");
                txtCriarContaSenha.setText("senha2");
            }
        }

        mAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
    }

    public void btnEntrarOnClick(View view) {
        emailUsuario = txtLogin.getText().toString();
        emailB24Usuario = Base64Custom.codificarBase64(emailUsuario);
        senhaUsuario = txtSenha.getText().toString();

        if (!Constants.TESTING) {
            mAuth.signInWithEmailAndPassword(emailUsuario, senhaUsuario).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(MainActivity.TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                    if (task.isSuccessful()) {
                        Log.d(MainActivity.TAG, "Login success", task.getException());
                        getUsuarioOnDb();
                    } else {
                        Log.d(MainActivity.TAG, "Login failed", task.getException());

                        String erro = "";
                        try{
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            erro = "Não existe conta com os dados informados.";
                        } catch (FirebaseAuthInvalidCredentialsException  e) {
                            erro = "Usuário ou senha inválido.";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(LoginActivity.this, "Erro ao fazer login: " + erro, Toast.LENGTH_LONG ).show();
                    }
                }
            });
        }
    }

    private void openUsuarioActivity() {
        Intent returnIntent = new Intent(this, UsuarioActivity.class);
        returnIntent.putExtra("result", "Success");
//            setResult(MainActivity.REQUEST_CODE_LOGIN_SUCCESS,returnIntent);
        Toast.makeText(LoginActivity.this, "Destruir LoginDecisao activity", Toast.LENGTH_LONG ).show();
        finish();
        startActivity(returnIntent);
    }

    public void btnCriarContaOnClick(View view) {
        nomeUsuario = txtCriarContaNome.getText().toString();
        emailUsuario = txtCriarContaLogin.getText().toString();
        emailB24Usuario = Base64Custom.codificarBase64(emailUsuario);
        senhaUsuario = txtCriarContaSenha.getText().toString();

        mAuth.createUserWithEmailAndPassword(emailUsuario, senhaUsuario)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(MainActivity.TAG, "Usuário criado com sucesso!", task.getException());
                    nivelUsuario = Usuario.NivelUsuario.ADMIN;
                    unidadesUsuario = new ArrayList<Unidade>();
                    createUsuarioOnDb();
                    saveUsuarioOnPreferences();
                    setUsuarioLogado();
                    openUsuarioActivity();
                } else {
                    Log.d(MainActivity.TAG, "Ocorreu um erroao criar conta de usuário", task.getException());

                    String erro = "";
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Escolha uma senha que contenha, letras e números.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Email indicado não é válido.";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Já existe uma conta com esse e-mail.";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, "Erro ao cadastrar usuário: " + erro, Toast.LENGTH_LONG ).show();
                }
                }
            });
    }

    private void setUsuarioLogado() {
        Usuario usuarioLogado = UsuarioLogado.getInstance();

        usuarioLogado.setId(ConfiguracaoFirebase.getFirebaseAutenticacao().getCurrentUser().getUid());
        usuarioLogado.setEmail(emailUsuario);
        usuarioLogado.setNome(nomeUsuario);
        usuarioLogado.setUnidades(unidadesUsuario);
        usuarioLogado.setNivel(nivelUsuario);
    }

    private void createUsuarioOnDb() {
        DatabaseReference db = ConfiguracaoFirebase.getFirebase();
        FirebaseUser user = ConfiguracaoFirebase.getFirebaseAutenticacao().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            db.child("usuarios").child(uid).child("nome").setValue(nomeUsuario);
            db.child("usuarios").child(uid).child("email").setValue(emailUsuario);
            db.child("usuarios").child(uid).child("nivel").setValue(nivelUsuario);
            db.child("usuarios").child(uid).child("unidades");
            // db.child("usuarios").child(uid).child("login").setValue(emailB24Usuario);

            Calendar calendar = Calendar.getInstance();
            db.child("usuarios").child(uid).child("data_criacao").child("dia").setValue(calendar.get(Calendar.DAY_OF_MONTH));
            db.child("usuarios").child(uid).child("data_criacao").child("mes").setValue(calendar.get(Calendar.MONTH));
            db.child("usuarios").child(uid).child("data_criacao").child("ano").setValue(calendar.get(Calendar.YEAR));
            db.child("usuarios").child(uid).child("data_criacao").child("timestamp").setValue(calendar.getTimeInMillis());
        } else {
            Log.d(MainActivity.TAG, "Usuário do Firebase é null");
        }
    }

    private void getUsuarioOnDb() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();

            DatabaseReference db = ConfiguracaoFirebase.getFirebase().child("usuarios").child(uid); // .child("nome")
            db.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);

                        idUsuario = usuario.getId();
                        nomeUsuario = usuario.getNome();
                        emailUsuario = usuario.getEmail();
                        unidadesUsuario = usuario.getUnidades();
                        nivelUsuario = usuario.getNivel();

                        saveUsuarioOnPreferences();
                        setUsuarioLogado();
                        openUsuarioActivity();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(MainActivity.TAG, "Erro obter nome do usuário");
                        Toast.makeText(LoginActivity.this, "Erro obter nome do usuário", Toast.LENGTH_LONG ).show();
                    }
                }
            );
        }
    }

    private void saveUsuarioOnPreferences() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            boolean emailVerified = user.isEmailVerified();
            String uid = user.getUid();
            Log.d(MainActivity.TAG, "name: " + name);
            Log.d(MainActivity.TAG, "email: " + email);
            Log.d(MainActivity.TAG, "protoUrl: " + photoUrl);
            Log.d(MainActivity.TAG, "uid" + uid);
            Log.d(MainActivity.TAG, "token" + user.getToken(false));

            Preferencias preferencias = new Preferencias(LoginActivity.this);
            preferencias.salvarDados(uid, emailUsuario, emailB24Usuario, nomeUsuario, nivelUsuario, unidadesUsuario);
        } else {
            Intent returnIntent = new Intent();
            setResult(MainActivity.RESULT_CANCELED, returnIntent);
            finish();
        }
    }
}
