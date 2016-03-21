package lexico;

public class Lexico {

    //atributos
    String fuente;
    int ind;
    boolean continua;
    char c;
    int estado;

    public String simbolo;
    public int tipo;

    public Lexico() {
        ind = 0;
        simbolo = "";
    }

    public Lexico(String fuente) {
        ind = 0;
        this.fuente = fuente;
        simbolo = "";
    }

    public void entrada(String fuente) {
        ind = 0;
        this.fuente = fuente;

    }//fin del metodo entrada

    public void siguienteSimbolo() {
        int estadoActual, opcion;
        continua = true;
        simbolo = "";

        estadoActual = 0;
        estado = estadoActual;


        while (continua) {
            c = siguienteCaracter();

            tipo = palabraReservada(c);

            if (tipo != -1)
                return;

            if (esLetra(c))
                opcion = 0;
            else if (esDigito(c))
                opcion = 1;
            else if (c == '.')
                opcion = 2;
            else if (c == '"')
                opcion = 3;
            else if (c == '+' || c == '-')
                opcion = 4;
            else if (c == '*' || c == '/')
                opcion = 5;
            else if (c == '<' || c == '>')
                opcion = 6;
            else if (c == '|')
                opcion = 7;
            else if (c == '&')
                opcion = 8;
            else if (c == '!')
                opcion = 9;
            else if (c == '=')
                opcion = 10;
            else if (c == ';')
                opcion = 11;
            else if (c == ',')
                opcion = 12;
            else if (c == '(')
                opcion = 13;
            else if (c == ')')
                opcion = 14;
            else if (c == '{')
                opcion = 15;
            else if (c == '}')
                opcion = 16;
            else if (c == '$')
                opcion = 17;
            else if (esEspacio(c))
                opcion = 18;
            else
                opcion = 19;


            estadoActual = estado;
            estado = Estados.tablaEstados[estado][opcion];
            simbolo += c;

            if (Estados.ACP == estado) {
                establecerTipo(estadoActual);
                //System.out.println(simbolo.replace('$', ' '));
                if (esEspacio(c)) {
                    simbolo = simbolo.substring(0, simbolo.length() - 1);
                    break;
                }

                retroceso();
                break;
            } else if (Estados.ERR == estado) {
                tipo = TipoSimbolo.ERROR;

                if (estadoActual == 2 || estadoActual == 4 || estadoActual == 11 || estadoActual == 13) {
                    retroceso();
                }

                break;
            }//fin de else if

        }//fin de while

        if (estado == Estados.ACP && estadoActual == 0)
            siguienteSimbolo();


    }//fin del metodo siguienteSimbolo

    private void establecerTipo(int estadoActual) {
        switch (estadoActual) {
            case 1:
                tipo = TipoSimbolo.ENTERO;
                break;

            case 3:
                tipo = TipoSimbolo.REAL;
                break;

            case 5:
                tipo = TipoSimbolo.CADENA;
                break;

            case 6:
                tipo = TipoSimbolo.IDENTIFICADOR;
                break;

            case 7:
                tipo = TipoSimbolo.opRELAC;
                break;

            case 8:
                tipo = TipoSimbolo.opRELAC;
                break;

            case 9:
                tipo = TipoSimbolo.opSUMA;
                break;

            case 10:
                tipo = TipoSimbolo.opMULT;
                break;

            case 12:
                tipo = TipoSimbolo.opOR;
                break;

            case 14:
                tipo = TipoSimbolo.opAND;
                break;

            case 15:
                tipo = TipoSimbolo.opNOT;
                break;

            case 16:
                tipo = TipoSimbolo.opIGUALDAD;
                break;

            case 17:
                tipo = TipoSimbolo.IGUAL;
                break;

            case 18:
                tipo = TipoSimbolo.opIGUALDAD;
                break;

            case 19:
                tipo = TipoSimbolo.PUNTO_COMA;
                break;

            case 20:
                tipo = TipoSimbolo.COMA;
                break;

            case 21:
                tipo = TipoSimbolo.PARENTESIS_INICIO;
                break;

            case 22:
                tipo = TipoSimbolo.PARENTESIS_FIN;
                break;

            case 23:
                tipo = TipoSimbolo.LLAVE_INICIO;
                break;

            case 24:
                tipo = TipoSimbolo.LLAVE_FIN;
                break;

            case 25:
                tipo = TipoSimbolo.PESOS;
                break;

        }//fin de switch

    }

    public char siguienteCaracter() {
        if (terminado()) {
            return '$';
        }

        return fuente.charAt(ind++);
    }

    public boolean terminado() {
        return ind >= fuente.length();
    }

    private boolean esLetra(char caracter) {
        return Character.isLetter(c);
    }//fin del metodo esLetra

    private boolean esDigito(char caracter) {
        return Character.isDigit(c);
    }//fin del metodo EsDigito

    private boolean esEspacio(char caracter) {
        return caracter == ' ' || caracter == '\t' || caracter == '\n' || caracter == '\r';
    }//fin del metodo esEspacio

    public int siguienteEstado(int estado) {
        return Estados.tablaEstados[estado][estado];
    }//fin del metodo siguienteEstado


    private void retroceso() {
        simbolo = simbolo.substring(0, simbolo.length() - 1);
        if (c != '$') ind--;
        continua = false;
    }//fin dle metodo retroceso

