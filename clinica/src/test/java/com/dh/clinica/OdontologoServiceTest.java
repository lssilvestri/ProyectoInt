//package com.dh.clinica;
//import com.dh.clinica.dao.impl.OdontologoDaoH2;
//import com.dh.clinica.db.H2Connection;
//import com.dh.clinica.entity.Odontologo;
//import com.dh.clinica.service.odontologo.OdontologoService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.platform.commons.logging.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;
//import java.util.ArrayList;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class OdontologoServiceTest {
//    static Logger logger = LoggerFactory.getLogger(OdontologoServiceTest.class);
//    OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());
//
//    @BeforeAll
//    static void tablas(){
//        H2Connection.crearTablas();
//    }
//
//    @Test
//    @DisplayName("Testear que un odontologo se guarde en la base de datos")
//    void caso1(){
//        Odontologo odontologo = new Odontologo("asdaw","Luciana", "Gimenez");
//        Odontologo odontologo1 = odontologoService.guardarOdontologo(odontologo);
//        assertNotNull(odontologo1.getId());
//    }
//
//    @Test
//    @DisplayName("Testear que se buscan todos los odontologos")
//    void caso2(){
//        List<Odontologo> odontologos = new ArrayList<>();
//        odontologos = odontologoService.buscarTodos();
//        assertTrue(odontologos.size()!=0);
//    }
//}