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
import java.util.Date;

import mz.uem.inovacao.fiscaisapp.entities.Categoria;
import mz.uem.inovacao.fiscaisapp.entities.Contentor;
import mz.uem.inovacao.fiscaisapp.entities.Distrito;
import mz.uem.inovacao.fiscaisapp.entities.Ocorrencia;

/**
 * Responsavel por fazer requisicoes à API do MOPA. A API do MOPA usa
 */

public class ApiMOPA {

    private Context context;
    private static final String MOPA_REQUESTS_JSON_URL = "http://www.mopa.co.mz/georeport/v2/requests.json";
    private static final String MOPA_LOCATIONS_JSON_URL = "http://www.mopa.co.mz/georeport/v2/locations.json";
    private static final String MOPA_CATEGORIES_JSON_URL = "http://www.mopa.co.mz/georeport/v2/services.json";

    private RequestQueue requestQueue;

    public ApiMOPA(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchOcorrencias(OccorrenciasResponseListener listener) {

        makeRequestOcorrencias(listener);
    }

    public void fetchCategories(CategoriasResponseListener listener) {

        makeRequestCategories(listener);
    }

    public void fetchDistritos(DistritosResponseListener listener) {

        makeRequestDistritos(listener);

    }

    public void fetchContentores(String bairro, MOPAResponseListener<Contentor> listener){

        makeRequestContentores(bairro, listener);
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

        StringRequest stringRequest = new StringRequest(Request.Method.GET, mopaUrlWithStartDate(),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        ArrayList<Ocorrencia> ocorrencias = extractOcorrencias(response);
                        responseListener.onSuccess(ocorrencias);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                /*Log.v("Volley", error.getMessage());
                Log.v("Volley", error.networkResponse.statusCode + "");*/

                responseListener.onError();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 5, 2));

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

                String idMOPA = ocorrenciasObject.getString(MOPA_ID);
                String estado = ocorrenciasObject.getString(MOPA_ESTADO);
                String categoria = ocorrenciasObject.getString(MOPA_CATEGORIA);
                String descricao = ocorrenciasObject.getString(MOPA_DESCRICAO);
                String bairro = ocorrenciasObject.getString(MOPA_BAIRRO);
                String dataCriacao = ocorrenciasObject.getString(MOPA_DATA_CRIACAO);
                String dataUpdate = ocorrenciasObject.getString(MOPA_DATA_UPTADE);

                Log.v("Ocorrencia", idMOPA + "-" + estado + "-" + categoria + "-" + descricao + "-" +
                        bairro + "-" + dataCriacao + "-" + dataUpdate);

                Ocorrencia ocorrencia = new Ocorrencia(1, idMOPA, null, bairro, categoria, null, null,
                        dataCriacao, descricao, estado, null);

                ocorrencias.add(ocorrencia);

            }
        } catch (JSONException e) {

            Toast.makeText(context, "JSON Exception",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return ocorrencias;
    }

    private void makeRequestCategories(final CategoriasResponseListener listener) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MOPA_CATEGORIES_JSON_URL + "?",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        ArrayList<Categoria> categorias = extractCategories(response);

                        listener.onSuccess(categorias);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        requestQueue.add(stringRequest);
    }

    private ArrayList<Categoria> extractCategories(String requestResponse) {

        ArrayList<Categoria> categorias = new ArrayList<>();

        try {
            JSONArray arrayCategorias = new JSONArray(requestResponse);

            for (int i = 0; i < arrayCategorias.length(); i++) {

                JSONObject categoriaObject = arrayCategorias.getJSONObject(i);

                JSONArray keywords = categoriaObject.getJSONArray("keywords");

                boolean containsPeriurban = false;

                if (keywords.length() == 2) {//um deles é periurban
                    containsPeriurban = true;

                } else {

                    if (keywords.getString(0).equals("periurban")) {

                        containsPeriurban = true;
                    }
                }

                if (containsPeriurban) {
                    String id = categoriaObject.getString("service_code");
                    String nome = categoriaObject.getString("service_name");

                    boolean requiresContentor = false;

                    int serviceCode = Integer.parseInt(id);

                    if (serviceCode == 1 || serviceCode == 2 || serviceCode == 3) {
                        requiresContentor = true;
                    }

                    Log.d("Categoria", id + " - " + nome);

                    Categoria categoria = new Categoria(id, nome, requiresContentor);
                    categorias.add(categoria);
                }
            }
            return categorias;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    private void makeRequestDistritos(final DistritosResponseListener listener) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                MOPA_LOCATIONS_JSON_URL + "?type=district", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                ArrayList<Distrito> distritos = extractDistritos(response);
                listener.onSuccess(distritos);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                error.printStackTrace();
                listener.onError();
            }

        });

        requestQueue.add(stringRequest);
    }

    private ArrayList<Distrito> extractDistritos(String requestResponse) {

        ArrayList<Distrito> distritos = new ArrayList<>();

        try {
            JSONArray arrayDistritos = new JSONArray(requestResponse);

            for (int i = 0; i < arrayDistritos.length(); i++) {

                JSONObject distritoObject = arrayDistritos.getJSONObject(i);

                String id = distritoObject.getString("location_id");
                String name = distritoObject.getString("location_name");
                JSONArray arrayBairros = distritoObject.getJSONArray("neighbourhood");

                ArrayList<String> bairros = new ArrayList<>();

                for (int j = 0; j < arrayBairros.length(); j++) {

                    bairros.add(arrayBairros.getString(j));
                }

                distritos.add(new Distrito(Integer.valueOf(id), name, bairros));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return distritos;
    }

    private void makeRequestContentores(String bairro, final MOPAResponseListener<Contentor> listener) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                MOPA_LOCATIONS_JSON_URL + "?type=container&neighbourhood=" + bairro,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        ArrayList<Contentor> contentores = extractContentores(response);
                        listener.onSuccess(contentores);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                listener.onError();
            }
        });

        requestQueue.add(stringRequest);
    }

    private ArrayList<Contentor> extractContentores(String requestResponse){

        ArrayList<Contentor> contentores = new ArrayList<>();

        try {
            JSONArray arrayContentores = new JSONArray(requestResponse);

            for (int i = 0; i < arrayContentores.length(); i++) {

                JSONObject contentorObject = arrayContentores.getJSONObject(i);

                String id = contentorObject.getString("location_id");
                String name = contentorObject.getString("location_name");

                contentores.add(new Contentor(id,name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contentores;
    }

    public static final String MOPA_ID = "service_request_id";
    public static final String MOPA_ESTADO = "service_notice";//Em processo, Resolvido,
    // Inv\u00e1lido,Reaberto,Arquivado
    public static final String MOPA_ESTADO_NOTAS = "status_notes";//resposta dada pelo conselho municipal.
    public static final String MOPA_DESCRICAO = "description";
    public static final String MOPA_BAIRRO = "neighbourhood";
    public static final String MOPA_CATEGORIA = "service_name";//Contentor esta cheio, Vala...
    public static final String MOPA_DATA_CRIACAO = "requested_datetime";
    public static final String MOPA_DATA_UPTADE = "updated_datetime";
    public static final String MOPA_LATITUDE = "lat";
    public static final String MOPA_LONGITUDE = "long";

    public static final String MOPA_CODIGO_SERVICO = "service_code";

    //    public static final String MOPA_LOCATION_

    /**
     * Mopa Response Listener interfaces
     */
    public interface OccorrenciasResponseListener {

        void onSuccess(ArrayList<Ocorrencia> ocorrencias);

        void onError();
    }

    public interface CategoriasResponseListener {

        void onSuccess(ArrayList<Categoria> categorias);

        void onError();
    }

    public interface DistritosResponseListener {

        void onSuccess(ArrayList<Distrito> distritos);

        void onError();
    }

    public interface ContentoresResponseListener {

        void onSuccess(ArrayList<Contentor> contentores);

        void onError();
    }

    public interface MOPAResponseListener<E> {

        void onSuccess(ArrayList<E> objectos);

        void onError();
    }

}
