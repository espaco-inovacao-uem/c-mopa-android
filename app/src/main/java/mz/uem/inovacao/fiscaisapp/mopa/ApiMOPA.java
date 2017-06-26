package mz.uem.inovacao.fiscaisapp.mopa;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mz.uem.inovacao.fiscaisapp.entities.Ocorrencia;

/**
 * Responsavel por fazer requisicoes à API do MOPA. A API do MOPA usa
 */

public class ApiMOPA {

    private Context context;
    private static final String MOPA_REQUESTS_JSON_URL = "http://www.mopa.co.mz/georeport/v2/requests.json";

    public ApiMOPA(Context context) {
        this.context = context;
    }

    public void fetchOcorrencias(OccorrenciasResponseListener listener) {

        makeRequestOcorrencias(listener);
    }

    private String mopaUrlWithStartDate() {

        DateTime dateTime = DateTime.now();
        DateTime startDate = dateTime.minusDays(7);

        String startDateString = startDate.toString();
        String urlResult = MOPA_REQUESTS_JSON_URL + "?start_date=" + startDateString;

        Log.v("MOPA", startDateString);
        return urlResult;
    }

    /**
     *
     */
    private void makeRequestOcorrencias(final OccorrenciasResponseListener responseListener) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, mopaUrlWithStartDate(),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(context, "MOPA request success",
                                Toast.LENGTH_LONG).show();
                        ArrayList<Ocorrencia> ocorrencias = extractOcorrencias(response);
                        responseListener.onSuccess(ocorrencias);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "MOPA request FAILURE:" + error.getMessage(),
                        Toast.LENGTH_LONG).show();

                error.printStackTrace();
                /*Log.v("Volley", error.getMessage());
                Log.v("Volley", error.networkResponse.statusCode + "");*/

                responseListener.onError();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 5, 2));

        Toast.makeText(context, "Starting MOPA request",
                Toast.LENGTH_LONG).show();

        requestQueue.add(stringRequest);

    }

    private ArrayList<Ocorrencia> extractOcorrencias(String requestResponse) {

        ArrayList<Ocorrencia> ocorrencias = new ArrayList<>();

        try {
            JSONArray arrayOcorrencias = new JSONArray(requestResponse);

            Toast.makeText(context, "Fetched objects" + arrayOcorrencias.length(),
                    Toast.LENGTH_LONG).show();

            for (int i = 0; i < arrayOcorrencias.length(); i++) {

                JSONObject ocorrenciasObject = arrayOcorrencias.getJSONObject(i);

                String id = ocorrenciasObject.getString(MOPA_ID);
                String estado = ocorrenciasObject.getString(MOPA_ESTADO);
                String categoria = ocorrenciasObject.getString(MOPA_CATEGORIA);
                String descricao = ocorrenciasObject.getString(MOPA_DESCRICAO);
                String bairro = ocorrenciasObject.getString(MOPA_BAIRRO);
                String dataCriacao = ocorrenciasObject.getString(MOPA_DATA_CRIACAO);
                String dataUpdate = ocorrenciasObject.getString(MOPA_DATA_UPTADE);

                Log.v("Ocorrencia", id + "-" + estado + "-" + categoria + "-" + descricao + "-" +
                        bairro + "-" + dataCriacao + "-" + dataUpdate);

            }
        } catch (JSONException e) {

            Toast.makeText(context, "JSON Exception",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return ocorrencias;
    }

    private static final String MOPA_ID = "service_request_id";
    private static final String MOPA_ESTADO = "service_notice";//Em processo, Resolvido,
    // Inv\u00e1lido,Reaberto,Arquivado
    private static final String MOPA_ESTADO_NOTAS = "status_notes";//resposta dada pelo conselho municipal.
    private static final String MOPA_DESCRICAO = "description";
    private static final String MOPA_BAIRRO = "neighbourhood";
    private static final String MOPA_CATEGORIA = "service_name";//Contentor esta cheio, Vala...
    private static final String MOPA_DATA_CRIACAO = "requested_datetime";
    private static final String MOPA_DATA_UPTADE = "updated_datetime";
    private static final String MOPA_LATITUDE = "lat";
    private static final String MOPA_LONGITUDE = "long";


    private static final String MOPA_CODIGO_SERVICO = "service_code";


}
