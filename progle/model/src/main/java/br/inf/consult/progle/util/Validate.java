package br.inf.consult.progle.util;

public class Validate {

    public static boolean verificarSenhasIguais(String senha, String novaSenha) {
        return senha != null && senha.equals(novaSenha);
    }
}
