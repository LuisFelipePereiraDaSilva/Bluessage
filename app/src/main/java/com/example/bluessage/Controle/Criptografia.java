package com.example.bluessage.Controle;

public class Criptografia {

    private final static int size = 255;
    private final static int k = 11;

    public static String criptografar(String textoPuro) {
        String textoCriptografado = "";
        char caractere_puro;
        int represent_numerica_caractere;
        char caractere_cifrado;

        for (int i = 0; i < textoPuro.length(); i++){
            caractere_puro = textoPuro.charAt(i);

            represent_numerica_caractere = (int) caractere_puro;
            represent_numerica_caractere = (represent_numerica_caractere + k) % size;

            caractere_cifrado =  (char) (represent_numerica_caractere);

            textoCriptografado += caractere_cifrado;
        }

        return textoCriptografado;
    }

    public static String descriptografar(String textoCifrado) {
        String textoDescriptografado = "";
        char caractere_cifrado;
        int represent_numerica_caractere;
        char caractere_descifrado;

        for (int i = 0; i < textoCifrado.length(); i++){
            caractere_cifrado = textoCifrado.charAt(i);
            represent_numerica_caractere = (int) caractere_cifrado;

            if(represent_numerica_caractere < k) {
                represent_numerica_caractere += size;
            }

            represent_numerica_caractere = (represent_numerica_caractere - k) % size;
            caractere_descifrado =  (char) (represent_numerica_caractere);

            textoDescriptografado += caractere_descifrado;
        }

        return textoDescriptografado;
    }
}
