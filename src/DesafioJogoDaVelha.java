import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class DesafioJogoDaVelha {
    private Map<String, String> matrizJogo = new HashMap<>();
    private int jogador = 1;
    private String ultimaJogada = null;
    private Object[][] valores = new Object[3][3];

    public void iniciar() {
        int jogadorVencedor = 0;
        while (jogadorVencedor == 0) {
            String simbolo = (jogador == 1) ? "X" : "O";
            String mensagem = JOptionPane.showInputDialog("Jogador: " + jogador + " Simbolo: " + simbolo + "\nColuna e Linha (separe por ,): ");
            if (!validarJogada(mensagem)) {
                JOptionPane.showMessageDialog(null, "Jogada Inválida!");
                continue;
            }
            realizarJogada(mensagem);
            mostrarTabela();
            jogadorVencedor = pegarVencedor();
            jogador = (jogador == 1) ? 2 : 1;
        }
        mostrarVencedor(jogadorVencedor);
    }

    public boolean validarJogada(String posicao) {
        try {
            String[] splitPosicao = posicao.split(",");
            int coluna = Integer.parseInt(splitPosicao[0]);
            int linha = Integer.parseInt(splitPosicao[1]);
            if (coluna < 1 || coluna > 3 || linha < 1 || linha > 3) {
                return false;
            }
            if (matrizJogo.get(posicao) != null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void realizarJogada(String posicao) {
        ultimaJogada = posicao;
        if (jogador == 1) {
            matrizJogo.put(posicao, "X");
        } else {
            matrizJogo.put(posicao, "O");
        }
    }

    public int pegarVencedor() {
        int countColuna = 0, countLinha = 0;
        String simboloUltimaJogada = matrizJogo.get(ultimaJogada);
        String[] splitUltimaJogada = ultimaJogada.split(",");
        int colunaUltimaJogada = Integer.parseInt(splitUltimaJogada[0]);
        int linhaUltimaJogada = Integer.parseInt(splitUltimaJogada[1]);

        for (String posicao : matrizJogo.keySet()) {
            String[] splitPosicao = posicao.split(",");
            int coluna = Integer.parseInt(splitPosicao[0]);
            int linha = Integer.parseInt(splitPosicao[1]);

            if (colunaUltimaJogada == coluna && matrizJogo.get(posicao).equals(simboloUltimaJogada)) {
                countColuna += 1;
            }

            if (countColuna == 3) {
                return jogador;
            }

            if (linhaUltimaJogada == linha && matrizJogo.get(posicao).equals(simboloUltimaJogada)) {
                countLinha += 1;
            }

            if (countLinha == 3) {
                return jogador;
            }

            if (matrizJogo.getOrDefault("1,1", "-").equals(simboloUltimaJogada) && matrizJogo.getOrDefault("2,2", "-").equals(simboloUltimaJogada) && matrizJogo.getOrDefault("3,3", "-").equals(simboloUltimaJogada)) {
                return jogador;
            }

            if (matrizJogo.getOrDefault("3,1", "-").equals(simboloUltimaJogada) && matrizJogo.getOrDefault("1,3", "-").equals(simboloUltimaJogada) && matrizJogo.getOrDefault("2,2", "-").equals(simboloUltimaJogada)) {
                return jogador;
            }
        }
        if (matrizJogo.size() == 9) {
            return 3;
        }
        return 0;
    }

    public void mostrarVencedor(int jogadorVencedor) {
        switch (jogadorVencedor) {
            case 1 -> {
                JOptionPane.showMessageDialog(null, "Jogador 1 vencedor");
            }
            case 2 -> {
                JOptionPane.showMessageDialog(null, "Jogador 2 vencedor");
            }
            case 3 -> {
                JOptionPane.showMessageDialog(null, "Empate");
            }
        }
    }

    public void mostrarTabela() {
        String[] colunas = {"", "", ""};
        for (String posicao : matrizJogo.keySet()) {
            String[] splitPosicao = posicao.split(",");
            int coluna = Integer.parseInt(splitPosicao[0]) - 1;
            int linha = Integer.parseInt(splitPosicao[1]) - 1;

            valores[linha][coluna] = matrizJogo.get(posicao);
        }
        JTable table = new JTable(valores, colunas);
        table.setTableHeader(null);

        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.setDefaultRenderer(table.getColumnClass(i), dtcr);
        }

        JOptionPane.showMessageDialog(null, new JScrollPane(table));
    }

    public static void main(String[] args) {
        DesafioJogoDaVelha JogoDaVelha = new DesafioJogoDaVelha();
        JogoDaVelha.iniciar();
    }
}

