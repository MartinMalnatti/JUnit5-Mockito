package junit5;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta(){
        Cuenta cuenta = new Cuenta();
        cuenta.setPersona("Martin");
        String esperado = "Martin";
        String real = cuenta.getPersona();
        assertNotNull(real);
        assertEquals(esperado,real);
    }

    @Test
    void saldoCuenta(){
        Cuenta cuenta = new Cuenta("Martin", new BigDecimal("1000.4444"));
        assertEquals(1000.4444,cuenta.getSaldo().doubleValue());

    }

    @Test
    void testReferenciaCuenta(){
        Cuenta cuenta = new Cuenta("John Rambo", new BigDecimal("8989.5454"));
        Cuenta cuenta2 = new Cuenta ("John Rambo", new BigDecimal("8989.5454"));
        assertEquals(cuenta,cuenta2);
    }

    @Test
    void testDeitoCuenta(){
        Cuenta cuenta = new Cuenta ("John Rambo", new BigDecimal(100));
        cuenta.debito(new BigDecimal(10));
        assertNotNull(cuenta.getSaldo());
        assertEquals(90,cuenta.getSaldo().intValue());
    }


}