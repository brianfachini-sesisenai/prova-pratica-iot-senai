package br.com.senai;

import org.eclipse.paho.client.mqttv3.*; // Importação da biblioteca Paho MQTT

public class App {

    public static void main(String[] args) {
        
        // ==========================================
        // PARÂMETROS DE CONEXÃO E REDE
        // ==========================================
        String broker = "tcp://broker.hivemq.com:1883"; // Endereço do broker público
        String clientId = "JavaBackend_Brian_Fachini";  // Identificador único de cliente (Segurança/Rastreabilidade)
        String topico = "senai/brian/motor/telemetria"; // Tópico idêntico ao configurado no C++

        try {
            // Instancia o cliente MQTT com o endereço do broker e o ID do cliente
            MqttClient client = new MqttClient(broker, clientId);
            
            // Estabelece a conexão do backend Java com o broker MQTT
            client.connect();
            System.out.println("Backend conectado ao Broker MQTT com sucesso!");
            System.out.println("Aguardando telemetria dos motores no tópico: " + topico);

            // ==========================================
            // SUBSCRIÇÃO E RECEBIMENTO DE DADOS
            // ==========================================
            // Inscreve-se no tópico para escutar as mensagens publicadas pelo ESP32.
            // Utiliza-se uma expressão Lambda (topic, message) para processar os dados recebidos.
            client.subscribe(topico, (topic, message) -> {
                
                // Converte a carga útil (payload) recebida de um array de bytes para String
                String dadosRecebidos = new String(message.getPayload());
                
                // Exibe a mensagem de validação exigida seguida dos dados formatados (CSV)
                System.out.println("Dados de Telemetria Coletados com Sucesso: [" + dadosRecebidos + "]");
                
            });

        } catch (MqttException e) {
            // Captura e exibe eventuais erros de rede ou de conexão com o broker
            System.err.println("Falha na comunicação MQTT: " + e.getMessage());
            e.printStackTrace();
        }
    }
}