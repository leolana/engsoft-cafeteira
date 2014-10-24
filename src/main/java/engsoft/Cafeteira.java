package engsoft;

/**
 * Esta classe implementa a cafeteira CafeBemBrasileiro.
 * Esta classe  o centro da comunicação o entre o aquecedor, vaporizador e
 * IHC. Ela implementa todas as três interfaces de clientes e direciona
 * as mensagens entre estas abstrações.
 */
public class Cafeteira {
    /** A Cafeteira é implementada por um hardware */
    private Hardware           hardware;
    
    /** 
     * Os atributos estado* representam o estado de cada componente
     * na perspectiva da cafeteira.
     */
    private EstadoIHC          estadoIHC;
    private EstadoVaporizador  estadoVaporizador;
    private EstadoAquecedor    estadoAquecedor;

    /**
     * O construtor garante que cada um dos componentes do hardware <br>
     * é iniciado corretamente. <br>
     * A Interface está em espera (naoFazendo). <br>
     * O Aquecedor está em espera (naoFazendo). <br>
     * O Vaporidador está em espera (naoFazendo). <br>
     * Esta é a transição que ocorre quando a Cafeteira é <br>
     * conectada à energia elétrica. <br>
     * 
     * @param oHardware estabelece o relacionamento entre hardware e cafeteira
     * 
     */
    
    public Cafeteira(Hardware hardware) {
        this.hardware = hardware;
        estadoIHC = EstadoIHC.naoFazendo;
        hardware.atuLuzIndicadora(EstadoHardware.ehIndicadoraDesligada);
        estadoAquecedor = EstadoAquecedor.naoAquecendo;
        hardware.atuElementoAquecedor(EstadoHardware.ehAquecedorDesligado);
        estadoVaporizador = EstadoVaporizador.naoFazendo;
        hardware.atuEstadoElementoEbulidor(EstadoHardware.ehEbulidorDesligado);
        hardware.atuValvulaPressao(EstadoHardware.ehValvulaFechada);
    }
    
    /*
     *  Os três getters a seguir devem ser utilizados exclusivamente
     *  para testes da cafeteira.
     */
    
    public EstadoIHC getEstadoIHC() {
    	return estadoIHC;
    }
    
    public EstadoVaporizador getEstadoVaporizador() {
    	return estadoVaporizador;
    }
    
    public EstadoAquecedor getEstadoAquecedor() {
    	return estadoAquecedor;
    }

    // Eventos (Ações) na abstração da Cafeteira.
    /**
     * cafeFeito <br>
     * 
     *  
     */
    public void cafeFeito() {
        if (estadoIHC.equals(EstadoIHC.verificandoProntidao))
            estadoIHC = EstadoIHC.naoFazendo;
        else if (estadoIHC.equals(EstadoIHC.fazendo)) {
            estadoIHC = EstadoIHC.cafeFeito;
            hardware.atuLuzIndicadora(EstadoHardware.ehIndicadoraLigada);
            hardware.atuValvulaPressao(EstadoHardware.ehValvulaFechada);
        }
        if (estadoAquecedor.equals(EstadoAquecedor.fazendoJarVazia)) {
            estadoAquecedor = EstadoAquecedor.naoAquecendo;
            cicloCompleto();
        } else if (estadoAquecedor.equals(EstadoAquecedor.fazendoAquecendo)) {
            estadoAquecedor = EstadoAquecedor.cafeFeito;
        } else if (estadoAquecedor.equals(EstadoAquecedor.jarVaziaRemovida)) {
            estadoAquecedor = EstadoAquecedor.naoAquecendo;
            cicloCompleto();
        } else if (estadoAquecedor.equals(EstadoAquecedor.jarCheiaRemovida)) {
            estadoAquecedor = EstadoAquecedor.feitoJarRemovida;
        }
    }
    
    public void cicloCompleto() {
        if (estadoIHC.equals(EstadoIHC.verificandoProntidao))
            estadoIHC = EstadoIHC.naoFazendo;
        else if (estadoIHC.equals(EstadoIHC.fazendo))
            estadoIHC = EstadoIHC.naoFazendo;
        else if (estadoIHC.equals(EstadoIHC.cafeFeito)) {
            estadoIHC = EstadoIHC.naoFazendo;
            hardware.atuLuzIndicadora(EstadoHardware.ehIndicadoraDesligada);
            hardware.atuValvulaPressao(EstadoHardware.ehValvulaFechada);
        }
    }

