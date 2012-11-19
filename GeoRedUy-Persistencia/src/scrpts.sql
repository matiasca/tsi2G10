/*
-- Query: SELECT * FROM tsi2.categoria
LIMIT 0, 1000

-- Date: 2012-10-14 19:51
*/
INSERT INTO `categoria` (`nombreCategoria`) VALUES ('Deportes');
INSERT INTO `categoria` (`nombreCategoria`) VALUES ('Entretenimiento');
INSERT INTO `categoria` (`nombreCategoria`) VALUES ('Estetica');
INSERT INTO `categoria` (`nombreCategoria`) VALUES ('Gastronomia');
INSERT INTO `categoria` (`nombreCategoria`) VALUES ('Inmuebles');
INSERT INTO `categoria` (`nombreCategoria`) VALUES ('Juegos');
INSERT INTO `categoria` (`nombreCategoria`) VALUES ('Musica');
INSERT INTO `categoria` (`nombreCategoria`) VALUES ('Negocios');
INSERT INTO `categoria` (`nombreCategoria`) VALUES ('Turismo');

INSERT INTO `tiponotificacion` (`nombreNotificacion`) VALUES ('CheckIn');
INSERT INTO `tiponotificacion` (`nombreNotificacion`) VALUES ('Eventos');
INSERT INTO `tiponotificacion` (`nombreNotificacion`) VALUES ('Ofertas');
INSERT INTO `tiponotificacion` (`nombreNotificacion`) VALUES ('Sitios de Interes');

INSERT INTO `administrador` (`Tipo`,`mail`,`contrasenia`,`empresa_nombre`) VALUES ('Aplicacion','admin','1234',NULL);

INSERT INTO `sitiointeres` (`nombre`,`descripcion`,`direccion`,`latitud`,`longitud`) VALUES ('Centenario','El estadio de los uruguayos','Av Dr. Américo Ricaldoni, Montevideo 11600, Uruguay',-34893104,-56152320);
INSERT INTO `sitiointeres` (`nombre`,`descripcion`,`direccion`,`latitud`,`longitud`) VALUES ('FING','EL MEJOR LUGAR DEL MUNDO','Parada 192, Av. Julio Herrera y Reissig, Facultad de Ingeniería, Montevideo 11200, Uruguay',-34918360,-56166336);
INSERT INTO `sitiointeres` (`nombre`,`descripcion`,`direccion`,`latitud`,`longitud`) VALUES ('Maeso','La cancha de la gente','Senda Nelson Landoni, Montevideo, Uruguay',-34917520,-56166208);
INSERT INTO `sitiointeres` (`nombre`,`descripcion`,`direccion`,`latitud`,`longitud`) VALUES ('Panaderia PANES','El pan mas rico','Ing. Eduardo García de Zúñiga, Montevideo 11300, Uruguay',-34918644,-56165176);
INSERT INTO `sitiointeres` (`nombre`,`descripcion`,`direccion`,`latitud`,`longitud`) VALUES ('Tres Cruces','Donde un pueblo se junta','TRES CRUCES, Acevedo Diaz, Terminal de omnibus Tres Cruces, Montevideo 11800, Uruguay',-34893740,-56166312);


