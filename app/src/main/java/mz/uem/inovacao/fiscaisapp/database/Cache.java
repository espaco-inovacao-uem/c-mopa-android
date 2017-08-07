package mz.uem.inovacao.fiscaisapp.database;

import mz.uem.inovacao.fiscaisapp.entities.Alocacao;
import mz.uem.inovacao.fiscaisapp.entities.Equipa;
import mz.uem.inovacao.fiscaisapp.entities.Fiscal;
import mz.uem.inovacao.fiscaisapp.entities.Pedido;
import mz.uem.inovacao.fiscaisapp.entities.User;

/**
 *
 */

public class Cache {

    public static User user;
    public static Fiscal fiscal;
    public static Equipa equipa;
    public static Pedido pedidoValidacao;
    public static Alocacao alocacao;

    public static void clearAll(){

        user = null;
        fiscal = null;
        equipa = null;
        pedidoValidacao = null;
        alocacao = null;
    }
}
