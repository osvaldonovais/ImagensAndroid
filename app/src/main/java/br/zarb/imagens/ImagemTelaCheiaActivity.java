package br.zarb.imagens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;


public class ImagemTelaCheiaActivity extends ActionBarActivity {

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagem_tela_cheia);

        ImageView imageView =(ImageView) findViewById(R.id.imgTelaCheia);
        String path = getIntent().getStringExtra("pathImagem");

        // OBS ao invés de colocar 1024x768, uma opção interessante é detectar o tamanho
        // atual da tela

        bitmap = ImagemUtil.getImagemAjustada(new File(path),800,480);
        imageView.setImageBitmap(bitmap);
    }


    @Override
    protected void onStop() {
        bitmap.recycle();
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_imagem_tela_cheia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent itRetorno = new Intent();

        switch (id) {
            case R.id.gostei:
                itRetorno.putExtra("gostou",true);
                break;
            case R.id.nao_gostei:
                itRetorno.putExtra("gostou",false);
                break;
        }

        setResult(RESULT_OK,itRetorno);
        finish();

        return super.onOptionsItemSelected(item);
    }
}