    public String simboloCadena(int tipo) {
        String cad = "";

        switch (tipo) {
            case TipoSimbolo.IDENTIFICADOR:
                cad = "Identificador";
                break;

            case TipoSimbolo.ENTERO:
                cad = "Entero";
                break;

            case TipoSimbolo.REAL:
                cad = "Real";
                break;

            case TipoSimbolo.CADENA:
                cad = "Cadena";
                break;

            case TipoSimbolo.TIPO:
                cad = "Tipo";
                break;

            case TipoSimbolo.opSUMA:
                cad = "Operador Suma";
                break;

            case TipoSimbolo.opMULT:
                cad = "Operador Multiplicacion";
                break;

            case TipoSimbolo.opRELAC:
                cad = "Operador Relacional";
                break;

            case TipoSimbolo.opOR:
                cad = "Operador OR";
                break;

            case TipoSimbolo.opAND:
                cad = "Operador AND";
                break;

            case TipoSimbolo.opNOT:
                cad = "Operador NOT";
                break;

            case TipoSimbolo.opIGUALDAD:
                cad = "Operador Igualdad";
                break;

            case TipoSimbolo.PUNTO_COMA:
                cad = "Punto y Coma";
                break;

            case TipoSimbolo.COMA:
                cad = "Coma";
                break;

            case TipoSimbolo.PARENTESIS_INICIO:
                cad = "Parentesis Inicio";
                break;

            case TipoSimbolo.PARENTESIS_FIN:
                cad = "Parentesis Fin";
                break;

            case TipoSimbolo.LLAVE_INICIO:
                cad = "Llave Inicio";
                break;

            case TipoSimbolo.LLAVE_FIN:
                cad = "Llave Fin";
                break;

            case TipoSimbolo.IGUAL:
                cad = "Operador Asignacion";
                break;

            case TipoSimbolo.IF:
                cad = "Palabra Reservada if";
                break;

            case TipoSimbolo.WHILE:
                cad = "Palabra Reservada while";
                break;

            case TipoSimbolo.RETURN:
                cad = "Palabra Reservada return";
                break;

            case TipoSimbolo.ELSE:
                cad = "Palabra Reservada else";
                break;

            case TipoSimbolo.PESOS:
                cad = "Fin de la entrada";
                break;

            case TipoSimbolo.ERROR:
                cad = "Error";
                break;
        }//fin de switch


        return cad;
    }

    private int palabraReservada(char c) {
        char caracter;
        String palabra;

        if (c != '$') {
            switch (c) {
                case 'i':
                    caracter = siguienteCaracter();

                    if (caracter == 'f') {
                        palabra = String.valueOf(c);
                        palabra += caracter;
                        simbolo = palabra;
                        return TipoSimbolo.IF;
                    } else {
                        ind--;
                        return -1;
                    }

                case 'e':
                    caracter = siguienteCaracter();
                    if (caracter == 'l') {
                        palabra = String.valueOf(c);
                        palabra += caracter;

                        caracter = siguienteCaracter();
                        if (caracter == 's') {
                            palabra += caracter;

                            caracter = siguienteCaracter();

                            if (caracter == 'e') {
                                palabra += caracter;
                                simbolo = palabra;
                                return TipoSimbolo.ELSE;
                            } else {
                                ind = ind - 3;
                                return -1;
                            }
                        } else {
                            ind = ind - 2;
                            return -1;
                        }
                    } else {
                        ind--;
                        return -1;
                    }

                case 'w':
                    caracter = siguienteCaracter();
                    if (caracter == 'h') {
                        palabra = String.valueOf(c);
                        palabra += caracter;

                        caracter = siguienteCaracter();
                        if (caracter == 'i') {
                            palabra += caracter;

                            caracter = siguienteCaracter();

                            if (caracter == 'l') {
                                palabra += caracter;

                                caracter = siguienteCaracter();

                                if (caracter == 'e') {
                                    palabra += caracter;
                                    simbolo = palabra;
                                    return TipoSimbolo.WHILE;
                                } else {
                                    ind = ind - 4;
                                    return -1;
                                }
                            } else {
                                ind = ind - 3;
                                return -1;
                            }
                        } else {
                            ind = ind - 2;
                            return -1;
                        }

                    } else {
                        ind--;
                        return -1;
                    }

                case 'r':
                    caracter = siguienteCaracter();

                    if (caracter == 'e') {
                        palabra = String.valueOf(c);
                        palabra += caracter;

                        caracter = siguienteCaracter();

                        if (caracter == 't') {
                            palabra += caracter;

                            caracter = siguienteCaracter();
                            if (caracter == 'u') {
                                palabra += caracter;

                                caracter = siguienteCaracter();
                                if (caracter == 'r') {
                                    palabra += caracter;

                                    caracter = siguienteCaracter();

                                    if (caracter == 'n') {
                                        palabra += caracter;
                                        simbolo = palabra;
                                        return TipoSimbolo.RETURN;
                                    } else {
                                        ind = ind - 5;
                                        return -1;
                                    }
                                } else {
                                    ind = ind - 4;
                                    return -1;
                                }
                            } else {
                                ind = ind - 3;
                                return -1;
                            }
                        } else {
                            ind = ind - 2;
                            return -1;
                        }
                    } else {
                        ind--;
                        return -1;
                    }
            }//fin de switch

        }//fin de if

        return -1;

    }//fin del metodo palabraReserbada


}//fin de la clase Lexico