    public void jarra() {
        if (estadoVaporizador.equals(EstadoVaporizador.fazendoJarRemovida)) {
            estadoVaporizador = EstadoVaporizador.vaporizando;
            hardware.atuEstadoElementoEbulidor(EstadoHardware.ehEbulidorLigado);
            hardware.atuValvulaPressao(EstadoHardware.ehValvulaFechada);
        }
    }

    public void semJarra() {
        if (estadoVaporizador.equals(EstadoVaporizador.vaporizando)) {
            estadoVaporizador = EstadoVaporizador.fazendoJarRemovida;
            hardware.atuEstadoElementoEbulidor(EstadoHardware.ehEbulidorDesligado);
            hardware.atuValvulaPressao(EstadoHardware.ehValvulaAberta);
        }
    }

    public void fazerCafe() {
        if (estadoAquecedor.equals(EstadoAquecedor.naoAquecendo)) {
            estadoAquecedor = EstadoAquecedor.fazendoJarVazia;
        }
        if (estadoVaporizador.equals(EstadoVaporizador.naoFazendo)) {
            estadoVaporizador = EstadoVaporizador.vaporizando;
            hardware.atuValvulaPressao(EstadoHardware.ehValvulaFechada);            
            hardware.atuEstadoElementoEbulidor(EstadoHardware.ehEbulidorLigado);
        }
    }
    

    // Eventos da máquina de estados
    public void inicio() {
        if (estadoIHC.equals(EstadoIHC.naoFazendo)) {
            estadoIHC = EstadoIHC.verificandoProntidao;
            if (hardware.leEstadoAquecedor().equals(EstadoHardware.ehJarraVazia)
                    && hardware.leEstadoEbulidor().equals(
                            EstadoHardware.ehEbulidorNaoVazio)) {
                if (estadoIHC.equals(EstadoIHC.verificandoProntidao)) {
                    estadoIHC = EstadoIHC.fazendo;
                    fazerCafe();
                }
            } else {
                if (estadoIHC.equals(EstadoIHC.verificandoProntidao)) {
                    estadoIHC = EstadoIHC.naoFazendo;
                }
            }
        }
    }

    /**
     * <p> vaporizadorVazio </p>
     * 
     * Se o vaporizador já vaporizou toda a água, então---supondo-se que café <br>
     * foi colocado no filtro---haverá café coado na jarra. <br><br>
     * 
     * evento: vaporizadorVazio <br>
     * condição: vaporizando <br>
     * ação: indica fim do ciclo, desliga ebulidor, abre a valvula e indica que o café <br>
     *       está feito. <br>
     *       
     */
    public void vaporizadorVazio() {
        if (estadoVaporizador.equals(EstadoVaporizador.vaporizando)) {
            estadoVaporizador = EstadoVaporizador.naoFazendo;
            hardware.atuEstadoElementoEbulidor(EstadoHardware.ehEbulidorDesligado);
            hardware.atuValvulaPressao(EstadoHardware.ehValvulaAberta);
            cafeFeito();
        } else if (estadoVaporizador.equals(EstadoVaporizador.fazendoJarRemovida)) {
            estadoVaporizador = EstadoVaporizador.naoFazendo;
            cafeFeito();
        } 
    }
    
    public void jarraVazia() {
        if (estadoAquecedor.equals(EstadoAquecedor.fazendoAquecendo)) {
            estadoAquecedor = EstadoAquecedor.fazendoJarVazia;
            hardware.atuElementoAquecedor(EstadoHardware.ehAquecedorDesligado);
        } else if (estadoAquecedor.equals(EstadoAquecedor.jarVaziaRemovida)
                || estadoAquecedor.equals(EstadoAquecedor.jarCheiaRemovida)) {
            estadoAquecedor = EstadoAquecedor.fazendoJarVazia;
            jarra();
        } else if (estadoAquecedor.equals(EstadoAquecedor.cafeFeito)) {
            estadoAquecedor = EstadoAquecedor.naoAquecendo;
            hardware.atuElementoAquecedor(EstadoHardware.ehAquecedorDesligado);
            cicloCompleto();
        } else if (estadoAquecedor.equals(EstadoAquecedor.feitoJarRemovida)) {
            estadoAquecedor = EstadoAquecedor.naoAquecendo;
            cicloCompleto();
        }
    }

