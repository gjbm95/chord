package com.Utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RespuestaUtils {

    /**
     * Aplica el algoritmo de compendio SHA-1 sobre la entrada y retorna su representacion numerica
     * @param entrada Caracteres alfanumericos
     * @return Numero de maximo 60 digitos
     */
    public static BigInteger generarHash (String entrada) throws NoSuchAlgorithmException
    {
        MessageDigest algoritmoCompendio = MessageDigest.getInstance("SHA1");
        algoritmoCompendio.update(StandardCharsets.UTF_8.encode(entrada));

        BigInteger hash = new BigInteger (1, algoritmoCompendio.digest() );
        return hash.mod(BigInteger.valueOf(20));
    }
}
