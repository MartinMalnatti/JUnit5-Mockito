package junit5;


import junit5.models.Banco;
import junit5.models.Cuenta;
import junit5.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CuentaTest {

    Cuenta cuenta;

    @BeforeEach
    void  initMetodoTest (){
        this.cuenta = new Cuenta ("Ramon", new BigDecimal("1000.12345"));
        System.out.println("iniciando metodo...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("metodo de prueba finalizado");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("inicializando el test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("finalizando el test");
    }

    @Test
    @DisplayName("Nombre cuenta")
    void testNombreCuenta(){
        Cuenta cuenta = new Cuenta();
        cuenta.setPersona("Martin");
        String esperado = "Martin";
        String real = cuenta.getPersona();
        /**
         * se utiliza expresion lambda para crear el mensaje de error
         * esto hacer que si el test falla se cree el objeto String si no no
         */
        assertNotNull(real,()->"la cuenta no puede ser nula");
        assertEquals(esperado,real, ()->"el nombre de la cuenta no es que se esperaba");
        assertTrue(real.equals("Martin"), ()->"nombre de cuenta esperada debe ser igual a la real");
    }

    @Test
    @DisplayName("Testeando saldo en cuenta")
    void testSaldoCuenta(){
        cuenta = new Cuenta("Martin", new BigDecimal("1000.4444"));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.4444,cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO)<0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO)>0);
    }

    @Test
    @DisplayName("Testeando diferencias en cuenta")
    void testReferenciaCuenta(){
        cuenta = new Cuenta("John Rambo", new BigDecimal("8989.5454"));
        Cuenta cuenta2 = new Cuenta ("John Rambo", new BigDecimal("8989.5454"));
        assertEquals(cuenta,cuenta2);
    }

    @Test
    @DisplayName("Testeando debito en cuenta")
    void testDebitoCuenta(){
        cuenta.debito(new BigDecimal(10));
        assertNotNull(cuenta.getSaldo());
        assertEquals(990,cuenta.getSaldo().intValue());
    }

    @Test
    @DisplayName("Testeando credito en cuenta")
    void testCreditoCuenta(){
        cuenta.credito(new BigDecimal(10));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1010.0,cuenta.getSaldo().intValue());
    }

    @Test
    @DisplayName("Testeando  exception")
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
    @DisplayName("Testeando transferencias")
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
    @Disabled
    @DisplayName("Testeando relaciuon banco cuenta")
    void testRelacionBancoCuenta() {
        fail();
        Cuenta cuenta1= new Cuenta("Jhon Rambito", new BigDecimal( "2500"));
        Cuenta cuenta2= new Cuenta("Ramon Valdés", new BigDecimal( "1500"));

        Banco banco = new Banco();
        banco.setNombre("Columbus");
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        banco.transferir(cuenta1,cuenta2,new BigDecimal(500));
        assertAll(()->{
                assertEquals(2,banco.getCuentas().size(),()-> "la cantidad de cuentas no coincide");
            },()->{
                assertEquals("Ramon Valdés",banco.getCuentas().stream().filter(
                                c -> c.getPersona().equals("Ramon Valdés"))
                        .findFirst()
                        .get()
                        .getPersona(),()->"la cuenta no pertence a Ramon Valdés");
            },()->{
                assertEquals("Columbus",cuenta1.getBanco().getNombre(),()->"la cuenta no pertenece" +
                        "al banco");
            },()->{
                assertTrue(banco.getCuentas().stream().
                        filter(cuenta -> cuenta.getPersona().equals("Ramon Valdés")).
                        findFirst()
                        .isPresent(),()->"la cuenta no pertence a Ramon Valdés");
            },()->{
                assertTrue(banco.getCuentas().stream().anyMatch(
                        cuenta -> cuenta.getPersona().equals("Ramon Valdés")
                ));
            });
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSoloWindows() {
    }

    @Test
    @EnabledOnOs({OS.LINUX,OS.MAC})
    void testSoloLinuxMac() {
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void name() {
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void soloJdk8(){
    }

    @Test
    @DisabledOnJre(JRE.JAVA_15)
    void testNoJdk15(){
    }

    @Test
    void imprimirProperties() {
        Properties properties = System.getProperties();
        properties.forEach((k,v)-> System.out.println(k + ":" + v));
    }

    @Test
    @EnabledIfSystemProperty(named = "java.version",matches = "21")
    void testJavaVersion() {
    }

    @Test
    @EnabledIfSystemProperty(named = "ENV", matches = "dev")
    void testDev() {
    }

    @Test
    void imprimirVariablesAmbiente() {
        Map<String, String> getenv = System.getenv();
        getenv.forEach((k,v)-> System.out.println(k + "=" + v));
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "JAVA_HOME",matches = ".*jdk-21.*")
    void testJavaHome() {
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENVIROMENT",matches = "dev")
    void tesDev() {
    }


}