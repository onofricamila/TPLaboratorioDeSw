# Laboratorio de Software | Trabajo Final Cursada 2018

## Proyecto: Juego CEDICA Razas y Pelajes

### Introducción

Tomando como punto de partida la vigencia y beneficios que provee la utilización
de TIC (Tecnologías de la Información y la Comunicación) en los procesos
terapéuticos y de enseñanza y aprendizaje, el objetivo de este proyecto es poner a
disposición de las alumnas y alumnos que desarrollan TACAs en [CEDICA](https://www.facebook.com/cedica.equitacionparatodos/), un recurso tecnológico novedoso que colabore y potencie su progreso terapéutico.
En la cursada 2018 de Laboratorio de Software se desarrollarán como parte del
trabajo final de la materia un juego didáctico en Android para tablets y celulares, que
se utilizará en CEDICA para favorecer los procesos terapéuticos y de enseñanza y
aprendizaje de las alumnas y alumnos que concurren al centro.
El juego a desarrollar han sido diseñado en conjunto entre los profesores y JTPs
de “Laboratorio de Software” y el equipo terapéutico de CEDICA a cargo del
Psicomotricista Lucas Fernández, con la colaboración de Máximo (Pit) Bibiloni en el
arte del juego.

### JUEGO de Razas y Pelajes

El objetivo general de este juego es favorecer el reconocimiento e identificación de
las razas y pelajes del caballo que se utiliza en la sesión o clase de CEDICA.

### Metodología

```
El trabajo final se podrá desarrollar en forma individual o en grupo de 2
estudiantes. Dependiendo de la cantidad de integrantes será la complejidad del juego,
esto se traduce en niveles del juego a implementar. A cada grupo se le asignará un
docente que lo guiará durante el proceso de desarrollo del juego.
En el trabajo final desarrollado en forma individual, la o el estudiante deberá
trabajar con las ​ razas y pelajes ​, mientras que en la modalidad grupal las o los
estudiantes desarrollarán el trabajo contemplando las ​ cruzas ​.
La cátedra proveerá de la especificación de la dinámica del juego, un maquetado,
los assets (imágenes, audios, íconos) y el logo del juego.
Fechas de entrega: 12/12/2018 (tercera evaluación parcial) se entregará una
primera versión del juego de acuerdo al alcance indicado por la cátedra y  20/2/2019 se
entregará la versión final.
```
### Dinámica del juego

### Es un juego de un único jugador y cuenta con 2 modos: Reconocimiento y  Juego.

**Modo reconocimiento**
El objetivo de este modo es que las amazonas^2 y jinetes^3 puedan distinguir su caballo
y el de sus compañeras y compañeros.
Se trata de una instancia de reconocimiento de razas, pelajes y cruzas.
Las amazonas y jinetes pueden optar por saltear este modo.
Se implementará como una galería de imágenes, audios y textos, que pueden filtrarse
de acuerdo a las siguientes categorías: razas, pelajes y cruzas. Estos filtros se
establecerán en la pantalla de configuración y de esta manera permite adaptar la
galería a las necesidades de cada jugadora o jugador.
La visualización en pantalla de la galería podrá hacerse como grilla o listado (modo
vertical con leyenda). La forma de interacción de la galería consiste en presionar la
imagen del caballo produciendo que ésta se agrande y por audio diga la raza, el pelaje
o cruza.

**Modo Juego**
Consiste de 3 minijuegos
1) minijuego: Razas y Pelajes
Objetivo: identificar razas y pelajes de manera individual.
2) minijuego: Razas y Pelajes juntas
Objetivo: identificar la raza y el pelaje de cada caballo en forma conjunta.
3) minijuego: Cruza
Objetivo: identificar cruzas de razas entre varias opciones, esto es dados dos
caballos, elegir la cruza correcta (en este caso, una única imagen contiene a los
dos caballos).

### Formas de interacción

A continuación se detallan las interacciones que deben implementar los minijuegos
anteriores:
**Interacción A:** dada una imagen, se la debe asociar a una palabra con su audio
mediante la selección entre varias palabras (Minijuego 1 y 2)
**Interacción B:** dada una palabra con su audio, se la debe asociar a una imagen
mediante la selección entre varias imágenes (Minijuego 1 y 2)
**Interacción C:** dada una imagen, se la debe asociar con otra imagen mediante la
selección entre varias imágenes (Minijuego 3)

### Niveles de dificultad

Cada minijuego puede jugarse en los siguientes niveles:
**Nivel 1** ​​: se elige una opción (imagen o palabra con audio) entre 2 disponibles.
**Nivel 2** ​​: se elige una opción entre 4 (imagen o palabra con audio) disponibles.

### Configuración
Debe incluir:

- Modo Reconocimiento
    - Opción de visualización para modo reconocimiento: Grilla o Lista
    - Filtro: Razas , Pelajes y/o cruzas
- Modo Juego
    - Selección del Minijuego: esto incluye la configuración de la Dinámica A, B o C para cada minijuego según corresponda
- Selección de dificultad: Nivel 1 o Nivel 2
- Audio: reproducción de audios con voz femenina o masculina.



