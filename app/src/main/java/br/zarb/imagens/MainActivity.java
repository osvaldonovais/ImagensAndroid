package br.zarb.imagens;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.renderscript.Int3;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

    private List<Imagem> listaImagens;

    int posicaoSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listaImagens = new ArrayList<>();

        File diretorioCartaoMemoria = Environment.getExternalStorageDirectory();

        buscarImagens(diretorioCartaoMemoria, listaImagens);

        if (listaImagens.isEmpty()) {
            Toast.makeText(this, "Nenhuma imagem encontrada", Toast.LENGTH_LONG).show();
        } else {

            setListAdapter(new ListaImagensAdapter(listaImagens));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ListAdapter adapter = getListAdapter();
        Imagem imagemSelecionada = (Imagem) getListAdapter().getItem(position);
        Intent it = new Intent(this,ImagemTelaCheiaActivity.class);
        it.putExtra("pathImagem",imagemSelecionada.getPath());

        posicaoSelecionada = position;

        startActivityForResult(it, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK && data != null && data.hasExtra("gostou")) {
                    boolean gostou = data.getBooleanExtra("gostou", false);
                    Imagem imagemSelecionada = (Imagem) getListAdapter().getItem(posicaoSelecionada);
                    imagemSelecionada.setLike(gostou);

                    setListAdapter(new ListaImagensAdapter(listaImagens));
                }
                break;
        }
    }

    private void buscarImagens(File f, List<Imagem> listaImagens) {
        File[] lista = f.listFiles();
        if (lista != null) {
            for (File file : lista) {
                if (file.isDirectory()) {
                    buscarImagens(file, listaImagens);
                } else {
                    if (file.getName().contains(".jpg") || file.getName().contains(".png") || file.getName().contains(".jpeg")) {
                        Imagem imagem = new Imagem();
                        imagem.setPath(file.getAbsolutePath());
                        imagem.setNome(file.getName());

                        listaImagens.add(imagem);

                    }
                }
            }
        }
    }

    private class ListaImagensAdapter extends BaseAdapter {

        private List<Imagem> listaImagens;

        public ListaImagensAdapter(List<Imagem> listaImagens) {
            this.listaImagens = listaImagens;
        }

        @Override
        public int getCount() {
            return listaImagens.size();
        }

        @Override
        public Object getItem(int position) {
            return listaImagens.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout layout = new LinearLayout(MainActivity.this);
            layout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView imageView = new ImageView(MainActivity.this);
            TextView txtNome = new TextView(MainActivity.this);
            ImageView imgLike = new ImageView(MainActivity.this);

            txtNome.setText(listaImagens.get(position).getNome());

            imageView.setImageBitmap(ImagemUtil.getImagemAjustada(new File(listaImagens.get(position).getPath()),200,200));
            imageView.setLayoutParams(new ViewGroup.LayoutParams(200,200));

            if (listaImagens.get(position).getLike() != null) {
                if (listaImagens.get(position).getLike()) {
                    imgLike.setImageResource(R.drawable.like);
                } else {
                    imgLike.setImageResource(R.drawable.dislike);
                }
            }

            layout.addView(imageView);
            layout.addView(txtNome);
            layout.addView(imgLike);

            return layout;
        }
    }

}
