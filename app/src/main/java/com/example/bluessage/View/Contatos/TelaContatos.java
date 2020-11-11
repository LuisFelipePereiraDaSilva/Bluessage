package com.example.bluessage.View.Contatos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.bluessage.R;
import com.example.bluessage.View.Contatos.ContatosNaoPareados.TelaContatosNaoPareados;
import com.example.bluessage.View.Contatos.ContatosPareados.TelaContatosPareados;
import com.example.bluessage.View.MenuTresPontos.MenuTresPontos;

public class TelaContatos extends AppCompatActivity {

    public static TelaContatos telaContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);
        telaContatos = this;

        getSupportActionBar().setTitle("Contatos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPager = (ViewPager) findViewById(R.id.pagerContatos);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (MenuTresPontos.abrirTelaSelecionada(TelaContatos.this, item.getItemId(), -1))
            return true;

        return super.onOptionsItemSelected(item);
    }

    //region Botoes Menu

    public void montarBarraBotaoSelecionado(View layoutBotao){
        /*
        <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="6dp"
                android:background="@drawable/botao_sobra"
                android:layout_marginLeft="10dp"
                android:layout_marginRight="10dp"
                />
        * */

        LinearLayout.LayoutParams tamanho_barra = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);

        tamanho_barra.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        tamanho_barra.height = 20;
        tamanho_barra.leftMargin = 10;
        tamanho_barra.rightMargin = 10;

        LinearLayout barra = new LinearLayout(this);
        barra.setLayoutParams(tamanho_barra);
        barra.setBackgroundResource(R.drawable.botao_sombra);
        barra.setId(0);

        LinearLayout linearLayoutBotao = (LinearLayout) layoutBotao;
        linearLayoutBotao.addView(barra);
    }
    public void removerBarra(View layoutBotao){
        LinearLayout linearLayoutBotao = (LinearLayout) layoutBotao;
        View view = linearLayoutBotao.findViewById(0);
        if(view != null)
            linearLayoutBotao.removeView(view);
    }
    public void ajustarBarraBotaoSelecionado(){
        try {
            int i = mPager.getCurrentItem();
            if(indexTelaAberta != i) {
                indexTelaAberta = i;
                removerBarra(findViewById(R.id.linearLayoutBotaoContatosSalvos));
                removerBarra(findViewById(R.id.linearLayoutBotaoContatosNovos));
                if (i == 0)
                    montarBarraBotaoSelecionado(findViewById(R.id.linearLayoutBotaoContatosSalvos));
                else if (i == 1)
                    montarBarraBotaoSelecionado(findViewById(R.id.linearLayoutBotaoContatosNovos));
            }
        }catch (Exception e){}
    }

    public void clickBotaoContatosSalvos(){
        mPager.setCurrentItem(0);
    }
    public void clickBotaoContatosSalvos(View view){
        clickBotaoContatosSalvos();
    }

    public void clickBotaoContatosNovos(){
        mPager.setCurrentItem(1);
    }
    public void clickBotaoContatosNovos(View view){
        clickBotaoContatosNovos();
    }

    private int indexTelaAberta = -1;
    private static final int NUM_PAGES = 2;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0)
                return new TelaContatosPareados();
            else if(position == 1)
                return new TelaContatosNaoPareados();

            return new TelaContatosPareados();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            ajustarBarraBotaoSelecionado();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

    //endregion
}

