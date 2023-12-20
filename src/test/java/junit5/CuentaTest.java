package junit5;


import junit5.models.Banco;
import junit5.models.Cuenta;
import junit5.exceptions.DineroInsuficienteException;
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
    void testSaldoCuenta(){
        Cuenta cuenta = new Cuenta("Martin", new BigDecimal("1000.4444"));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.4444,cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO)<0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO)>0);
    }

    @Test
    void testReferenciaCuenta(){
        Cuenta cuenta = new Cuenta("John Rambo", new BigDecimal("8989.5454"));
        Cuenta cuenta2 = new Cuenta ("John Rambo", new BigDecimal("8989.5454"));
        assertEquals(cuenta,cuenta2);
    }

    @Test
    void testDebitoCuenta(){
        Cuenta cuenta = new Cuenta ("John Rambo", new BigDecimal(100));
        cuenta.debito(new BigDecimal(10));
        assertNotNull(cuenta.getSaldo());
        assertEquals(90,cuenta.getSaldo().intValue());
    }

    @Test
    void testDineroInsuficienteException(){
        Cuenta cuenta = new Cuenta("Ramon",new BigDecimal("1000.1234"));
        Exception exception = assertThrows(DineroInsuficienteException.class,()->{
            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero insuficiente";
        assertEquals(esperado,actual);
    }

    @Test
    void testTransferirDinceroCuentas() {
        Cuenta cuenta1= new Cuenta("Jhon Rambito", new BigDecimal( "2500"));
        Cuenta cuenta2= new Cuenta("Ramon Valdés", new BigDecimal( "1500"));

        Banco banco = new Banco();
        banco.setNombre("Columbus");
        banco.transferir(cuenta1,cuenta2,new BigDecimal(500));
        assertEquals("2000",cuenta1.getSaldo().toPlainString());
        assertEquals("2000",cuenta2.getSaldo().toPlainString());
    }

    @Test
    void testRelacionBancocuenta() {
        Cuenta cuenta1= new Cuenta("Jhon Rambito", new BigDecimal( "2500"));
        Cuenta cuenta2= new Cuenta("Ramon Valdés", new BigDecimal( "1500"));

        Banco banco = new Banco();
        banco.setNombre("Columbus");
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        assertEquals(2,banco.getCuentas().size());
    }
}