INSERT INTO `sitiointeres_categoria` (`SitioInteres_nombre`,`categorias_nombreCategoria`) VALUES ('FING','Entretenimiento');
INSERT INTO `sitiointeres_categoria` (`SitioInteres_nombre`,`categorias_nombreCategoria`) VALUES ('FING','Negocios');
INSERT INTO `sitiointeres_categoria` (`SitioInteres_nombre`,`categorias_nombreCategoria`) VALUES ('FING','Deportes');
INSERT INTO `sitiointeres_categoria` (`SitioInteres_nombre`,`categorias_nombreCategoria`) VALUES ('FING','Juegos');
INSERT INTO `sitiointeres_categoria` (`SitioInteres_nombre`,`categorias_nombreCategoria`) VALUES ('FING','Musica');
INSERT INTO `sitiointeres_categoria` (`SitioInteres_nombre`,`categorias_nombreCategoria`) VALUES ('FING','Inmuebles');
INSERT INTO `sitiointeres_categoria` (`SitioInteres_nombre`,`categorias_nombreCategoria`) VALUES ('Panaderia PANES','Gastronomia');
INSERT INTO `sitiointeres_categoria` (`SitioInteres_nombre`,`categorias_nombreCategoria`) VALUES ('Maeso','Entretenimiento');
INSERT INTO `sitiointeres_categoria` (`SitioInteres_nombre`,`categorias_nombreCategoria`) VALUES ('Maeso','Deportes');
INSERT INTO `sitiointeres_categoria` (`SitioInteres_nombre`,`categorias_nombreCategoria`) VALUES ('Centenario','Entretenimiento');
INSERT INTO `sitiointeres_categoria` (`SitioInteres_nombre`,`categorias_nombreCategoria`) VALUES ('Centenario','Deportes');
INSERT INTO `sitiointeres_categoria` (`SitioInteres_nombre`,`categorias_nombreCategoria`) VALUES ('Tres Cruces','Turismo');

INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('andres@gmail.com','Andres Echevarria',1,'Masculino','1234');
INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('matias@gmail.com','Matias Cabrera',1,'Masculino','1234');
INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('pirotto@gmail.com','Pablo Pirotto',1,'Masculino','1234');
INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('vane@gmail.com','Vanessa Diaz',1,'Femenino','1234');
INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('gabriel@gmail.com','Gabriel Madruga',1,'Masculino','1234');
INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('dahiana@gmail.com','Dahiana Morales',1,'Femenino','1234');
INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('emilio@gmail.com','Emilio Orsi',1,'Masculino','1234');
INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('carlos@gmail.com','Carlos Rodriguez',1,'Masculino','1234');
INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('mauricio@gmail.com','Mauricio Bouza',1,'Masculino','1234');
INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('beatriz@gmail.com','Beatriz Fernandez',1,'Femenino','1234');
INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('victoria@gmail.com','Victoria Lavella',1,'Femenino','1234');
INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('emiliano@gmail.com','Emiliano Alonzo',1,'Masculino','1234');
INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('fernando.peralta@gmail.com','Fernando Peralta',1,'Masculino','1234');
INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('fernandopuntoperaltao@gmail.com','Fernando Peralta',1,'Masculino','1234');
INSERT INTO `usuario` (`mail`,`nombre`,`registrado`,`sexo`,`token`) VALUES ('falonzo@gmail.com','Fernando Alonzo',1,'Masculino','1234');

INSERT INTO `checkin` (`id`,`comentario`,`fecha`,`foto_id`,`sitio_nombre`,`usuario_mail`) VALUES (1,'esto esta buenaso','2012-10-28 17:26:16',NULL,'Centenario','vane@gmail.com');
INSERT INTO `checkin` (`id`,`comentario`,`fecha`,`foto_id`,`sitio_nombre`,`usuario_mail`) VALUES (2,'estoy podrida','2012-10-28 17:26:40',NULL,'FING','vane@gmail.com');
INSERT INTO `checkin` (`id`,`comentario`,`fecha`,`foto_id`,`sitio_nombre`,`usuario_mail`) VALUES (4,'q bizcochos','2012-10-28 17:29:09',NULL,'Panaderia PANES','vane@gmail.com');

INSERT INTO `usuario_checkin` (`Usuario_mail`,`checksIn_id`) VALUES ('vane@gmail.com',1);
INSERT INTO `usuario_checkin` (`Usuario_mail`,`checksIn_id`) VALUES ('vane@gmail.com',2);
INSERT INTO `usuario_checkin` (`Usuario_mail`,`checksIn_id`) VALUES ('vane@gmail.com',4);