    public void jarraNaoVazia() {
        if (estadoAquecedor.equals(EstadoAquecedor.fazendoJarVazia)) {
            estadoAquecedor = EstadoAquecedor.fazendoAquecendo;
            hardware.atuElementoAquecedor(EstadoHardware.ehAquecedorLigado);
        } else if (estadoAquecedor.equals(EstadoAquecedor.jarVaziaRemovida)
                || estadoAquecedor.equals(EstadoAquecedor.jarCheiaRemovida)) {
            estadoAquecedor = EstadoAquecedor.fazendoAquecendo;
            hardware.atuElementoAquecedor(EstadoHardware.ehAquecedorLigado);
            jarra();
        } else if (estadoAquecedor.equals(EstadoAquecedor.feitoJarRemovida)) {
            estadoAquecedor = EstadoAquecedor.cafeFeito;
            hardware.atuElementoAquecedor(EstadoHardware.ehAquecedorLigado);
        }
    }

    public void placaVazia() {
        if (estadoAquecedor.equals(EstadoAquecedor.fazendoJarVazia)) {
            estadoAquecedor = EstadoAquecedor.jarVaziaRemovida;
            semJarra();
        } else if (estadoAquecedor.equals(EstadoAquecedor.fazendoAquecendo)) {
            estadoAquecedor = EstadoAquecedor.jarCheiaRemovida;
            semJarra();
            hardware.atuElementoAquecedor(EstadoHardware.ehAquecedorDesligado);
        } else if (estadoAquecedor.equals(EstadoAquecedor.cafeFeito)) {
            estadoAquecedor = EstadoAquecedor.feitoJarRemovida;
            hardware.atuElementoAquecedor(EstadoHardware.ehAquecedorDesligado);
        }
    }
    

    // Ligação ao programa principal
    public void executa() {
        if ((estadoIHC.equals(EstadoIHC.naoFazendo))
                && (hardware.leEstadoInterruptor().equals(
                        EstadoHardware.ehInterruptorPressionado))) {
            inicio();
        }
        if (!hardware.leEstadoEbulidor().equals(
                EstadoHardware.ehEbulidorNaoVazio)) {
            vaporizadorVazio();
        }
        if (hardware.leEstadoAquecedor().equals(EstadoHardware.ehPlacaVazia)) {
            placaVazia();
        } else if (hardware.leEstadoAquecedor().equals(
                EstadoHardware.ehJarraVazia)) {
            jarraVazia();
        } else if (hardware.leEstadoAquecedor().equals(
                EstadoHardware.ehJarraNaoVazia)) {
            jarraNaoVazia();
        }
    }
    /*
     * InnerEstadoAquecedor is not used by the Cafeteira,
     * instead EstadoAquecedor is used.
     * This class is here only to verify that my MetaClass class
     * works as planned, see RTestCafeteira.
     */

    static class InnerEstadoAquecedor {
        
        // Estados da máquina de estados do aquecedor
        public static final InnerEstadoAquecedor naoAquecendo    =new InnerEstadoAquecedor(0); 
        public static final InnerEstadoAquecedor fazendoJarVazia =new InnerEstadoAquecedor(1); 
        public static final InnerEstadoAquecedor fazendoAquecendo=new InnerEstadoAquecedor(2); 
        public static final InnerEstadoAquecedor jarVaziaRemovida=new InnerEstadoAquecedor(3);
        public static final InnerEstadoAquecedor jarCheiaRemovida=new InnerEstadoAquecedor(4); 
        public static final InnerEstadoAquecedor cafeFeito=       new InnerEstadoAquecedor(5); 
        public static final InnerEstadoAquecedor feitoJarRemovida=new InnerEstadoAquecedor(6); 
        
        private int id;
        
        private InnerEstadoAquecedor(int id) {
            this.id = id;
        }
        
        public InnerEstadoAquecedor getEstadoAquecedor() {
        	return this;
        }

        public boolean equals(Object obj) {
            return (obj != null) && (obj instanceof InnerEstadoAquecedor)
                    && ((InnerEstadoAquecedor) obj).id == id;
        }
    }


    
    
    public static void main(String[] args) {
        // Cria os componentes da cafeteira
        // StandardHardware hardware = new StandardHardware();
    	Hardware hardware = new Hardware(false);
        Cafeteira cafeteira = new Cafeteira(hardware);

        //////////////////////////////////////////////////////////////////
        // Executa a cafeteira

        // Inicia o hardware
        hardware.iniciar();

        // Executa o software de controle
        while (true) {
            cafeteira.executa();
        }
    }
}

