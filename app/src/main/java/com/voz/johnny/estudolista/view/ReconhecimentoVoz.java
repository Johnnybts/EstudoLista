package com.voz.johnny.estudolista.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.voz.johnny.estudolista.R;
import com.voz.johnny.estudolista.controller.ChavesController;
import com.voz.johnny.estudolista.model.Chave;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;


public class ReconhecimentoVoz extends Activity {


    //verificar se precisa do wakelock
    private static final String TAG = ReconhecimentoVoz.class.getName();
    private SpeechRecognizer reconhecedor;
    private Handler mHandler = new Handler();
    TextView resposta;
    Intent reconhecedorIntent;

    String[] gramatica = new String[10];
    boolean finalizador = false;

    private static final String[] GRAMATICA = {
            "teste",
            "validar",
            "bom dia",
            "Estou Seguro"
    };
    private static final int GRAMATICA_SIZE = GRAMATICA.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconhecimentovoz);

        resposta = (TextView) findViewById(R.id.txtResposta);


    }




    @Override
    protected void onStart(){
        resposta.setText("Start");


        reconhecedor = SpeechRecognizer.createSpeechRecognizer(ReconhecimentoVoz.this);
        SpeechListener reconhecedorListener = new SpeechListener();
        reconhecedor.setRecognitionListener(reconhecedorListener);

        reconhecedorIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        reconhecedorIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getApplication().getPackageName());
        reconhecedorIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        reconhecedorIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 20);
        reconhecedorIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        reconhecedor.startListening(reconhecedorIntent);
        resposta.setText("Start2");
        super.onStart();
    }


    @Override
    protected void onPause() {
        //kill the voice recognizer
        if(reconhecedor != null){
            reconhecedor.destroy();
            reconhecedor = null;

        }
        super.onPause();
    }

    private void processCommand(ArrayList<String> matchStrings){
        String response = "Comando desconhecido";
        int maxStrings = matchStrings.size();
      /*  boolean resultFound = false;
        for(int i =0; i < GRAMATICA_SIZE && !resultFound;i++){
            for(int j=0; j < maxStrings && !resultFound; j++){
                if(StringUtils.getLevenshteinDistance(matchStrings.get(j), GRAMATICA[i]) <(GRAMATICA[i].length() / 3) ){
                    response = "Comando Reconhecido!";
                }
            }
        }*/

        /*ArrayList<String> arr = getIntent().getExtras().getStringArrayList("nova_gramatica");*/

        List<Chave> arr = new ChavesController(this).somente_chaves();


        if(arr.size()>0){
            int x = 0;

            for(Chave ch : arr){

                String chavetxt = ch.getChave();
                gramatica[x] = chavetxt;
                Log.d(TAG, "getchave "+chavetxt+x);
                Log.d(TAG, "gramatica "+chavetxt+x);

                x = x + 1;

                /*item.setOnLongClickListener(new RetrieveListener());*/
            }
        }

        //verificar o if se for < 0



        //alterei as variaveis de gramatica
        boolean resultado_encontrado = false;
        for (int i = 0; i < maxStrings; i++) {
            for (int j = 0; j < arr.size(); j++) {
                if (StringUtils.stripAccents(matchStrings.get(i)).equalsIgnoreCase(StringUtils.stripAccents(gramatica[j]))){

                    Log.d(TAG, "Falados:" + StringUtils.stripAccents(matchStrings.get(i)) + " Lista: " + gramatica[j]);

                    resultado_encontrado = true;
                    break;


                } else {

                }



            }
        }

        if(resultado_encontrado){
            response = "Resultado Encontrado";
        }

        final String finalResponse = response;
        mHandler.post(new Runnable() {
            public void run() {
                resposta.setText(finalResponse);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }


    /*private String getResponse(int command){
        Calendar c = Calendar.getInstance();

        String retString =  "I'm sorry, Dave. I'm afraid I can't do that.";
        SimpleDateFormat dfDate_day;
        switch (command) {
            case 0:
                dfDate_day= new SimpleDateFormat("HH:mm:ss");
                retString = "The time is " + dfDate_day.format(c.getTime());
                break;
            case 1:
                dfDate_day = new SimpleDateFormat("dd/MM/yyyy");
                retString= " Today is " + dfDate_day.format(c.getTime());
                break;
            case 2:
                retString = "My name is R.A.L. - Responsive Android Language program";
                break;

            case 4:
                finalizador = true;
                finish();
                break;
            default:
                break;
        }
        return retString;
    }*/

    class SpeechListener  implements RecognitionListener {
        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "buffer recieved ");
        }
        public void onError(int error) {
            //if critical error then exit
            if(error == SpeechRecognizer.ERROR_CLIENT || error == SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS){
                Log.d(TAG, "client error" + error );
            }
            //else ask to repeats
            else{
                Log.d(TAG, "other error");
                reconhecedor.startListening(reconhecedorIntent);
            }
        }
        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent");
        }
        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "partial results");
        }
        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "on ready for speech");
        }
        public void onResults(Bundle results) {
            Log.d(TAG, "on results");
            ArrayList<String> matches = null;
            if(results != null){
                matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(matches != null){
                    Log.d(TAG, "results are " + matches.toString());
                    final ArrayList<String> matchesStrings = matches;
                    processCommand(matchesStrings);
                    if(!finalizador)
                        reconhecedor.startListening(reconhecedorIntent);
                    else
                        finish();

                }
            }

        }
        public void onRmsChanged(float rmsdB) {
            //			Log.d(TAG, "rms changed");
        }
        public void onBeginningOfSpeech() {
            Log.d(TAG, "speach begining");
        }
        public void onEndOfSpeech() {
            Log.d(TAG, "speach done");
        }

    };



}

